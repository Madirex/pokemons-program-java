package com.madiben.exceptions.pokedex;

/**
 * Excepción que representa un error al tratar de cargar la Pokédex
 */
public class PokedexLoadException extends PokedexException {
    /**
     * Constructor de la excepción.
     *
     * @param message Mensaje de error.
     */
    public PokedexLoadException(String message) {
        super("Error al intentar cargar los datos de la Pokédex: " + message);
    }
}
