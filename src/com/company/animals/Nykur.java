package com.company.animals;

import com.company.enums.Gender;
import com.company.enums.Species;
import com.company.foods.Seaweed;

public class Nykur extends Animal {
    Seaweed seaweed = new Seaweed();

    public Nykur(String name, Gender gender){
        super(name, gender, Species.NYKUR);
        this.setMaxOffspring(3);
        this.setHungerSatisfaction(10);
        addOnlyEat(seaweed);
    }

}
