package com.madiben;

import com.madiben.controller.PokemonController;
import com.madiben.dto.PokemonDataDTO;
import com.madiben.io.CsvManager;
import com.madiben.models.NextEvolution;
import com.madiben.models.Pokedex;
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

    private String separator(){
        return "\n----------------------------------------\n";
    }
    private void printConsoleData() {
        PokemonController pc = PokemonController.getInstance();
        Utils.print(separator() + "(1) Nombre los 10 primeros Pokémon:\n" + pc.getFirstPokemonNames(10));
        Utils.print(separator() + "(2) Nombre de los 5 últimos Pokémon:\n" + pc.getLastPokemonNames(5));
        Utils.print(separator() + "(3) Datos de Pikachu:\n" + getPokemonData(pc.getPokemonByName("Pikachu")));
        Utils.print(separator() + "(4) Siguiente evolución de Charmander:\n" + getNextEvolutionByName("Charmander"));
        Utils.print(separator() + "(5) Nombre de los Pokémon de tipo Fire:\n" + pc.getNamesByTypeName("Fire"));
        Utils.print(separator() + "(6) Pokémon con debilidad Water o Electric:\n" + pc
                .filterByWeaknessesAnyMatch(new ArrayList<>(Arrays.asList("Water", "Electric"))));
        Utils.print(separator() + "(7) Pokémon con solo una debilidad:\n" + pc.countPokemonsWithANumberOfWeaknesses(1));
        Utils.print(separator() + "(8) Pokémon con más debilidades:\n" + getPokemonData(pc.getPokemonWithMoreWeaknesses()));
        Utils.print(separator() + "(9) Pokémon con menos evoluciones:\n" + getPokemonData(pc.getPokemonWithFewerWeaknesses()));
        Utils.print(separator() + "(10) Pokémon con 1 evolución que no es de tipo Fire:\n" +
                getPokemonData(getFirstPokemonOfList(pc.pokemonListExcludeByEvolution("Fire"))));
        Utils.print(separator() + "(11) Pokémon más pesado:\n" + getPokemonData(pc.getHighestWeightPokemon()));
        Utils.print(separator() + "(12) Pokémon más alto:\n" + getPokemonData(pc.getHeighestPokemon()));
        Utils.print(separator() + "(13) Pokémon con el nombre más largo:\n" + getPokemonData(pc.getPokemonWithLongestName()));
        Utils.print(separator() + "(14) Media de peso de los Pokémon:\n" + String.format("%.2f",pc.getPokemonWeightAvg()));
        Utils.print(separator() + "(15) Media de altura de los Pokémon:\n" + String.format("%.2f",pc.getPokemonHeightAvg()));
        Utils.print(separator() + "(16) Media de evoluciones de los Pokémon:\n" + String.format("%.2f",pc.getPokemonEvolutionAvg()));
        Utils.print(separator() + "(17) Media de debilidades de los Pokémon:\n" + String.format("%.2f",pc.getPokemonWeaknessesAvg()));
        printMap(pc.groupPokemonByType(), separator() + "(18) Pokémon agrupados por tipo:\n", "Tipo");
        printMap(pc.groupPokemonByWeaknesses(), separator() + "(19) Número de Pokémon agrupados por debilidad:\n", "Debilidad");
        printMap(pc.groupPokemonByEvolutionNumber(), separator() + "(20) Pokémon agrupados por número de evoluciones:\n", "Número evoluciones");

        //TODO: Sacar la debilidad más común.
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
     * @param map Mapa a imprimir
     * @param title Título del mapa
     * @param mapKeyName Nombre de la clave del mapa
     * @param <T> Tipo de la clave del mapa
     */
    private <T,L> void printMap(Map<T, L> map, String title, String mapKeyName){
        Utils.print(title);
        map.forEach((type, pokemonList) -> {
            Utils.print("\n" + mapKeyName + ": " + type);

            if (pokemonList instanceof List<?> && !pokemonList.isEmpty()) {
                for (int i = 0; i < pokemonList.size(); i++) {
                    Utils.print("\t(" + (i + 1) + ") Nombre: " + pokemonList.get(i).getName());
                }
            }
        });
    }
}
