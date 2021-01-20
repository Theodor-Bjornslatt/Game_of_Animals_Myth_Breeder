package com.company;

public class Gloson extends Animal{

    public Gloson(String name, Gender gender){
        super(name, gender, Species.GLOSON);
        this.setMaxOffspring(7);
        this.setHunger(5);
        addOnlyEat(FoodType.HELPLESS_HUMAN);
    }
}
