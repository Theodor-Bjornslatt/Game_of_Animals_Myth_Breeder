package com.company;

public class Tilberi extends Animal{

    public Tilberi(String name, Gender gender){
        super(name, gender, Species.TILBERI);
        this.setMaxOffspring(3);
        this.setHunger(15);
    }

}
