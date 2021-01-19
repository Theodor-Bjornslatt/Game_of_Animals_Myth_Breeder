package com.company;

import java.util.ArrayList;

public class Player {

    ArrayList<Animal>animals = new ArrayList<>();
    ArrayList<Food>food = new ArrayList<>();
    Seaweed seaweed = new Seaweed();
    Milk milk = new Milk();
    HelplessHuman helplessHuman = new HelplessHuman();

    private String name;
    private int goldAmount = 0;


    public Player(String name){
        this.name = name;
        // Add all possible food types to the players list
        // of foods (default amount is 0 kg)
        food.add(seaweed);
        food.add(milk);
        food.add(helplessHuman);
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

    public void addAnimal(Animal animal){
        this.animals.add(animal);
    }

    public void removeAnimal(int index){
        this.animals.remove(index);
    }

    public ArrayList<Food> getFoodList(){
        return this.food;
    }

    public void addFood(Food food){
        this.food.add(food);
    }

    public void removeFood(int index){
        this.food.remove(index);
    }
}
