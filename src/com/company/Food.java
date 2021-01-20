package com.company;

// TODO
//  If double reaches 0, remove foodItem from list completely

//  TODO Make method to add one item to food by adding one to the integer
//   If the list does not already contain the food
//   add the item and set integer to 1
enum FoodType {
    SEAWEED("Seaweed", 2.0),
    MILK("Milk",1.0),
    HELPLESS_HUMAN("Helpless human",3.0);

    String foodType;
    FoodType(String foodType, double foodValue){
        this.foodType = foodType;
        this.foodValue = foodValue;
    }

    public String string(){
        return foodType;
    }

    double foodValue;
    public double getFoodValue(){
        return foodValue;
    }
}
public abstract class Food {

    private final FoodType foodType;
    private double foodAmount;

    public Food(FoodType foodType){
        this.foodType = foodType;
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
