package com.company;

public class Nykur extends Animal{

    public Nykur(String name, Gender gender){
        super(name, gender, Species.NYKUR);
        this.setMaxOffspring(5);
        this.setHunger(10);
        addOnlyEat(FoodType.HELPLESS_HUMAN);
        addOnlyEat(FoodType.SEAWEED);
    }

}
