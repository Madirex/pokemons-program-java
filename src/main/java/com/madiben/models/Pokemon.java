package com.madiben.models;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;

import java.util.ArrayList;

//@Getter
//@Builder
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
    private int candyCount;

    private String egg;

    @SerializedName("spawn_chance")
    private double spawnChance;

    @SerializedName("avg_spawns")
    private double avgSpawns;

    @SerializedName("spawn_time")
    private String spawnTime;

    private ArrayList<Double> multipliers;

    private ArrayList<String> weaknesses;

    @SerializedName("next_evolution")
    private ArrayList<NextEvolution> nextEvolution;

    @SerializedName("prev_evolution")
    private ArrayList<PrevEvolution> prevEvolution;

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

    public int getId() {
        return id;
    }

    public String getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public ArrayList<String> getType() {
        return type;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getCandy() {
        return candy;
    }

    public int getCandyCount() {
        return candyCount;
    }

    public String getEgg() {
        return egg;
    }

    public double getSpawnChance() {
        return spawnChance;
    }

    public double getAvgSpawns() {
        return avgSpawns;
    }

    public String getSpawnTime() {
        return spawnTime;
    }

    public ArrayList<Double> getMultipliers() {
        return multipliers;
    }

    public ArrayList<String> getWeaknesses() {
        return weaknesses;
    }

    public ArrayList<NextEvolution> getNextEvolution() {
        return nextEvolution;
    }

    public ArrayList<PrevEvolution> getPrevEvolution() {
        return prevEvolution;
    }
}

