package com.company;

import java.util.ArrayList;
import java.util.List;

public class Player {

    ArrayList<Animal>animals = new ArrayList<>();
    private Seaweed seaweed = new Seaweed();
    private Milk milk = new Milk();
    private HelplessHuman helplessHuman = new HelplessHuman();
    private ArrayList<Food> foods = new ArrayList<>(List.of(seaweed, milk, helplessHuman));

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
}
