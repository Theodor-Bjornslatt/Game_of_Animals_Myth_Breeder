package com.company;

import java.util.ArrayList;

public class Player {

    ArrayList<Animal>animals = new ArrayList<>();
    ArrayList<Food>food = new ArrayList<>();

    private String name;
    private int goldAmount = 0;


    public Player(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getGoldAmount(){
        return this.goldAmount;
    }

    public void setGoldAmount(int goldAmount){
        this.goldAmount = goldAmount;
        // TODO Must be changed to add the amount of money the animal costs when sold

    }

    public void addGold(int goldAdded){
        this.goldAmount += goldAdded;
    }

    public void removeGold(int goldRemoved){
        this.goldAmount -= goldRemoved;
    }

    public ArrayList<Animal> getAnimalList(){
        return this.animals;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

    public void addAnimal(Animal animal){
        this.animals.add(animal);
    }

    public void removeAnimal(Animal animal){
        this.animals.remove(animal);
    }
}
