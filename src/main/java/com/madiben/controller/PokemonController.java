package com.madiben.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.madiben.models.NextEvolution;
import com.madiben.models.Pokedex;
import com.madiben.models.Pokemon;
import com.madiben.utils.StringConverters;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;


public class PokemonController {
    private static PokemonController instance;
    private Pokedex pokedex;


    private PokemonController() {
        loadPokedex();
    }

    public static PokemonController getInstance() {
        if (instance == null) {
            instance = new PokemonController();
        }
        return instance;
    }

    private void loadPokedex() {
        Path currentRelativePath = Paths.get("");
        String ruta = currentRelativePath.toAbsolutePath().toString();
        String dir = ruta + File.separator + "data";
        String paisesFile = dir + File.separator + "data/pokemon.json";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Actualizar a try-with-resources
        try (Reader reader = Files.newBufferedReader(Paths.get(paisesFile))) {
            this.pokedex = gson.fromJson(reader, new TypeToken<Pokedex>() {}.getType());
            System.out.println("Pokedex loaded! There are: " + pokedex.pokemon.size());
        } catch (Exception e) {
            System.out.println("Error loading Pokedex!");
            System.out.println("Error: " + e.getMessage());
        }
    }
    int cantPoke=151;
    List<String> pokemon5=pokedex.getPokemon().stream().limit(cantPoke-5).map(Pokemon::getName).toList();

    List<String> pokemon10=pokedex.getPokemon().stream().limit(10).map(Pokemon::getName).toList();

    List<Pokemon> pikachu=pokedex.getPokemon().stream().filter(pokemon -> pokemon.getName().contains("Pikachu")).toList();

    List<NextEvolution> evCharmander=pokedex.getPokemon().stream().filter(pokemon -> pokemon.getType().contains("Charmander")).map(Pokemon::getNext_evolution).toList().get(0);



    //Nombre pokemon de tipo fuego
    List<String> pokemonF=pokedex.getPokemon().stream().filter(pokemon -> pokemon.getType().contains("Fire")).map(Pokemon::getName).toList();

    List<String> pokemonW=pokedex.getPokemon().stream().filter(pokemon -> pokemon.getWeaknesses().contains("water")).map(Pokemon::getName).toList();
    long pokemon1d=pokedex.getPokemon().stream().filter(pokemon -> pokemon.getWeaknesses().size()==1).count();

    StringConverters stringConverter= StringConverters.getInstance();
    List<Pokemon> pokemonEvF=pokedex.getPokemon().stream().filter(pokemon -> !pokemon.getNext_evolution().isEmpty()).filter(pokemon -> pokemon.getNext_evolution());
    Pokemon pokemonMasPes=pokedex.getPokemon().stream().max(Comparator.comparingDouble(pokemon -> stringConverter.stringPositiveDoubleValueToDoubleParser(pokemon.getWeight()).orElse(0.0))).get();
    Pokemon pokemonNombreMasL=pokedex.getPokemon().stream().max(Comparator.comparing(pokemon -> pokemon.getName().length())).stream().toList().get(0);
    Pokemon pokemonMasAlto=pokedex.getPokemon().stream().max(Comparator.comparingDouble(pokemon -> stringConverter.stringPositiveDoubleValueToDoubleParser(pokemon.getHeight()).orElse(0.0))).get();
    double pokemonMediaPeso = pokedex.getPokemon().stream()
            .mapToDouble(pokemon -> stringConverter.stringPositiveDoubleValueToDoubleParser(pokemon.getWeight()).orElse(0.0))
            .average()
            .orElse(0.0);

    double pokemonMediaAltura = pokedex.getPokemon().stream()
            .mapToDouble(pokemon -> stringConverter.stringPositiveDoubleValueToDoubleParser(pokemon.getHeight()).orElse(0.0))
            .average()
            .orElse(0.0);
    double pokemonMediaEv=pokedex.getPokemon().stream().mapToDouble(pokemon -> pokemon.getNext_evolution().size() ).average().orElseGet(()->0.0);
    Map<String, List<Pokemon>> pokeAgrupTip=pokedex.getPokemon().stream().collect(Collectors.groupingBy(pokemon -> pokemon.getType().toString()));
    Map<Integer, List<Pokemon>> pokeAgrupEv=pokedex.getPokemon().stream().collect(Collectors.groupingBy(pokemon -> pokemon.getNext_evolution().size()));



    public Pokemon getPokemon(int index) {
        return pokedex.pokemon.get(index);
    }
}

