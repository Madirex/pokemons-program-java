package com.madiben;

import com.madiben.controller.PokemonController;
import com.madiben.dto.PokemonDataDTO;
import com.madiben.io.CsvManager;
import com.madiben.models.NextEvolution;
import com.madiben.models.Pokemon;
import com.madiben.utils.Utils;

import java.util.*;
import java.util.stream.Stream;

/**
 * Clase principal del programa
 */
public class PokemonProgram {

    private static PokemonProgram pokemonProgramInstance;

    /**
     * Constructor privado para evitar instanciación
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
     */
    public void init() {
        printConsoleData();
        csvExportData(); //TODO: comprobar que funcione correctamente
        //Optional<Stream<PokemonDataDTO>> pokemons = readFileGetPokemonDataDTOAndPrint(""); //TODO: comprobar que funcione correctamente
        //stringDataToDatabaseAndPrint(pokemons); //TODO: comprobar que funcione correctamente
        printPokemonDataFromDatabase("Pikachu"); //TODO: comprobar que funcione correctamente
    }

    private void printPokemonDataFromDatabase(String pokemonName) {
        //        TODO: Desde la base de datos, saca la información de Pikachu.
    }

    private void stringDataToDatabaseAndPrint(Optional<Stream<PokemonDataDTO>> data) {
        //        TODO: Introduce los datos que has importado de un CSV en una base de datos en fichero como H2 o SQLite y luego realiza un select para ver el resultado de las operaciones. Los datos de conexión deben estar encapsulados en un manejador y leídos de un fichero de propiedades o de entorno.
    }

    private void csvExportData() {
        CsvManager.getInstance().exportPokemonDataToCSV();


    }

    /*private Optional<Stream<PokemonDataDTO>> readFileGetPokemonDataDTOAndPrint(String path) {
        Optional<Stream<PokemonDataDTO>> pokemons = CsvManager.getInstance().fileToPokemonDataDTO(path);
        if (pokemons.isPresent()) {
            pokemons.get().forEach(System.out::println);
        } else {
            Utils.print("No se ha podido leer el archivo.");
        }
        return pokemons;
    }

         */


    private void printConsoleData() {
        PokemonController pc = PokemonController.getInstance();
        Utils.print("\n(1) Nombre los 10 primeros Pokémon:\n" + pc.getFirstPokemonNames(10));
        Utils.print("\n(2) Nombre de los 5 últimos Pokémon:\n" + pc.getLastPokemonNames(5));
        Utils.print("\n(3) Datos de Pikachu:\n" + getPokemonData(pc.getPokemonByName("Pikachu")));
        Utils.print("\n(4) Siguiente evolución de Charmander:\n" + getNextEvolutionByName("Charmander"));
        Utils.print("\n(5) Nombre de los Pokémon de tipo Fire:\n" + pc.getNamesByTypeName("Fire"));
        Utils.print("\n(6) Pokémon con debilidad Water o Electric:\n" + pc
                .filterByWeaknessesAnyMatch(new ArrayList<>(Arrays.asList("Water", "Electric"))));
        Utils.print("\n(7) Pokémon con solo una debilidad:\n" + pc.countPokemonsWithANumberOfWeaknesses(1));
        Utils.print("\n(8) Pokémon con más debilidades:\n" + getPokemonData(pc.getPokemonWithMoreWeaknesses()));
        Utils.print("\n(11) Pokémon más pesado:\n" + getPokemonData(pc.getHighestWeightPokemon()));
        Utils.print("\n(12) Pokémon más alto:\n" + getPokemonData(pc.getHeighestPokemon()));
        Utils.print("\n(13) Pokémon con el nombre más largo:\n" + getPokemonData(pc.getPokemonWithLongestName()));
//        TODO: TESTEAR QUE LOS DATOS OBTENIDOS DE ABAJO SON CORRECTOS
        // TODO: Utils.print("\n(9) Pokémon con menos evoluciones:\n" + getPokemonData(pc.getPokemonWithFewerWeaknesses()));
        // TODO: Utils.print("\n(10) Pokémon con 1 evolución que no es de tipo Fire:\n" +
        //       getPokemonData(getFirstPokemonOfList(pc.pokemonListExcludeByEvolution("Fire"))));
        Utils.print("\n(14) Media de peso de los Pokémon:\n" + pc.getPokemonWeightAvg());
        Utils.print("\n(15) Media de altura de los Pokémon:\n" + pc.getPokemonHeightAvg());
        //TODO: Utils.print("\n(16) Media de evoluciones de los Pokémon:\n" + pc.getPokemonEvolutionAvg());
        //TODO: Utils.print("\n(17) Media de debilidades de los Pokémon:\n" + pc.getPokemonWeaknessesAvg());
        //printMap
//        TODO: Pokemons agrupados por tipo.
//        TODO: Numero de pokemons agrupados por debilidad.
//        TODO: Pokemons agrupados por número de evoluciones.
//        TODO: Sacar la debilidad más común.


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
     * Imprime el mapa de tipos de Pokémon
     *
     * @param map Mapa de tipos de Pokémon
     */
    private void printMap(Map<String, List<Pokemon>> map) {
        map.forEach((type, pokemonList) -> {
            Utils.print("\nTipo: " + type);

            if (!pokemonList.isEmpty()) {
                for (int i = 0; i < pokemonList.size(); i++) {
                    Utils.print("\t(" + i + ") Nombre: " + pokemonList.get(i).getName());
                }
            }
        });
    }
}
