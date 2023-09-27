package com.madiben;

import com.madiben.controller.PokemonController;

/**
 * Clase Main
 */
public class Main {
    /**
     * Método Main
     * @param args Arguments
     */
    public static void main(String[] args) {
        PokemonController.getInstance().loadPokedex();
        PokemonProgram.getInstance().init();
    }
}