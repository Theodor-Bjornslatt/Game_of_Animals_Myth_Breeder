package com.company;
import java.util.Random;
import java.util.ArrayList;
import java.io.Serializable;

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

enum HealthStatus{
    HEALTHY,
    DISEASED
}

public abstract class Animal implements Serializable {
    private final ArrayList<FoodType>onlyEat = new ArrayList<>();

    private final String name;
    private final Gender gender;
    private final Species species;
    private HealthStatus healthStatus = HealthStatus.HEALTHY;
    private int hunger;
    private int health = 100;
    private int healthChange = 0;
    private int maxOffspring;

    public Animal(String name, Gender gender, Species species){
        this.name = name;
        this.gender = gender;
        this.species = species;
    }

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
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
        Random randomNum = new Random();
        healthChange = randomNum.nextInt(21) + 10;
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
