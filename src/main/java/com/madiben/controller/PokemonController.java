package com.madiben.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.madiben.database.DatabaseManager;
import com.madiben.dto.PokemonDataDTO;
import com.madiben.models.NextEvolution;
import com.madiben.models.Pokedex;
import com.madiben.models.Pokemon;
import com.madiben.repositories.PokemonRepositoryImpl;
import com.madiben.utils.StringConverters;
import com.madiben.utils.Utils;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Clase controladora del Pokémon
 */
public class PokemonController {
    private static PokemonController instance;

    @Getter
    private Pokedex pokedex;
    private PokemonRepositoryImpl pokemonRepository = new PokemonRepositoryImpl(DatabaseManager.getInstance());

    /**
     * Constructor privado para evitar la creación de la instancia
     */
    private PokemonController() {
    }

    /**
     * Método singleton para obtener la instancia de la clase
     *
     * @return Instancia de la clase
     */
    public static PokemonController getInstance() {
        if (instance == null) {
            instance = new PokemonController();
        }
        return instance;
    }


    public void insertAllPokemonDataToDatabase(List<PokemonDataDTO> pokemonDataDTOS) {
        AtomicBoolean failed = new AtomicBoolean(false);
        pokemonDataDTOS.forEach(e -> {
            try {
                pokemonRepository.save(e);
            } catch (SQLException throwables) {
                System.out.println(throwables);
                failed.set(true);
            }
        });

        if (failed.get()){
            Utils.print("Error al insertar los datos en la base de datos");
        }
    }

    public List<PokemonDataDTO> findAllPokemon(){
        try {
            return pokemonRepository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PokemonDataDTO> getPokemonDatabaseByName(String name) {
        try {
            return pokemonRepository.findByName(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public Map<String, Long> groupPokemonByWeaknesses() {
        return pokedex.getPokemon().stream()
                .flatMap(pokemon -> pokemon.getWeaknesses().stream())
                .distinct()
                .collect(Collectors.toMap(
                        weakness -> weakness,
                        weakness -> pokedex.getPokemon().stream()
                                .filter(pokemon -> pokemon.getWeaknesses().contains(weakness)).count())
                );
    }

    public String getMostCommonWeakness() {
        Map<String, Long> weaknessFrequency = pokedex.getPokemon().stream()
                .flatMap(pokemon -> pokemon.getWeaknesses().stream())
                .collect(Collectors.groupingBy(
                        weakness -> weakness,
                        Collectors.counting()
                ));
        return weaknessFrequency.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseGet(() -> "No hay debilidades");
    }


    @NotNull
    public Map<Integer, List<Pokemon>> groupPokemonByEvolutionNumber() {
        return pokedex.getPokemon().stream().collect(Collectors.groupingBy(pokemon -> pokemon.getNextEvolution().size()));
    }

    @NotNull
    public Map<String, List<Pokemon>> groupPokemonByType() {
        return pokedex.getPokemon().stream()
                .flatMap(pokemon -> pokemon.getType().stream())
                .distinct()
                .collect(Collectors.toMap(
                        type -> type,
                        type -> pokedex.getPokemon().stream()
                                .filter(pokemon -> pokemon.getType().contains(type))
                                .collect(Collectors.toList())
                ));
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
                        .strPositiveValToDoubleParser(pokemon.getHeight()).orElseGet(() -> 0.0))
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

    /**
     * Devuelve un Optional con la lista de Pokémon que coincida con la lista de debilidades pasada por parámetro
     *
     * @param weaknesses Lista de debilidades a filtrar
     * @return Optional con la lista de Pokémon que coincida con la lista de debilidades pasada por parámetro
     */
    public List<Pokemon> filterByWeaknessesAnyMatch(List<String> weaknesses) {
        return pokedex.getPokemon().stream().filter(pokemon -> weaknesses.stream()
                .anyMatch(pokemon.getWeaknesses()::contains)).distinct().toList();
    }

    /**
     * Devuelve un Optional con la lista de Pokémon que tengan el tipo dado
     *
     * @param typeName Nombre del tipo a buscar
     * @return Optional con la lista de nombres de los Pokémon que tengan el tipo dado
     */
    public List<String> getNamesByTypeName(String typeName) {
        return pokedex.getPokemon().stream().filter(pokemon -> pokemon.getType().contains(typeName))
                .map(Pokemon::getName).toList();
    }

    /**
     * Devuelve un Optional con las evoluciones del Pokémon dado un nombre
     *
     * @param name Nombre del Pokémon a buscar
     * @return Optional con la lista de evoluciones del Pokémon
     */
    public Optional<ArrayList<NextEvolution>> getEvolutionsByPokemonName(String name) {
        return pokedex.getPokemon().stream().filter(pokemon -> pokemon.getName().equalsIgnoreCase(name))
                .map(Pokemon::getNextEvolution).findFirst();
    }

    /**
     * Devuelve un Optional con el Pokémon que tenga el nombre dado
     *
     * @param name Nombre del Pokémon a buscar
     * @return Optional con el Pokémon encontrado
     */
    public Optional<Pokemon> getPokemonByName(String name) {
        return pokedex.getPokemon().stream().filter(pokemon -> pokemon.getName().equalsIgnoreCase(name)).findFirst();
    }

    /**
     * Devuelve los nombres de los primeros q Pokémon
     *
     * @param q Cantidad de Pokémon a devolver
     * @return Lista de nombres de los primeros q Pokémon
     */
    public List<String> getFirstPokemonNames(long q) {
        return pokedex.getPokemon().stream().limit(q).map(Pokemon::getName).toList();
    }

    /**
     * Devuelve los nombres de los últimos q Pokémon
     *
     * @param q Cantidad de Pokémon a devolver
     * @return Lista de nombres de los últimos q Pokémon
     */
    public List<String> getLastPokemonNames(long q) {
        return pokedex.getPokemon().stream().skip(pokedex.getPokemon().size() - q)
                .map(Pokemon::getName).toList();
    }

}

