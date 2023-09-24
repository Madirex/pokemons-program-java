package com.madiben.io;

import com.madiben.dto.PokemonDataDTO;
import com.madiben.models.Pokemon;
import com.madiben.utils.StringConverters;
import com.madiben.controller.PokemonController;
import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CsvManager {


    private static CsvManager csvManagerInstance;

    private CsvManager() {
    }

    public static CsvManager getInstance() {
        if (csvManagerInstance == null) {
            csvManagerInstance = new CsvManager();
        }
        return csvManagerInstance;
    }
    PokemonController pokemonController = PokemonController.getInstance();

    public Optional<Stream<PokemonDataDTO>> fileToPokemonDataDTO(String path) {
//        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
//            return Optional.of(reader.lines()
//                    .map(line -> line.split(","))
//                    .map(values -> new PokemonDataDTO(values[0],
//                            StringConverters.getInstance().strPositiveValToDoubleParser(values[1]).orElse(0.0),
//                            StringConverters.getInstance().strPositiveValToDoubleParser(values[2]).orElse(0.0))));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return Optional.empty();
    }
    public void exportPokemonDataToCSV() {
        PokemonController pokemonController = PokemonController.getInstance();
        pokemonController.loadPokedex(); // Asegúrate de cargar la Pokédex primero

        List<Pokemon> pokemonList = pokemonController.getPokedex().getPokemon();

        try (CSVWriter writer = new CSVWriter(new FileWriter("pokemon_data.csv"))) {
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

            System.out.println("Datos de Pokémon exportados correctamente a pokemon_data.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
