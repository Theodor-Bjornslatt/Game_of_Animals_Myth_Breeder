package com.company.maingame;

import com.company.animals.*;
import com.company.enums.FoodType;
import com.company.enums.Gender;
import com.company.enums.Species;
import com.company.foods.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Store implements Serializable {

    private Animal tempAnimal;
    private Food tempFood;

    private Species chosenSpecies;
    private Gender chosenGender;
    private int goldToPay;
    private double wantedFoodAmount;

    private final String shopKeeper = "Shopkeeper Lucifer";
    private int unsuccessfulAttempts;
    private boolean leaveStore;
    private boolean madeChange;

    public void goToAnimalStore(){
        leaveStore=false;
        tempFood = null;
        madeChange = false;
        unsuccessfulAttempts = 0;
        Helper.clearConsole();
        System.out.println("WELCOME TO MYTH STORE ANIMAL DEPARTMENT, " +
                Game.getCurrentPlayer().getName().toUpperCase() + "!");
        do{
            Game.printPlayerStats();

            System.out.println("\nWhich animal do you want to buy? \n" +
                               "|1| Nykur....." + Species.NYKUR.price + " Gold \n" +
                               "|2| Gloson...." + Species.GLOSON.price + " Gold \n" +
                               "|3| Kraken...." + Species.KRAKEN.price + " Gold \n" +
                               "|4| Linnr....." + Species.LINNR.price + " Gold \n" +
                               "|5| Tilberi..." + Species.TILBERI.price + " Gold\n" +
                               "|6| Leave store\n");

            Helper.tryParseInt("", 1, 6);

            animalStoreSwitch();

            if(!leaveStore){
                if(Game.getCurrentPlayer().getGoldAmount() >= goldToPay){
                    buyAnimal();
                }
                else{
                    notEnoughGold();
                }

            }
        }while(!leaveStore);
    }

    public void animalStoreSwitch(){
        Helper.clearConsole();
        switch (Helper.getInputInt()) {
            case 1 -> {
                chosenSpecies = Species.NYKUR;
                System.out.println(shopKeeper + ":\nGood choice my fine friend. " +
                        "Just don't go swimming with it. I like my customers alive, " +
                        Game.getCurrentPlayer().getName());
            }
            case 2 -> {
                chosenSpecies = Species.GLOSON;
                System.out.println(shopKeeper + ":\nLovely choice. Try not to pet it though, " +
                        Game.getCurrentPlayer().getName() +
                        ", I don't want to see you get hurt. ");
            }
            case 3 -> {
                chosenSpecies = Species.KRAKEN;
                System.out.println(shopKeeper + ":\nAh, I see you have great taste, " +
                        Game.getCurrentPlayer().getName() + "! " +
                        "Watch out for it's ravenous appetite though. ");
            }
            case 4 -> {
                chosenSpecies = Species.LINNR;
                System.out.println(shopKeeper + ":\nExcellent choice " +
                        Game.getCurrentPlayer().getName() + ". But be advised " +
                        "that this is not an enchanted prince. ");
            }
            case 5 -> {
                chosenSpecies = Species.TILBERI;
                System.out.println(shopKeeper + ":\nTruly inspired choice, " +
                        Game.getCurrentPlayer().getName() + ". " +
                        "\n Remember not to bring it home to a friend... " +
                        "if you want to keep your friends that is.");
            }
            case 6 -> {
                leaveStore();
                return;
            }
        }
        goldToPay = chosenSpecies.price;
    }

    public void leaveStore(){
        Helper.clearConsole();
        if(!madeChange){
            System.out.println("\n" + shopKeeper + ":\nDon't let the door hit you on the way out!");
            Helper.setValidChoice(false);
        }
        else{
            System.out.println("\n" + shopKeeper + ":\nThank you for your patronage, " +
                    Game.getCurrentPlayer().getName() + ". Do come back again soon!");
            Helper.setValidChoice(true);
        }
        leaveStore = true;
    }

    public void notEnoughGold(){
        unsuccessfulAttempts += 1;
        if(unsuccessfulAttempts<=3){
            System.out.println("\n" + shopKeeper + ":\nHang on! Don't try to cheat me! " +
                    "You don't have that much gold!");
        }
        else{
            System.out.println("\n" + shopKeeper + ":\n Wait a second... Really now. " +
                    "Nobody likes a liar or a thief. \n If you don't have any gold, " +
                    "it might be time for you to leave.");
        }
    }

    public void buyAnimal(){
        chooseGender();
        String chosenName = Helper.assignName("\n" + shopKeeper + ":" +
                "\nYou have only made excellent choices so far dear customer. " +
                "\nThink carefully now and let me know what you want to name " +
                "your animal friend:");

        if(chosenSpecies == Species.NYKUR){
            tempAnimal = new Nykur(chosenName, chosenGender);
        }
        else if(chosenSpecies == Species.GLOSON){
            tempAnimal = new Gloson(chosenName, chosenGender);
        }
        else if(chosenSpecies == Species.KRAKEN){
            tempAnimal = new Kraken(chosenName, chosenGender);
        }
        else if(chosenSpecies == Species.LINNR){
            tempAnimal = new Linnr(chosenName, chosenGender);
        }
        else if(chosenSpecies == Species.TILBERI){
            tempAnimal = new Tilberi(chosenName, chosenGender);
        }
        Game.getCurrentPlayer().removeGold(goldToPay);
        Game.getCurrentPlayer().addAnimal(tempAnimal);
        madeChange = true;
        Helper.clearConsole();
        System.out.println("\n" + shopKeeper + ":\nI'll just go ahead and give you " +
                chosenName + "'s amulet then. Anything else of interest to you?");
    }

    public void chooseGender(){
        do{
            Helper.setValidChoice(true);
            System.out.println("\nWhat gender would you prefer? Male or female? (m/f)");
            String gender = Helper.scan.nextLine().toLowerCase();
            if(gender.equals("m")){
                chosenGender = Gender.MALE;
            }
            else if(gender.equals("f")){
                chosenGender = Gender.FEMALE;
            }
            else{
                System.out.println("""

                        Gamemaker:
                        Come on now, i know the shopkeeper is a devil, but you still need to be nice to the shopkeeper and enter m or f
                        to let them know which gender your new animal should be.""");
                Helper.setValidChoice(false);
            }
        } while(!Helper.isValidChoice());
    }

    public void goToFoodStore(){
        leaveStore=false;
        madeChange = false;
        tempFood = null;

        Helper.clearConsole();
        System.out.println("WELCOME TO THE MYTH STORE FOOD DEPARTMENT, " +
                Game.getCurrentPlayer().getName().toUpperCase() + "!");
        do{
            Game.printPlayerStats();

            String menuText = ("\nWhat kind of food do you want to buy?" +
                    "\n|1| Seaweed (foodvalue: " + FoodType.SEAWEED.foodValue + ")" +
                    ".............." + FoodType.SEAWEED.price + " Gold/kg" +
                    "\n|2| Milk (foodvalue: " + FoodType.MILK.foodValue + ")" +
                    "................." + FoodType.MILK.price + " Gold/kg" +
                    "\n|3| Helpless human (foodvalue: " + FoodType.HELPLESS_HUMAN.foodValue + ")" +
                    "......." + FoodType.HELPLESS_HUMAN.price + " Gold/kg" +
                    "\n|4| None, leave store");

            int input = Helper.tryParseInt(menuText, 1, 4);

            switch (input) {
                case 1 -> tempFood = new Seaweed();
                case 2 -> tempFood = new Milk();
                case 3 -> tempFood = new HelplessHuman();
                case 4 -> leaveStore();
                default -> System.out.println("You must make a choice by entering a " +
                        "number from the list.");
            }
            if(0 < input &&
               input < 4){
                buyFood();
            }
        }while(!leaveStore);
    }

    public void buyFood(){

        double playerGold = Game.getCurrentPlayer().getGoldAmount();
        double maxFoodAmount = playerGold/(double)tempFood.getFoodType().price;

        String chooseAmount = shopKeeper + ": We only sell food in increments of 0.5 kg. " +
                "\nHow many kg of " + tempFood.getFoodType().foodType.toLowerCase() + " would you like to buy?";

        wantedFoodAmount = Helper.tryParseDouble(chooseAmount, 0.5);

        if(wantedFoodAmount % 0.5 == 0){
            if(wantedFoodAmount <= maxFoodAmount){
                goldToPay = (int)(wantedFoodAmount * tempFood.getFoodType().price);
                payForFood();
            }
            else{
                notEnoughGold();
            }
        }
        else{
            Helper.clearConsole();
            Helper.setValidChoice(false);
            System.out.println("You can only buy food in increments of 0.5 kg.");
        }
    }

    public void payForFood(){
        Game.getCurrentPlayer().removeGold(goldToPay);
        Food.addFood(tempFood, wantedFoodAmount);
        Helper.clearConsole();
        System.out.println(shopKeeper + ": Thank you for your purchase of " + wantedFoodAmount +
                " kg of " + tempFood.getFoodType().foodType.toLowerCase() + "." +
                "\nI'm sure your animals will appreciate it!");

        Helper.setValidChoice(true);
        madeChange = true;
    }

    public void sellAnimal(){
        madeChange = false;

        do{
            leaveStore = false;
            Game.printPlayerStats();
            ArrayList<Animal>playerAnimals = Game.getCurrentPlayer().animals;

            String answer = Helper.yesOrNo("\nDo you still want to sell one of your animals? (y/n)");

            switch (answer) {
                case "y" -> {
                    Helper.tryParseInt("\nEnter the number of the animal you want to sell:",
                            1, playerAnimals.size());
                    int input = Helper.getInputInt();
                    tempAnimal = playerAnimals.get(input - 1);
                    answer = Helper.yesOrNo("\n" + shopKeeper + ":\nI'll pay you " +
                                                   findAnimalPrice() + " gold for " + tempAnimal.getName() +
                                                   ". Do you accept? (y/n)");
                    Helper.clearConsole();
                    if (answer.equals("y")) {
                        madeChange = true;
                        Game.getCurrentPlayer().addGold(findAnimalPrice());
                        Game.getCurrentPlayer().removeAnimal(input - 1);
                        Helper.clearConsole();
                        System.out.println("You have now sold " + tempAnimal.getName() + " to Myth Store.");
                        if(Game.getCurrentPlayer().animals.isEmpty()){
                            System.out.println("\n" + shopKeeper + ": " +
                                    "\nI see that I've bought all your animals. I love a loyal customer." +
                                    "\nPlease come back soon!");
                        }
                    } else {
                        System.out.println(shopKeeper + ": Shame, " +
                                           "but if you're happy, I'm happy, I suppose.");
                    }
                }
                case "n" -> leaveStore();
            }
        }while(!Game.getCurrentPlayer().animals.isEmpty() && !leaveStore);
    }

    public int findAnimalPrice(){
        int price;
        double health;
        double decimal = 0.01;

        health = tempAnimal.getHealth();

        price = (int) Math.ceil(tempAnimal.getSpecies().price * health * decimal);

        return price;
    }

    public void sellAllAnimals(){
        for(Animal animal : Game.getCurrentPlayer().animals){
            tempAnimal = animal;
            Game.getCurrentPlayer().addGold(findAnimalPrice());
        }
        Game.getCurrentPlayer().animals.clear();

        System.out.println("\nSelling " + Game.getCurrentPlayer().getName() + "'s animals...");
    }

}