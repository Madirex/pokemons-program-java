package com.madiben.exceptions.pokemon;

/**
 * Clase abstracta que representa una excepción de Pokemon.
 */
public abstract class PokemonException extends RuntimeException {
    /**
     * Constructor de la excepción.
     * @param message Mensaje de error.
     */
    protected PokemonException(String message) {
        super(message);
    }
}