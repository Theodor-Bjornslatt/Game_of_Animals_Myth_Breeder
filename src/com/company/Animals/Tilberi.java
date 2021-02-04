package com.company.Animals;

import com.company.Enums.Gender;
import com.company.Enums.Species;
import com.company.Foods.Milk;

public class Tilberi extends Animal {
    Milk milk = new Milk();

    public Tilberi(String name, Gender gender){
        super(name, gender, Species.TILBERI);
        this.setMaxOffspring(2);
        this.setHungerSatisfaction(20);
        addOnlyEat(milk);
    }

}
