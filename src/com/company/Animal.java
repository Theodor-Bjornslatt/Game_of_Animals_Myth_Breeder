package com.company;

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
    int cost;

    public Animal(String name, String gender){
        this.name = name;
        this.gender = gender;
    }

    public void setOnlyEat(Food food){
        onlyEat.add(food);
    }

    public String getOnlyEat(){
        String onlyEats =this.getClass().toString() + " only eats ";
        // For each food in list onlyEat, get string value of class;
        for(Food food : onlyEat){
            String next = food.getClass().toString();
            onlyEats += next;
        }
        return onlyEats;
    }

    public Animal mateAnimals(){
        //Create an instance of an object to return, with null as placeholder
        Animal tempAnimal = null;

        // if player has two animals of same instance and opposite genders,
        // give 50% chance to have offspring
        // if they have offspring, give 50% chance of either gender being generated


        return tempAnimal;
    }

    public void feedAnimal(){
        // If player has food item in foodList in correct amount

        // and animalsubclass has item in list of foods it eats

        // Let the animal eat x amount of food

        // and add 10 to health

    }

    public int loseHealth(){
        //set lostHealth to random number between 10 and 30
        int lostHealth = 10;
        // If lostHealth is bigger than health, return
        // remaining health as lostHealth and let health reach zero
        // Else, subtract lostHealth from health
        if(lostHealth >= health){
            lostHealth = health;
            this.health = 0;
        }
        else{
            this.health -= lostHealth;
        }
        return lostHealth;
    }

}
