package com.madiben.exceptions;

/**
 * Excepción que representa un archivo no encontrado.
 */
public class FileNotFoundException extends RuntimeException{
    /**
     * Constructor de la excepción.
     * @param message Mensaje de error.
     */
    public FileNotFoundException(String message) {
        super("Archivo no encontrado: " + message);
    }
}
