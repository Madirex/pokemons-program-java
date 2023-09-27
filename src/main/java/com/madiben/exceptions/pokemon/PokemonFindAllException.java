package com.madiben.exceptions.pokemon;

/**
 * Excepción que se lanza cuando no se puede realizar consulta findAll.
 */
public class PokemonFindAllException extends PokemonException{
    /**
     * Constructor de la excepción.
     * @param message Mensaje de error.
     */
    public PokemonFindAllException(String message) {
        super("Error al buscar todos los Pokémon: " + message);
    }
}
