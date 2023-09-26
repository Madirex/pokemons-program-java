package com.madiben.dto;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;


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