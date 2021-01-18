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

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getGender(){
        return gender;
    }

    public String getSpecies(){
        return this.getClass().getSimpleName();
    }//TODO check if working

    public void setOnlyEat(Food food){
        onlyEat.add(food);
    }

    public String getOnlyEat(){
        String onlyEats =this.getClass().getSimpleName() + "s only eats ";
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

        //TODO if player has two animals of same instance and opposite genders,
        // give 50% chance to have offspring
        // if they have offspring, give 50% chance of either gender being generated


        return tempAnimal;
    }

    public void feedAnimal(){
        //TODO If player has food item in foodList in correct amount
        // and animalsubclass has item in list of foods it eats
        // Let the animal eat x amount of food
        // and add 10 to health

    }

    public int loseHealth(){
        //TODO set lostHealth to random number between 10 and 30
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

    public void createOffspring(){
        Random random = new Random();
        int numberOfOffspring = 1 + random.nextInt(getMaxOffspring()+1);

        for(int i = 0; i<=numberOfOffspring; i++){
            //assign gender to offspring then prompt player for name
        }
    }

    public int getHealth(){
        return health;
    }

}
