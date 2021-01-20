package com.company;
import java.util.Random;

import java.util.ArrayList;

enum Gender{
    MALE("Male"),
    FEMALE("Female");

    String gender;
    private Gender(String gender){
        this.gender = gender;
    }
}

public abstract class Animal {
    private ArrayList<Food>onlyEat = new ArrayList<>();

    private String name;
    private String gender;
    private int health = 100;
    private int healthChange = 0;
    private int maxOffspring;

    public Animal(String name, String gender){
        this.name = name;
        this.gender = gender;
    }

    public void setMaxOffspring(int offspring){
        this.maxOffspring = offspring;
    }

    public int getMaxOffspring(){
        return maxOffspring;
    }

    public String getName(){
        return name;
    }


    public void setHealth(int health) {
        this.health = health;
    }

    public String getGender(){
        return gender;
    }

    public String getSpecies(){
        return this.getClass().getSimpleName();
    }

    public void setOnlyEat(Food food){
        onlyEat.add(food);
    }

    public String getOnlyEat(){
        String onlyEats =this.getClass().getSimpleName() + "s only eat: ";
        // For each food in list onlyEat, get string value of class;
        for(Food food : onlyEat){
            String next = "\n" + food.getClass().toString();
            onlyEats += next;
        }
        return onlyEats;
    }

    public void feedAnimal(){
        //TODO If player has food item in foodList in correct amount
        // and animalsubclass has item in list of foods it eats
        // Let the animal eat x amount of food
        // and add 10 to health

    }

    public void loseHealth(){
        // Make the lost amount of health be a random number from 10 to 30
        Random randomNum = new Random();
        healthChange = randomNum.nextInt(21) + 10;
        // If lostHealth is bigger than health, return
        // remaining health as lostHealth and let health reach zero
        // Else, subtract lostHealth from health
        if(healthChange >= health){
            healthChange = health;
            health = 0;
        }
        else{
            health -= healthChange;
        }
    }

    public int getLostHealth(){
        return healthChange;
    }


    public int getHealth(){
        return health;
    }

}
