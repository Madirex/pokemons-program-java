package com.madiben;

import com.madiben.controller.PokemonController;
import com.madiben.dto.PokemonDataDTO;
import com.madiben.models.NextEvolution;
import com.madiben.models.Pokemon;
import com.madiben.services.database.DatabaseManager;
import com.madiben.services.io.CsvManager;
import com.madiben.utils.LogGeneral;

import java.io.File;
import java.util.*;

/**
 * Clase principal del programa
 */
public class PokemonProgram {

    private static PokemonProgram pokemonProgramInstance;

    /**
     * Constructor privado para evitar la creación de instancia
     */
    private PokemonProgram() {
    }

    /**
     * Método singleton para obtener la instancia de la clase
     *
     * @return Instancia de la clase
     */
    public static PokemonProgram getInstance() {
        if (pokemonProgramInstance == null) {
            pokemonProgramInstance = new PokemonProgram();
        }
        return pokemonProgramInstance;
    }


    /**
     * Método inicializador del programa
     * 1. Imprime los datos del programa
     * 2. Exporta a CSV los datos del programa
     * 3. Lee los datos del CSV y los imprime. Introduce los datos del CSV en una base de datos y hace un select
     * 4. Imprime los datos de Pikachu haciendo una consulta a la base de datos
     */
    public void init() {
        printConsoleData();
        csvExportData();
        pokemonDataToDatabaseAndPrintPokemonsData(readFileGetPokemonDataDTOAndPrint("out" + File.separator + "pokemon_data.csv"));
        printPokemonDataFromDatabase("Pikachu");
        DatabaseManager.getInstance().close();
    }

    /**
     * Imprime los datos de un Pokémon dado su nombre haciendo una consulta a la base de datos
     *
     * @param pokemonName Nombre del Pokémon
     */
    private void printPokemonDataFromDatabase(String pokemonName) {
        List<PokemonDataDTO> pikachu = PokemonController.getInstance().getPokemonDatabaseByName(pokemonName);
        if (!pikachu.isEmpty()) {
            LogGeneral.getInstance().info("\nInformación de " + pokemonName + " desde la base de datos:\n");
            LogGeneral.getInstance().info(pikachu.get(0).toString());
        } else {
            LogGeneral.getInstance().info(pokemonName + " no se encuentra en la base de datos");
        }
    }

    /**
     * Introduce los datos de los Pokémon en la base de datos y los imprime
     *
     * @param data Datos de los Pokémon
     */
    private void pokemonDataToDatabaseAndPrintPokemonsData(Optional<List<PokemonDataDTO>> data) {
        if (data.isPresent()) {
            PokemonController.getInstance().insertAllPokemonDataToDatabase(data.get());
            PokemonController.getInstance().findAllPokemon().forEach(e -> LogGeneral.getInstance().info(e.toString()));
        } else {
            LogGeneral.getInstance().info("No se ha podido obtener los datos");
        }
    }

    /**
     * Exporta los datos de los Pokémon a un CSV
     */
    private void csvExportData() {
        CsvManager.getInstance().exportPokemonDataToCSV();
    }

    /**
     * Lee los datos de un CSV y los imprime
     *
     * @param path Ruta del CSV
     * @return Optional con los datos de los Pokémon
     */
    private Optional<List<PokemonDataDTO>> readFileGetPokemonDataDTOAndPrint(String path) {
        Optional<List<PokemonDataDTO>> pokemons = CsvManager.getInstance().fileToPokemonDataDTO(path);
        if (pokemons.isPresent()) {
            pokemons.get().forEach(e -> LogGeneral.getInstance().info(e.toString()));
        } else {
            LogGeneral.getInstance().error("No se ha podido leer el archivo.", PokemonProgram.class);
        }
        return pokemons;
    }

    /**
     * Imprime los datos del programa
     */
    private void printConsoleData() {
        PokemonController pc = PokemonController.getInstance();
        LogGeneral.getInstance().info("[1] Nombre los 10 primeros Pokémon:\n" + pc.getFirstPokemonNames(10));
        LogGeneral.getInstance().info("[2] Nombre de los 5 últimos Pokémon:\n" + pc.getLastPokemonNames(5));
        LogGeneral.getInstance().info("[3] Datos de Pikachu:\n" + getPokemonData(pc.getPokemonByName("Pikachu")));
        LogGeneral.getInstance().info("[4] Siguiente evolución de Charmander:\n" + getNextEvolutionByName("Charmander"));
        LogGeneral.getInstance().info("[5] Nombre de los Pokémon de tipo Fire:\n" + pc.getNamesByTypeName("Fire"));
        LogGeneral.getInstance().info("[6] Pokémon con debilidad Water o Electric:\n" + pc
                .filterByWeaknessesAnyMatch(new ArrayList<>(Arrays.asList("Water", "Electric"))));
        LogGeneral.getInstance().info("[7] Pokémon con solo una debilidad:\n" + pc.countPokemonsWithANumberOfWeaknesses(1));
        LogGeneral.getInstance().info("[8] Pokémon con más debilidades:\n" + getPokemonData(pc.getPokemonWithMoreWeaknesses()));
        LogGeneral.getInstance().info("[9] Pokémon con menos evoluciones:\n" + getPokemonData(pc.getPokemonWithFewerWeaknesses()));
        LogGeneral.getInstance().info("[10] Pokémon con 1 evolución que no es de tipo Fire:\n" +
                getPokemonData(getFirstPokemonOfList(pc.pokemonListExcludeByEvolution("Fire"))));
        LogGeneral.getInstance().info("[11] Pokémon más pesado:\n" + getPokemonData(pc.getHighestWeightPokemon()));
        LogGeneral.getInstance().info("[12] Pokémon más alto:\n" + getPokemonData(pc.getHeighestPokemon()));
        LogGeneral.getInstance().info("[13] Pokémon con el nombre más largo:\n" + getPokemonData(pc.getPokemonWithLongestName()));
        LogGeneral.getInstance().info("[14] Media de peso de los Pokémon:\n" + String.format("%.2f", pc.getPokemonWeightAvg()));
        LogGeneral.getInstance().info("[15] Media de altura de los Pokémon:\n" + String.format("%.2f", pc.getPokemonHeightAvg()));
        LogGeneral.getInstance().info("[16] Media de evoluciones de los Pokémon:\n" + String.format("%.2f", pc.getPokemonEvolutionAvg()));
        LogGeneral.getInstance().info("[17] Media de debilidades de los Pokémon:\n" + String.format("%.2f", pc.getPokemonWeaknessesAvg()));
        printMap(pc.groupPokemonByType(), "[18] Pokémon agrupados por tipo:\n", "Tipo");
        printMapLong(pc.numberOfPokemonGroupedByWeaknesses(), "[19] Número de Pokémon agrupados por debilidad:\n", "Debilidad");
        printMap(pc.groupPokemonByEvolutionNumber(), "[20] Pokémon agrupados por número de evoluciones:\n", "Número evoluciones");
        LogGeneral.getInstance().info("[21] Debilidad más común:\n" + (pc.getMostCommonWeakness()));

    }

    /**
     * Obtiene la siguiente evolución del Pokémon con el nombre indicado
     *
     * @param name Nombre del Pokémon
     * @return String con los datos de la siguiente evolución o un mensaje si no se ha encontrado
     */
    private static String getNextEvolutionByName(String name) {
        Optional<ArrayList<NextEvolution>> evCharmander = PokemonController.getInstance().getEvolutionsByPokemonName(name);
        String nextEvolution = "No se ha encontrado el Pokémon";
        if (evCharmander.isPresent()) {
            nextEvolution = evCharmander.get().get(0).toString();
        }
        return nextEvolution;
    }

    /**
     * Obtiene los datos del Pokémon dado el Optional de Pokémon
     *
     * @param pokemonOpt Optional con el Pokémon
     * @return String con los datos del Pokémon o un mensaje si no se ha encontrado
     */
    private String getPokemonData(Optional<Pokemon> pokemonOpt) {
        String pikachu = "No se ha encontrado el Pokémon";
        if (pokemonOpt.isPresent()) {
            pikachu = pokemonOpt.get().toString();
        }
        return pikachu;
    }

    /**
     * Obtiene el primer Pokémon de la lista
     *
     * @param pokeList Lista de Pokémon
     * @return Optional con el primer Pokémon de la lista o un Optional vacío si la lista está vacía
     */
    private Optional<Pokemon> getFirstPokemonOfList(List<Pokemon> pokeList) {
        if (!pokeList.isEmpty()) {
            return Optional.of(pokeList.get(0));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Imprime un mapa de tipo <T, List<Pokemon>>
     *
     * @param map        Mapa a imprimir
     * @param title      Título del mapa
     * @param mapKeyName Nombre de la clave del mapa
     * @param <T>        Tipo de la clave del mapa
     */
    private <T> void printMap(Map<T, List<Pokemon>> map, String title, String mapKeyName) {
        LogGeneral.getInstance().info(title);
        map.forEach((type, pokemonList) -> {
            LogGeneral.getInstance().info(mapKeyName + ": " + type);
            if (!pokemonList.isEmpty()) {
                for (int i = 0; i < pokemonList.size(); i++) {
                    LogGeneral.getInstance().info("\t(" + (i + 1) + ") Nombre: " + pokemonList.get(i).getName());
                }
            }
        });
    }

    /**
     * Imprime un mapa de tipo <T, Long>
     *
     * @param map        Mapa a imprimir
     * @param title      Título del mapa
     * @param mapKeyName Nombre de la clave del mapa
     * @param <T>        Tipo de la clave del mapa
     */
    private <T> void printMapLong(Map<T, Long> map, String title, String mapKeyName) {
        LogGeneral.getInstance().info(title);
        map.forEach((type, amount) -> {
            LogGeneral.getInstance().info(mapKeyName + ": " + type);
            LogGeneral.getInstance().info("\tCantidad: " + amount);
        });
    }
}
