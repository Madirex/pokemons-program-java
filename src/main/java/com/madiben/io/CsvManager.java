package com.madiben.io;

import com.madiben.models.Pokemon;
import com.madiben.controller.PokemonController;
import com.madiben.utils.Utils;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvManager {

    private static CsvManager csvManagerInstance;

    private CsvManager() {
    }

    /**
     * Obtiene la instancia de CsvManager
     *
     * @return Instancia de CsvManager
     */
    public static CsvManager getInstance() {
        if (csvManagerInstance == null) {
            csvManagerInstance = new CsvManager();
        }
        return csvManagerInstance;
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
