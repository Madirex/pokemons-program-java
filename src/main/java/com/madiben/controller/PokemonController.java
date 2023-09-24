package com.madiben.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.madiben.models.NextEvolution;
import com.madiben.models.Pokedex;
import com.madiben.models.Pokemon;
import com.madiben.utils.StringConverters;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class PokemonController {
    private static PokemonController instance;
    private Pokedex pokedex;


    private PokemonController() {
    }

    public static PokemonController getInstance() {
        if (instance == null) {
            instance = new PokemonController();
        }
        return instance;
    }

    private File loadResource(String resourceName) {
        URL resource = getClass().getClassLoader().getResource("data" + File.separator + resourceName);
        try {
            if (resource == null) {
                throw new IllegalArgumentException("¡Archivo no encontrado!");
            } else {
                return new File(resource.toURI());
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException("¡Archivo no encontrado!");
        }
    }

    public Pokedex getPokedex() {
        return pokedex;
    }

    public void loadPokedex() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        // Actualizar a try-with-resources
        try (Reader reader = new FileReader(loadResource("pokemon.json"))) {
            this.pokedex = gson.fromJson(reader, new TypeToken<Pokedex>() {
            }.getType());
        } catch (Exception e) {
            System.out.println("Error cargando la Pokédex!\n" + e.getMessage());
        }
    }

    @NotNull
    private Map<String, List<Pokemon>> groupPokemonByWeaknesses() {
        return pokedex.getPokemon().stream().collect(Collectors.groupingBy(pokemon -> pokemon.getWeaknesses().toString()));
        //TODO: FIX
    }

    @NotNull
    public Map<Integer, List<Pokemon>> groupPokemonByEvolutionNumber() {
        return pokedex.getPokemon().stream().collect(Collectors.groupingBy(pokemon -> pokemon.getNextEvolution().size()));
    }

    @NotNull
    public Map<String, List<Pokemon>> groupPokemonByType() {
       return pokedex.getPokemon().stream().collect(Collectors.groupingBy(pokemon -> pokemon.getType().toString()));
       //TODO: FIX
//        Stream<String> types = pokedex.getPokemon()
//                .stream()
//                .distinct()
//                .flatMap(pokemon -> pokemon.getType().stream()); //uno a muchos
//
//        return types.collect(Collectors.groupingBy(
//                type -> type,
//                type -> pokedex.getPokemon().stream()
//                        .filter(pokemon -> pokemon.getType().contains(type))
//                        .collect(Collectors.toList()))
//        );
    }

    public double getPokemonWeaknessesAvg() {
        return pokedex.getPokemon().stream()
                .mapToDouble(pokemon -> pokemon.getWeaknesses().size())
                .average()
                .orElseGet(() -> 0.0);
    }

    public double getPokemonEvolutionAvg() {
        return pokedex.getPokemon().stream()
                .mapToDouble(pokemon -> pokemon.getNextEvolution().size())
                .average()
                .orElseGet(() -> 0.0);
    }

    public double getPokemonHeightAvg() {
        return pokedex.getPokemon().stream()
                .mapToDouble(pokemon -> StringConverters.getInstance()
                        .strPositiveValToDoubleParser(pokemon.getHeight()).orElseGet(() ->0.0))
                .average()
                .orElseGet(() -> 0.0);
    }

    public double getPokemonWeightAvg() {
        return pokedex.getPokemon().stream()
                .mapToDouble(pokemon -> StringConverters.getInstance()
                        .strPositiveValToDoubleParser(pokemon.getWeight()).orElseGet(() -> 0.0))
                .average()
                .orElseGet(() -> 0.0);
    }

    public Optional<Pokemon> getHeighestPokemon() {
        return pokedex.getPokemon().stream().max(Comparator.comparingDouble(pokemon -> StringConverters.getInstance()
                .strPositiveValToDoubleParser(pokemon.getHeight()).orElseGet(() -> 0.0)));
    }

    public Optional<Pokemon> getPokemonWithLongestName() {
        return pokedex.getPokemon().stream().max(Comparator.comparing(pokemon -> StringConverters
                .getInstance().parsePokemonName(pokemon.getName()).length()));
    }

    public Optional<Pokemon> getHighestWeightPokemon() {
        return pokedex.getPokemon().stream().max(Comparator.comparingDouble(pokemon -> StringConverters.getInstance()
                .strPositiveValToDoubleParser(pokemon.getWeight()).orElseGet(() -> 0.0)));
    }

    public List<Pokemon> pokemonListExcludeByEvolution(String type) {
        return pokedex.getPokemon().stream().filter(pokemon -> pokemon.getNextEvolution()
                .stream().noneMatch(e -> e.getName().equalsIgnoreCase(type))).toList();
    }

    public Optional<Pokemon> getPokemonWithFewerWeaknesses() {
        return pokedex.getPokemon().stream().min(Comparator.comparingDouble(pokemon -> pokemon.getNextEvolution().size()));
    }

    public Optional<Pokemon> getPokemonWithMoreWeaknesses() {
        return pokedex.getPokemon().stream().max(Comparator.comparingDouble(pokemon -> pokemon.getWeaknesses().size()));
    }

    public long countPokemonsWithANumberOfWeaknesses(int q) {
        return pokedex.getPokemon().stream().filter(pokemon -> pokemon.getWeaknesses().size() == q).count();
    }

    public List<Pokemon> filterByWeaknessesAnyMatch(List<String> weaknesses) {
        return pokedex.getPokemon().stream().filter(pokemon -> weaknesses.stream()
                .anyMatch(pokemon.getWeaknesses()::contains)).distinct().toList();
    }

    public List<Pokemon> filterByWeaknessesContainsAll(List<String> weaknesses) {
        return pokedex.getPokemon().stream().filter(pokemon -> pokemon.getWeaknesses().containsAll(weaknesses)).toList();
    }

    public List<String> getNamesByTypeName(String typeName) {
        return pokedex.getPokemon().stream().filter(pokemon -> pokemon.getType().contains(typeName))
                .map(Pokemon::getName).toList();
    }

    public Optional<ArrayList<NextEvolution>> getEvolutionsByPokemonName(String name) {
        return pokedex.getPokemon().stream().filter(pokemon -> pokemon.getName().equalsIgnoreCase(name))
                .map(Pokemon::getNextEvolution).findFirst();
    }

    public Optional<Pokemon> getPokemonByName(String name) {
        return pokedex.getPokemon().stream().filter(pokemon -> pokemon.getName().equalsIgnoreCase(name)).findFirst();
    }

    public List<String> getFirstPokemonNames(long q) {
        return pokedex.getPokemon().stream().limit(q).map(Pokemon::getName).toList();
    }

    public List<String> getLastPokemonNames(long q) {
        return pokedex.getPokemon().stream().skip(pokedex.getPokemon().size() - q)
                .map(Pokemon::getName).toList();
    }
}

