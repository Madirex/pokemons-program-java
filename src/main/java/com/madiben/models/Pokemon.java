package com.madiben.models;


import lombok.Data;

import java.util.ArrayList;

@Data
public class Pokemon {
    private int id;
    private String num;
    private String name;
    private String img;
    private ArrayList<String> type;
    private String height;
    private String weight;
    private String candy;
    private int candy_count;
    private String egg;
    private double spawn_chance;
    private double avg_spawns;
    private String spawn_time;
    private ArrayList<Double> multipliers;
    private ArrayList<String> weaknesses;
    private ArrayList<NextEvolution> next_evolution;
    private ArrayList<PrevEvolution> prev_evolution;

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

    public int getCandy_count() {
        return candy_count;
    }

    public String getEgg() {
        return egg;
    }

    public double getSpawn_chance() {
        return spawn_chance;
    }

    public double getAvg_spawns() {
        return avg_spawns;
    }

    public String getSpawn_time() {
        return spawn_time;
    }

    public ArrayList<Double> getMultipliers() {
        return multipliers;
    }

    public ArrayList<String> getWeaknesses() {
        return weaknesses;
    }

    public ArrayList<NextEvolution> getNext_evolution() {
        return next_evolution;
    }

    public ArrayList<PrevEvolution> getPrev_evolution() {
        return prev_evolution;
    }

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

