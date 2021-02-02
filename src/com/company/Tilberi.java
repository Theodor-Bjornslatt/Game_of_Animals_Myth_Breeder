package com.company;

public class Tilberi extends Animal{
    Milk milk = new Milk();

    public Tilberi(String name, Gender gender){
        super(name, gender, Species.TILBERI);
        this.setMaxOffspring(2);
        this.setHungerSatisfaction(20);
        addOnlyEat(milk);
    }

}
