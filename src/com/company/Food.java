package com.company;

// TODO
//  If double reaches 0, remove foodItem from list completely

//  TODO Make method to add one item to food by adding one to the integer
//   If the list does not already contain the food
//   add the item and set integer to 1
enum FoodType {
    SEAWEED("Seaweed"),
    MILK("Milk"),
    HELPLESS_HUMAN("Helpless human");

    String foodType;
    FoodType(String foodType){
        this.foodType = foodType;
    }

    public String string(){
        return foodType;
    }
}
public abstract class Food {

    private FoodType foodType;
    private double foodAmount = 0;

    public Food(String foodType){
        this.foodType = FoodType.valueOf(foodType.toUpperCase());
    }

    public String getFoodType(){
        return foodType.string();
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
