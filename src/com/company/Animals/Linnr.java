package com.company.Animals;

import com.company.Enums.Gender;
import com.company.Enums.Species;
import com.company.Foods.HelplessHuman;
import com.company.Foods.Milk;

public class Linnr extends Animal {
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
