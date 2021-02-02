package com.company;

public class Kraken extends Animal{
    HelplessHuman helplessHuman = new HelplessHuman();
    Seaweed seaweed = new Seaweed();

    public Kraken(String name, Gender gender){
        super(name, gender, Species.KRAKEN);
        this.setMaxOffspring(5);
        this.setHungerSatisfaction(4);
        addOnlyEat(helplessHuman);
        addOnlyEat(seaweed);
    }

}
