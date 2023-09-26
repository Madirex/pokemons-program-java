package com.madiben.exceptions.pokedex;

/**
 * Excepción que representa la Pokédex
 */
public abstract class PokedexException extends RuntimeException{
    /**
     * Constructor de la excepción.
     * @param message Mensaje de error.
     */
    public PokedexException(String message) {
        super("Fallo al tratar los datos de la Pokédex: " + message);
    }
}
