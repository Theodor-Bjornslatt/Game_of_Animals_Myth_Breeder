package com.company.Enums;

public enum FoodType {
    SEAWEED("Seaweed", 2.0),
    MILK("Milk", 1.0),
    HELPLESS_HUMAN("Helpless human", 3.0);

    public String foodType;
    public double foodValue;

    FoodType(String foodType, double foodValue) {
        this.foodType = foodType;
        this.foodValue = foodValue;
    }
}
