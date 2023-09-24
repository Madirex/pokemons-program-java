package com.madiben.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;

@Getter
public class Pokemon {
    private int id;

    private String num;

    private String name;

    private String img;

    @NonNull
    private ArrayList<String> type;

    private String height;

    private String weight;

    private String candy;

    @SerializedName("candy_count")
    private int candyCount;

    private String egg;

    @SerializedName("spawn_chance")
    private double spawnChance;

    @SerializedName("avg_spawns")
    private double avgSpawns;

    @SerializedName("spawn_time")
    private String spawnTime;

    @NonNull
    private ArrayList<Double> multipliers;

    @NonNull
    private ArrayList<String> weaknesses;

    @SerializedName("next_evolution")
    @NonNull
    private ArrayList<NextEvolution> nextEvolution;

    @SerializedName("prev_evolution")
    @NonNull
    private ArrayList<PrevEvolution> prevEvolution;

    public Pokemon() {
        this.type = new ArrayList<>();
        this.multipliers = new ArrayList<>();
        this.weaknesses = new ArrayList<>();
        this.nextEvolution = new ArrayList<>();
        this.prevEvolution = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "\nPokemon{" +
                "id=" + id +
                ", num='" + num + '\'' +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", type=" + type +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", candy='" + candy + '\'' +
                ", candy_count=" + candyCount +
                ", egg='" + egg + '\'' +
                ", spawn_chance=" + spawnChance +
                ", avg_spawns=" + avgSpawns +
                ", spawn_time='" + spawnTime + '\'' +
                ", multipliers=" + multipliers +
                ", weaknesses=" + weaknesses +
                ", next_evolution=" + nextEvolution +
                ", prev_evolution=" + prevEvolution +
                '}';
    }

}

