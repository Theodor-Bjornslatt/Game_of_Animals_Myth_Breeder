package com.company;

public class Gloson extends Animal{
    HelplessHuman helplessHuman = new HelplessHuman();

    public Gloson(String name, Gender gender){
        super(name, gender, Species.GLOSON);
        this.setMaxOffspring(7);
        this.setHungerSatisfaction(5);
        addOnlyEat(helplessHuman);
    }
}
