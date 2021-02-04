package com.company.Animals;

import com.company.Enums.Gender;
import com.company.Enums.Species;
import com.company.Foods.HelplessHuman;
import com.company.Foods.Seaweed;

public class Nykur extends Animal {
    HelplessHuman helplessHuman = new HelplessHuman();
    Seaweed seaweed = new Seaweed();

    public Nykur(String name, Gender gender){
        super(name, gender, Species.NYKUR);
        this.setMaxOffspring(3);
        this.setHungerSatisfaction(10);
        addOnlyEat(helplessHuman);
        addOnlyEat(seaweed);
    }

}
