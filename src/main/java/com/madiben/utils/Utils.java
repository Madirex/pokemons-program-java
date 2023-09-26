package com.madiben.utils;

/**
 * Clase Utils que contiene métodos de utilidad
 */
public class Utils {

    /**
     * Constructor privado de la clase Utils
     */
    private Utils(){
        throw new IllegalStateException("Utility class");
    }

    public static final String SEPARATOR = "\n----------------------------------------\n";

    /**
     * Imprime un mensaje por consola
     *
     * @param message Mensaje a imprimir
     */
    public static void print(String message) {
        System.out.println(message);
    }
}
