package com.company.animals;

import com.company.enums.Gender;
import com.company.enums.Species;
import com.company.foods.Milk;

public class Tilberi extends Animal {
    Milk milk = new Milk();

    public Tilberi(String name, Gender gender){
        super(name, gender, Species.TILBERI);
        this.setMaxOffspring(3);
        this.setHungerSatisfaction(20);
        addOnlyEat(milk);
    }

}
