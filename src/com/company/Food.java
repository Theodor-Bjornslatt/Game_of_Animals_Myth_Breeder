package com.company;

import java.io.Serializable;

enum FoodType {
    SEAWEED("Seaweed", 2.0),
    MILK("Milk",1.0),
    HELPLESS_HUMAN("Helpless human",3.0);

    String foodType;
    FoodType(String foodType, double foodValue){
        this.foodType = foodType;
        this.foodValue = foodValue;
    }

    double foodValue;
}

public abstract class Food implements Serializable {

    private final FoodType foodType;
    private double foodAmount = 0;

    public Food(FoodType foodType){
        this.foodType = foodType;
    }

    public FoodType getFoodType(){
        return foodType;
    }

    public double getFoodAmount(){
        return foodAmount;
    }

    public static void addFood(Food chosenFood, double foodAmount){
        for(Food food : Game.getCurrentPlayer().getFoodList()){
            if(chosenFood.getFoodType() == food.getFoodType()){
                food.foodAmount += foodAmount;
                return;
            }
        }
        chosenFood.foodAmount = foodAmount;
        Game.getCurrentPlayer().getFoodList().add(chosenFood);
    }

    public static boolean removeFood(Food chosenFood, double foodAmount){
        for(Food food : Game.getCurrentPlayer().getFoodList()){
            if(chosenFood.getFoodType() == food.getFoodType()){
                if(food.foodAmount == 0){
                    return false;
                }
                food.foodAmount -= foodAmount;
                if(food.foodAmount<=0){
                    Game.getCurrentPlayer().getFoodList().remove(food);
                }
                return true;
            }
        }
        return false;
    }


}
