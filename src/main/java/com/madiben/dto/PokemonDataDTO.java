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
}
//TODO: Exporta a csv los siguientes datos de pokemons: id, num, name, height, width.
