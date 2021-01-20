package com.company;

public class Linnr extends Animal{

    public Linnr(String name, Gender gender){
            super(name, gender, Species.LINNR);
            this.setMaxOffspring(6);
            this.setHunger(8);
    }

}
