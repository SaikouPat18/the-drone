package com.example.the_drone.enums;

public enum DroneModel {
    LIGHTWEIGHT(500),
    MIDDLEWEIGHT(750),
    CRUISERWEIGHT(1000),
    HEAVYWEIGHT(1500);

    private final int weightLimit; // Weight limit in grams

    DroneModel(int weightLimit) {
        this.weightLimit = weightLimit;
    }

    public int getWeightLimit() {
        return weightLimit;
    }
}