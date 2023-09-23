package com.madiben.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PokemonDataDTO {
    private int id;
    private String num;
    private double height;
    private double width;

    /**
     * Constructor de la clase PokemonDataDTO
     *
     * @param num    Número del pokemon
     * @param height Altura del pokemon
     * @param width  Peso del pokemon
     */
    public PokemonDataDTO(String num, double height, double width) {
        this.num = num;
        this.height = height;
        this.width = width;
    }
}
