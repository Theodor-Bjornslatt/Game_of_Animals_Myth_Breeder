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
    public void setMoneyAmount(){
        this.moneyAmount += 10;

    }
}
