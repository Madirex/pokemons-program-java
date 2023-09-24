package com.madiben.dto;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PokemonDataDTO {
    private int id;
    private String num;
    private String name;
    private double height;
    private double weight;
}