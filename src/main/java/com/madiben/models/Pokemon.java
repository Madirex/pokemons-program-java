package com.madiben.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import java.util.ArrayList;

@Getter
public class Pokemon {
    private int id;

    private String num;

    private String name;

    private String img;

    private ArrayList<String> type;

    private String height;

    private String weight;

    private String candy;

    @SerializedName("candy_count")
    private int candy_count;

    private String egg;

    @SerializedName("spawn_chance")
    private double spawn_chance;

    @SerializedName("avg_spawns")
    private double avg_spawns;

    @SerializedName("spawn_time")
    private String spawn_time;

    private ArrayList<Double> multipliers;

    private ArrayList<String> weaknesses;

    @SerializedName("next_evolution")
    private ArrayList<NextEvolution> next_evolution;

    @SerializedName("prev_evolution")
    private ArrayList<PrevEvolution> prev_evolution;

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", num='" + num + '\'' +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", type=" + type +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", candy='" + candy + '\'' +
                ", candy_count=" + candy_count +
                ", egg='" + egg + '\'' +
                ", spawn_chance=" + spawn_chance +
                ", avg_spawns=" + avg_spawns +
                ", spawn_time='" + spawn_time + '\'' +
                ", multipliers=" + multipliers +
                ", weaknesses=" + weaknesses +
                ", next_evolution=" + next_evolution +
                ", prev_evolution=" + prev_evolution +
                '}';
    }
}

