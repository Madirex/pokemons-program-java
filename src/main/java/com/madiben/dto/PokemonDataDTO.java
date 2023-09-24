package com.madiben.dto;


import com.madiben.models.Pokedex;
import com.madiben.models.Pokemon;

import com.madiben.controller.PokemonController;
import lombok.Builder;
import lombok.Data;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.List;


@Data
@Builder

public class PokemonDataDTO {
    private int id;
    private String num;
    private double height;
    private double width;

    /**
     * Constructor de la clase PokemonDataDTO
     *
     * @param num    Número del pokemon
     * @param height Altura del pokemon
     * @param width  Peso del pokemon
     */
    public PokemonDataDTO(String num, double height, double width) {
        this.num = num;
        this.height = height;
        this.width = width;
    }
    public static void main(String[] args) {

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
//TODO: Exporta a csv los siguientes datos de pokemons: id, num, name, height, width.
