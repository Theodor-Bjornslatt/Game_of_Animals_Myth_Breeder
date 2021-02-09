package com.company.enums;

public enum FoodType {
    SEAWEED("Seaweed", 2.0, 6),
    MILK("Milk", 1.0, 4),
    HELPLESS_HUMAN("Helpless human", 3.0, 8);

    public String foodType;
    public double foodValue;
    public int price;

    FoodType(String foodType, double foodValue, int price) {
        this.foodType = foodType;
        this.foodValue = foodValue;
        this.price = price;
    }
}
