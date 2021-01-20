package com.company;

public class Kraken extends Animal{

    public Kraken(String name, Gender gender){
        super(name, gender, Species.KRAKEN);
        this.setMaxOffspring(7);
        this.setHunger(5);
    }

}
