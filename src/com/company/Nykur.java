package com.company;

public class Nykur extends Animal{

    public Nykur(String name, Gender gender){
        super(name, gender, Species.NYKUR);
        this.setMaxOffspring(5);
        this.setHunger(12);
    }

}
