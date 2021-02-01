package com.company;

public class Tilberi extends Animal{
    Milk milk = new Milk();

    public Tilberi(String name, Gender gender){
        super(name, gender, Species.TILBERI);
        this.setMaxOffspring(3);
        this.setHunger(15);
        addOnlyEat(milk);
    }

}
