package com.company;

import java.util.ArrayList;

public class Player {

    ArrayList<Animal>animals = new ArrayList<>();
    ArrayList<Food>food = new ArrayList<>();

    private String name;
    private int moneyAmount = 10;


    public Player(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getMoneyAmount(){
        return this.moneyAmount;
    }

    public void addMoneyAmount(int moneyChange){
        this.moneyAmount += moneyChange;
        // TODO Must be changed to add the amount of money the animal costs when sold

    }

    public ArrayList<Animal>getAnimals(){
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
