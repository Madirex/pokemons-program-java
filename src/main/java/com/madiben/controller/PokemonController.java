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
            System.out.println("Pokedex loaded! There are: " + pokedex.getPokemon().size());
        } catch (Exception e) {
            System.out.println("Error loading Pokedex!");
            System.out.println("Error: " + e.getMessage());
        }
    }
    long cantPoke=151;
    //Ultimos 5
    List<String> pokemon5=pokedex.getPokemon().stream().skip(cantPoke-5).map(Pokemon::getName).toList();
    //Primeros 10

    List<String> pokemon10=pokedex.getPokemon().stream().limit(10).map(Pokemon::getName).toList();
    //Informacion de Pikachu
    List<Pokemon> pikachu=pokedex.getPokemon().stream().filter(pokemon -> pokemon.getName().contains("Pikachu")).toList();
    //Evolucion de Charmander
    List<NextEvolution> evCharmander=pokedex.getPokemon().stream().filter(pokemon -> pokemon.getType().contains("Charmander")).map(Pokemon::getNextEvolution).toList().get(0);



    //Nombre pokemon de tipo fuego
    List<String> pokemonF=pokedex.getPokemon().stream().filter(pokemon -> pokemon.getType().contains("Fire")).map(Pokemon::getName).toList();
    //Numero de pokemon con una debilidad
    long pokemon1d=pokedex.getPokemon().stream().filter(pokemon -> pokemon.getWeaknesses().size()==1).count();

    StringConverters stringConverter= StringConverters.getInstance();
    //Pokemon con debilidad al Water y Electric
    String pokemonWeakWatElec=pokedex.getPokemon().stream().filter(pokemon -> pokemon.getWeaknesses().contains("Water")&& pokemon.getWeaknesses().contains("Electric")).toList().get(0).getName();
    //Pokemon con mas debilidades
    Pokemon pokemonMasDeb=pokedex.getPokemon().stream().max(Comparator.comparingDouble(pokemon->pokemon.getWeaknesses().size())).get();
   //Pokemon con menos evoluciones
    Pokemon pokemonMenosEv=pokedex.getPokemon().stream().min(Comparator.comparingDouble(pokemon -> pokemon.getNextEvolution().size())).get();
    //Pokemon cuya evolucion no es de tipo fire
    Pokemon pokemonNoEvFire=pokedex.getPokemon().stream().filter(pokemon -> !pokemon.getNextEvolution().contains("Fire")).toList().get(0);

    //Pokemon mas Pesado
    Pokemon pokemonMasPes=pokedex.getPokemon().stream().max(Comparator.comparingDouble(pokemon -> stringConverter.stringPositiveDoubleValueToDoubleParser(pokemon.getWeight()).orElse(0.0))).get();
    //Pokemon con nombre mas largo
    Pokemon pokemonNombreMasL=pokedex.getPokemon().stream().max(Comparator.comparing(pokemon -> pokemon.getName().length())).stream().toList().get(0);
    //Pokemon mas alto
    Pokemon pokemonMasAlto=pokedex.getPokemon().stream().max(Comparator.comparingDouble(pokemon -> stringConverter.stringPositiveDoubleValueToDoubleParser(pokemon.getHeight()).orElse(0.0))).get();
    //Media de peso de Pokemon
    double pokemonMediaPeso = pokedex.getPokemon().stream()
            .mapToDouble(pokemon -> stringConverter.stringPositiveDoubleValueToDoubleParser(pokemon.getWeight()).orElse(0.0))
            .average()
            .orElse(0.0);

    //Media de altura de Pokemon
    double pokemonMediaAltura = pokedex.getPokemon().stream()
            .mapToDouble(pokemon -> stringConverter.stringPositiveDoubleValueToDoubleParser(pokemon.getHeight()).orElse(0.0))
            .average()
            .orElse(0.0);
   //Media de evoluciones de Pokemons
    double pokemonMediaEv=pokedex.getPokemon().stream().mapToDouble(pokemon -> pokemon.getNextEvolution().size() ).average().orElseGet(()->0.0);
    //Agrupar por tipo
    Map<String, List<Pokemon>> pokeAgrupTip=pokedex.getPokemon().stream().collect(Collectors.groupingBy(pokemon -> pokemon.getType().toString()));
   //Agrupar por numero de evoluciones
    Map<Integer, List<Pokemon>> pokeAgrupEv=pokedex.getPokemon().stream().collect(Collectors.groupingBy(pokemon -> pokemon.getNextEvolution().size()));
    //Agrupar por debilidades
    Map<String, List<Pokemon>> pokeAgrupWeak=pokedex.getPokemon().stream().collect(Collectors.groupingBy(pokemon -> pokemon.getWeaknesses().toString() ));
    //Debilidad mas comun



    public Pokemon getPokemon(int index) {
        return pokedex.getPokemon().get(index);
    }
}

