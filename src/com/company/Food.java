package com.company;

import java.util.ArrayList;

// TODO Add integer to keep track of amounts of the same food
//  Make method to remove one item from food by reducing
//  double keeping track of food
//  If double reaches 0, remove foodItem from list completely

//  TODO Make method to add one item to food by adding one to the integer
//   If the list does not already contain the food
//   add the item and set integer to 1

public abstract class Food {
    private String foodType;
    private double foodAmount = 0;

    public Food(String foodType){
        this.foodType = foodType;
    }

    public String getFoodType(){
        return foodType;
    }

    public double getFoodAmount(){
        return foodAmount;
    }

    public void addFoodAmount(double foodAmount){
        this.foodAmount += foodAmount;
    }

    public void removeFoodAmount(double foodAmount){
        this.foodAmount -= foodAmount;
    }


}
