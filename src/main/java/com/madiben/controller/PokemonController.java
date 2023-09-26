package com.madiben.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.madiben.services.io.database.DatabaseManager;
import com.madiben.dto.PokemonDataDTO;
import com.madiben.models.NextEvolution;
import com.madiben.models.Pokedex;
import com.madiben.models.Pokemon;
import com.madiben.repositories.PokemonRepositoryImpl;
import com.madiben.services.utils.StringConverters;
import com.madiben.services.utils.Utils;
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


    /**
     * Inserta los datos de los Pokémon pasados por parámetro en la base de datos
     *
     * @param pokemonDataDTOS Lista de Pokémon a insertar
     */
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

        if (failed.get()) {
            Utils.print("Error al insertar los datos en la base de datos");
        }
    }

    /**
     * Busca todos los Pokémon en la base de datos
     * Devuelve una lista de PokemonDataDTO
     *
     * @return Lista de Pokémon
     */
    public List<PokemonDataDTO> findAllPokemon() {
        try {
            return pokemonRepository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retorna la lista de Pokémon por nombre
     * Si no se encuentra, retorna una lista vacía
     *
     * @param name Nombre del Pokémon
     * @return Lista de Pokémon
     */
    public List<PokemonDataDTO> getPokemonDatabaseByName(String name) {
        try {
            return pokemonRepository.findByName(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Carga el recurso pasado por parámetro y retorna el File del recurso
     *
     * @param resourceName Nombre del recurso
     * @return File del recurso
     */
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

    /**
     * Carga la Pokédex desde el fichero JSON
     * Si no se puede cargar, muestra un mensaje de error
     */
    public void loadPokedex() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try (Reader reader = new FileReader(loadResource("pokemon.json"))) {
            this.pokedex = gson.fromJson(reader, new TypeToken<Pokedex>() {
            }.getType());
        } catch (Exception e) {
            System.out.println("Error cargando la Pokédex!\n" + e.getMessage()); //TODO: DO REPLACE
        }
    }

    /**
     * Agrupa los números de Pokémon por debilidad
     *
     * @return Mapa de tipo <String, Long> (Nombre de la debilidad y cantidad)
     */
    public Map<String, Long> numberOfPokemonGroupedByWeaknesses() {
        return pokedex.getPokemon().stream()
                .flatMap(pokemon -> pokemon.getWeaknesses().stream())
                .distinct()
                .collect(Collectors.toMap(
                        weakness -> weakness,
                        weakness -> pokedex.getPokemon().stream()
                                .filter(pokemon -> pokemon.getWeaknesses().contains(weakness)).count())
                );
    }

    /**
     * Retorna un String de la debilidad más común
     * Si no hay debilidades, retorna "No hay debilidades"
     *
     * @return String de la debilidad más común
     */
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

    /**
     * Agrupa los Pokémon por número de evoluciones
     *
     * @return Mapa de tipo <Integer, List<Pokemon>>
     */
    @NotNull
    public Map<Integer, List<Pokemon>> groupPokemonByEvolutionNumber() {
        return pokedex.getPokemon().stream().collect(Collectors.groupingBy(pokemon -> pokemon.getNextEvolution().size()));
    }

    /**
     * Agrupa los Pokémon por tipo
     *
     * @return Mapa de tipo <String, List<Pokemon>>
     */
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

    /**
     * Devuelve la media de debilidades de todos los Pokémon
     *
     * @return Media de debilidades de todos los Pokémon
     */
    public double getPokemonWeaknessesAvg() {
        return pokedex.getPokemon().stream()
                .mapToDouble(pokemon -> pokemon.getWeaknesses().size())
                .average()
                .orElseGet(() -> 0.0);
    }

    /**
     * Devuelve la media de evoluciones de todos los Pokémon
     *
     * @return Media de evoluciones de todos los Pokémon
     */
    public double getPokemonEvolutionAvg() {
        return pokedex.getPokemon().stream()
                .mapToDouble(pokemon -> pokemon.getNextEvolution().size())
                .average()
                .orElseGet(() -> 0.0);
    }

    /**
     * Devuelve la media de altura de todos los Pokémon
     *
     * @return Media de altura de todos los Pokémon
     */
    public double getPokemonHeightAvg() {
        return pokedex.getPokemon().stream()
                .mapToDouble(pokemon -> StringConverters.getInstance()
                        .strPositiveValToDoubleParser(pokemon.getHeight()).orElseGet(() -> 0.0))
                .average()
                .orElseGet(() -> 0.0);
    }

    /**
     * Devuelve la media de peso de todos los Pokémon
     *
     * @return Media de peso de todos los Pokémon
     */
    public double getPokemonWeightAvg() {
        return pokedex.getPokemon().stream()
                .mapToDouble(pokemon -> StringConverters.getInstance()
                        .strPositiveValToDoubleParser(pokemon.getWeight()).orElseGet(() -> 0.0))
                .average()
                .orElseGet(() -> 0.0);
    }

    /**
     * Devuelve un Optional con el Pokémon que tenga la altura más alta
     *
     * @return Optional con el Pokémon que tenga la altura más alta
     */
    public Optional<Pokemon> getHeighestPokemon() {
        return pokedex.getPokemon().stream().max(Comparator.comparingDouble(pokemon -> StringConverters.getInstance()
                .strPositiveValToDoubleParser(pokemon.getHeight()).orElseGet(() -> 0.0)));
    }

    /**
     * Devuelve un Optional con el Pokémon que tenga el nombre más largo
     *
     * @return Optional con el Pokémon que tenga el nombre más largo
     */
    public Optional<Pokemon> getPokemonWithLongestName() {
        return pokedex.getPokemon().stream().max(Comparator.comparing(pokemon -> StringConverters
                .getInstance().parsePokemonName(pokemon.getName()).length()));
    }

    /**
     * Devuelve un Optional con el Pokémon que tenga más peso
     *
     * @return Optional con el Pokémon que tenga más peso
     */
    public Optional<Pokemon> getHighestWeightPokemon() {
        return pokedex.getPokemon().stream().max(Comparator.comparingDouble(pokemon -> StringConverters.getInstance()
                .strPositiveValToDoubleParser(pokemon.getWeight()).orElseGet(() -> 0.0)));
    }

    /**
     * Devuelve la lista de Pokémon excluidos por evolución dada
     *
     * @param type tipo de evolución
     * @return Lista de Pokémon
     */
    public List<Pokemon> pokemonListExcludeByEvolution(String type) {
        return pokedex.getPokemon().stream().filter(pokemon -> pokemon.getNextEvolution()
                .stream().noneMatch(e -> e.getName().equalsIgnoreCase(type))).toList();
    }

    /**
     * Devuelve un Optional con el Pokémon que tenga menos debilidades
     *
     * @return Optional con el Pokémon que tenga menos debilidades
     */
    public Optional<Pokemon> getPokemonWithFewerWeaknesses() {
        return pokedex.getPokemon().stream().min(Comparator.comparingDouble(pokemon -> pokemon.getNextEvolution().size()));
    }

    /**
     * Devuelve un Optional con el Pokémon que tenga más debilidades
     *
     * @return Optional con el Pokémon que tenga más debilidades
     */
    public Optional<Pokemon> getPokemonWithMoreWeaknesses() {
        return pokedex.getPokemon().stream().max(Comparator.comparingDouble(pokemon -> pokemon.getWeaknesses().size()));
    }

    /**
     * Devuelve la cantidad de Pokémon que tenga q número de debilidades
     *
     * @param q Cantidad de debilidades
     * @return Cantidad de Pokémon que tenga q número de debilidades
     */
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
