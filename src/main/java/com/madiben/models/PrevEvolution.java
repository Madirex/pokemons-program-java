package com.madiben.models;

import lombok.Data;

//@Data
public class PrevEvolution {
    private String num;
    private String name;

    @Override
    public String toString() {
        return "PrevEvolution{" +
                "num='" + num + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}