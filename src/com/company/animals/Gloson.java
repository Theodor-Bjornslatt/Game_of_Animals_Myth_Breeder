package com.company.animals;

import com.company.enums.Gender;
import com.company.enums.Species;
import com.company.foods.HelplessHuman;

public class Gloson extends Animal {
    HelplessHuman helplessHuman = new HelplessHuman();

    public Gloson(String name, Gender gender){
        super(name, gender, Species.GLOSON);
        this.setMaxOffspring(4);
        this.setHungerSatisfaction(5);
        addOnlyEat(helplessHuman);
    }
}
