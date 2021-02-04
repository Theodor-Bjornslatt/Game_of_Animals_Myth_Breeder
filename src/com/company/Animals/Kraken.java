package com.company.Animals;

import com.company.Enums.Gender;
import com.company.Enums.Species;
import com.company.Foods.HelplessHuman;
import com.company.Foods.Seaweed;

public class Kraken extends Animal {
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
