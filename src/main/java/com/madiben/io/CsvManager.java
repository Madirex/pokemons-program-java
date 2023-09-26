package com.madiben.io;

import com.madiben.controller.PokemonController;
import com.madiben.dto.PokemonDataDTO;
import com.madiben.models.Pokemon;
import com.madiben.utils.StringConverters;
import com.madiben.utils.Utils;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * Clase CsvManager que administra la exportación e importación de datos CSV
 */
public class CsvManager {

    private static CsvManager csvManagerInstance;

    /**
     * Constructor privado para evitar la creación de instancia
     * SINGLETON
     */
    private CsvManager() {
    }

    /**
     * Obtiene la instancia de CsvManager
     * SINGLETON
     * @return Instancia de CsvManager
     */
    public static CsvManager getInstance() {
        if (csvManagerInstance == null) {
            csvManagerInstance = new CsvManager();
        }
        return csvManagerInstance;
    }

    /**
     * Lee un archivo CSV y lo convierte en un Optional de la lista de PokemonDataDTO
     * @param path Ruta del archivo CSV
     * @return Optional de la lista de PokemonDataDTO
     */
    public Optional<List<PokemonDataDTO>> fileToPokemonDataDTO(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return Optional.of(reader.lines()
                    .map(line -> line.split(","))
                    .skip(1)
                    .map(values -> PokemonDataDTO.builder()
                                .num(values[1])
                                .name(values[2])
                                .height(StringConverters.getInstance().strPositiveValToDoubleParser(values[3]).orElse(0.0))
                                .weight(StringConverters.getInstance().strPositiveValToDoubleParser(values[4]).orElse(0.0))
                                .build()
                    )
                    .toList());
        } catch (IOException e) {
            Utils.print("Error al leer el archivo CSV");
        }
        return Optional.empty();
    }

    /**
     * Exporta los datos de los Pokémon a un archivo CSV
     */
    public void exportPokemonDataToCSV() {
        PokemonController pokemonController = PokemonController.getInstance();
        pokemonController.loadPokedex();
        List<Pokemon> pokemonList = pokemonController.getPokedex().getPokemon();
        createOutFolderIfNotExists();
        try (CSVWriter writer = new CSVWriter(new FileWriter("out" + File.separator + "pokemon_data.csv"))) {
            writer.writeNext(new String[]{"id", "num", "name", "height", "weight"});
            for (Pokemon pokemon : pokemonList) {
                String[] rowData = {
                        String.valueOf(pokemon.getId()),
                        pokemon.getNum(),
                        pokemon.getName(),
                        String.valueOf(pokemon.getHeight()),
                        String.valueOf(pokemon.getWeight())
                };
                writer.writeNext(rowData);
            }
            Utils.print("Datos de Pokémon exportados correctamente a pokemon_data.csv");
        } catch (IOException e) {
            Utils.print("Error al exportar los datos de Pokémon a pokemon_data.csv");
        }
    }

    /**
     * Crea la carpeta out si no existe
     */
    private void createOutFolderIfNotExists() {
        try {
            Path folderPath = Paths.get("out");
            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }
        } catch (IOException e) {
            Utils.print("Error al intentar crear la carpeta out");
        }
    }
}
