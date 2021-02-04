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
}

enum HealthStatus{
    HEALTHY,
    DISEASED
}

public abstract class Animal implements Serializable {
    private final ArrayList<Food>onlyEat = new ArrayList<>();

    private final String name;
    private final Gender gender;
    private final Species species;
    private HealthStatus healthStatus = HealthStatus.HEALTHY;
    private int hungerSatisfaction;
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

    public void setHungerSatisfaction(int hungerSatisfaction){
        this.hungerSatisfaction = hungerSatisfaction;
    }

    public int getHungerSatisfaction(){
        return hungerSatisfaction;
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

    public Gender getGender(){
        return gender;
    }

    public void addOnlyEat(Food food){
        onlyEat.add(food);
    }

    public ArrayList<Food> getOnlyEat(){
        return onlyEat;
    }

    public int gainHealth(int healthChange){
        int restoredHealth;
        if(health + healthChange > 100){
            restoredHealth = 100 - health;
            health = 100;
        }
        else{
            health += healthChange;
            restoredHealth = healthChange;
        }
        return restoredHealth;
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
