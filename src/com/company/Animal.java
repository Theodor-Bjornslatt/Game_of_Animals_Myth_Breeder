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
    private int hunger;
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

    public Species getSpecies(){
        return species;
    }

    public String getGender(){
        return gender.string();
    }

    public void addOnlyEat(FoodType foodtype){
        onlyEat.add(foodtype);
    }

    public ArrayList<FoodType> getOnlyEat(){
        return onlyEat;
    }

    public void gainHealth(int healthChange){
        if(health + healthChange > 100){
            health = 100;
        }
        else{
            health += healthChange;
        }
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
