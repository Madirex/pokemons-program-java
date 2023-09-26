package com.madiben.models;

import lombok.Getter;

import java.util.List;

/**
 * Clase Pokédex que contiene la lista de Pokémon
 */
@Getter
public class Pokedex {
    private List<Pokemon> pokemon;
}
