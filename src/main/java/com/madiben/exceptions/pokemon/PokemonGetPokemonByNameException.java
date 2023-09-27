package com.madiben.exceptions.pokemon;

/**
 * Excepción que se lanza cuando no se encuentra un Pokémon por nombre.
 */
public class PokemonGetPokemonByNameException extends PokemonException{
    /**
     * Constructor de la excepción.
     * @param message Mensaje de error.
     */
    public PokemonGetPokemonByNameException(String message) {
        super("Error al buscar Pokémon por nombre: " + message);
    }
}
