package com.company;

public class Linnr extends Animal{
    HelplessHuman helplessHuman = new HelplessHuman();
    Milk milk = new Milk();

    public Linnr(String name, Gender gender){
        super(name, gender, Species.LINNR);
        this.setMaxOffspring(4);
        this.setHungerSatisfaction(8);
        addOnlyEat(helplessHuman);
        addOnlyEat(milk);
    }

}
