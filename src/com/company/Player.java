package com.company;

import java.util.ArrayList;
import java.util.List;

public class Player {

    ArrayList<Animal>animals = new ArrayList<>();
    private static Seaweed seaweed = new Seaweed();
    private static Milk milk = new Milk();
    private static HelplessHuman helplessHuman = new HelplessHuman();
    private final static ArrayList<Food> foods = new ArrayList<>(List.of(seaweed, milk, helplessHuman));

    private String name;
    private int goldAmount = 0;


    public Player(String name){
        this.name = name;
        // Add all possible food types to the players list
        // of foods (default amount is 0 kg)
    }

    public String getName(){
        return this.name;
    }

    public int getGoldAmount(){
        return this.goldAmount;
    }

    public void setGoldAmount(int goldAmount){
        this.goldAmount = goldAmount;
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
        return this.foods;
    }

    public void addFood(Food food){
        this.foods.add(food);
    }

    public void removeFood(int index){
        this.foods.remove(index);
    }
}
