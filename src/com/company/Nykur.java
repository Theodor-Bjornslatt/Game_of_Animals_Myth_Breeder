package com.company;

public class Nykur extends Animal{
    HelplessHuman helplessHuman = new HelplessHuman();
    Seaweed seaweed = new Seaweed();

    public Nykur(String name, Gender gender){
        super(name, gender, Species.NYKUR);
        this.setMaxOffspring(5);
        this.setHunger(10);
        addOnlyEat(helplessHuman);
        addOnlyEat(seaweed);
    }

}
