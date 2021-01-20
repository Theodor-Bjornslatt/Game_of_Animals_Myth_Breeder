package com.company;
import java.util.Random;
import java.util.ArrayList;

enum Gender{
    MALE("Male"),
    FEMALE("Female");

    String gender;
    Gender(String gender){
        this.gender = gender;
    }

    public String string() {
        return gender;
    }
}

enum Species{
    NYKUR("Nykur"),
    GLOSON("Gloson"),
    KRAKEN("Kraken"),
    LINNR("Linnr"),
    TILBERI("Tilberi");

    String species;
    Species (String species){
        this.species=species;
    }

    public String string(){
        return species;
    }
}

public abstract class Animal {
    private ArrayList<FoodType>onlyEat = new ArrayList<>();

    private String name;
    private Gender gender;
    private Species species;
    private int hunger = 0;
    private int health = 100;
    private int healthChange = 0;
    private int maxOffspring;

    public Animal(String name, Gender gender, Species species){
        this.name = name;
        this.gender = gender;
        this.species = species;
    }

    public void setHunger(int hunger){
        this.hunger = hunger;
    }

    public int getHunger(){
        return hunger;
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

    public String getSpecies(){
        return species.string();
    }


    public void setHealth(int health) {
        this.health = health;
    }

    public String getGender(){
        return gender.string();
    }

    public void setOnlyEat(String foodtype){
        onlyEat.add(FoodType.valueOf(foodtype.toUpperCase()));
    }

    public String getOnlyEat(){
        String onlyEats =this.getClass().getSimpleName() + "s only eat: ";
        // For each food in list onlyEat, get string value of class;
        for(FoodType foodType : onlyEat){
            String next = "\n" + foodType.toString() + " ";
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
