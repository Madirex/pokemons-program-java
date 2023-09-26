package com.madiben.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Clase PokemonDataDTO que representa los datos de un Pokémon
 */
@Data
@Builder
public class PokemonDataDTO {
    @ToString.Exclude
    private long id;
    private String num;
    private String name;
    private double height;
    private double weight;
}