package com.madiben;

import com.madiben.controller.PokemonController;
import com.madiben.dto.PokemonDataDTO;
import com.madiben.io.CsvManager;
import com.madiben.models.NextEvolution;
import com.madiben.models.Pokemon;
import com.madiben.utils.StringConverters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        csvExportData();
        Optional<Stream<PokemonDataDTO>> pokemons = readFileGetPokemonDataDTOAndPrint("");
        stringDataToDatabaseAndPrint(pokemons);
        printPokemonDataFromDatabase("Pikachu");
    }

    private void printPokemonDataFromDatabase(String pokemonName) {
        //        TODO: Desde la base de datos, saca la información de Pikachu.
    }

    private void stringDataToDatabaseAndPrint(Optional<Stream<PokemonDataDTO>> data) {
        //        TODO: Introduce los datos que has importado de un CSV en una base de datos en fichero como H2 o SQLite y luego realiza un select para ver el resultado de las operaciones. Los datos de conexión deben estar encapsulados en un manejador y leídos de un fichero de propiedades o de entorno.
    }

    private void csvExportData() {
                String rutaDelArchivo = "/ruta/al/archivo.csv";
        //        TODO: Exporta a csv los siguientes datos de pokemons: id, num, name, height, width.
    }

    private Optional<Stream<PokemonDataDTO>> readFileGetPokemonDataDTOAndPrint(String path) {
        Optional<Stream<PokemonDataDTO>> pokemons = CsvManager.getInstance().fileToPokemonDataDTO(path);
        if (pokemons.isPresent()) {
            pokemons.get().forEach(System.out::println);
        } else {
            System.out.println("No se ha podido leer el archivo.");
        }
        return pokemons;
    }

    private void printConsoleData() {
        System.out.println("\nNombre los 10 primeros Pokémon:\n" + PokemonController.getInstance().getFirstPokemonNames(10));
        System.out.println("\nNombre de los 5 últimos Pokémon:\n" + PokemonController.getInstance().getLastPokemonNames(5));
        System.out.println("\nDatos de Pikachu:\n" + printPokemonByName("Pikachu"));
        System.out.println("\nSiguiente evolución de Charmander:\n" + printNextEvolutionByName("Charmander"));
        System.out.println("\nNombre de los Pokémon de tipo Fire:\n" + PokemonController.getInstance().getPokemonsFilterByTypeName("Fire"));
        System.out.println("\nTodos los Pokémon con debilidad Water o Electric:\n" + PokemonController.getInstance().filterByWeaknessesAnyMatch(new ArrayList<>(Arrays.asList("Water", "Electric"))));
        System.out.println("\nNúmero de Pokémon con solo una debilidad:\n" + PokemonController.getInstance().countPokemonsWithANumberOfWeaknesses(1));

//        TODO: Pokemon con más debilidades.
//        TODO: Pokemon con menos evoluciones.
//        TODO: Pokemon con una evolución que no es de tipo fire.
//        TODO: Pokemon más pesado.
//        TODO: Pokemon más alto.
//        TODO: Pokemon con el nombre mas largo.
//        TODO: Media de peso de los pokemons.
//        TODO: Media de altura de los pokemons.
//        TODO: Media de evoluciones de los pokemons.
//        TODO: Media de debilidades de los pokemons.
//        TODO: Pokemons agrupados por tipo.
//        TODO: Numero de pokemons agrupados por debilidad.
//        TODO: Pokemons agrupados por número de evoluciones.
//        TODO: Sacar la debilidad más común.






//        StringConverters stringConverter = StringConverters.getInstance();
//        //Pokemon con mas debilidades
//        Pokemon pokemonMasDeb = pokedex.getPokemon().stream().max(Comparator.comparingDouble(pokemon -> pokemon.getWeaknesses().size())).get();
//        //Pokemon con menos evoluciones
//        //Pokemon pokemonMenosEv = pokedex.getPokemon().stream().min(Comparator.comparingDouble(pokemon -> pokemon.getNextEvolution().size())).get();
//        //Pokemon cuya evolucion no es de tipo fire
//        //Pokemon pokemonNoEvFire = pokedex.getPokemon().stream().filter(pokemon -> !pokemon.getNextEvolution().contains("Fire")).toList().get(0);
//
//        //Pokemon mas Pesado
//        Pokemon pokemonMasPes = pokedex.getPokemon().stream().max(Comparator.comparingDouble(pokemon -> stringConverter.stringPositiveDoubleValueToDoubleParser(pokemon.getWeight()).orElse(0.0))).get();
//        //Pokemon con nombre mas largo
//        Pokemon pokemonNombreMasL = pokedex.getPokemon().stream().max(Comparator.comparing(pokemon -> pokemon.getName().length())).stream().toList().get(0);
//        //Pokemon mas alto
//        Pokemon pokemonMasAlto = pokedex.getPokemon().stream().max(Comparator.comparingDouble(pokemon -> stringConverter.stringPositiveDoubleValueToDoubleParser(pokemon.getHeight()).orElse(0.0))).get();
//        //Media de peso de Pokemon
//        double pokemonMediaPeso = pokedex.getPokemon().stream()
//                .mapToDouble(pokemon -> stringConverter.stringPositiveDoubleValueToDoubleParser(pokemon.getWeight()).orElse(0.0))
//                .average()
//                .orElse(0.0);
//
//        //Media de altura de Pokemon
//        double pokemonMediaAltura = pokedex.getPokemon().stream()
//                .mapToDouble(pokemon -> stringConverter.stringPositiveDoubleValueToDoubleParser(pokemon.getHeight()).orElse(0.0))
//                .average()
//                .orElse(0.0);
//        //Media de evoluciones de Pokemons
//        //double pokemonMediaEv = pokedex.getPokemon().stream().mapToDouble(pokemon -> pokemon.getNextEvolution().size()).average().orElseGet(() -> 0.0);
//        //Agrupar por tipo
//        Map<String, List<Pokemon>> pokeAgrupTip = pokedex.getPokemon().stream().collect(Collectors.groupingBy(pokemon -> pokemon.getType().toString()));
//        //Agrupar por numero de evoluciones
//        //Map<Integer, List<Pokemon>> pokeAgrupEv = pokedex.getPokemon().stream().collect(Collectors.groupingBy(pokemon -> pokemon.getNextEvolution().size()));
//        //Agrupar por debilidades
//        Map<String, List<Pokemon>> pokeAgrupWeak = pokedex.getPokemon().stream().collect(Collectors.groupingBy(pokemon -> pokemon.getWeaknesses().toString()));
//        //Debilidad mas comun
    }

    /**
     * Imprime por consola la siguiente evolución del Pokémon con el nombre indicado
     *
     * @param name Nombre del Pokémon
     */
    private static String printNextEvolutionByName(String name) {
        Optional<ArrayList<NextEvolution>> evCharmander = PokemonController.getInstance().getEvolutionsByPokemonName(name);
        String nextEvolution = "No se ha encontrado el Pokémon";
        if (evCharmander.isPresent()) {
            nextEvolution = evCharmander.get().get(0).toString();
        }
        return nextEvolution;
    }

    /**
     * Imprime por consola los datos del pokémon con el nombre indicado
     *
     * @param name Nombre del Pokémon
     */
    private String printPokemonByName(String name) {
        Optional<Pokemon> pikachuOpt = PokemonController.getInstance().getPokemonByName(name);
        String pikachu = "No se ha encontrado el Pokémon";
        if (pikachuOpt.isPresent()) {
            pikachu = pikachuOpt.get().toString();
        }
        return pikachu;
    }
}
