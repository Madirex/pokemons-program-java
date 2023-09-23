package com.madiben;

import com.madiben.controller.PokemonController;
import com.madiben.models.NextEvolution;
import com.madiben.models.Pokemon;
import com.madiben.utils.StringConverters;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        //TODO: Implementar la lógica de la aplicación
        PokemonController.getInstance().loadPokedex();
        PokemonProgram.getInstance().init();
    }
}