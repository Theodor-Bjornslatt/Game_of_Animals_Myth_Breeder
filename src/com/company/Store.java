package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class Store implements Serializable {

    private Animal tempAnimal;
    private Food tempFood;

    private Species chosenSpecies;
    private Gender chosenGender;
    private String chosenName;

    private final int nykurPrice = 30;
    private final int glosonPrice = 40;
    private final int krakenPrice = 10;
    private final int linnrPrice = 20;
    private final int tilberiPrice = 50;
    private final int seaweedPrice = 6;
    private final int milkPrice = 4;
    private final int helplessHumanPrice = 8;
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
        HelperMethods.clearConsole();
        System.out.println("WELCOME TO MYTH STORE ANIMAL DEPARTMENT, " +
                Game.getCurrentPlayer().getName().toUpperCase() + "!");
        do{
            Game.printPlayerStats();

            System.out.println("\nWhich animal do you want to buy? \n" +
                               "|1| Nykur....." + nykurPrice + " Gold \n" +
                               "|2| Gloson...." + glosonPrice + " Gold \n" +
                               "|3| Kraken...." + krakenPrice + " Gold \n" +
                               "|4| Linnr....." + linnrPrice + " Gold \n" +
                               "|5| Tilberi..." + tilberiPrice + " Gold\n" +
                               "|6| Leave store\n");

            HelperMethods.tryParseInt("", 1, 6);

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
        HelperMethods.clearConsole();
        switch (HelperMethods.getInputInt()) {
            case 1 -> {
                goldToPay = nykurPrice;
                chosenSpecies = Species.NYKUR;
                System.out.println(shopKeeper + ":\nGood choice my fine friend. " +
                        "Just don't go swimming with it. I like my customers alive, " +
                        Game.getCurrentPlayer().getName());
            }
            case 2 -> {
                goldToPay = glosonPrice;
                chosenSpecies = Species.GLOSON;
                System.out.println(shopKeeper + ":\nLovely choice. Try not to pet it though, " +
                        Game.getCurrentPlayer().getName() +
                        ", I don't want to see you get hurt. ");
            }
            case 3 -> {
                goldToPay = krakenPrice;
                chosenSpecies = Species.KRAKEN;
                System.out.println(shopKeeper + ":\nAh, I see you have great taste, " +
                        Game.getCurrentPlayer().getName() + "! " +
                        "Watch out for it's ravenous appetite though. ");
            }
            case 4 -> {
                goldToPay = linnrPrice;
                chosenSpecies = Species.LINNR;
                System.out.println(shopKeeper + ":\n Excellent choice " +
                        Game.getCurrentPlayer().getName() + ". But be advised " +
                        "that this is not an enchanted prince. ");
            }
            case 5 -> {
                goldToPay = tilberiPrice;
                chosenSpecies = Species.TILBERI;
                System.out.println(shopKeeper + ":\n Truly inspired choice, " +
                        Game.getCurrentPlayer().getName() + ". " +
                        "\n Remember not to bring it home to a friend... " +
                        "if you want to keep your friends that is.");
            }
            case 6 -> leaveStore();
        }
    }

    public void leaveStore(){
        if(!madeChange){
            System.out.println("\n" + shopKeeper + ":\n Don't let the door hit you on the way out!");
            HelperMethods.setValidChoice(false);
        }
        else{
            System.out.println("\n" + shopKeeper + ":\n Thank you for your patronage, " +
                    Game.getCurrentPlayer().getName() + ". Do come back again soon!");
            HelperMethods.setValidChoice(true);
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
        chosenName = HelperMethods.assignName("\n" + shopKeeper + ":" +
                                              "\nYou have only made excellent choices so far dear customer. " +
                                              "\nThink carefully now and let me know what you want to name " +
                                              "your animal friend:");

        if(chosenSpecies == Species.NYKUR){
            // Create the animal according to the player's choices
            tempAnimal = new Nykur(chosenName, chosenGender);
            // Make player pay for the animal
            Game.getCurrentPlayer().removeGold(nykurPrice);
        }
        else if(chosenSpecies == Species.GLOSON){
            tempAnimal = new Gloson(chosenName, chosenGender);
            Game.getCurrentPlayer().removeGold(glosonPrice);
        }
        else if(chosenSpecies == Species.KRAKEN){
            tempAnimal = new Kraken(chosenName, chosenGender);
            Game.getCurrentPlayer().removeGold(krakenPrice);
        }
        else if(chosenSpecies == Species.LINNR){
            tempAnimal = new Linnr(chosenName, chosenGender);
            Game.getCurrentPlayer().removeGold(linnrPrice);
        }
        else if(chosenSpecies == Species.TILBERI){
            tempAnimal = new Tilberi(chosenName, chosenGender);
            Game.getCurrentPlayer().removeGold(tilberiPrice);
        }

        madeChange = true;
        Game.getCurrentPlayer().addAnimal(tempAnimal);
        HelperMethods.clearConsole();
        System.out.println("\n" + shopKeeper + ":\nI'll just go ahead and give you " +
                chosenName + "'s amulet then. Anything else of interest to you?");
    }

    public void chooseGender(){
        do{
            HelperMethods.setValidChoice(true);
            System.out.println("\nWhat gender would you prefer? Male or female? (m/f)");
            String gender = HelperMethods.scan.nextLine().toLowerCase();
            if(gender.equals("m")){
                chosenGender = Gender.MALE;
            }
            else if(gender.equals("f")){
                chosenGender = Gender.FEMALE;
            }
            else{
                System.out.println("\nGamemaker:" + "\nCome on now, i know the shopkeeper is a devil, " +
                        "but you still need to be nice to the shopkeeper and enter m or f" +
                        "\nto let them know which gender your new animal should be.");
                HelperMethods.setValidChoice(false);
            }
        } while(!HelperMethods.isValidChoice());
    }

    public void goToFoodStore(){
        leaveStore=false;
        madeChange = false;
        tempFood = null;

        HelperMethods.clearConsole();
        System.out.println("WELCOME TO THE MYTH STORE FOOD DEPARTMENT, " +
                Game.getCurrentPlayer().getName().toUpperCase() + "!");
        do{
            Game.printPlayerStats();

            String menuText = ("\nWhat kind of food do you want to buy?" +
                               "\n|1| Seaweed.........." + seaweedPrice + " Gold/kg " +
                               "\n|2| Milk............." + milkPrice + " Gold/kg " +
                               "\n|3| Helpless human..." + helplessHumanPrice + " Gold/kg " +
                               "\n|4| None, leave store");

            int input = HelperMethods.tryParseInt(menuText, 1, 4);

            switch (input) {
                case 1 -> tempFood = new Seaweed();
                case 2 -> tempFood = new Milk();
                case 3 -> tempFood = new HelplessHuman();
                case 4 -> leaveStore();
                default -> System.out.println("You must make a choice by entering a " +
                        "number from the list.");
            }
            if(0 < input &&
               input < 4) {
                buyFood();
            }
        }while(!leaveStore);
    }

    public void buyFood(){

        double playerGold = Game.getCurrentPlayer().getGoldAmount();
        double maxSeaweed = playerGold/(double)seaweedPrice;
        double maxMilk = playerGold/(double)milkPrice;
        double maxHelplessHuman = playerGold/(double)helplessHumanPrice;

        String chooseAmount = shopKeeper + ": We only sell food in increments of 0.5 kg. " +
                "\nHow many kg of " + tempFood.getFoodType().string().toLowerCase() + " would you like to buy?";

        wantedFoodAmount = HelperMethods.tryParseDouble(chooseAmount, 0.5);

        if(wantedFoodAmount % 0.5 == 0){
            if(tempFood.getFoodType().string().equals(FoodType.SEAWEED.string()) &&
               wantedFoodAmount <= maxSeaweed){
                goldToPay = (int)(wantedFoodAmount * seaweedPrice);
                payForFood();
            }
            else if(tempFood.getFoodType().string().equals(FoodType.MILK.string()) &&
                    wantedFoodAmount <= maxMilk){
                goldToPay = (int)(wantedFoodAmount * milkPrice);
                payForFood();
            }
            else if(tempFood.getFoodType().string().equals(FoodType.HELPLESS_HUMAN.string()) &&
                    wantedFoodAmount <= maxHelplessHuman){
                goldToPay = (int)(wantedFoodAmount * helplessHumanPrice);
                payForFood();
            }
            else{
                notEnoughGold();
            }
        }
        else{
            HelperMethods.clearConsole();
            HelperMethods.setValidChoice(false);
            System.out.println("You can only buy food in increments of 0.5 kg.");
        }
    }

    public void payForFood(){
        Game.getCurrentPlayer().removeGold(goldToPay);
        addFood(wantedFoodAmount);
        madeChange = true;
    }

    public void addFood(double kilogram){
            HelperMethods.setValidChoice(true);
            for(Food food : Game.getCurrentPlayer().getFoodList()){
                if(food.getFoodType().string().equals(tempFood.getFoodType().string())){
                    food.addFoodAmount(kilogram);
                    System.out.println(shopKeeper + ": Thank you for your purchase, " +
                                       "I'm sure your animals will appreciate it!");
                    break;
                }
            }
    }

    public void sellAnimal(){
        madeChange = false;

        do{
            leaveStore = false;
            Game.printPlayerStats();
            ArrayList<Animal>playerAnimals = Game.getCurrentPlayer().animals;

            String answer = HelperMethods.yesOrNo("\nDo you still want to sell one of your animals? (y/n)");

            switch (answer) {
                case "y" -> {
                    HelperMethods.tryParseInt("\nEnter the number of the animal you want to sell:",
                            1, playerAnimals.size());
                    int input = HelperMethods.getInputInt();
                    tempAnimal = playerAnimals.get(input - 1);
                    answer = HelperMethods.yesOrNo("\n" + shopKeeper + ":\nI'll pay you " +
                                                   animalPrice() + " gold for " + tempAnimal.getName() +
                                                   ". Do you accept? (y/n)");
                    if (answer.equals("y")) {
                        madeChange = true;
                        Game.getCurrentPlayer().addGold(animalPrice());
                        Game.getCurrentPlayer().removeAnimal(input - 1);
                        System.out.println("You have now sold " + tempAnimal.getName() + " to Myth Store.");
                    } else {
                        System.out.println(shopKeeper + ": Shame, " +
                                           "but if you're happy, I'm happy, I suppose.");
                    }
                }
                case "n" -> leaveStore();
            }
        }while(!Game.getCurrentPlayer().animals.isEmpty() && !leaveStore);
    }

    public int animalPrice(){
        int price = 0;
        double health;
        double decimal = 0.01;

        health = tempAnimal.getHealth();

        if(tempAnimal.getSpecies()==Species.NYKUR){
            price = (int) Math.ceil(nykurPrice * health * decimal);
        }
        else if(tempAnimal.getSpecies()==Species.GLOSON){
            price = (int) Math.ceil(glosonPrice * health * decimal);
        }
        else if(tempAnimal.getSpecies()==Species.KRAKEN){
            price = (int) Math.ceil(krakenPrice * health * decimal);
        }
        else if(tempAnimal.getSpecies()==Species.LINNR){
            price = (int) Math.ceil(linnrPrice * health * decimal);
        }
        else if(tempAnimal.getSpecies()==Species.TILBERI){
            price = (int) Math.ceil(tilberiPrice * health * decimal);
        }
        return price;
    }

    public void sellAllAnimals(){
        for(Animal animal : Game.getCurrentPlayer().animals){
            tempAnimal = animal;
            Game.getCurrentPlayer().addGold(animalPrice());
        }
        Game.getCurrentPlayer().animals.clear();

        System.out.println("\nSelling " + Game.getCurrentPlayer().getName() + "'s animals...");
        System.out.println("\n" + Game.getCurrentPlayer().getName() + " has " +
                Game.getCurrentPlayer().getGoldAmount() + " Gold. ");
    }

}