package com.company;

// TODO
//  If double reaches 0, remove foodItem from list completely

//  TODO Make method to add one item to food by adding one to the integer
//   If the list does not already contain the food
//   add the item and set integer to 1
enum FOOD{
    SEAWEED("Seaweed"),
    MILK("Milk"),
    HELPLESS_HUMAN("Helpless human");

    String foodType;
    private FOOD(String foodType){
        this.foodType = foodType;
    }
}
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
