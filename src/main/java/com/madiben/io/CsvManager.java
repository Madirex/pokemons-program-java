package com.madiben.io;


import com.madiben.models.Pokemon;
import com.madiben.controller.PokemonController;
import com.opencsv.CSVWriter;


import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


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


    public void exportPokemonDataToCSV() {
        PokemonController pokemonController = PokemonController.getInstance();
        pokemonController.loadPokedex();

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
