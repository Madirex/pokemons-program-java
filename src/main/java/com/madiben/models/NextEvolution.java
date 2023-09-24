package com.madiben.models;

import lombok.Data;

//@Data
public class NextEvolution {
    private String num;
    private String name;

    @Override
    public String toString() {
        return "NextEvolution{" +
                "num='" + num + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }
}
