package com.company;

import java.util.ArrayList;

public class Store {

    private ArrayList<Animal> changedAnimals = new ArrayList<>();
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

    private final String shopKeeper = "Shopkeeper Lucifer";
    private int unsuccessfulAttempts;
    private boolean leaveStore;


    public void goToAnimalStore(){
        tempFood = null;
        changedAnimals.clear();
        System.out.println("\nWELCOME TO MYTH STORE ANIMAL DEPARTMENT, " +
                Game.getCurrentPlayer().getName().toUpperCase() + "!");
        do{
            leaveStore=false;
            Game.printPlayerStats();

            System.out.println("\nWhich animal do you want to buy? \n" +
                               "|1| Nykur....." + nykurPrice + " Gold \n" +
                               "|2| Gloson...." + glosonPrice + " Gold \n" +
                               "|3| Kraken...." + krakenPrice + " Gold \n" +
                               "|4| Linnr....." + linnrPrice + " Gold \n" +
                               "|5| Tilberi..." + tilberiPrice + " Gold\n" +
                               "|6| None, leave store\n");

            do{
                HelperMethods.tryParseInt();
            }while(HelperMethods.getInputInt() == -1);

            switch(HelperMethods.getInputInt()){

                case 1:
                    if(Game.getCurrentPlayer().getGoldAmount() >= nykurPrice){
                        chosenSpecies = Species.NYKUR;
                        System.out.println(shopKeeper +": Good choice my fine friend. " +
                                           "Just don't go swimming with it. I like my customers alive, " +
                                Game.getCurrentPlayer().getName());
                        buyAnimal();
                    }
                    else{
                        notEnoughGold();
                    }
                    break;

                case 2:
                    if(Game.getCurrentPlayer().getGoldAmount() >= glosonPrice){
                        chosenSpecies = Species.GLOSON;
                        System.out.println(shopKeeper + ": Lovely choice. Try not to pet it though, " +
                                Game.getCurrentPlayer().getName() +
                                           ", I don't want to see you get hurt. ");
                        buyAnimal();
                    }
                    else{
                        notEnoughGold();
                    }
                    break;

                case 3:
                    if(Game.getCurrentPlayer().getGoldAmount() >= krakenPrice){
                        chosenSpecies = Species.KRAKEN;
                        System.out.println(shopKeeper + ": Ah, I see you have great taste, " +
                                Game.getCurrentPlayer().getName() + "! " +
                                           "Watch out for it's ravenous appetite though. ");
                        buyAnimal();
                    }
                    else{
                        notEnoughGold();
                    }
                    break;

                case 4:
                    if(Game.getCurrentPlayer().getGoldAmount() >= linnrPrice){
                        chosenSpecies = Species.LINNR;
                        System.out.println(shopKeeper + ": Excellent choice " +
                                Game.getCurrentPlayer().getName() + ". I will not be held " +
                                           "responsible for what happens if you try to kiss it though. ");
                        buyAnimal();
                    }
                    else{
                        notEnoughGold();
                    }
                    break;

                case 5:
                    if(Game.getCurrentPlayer().getGoldAmount() >= tilberiPrice){
                        chosenSpecies = Species.TILBERI;
                        System.out.println(shopKeeper + ": Truly inspired choice, " +
                                Game.getCurrentPlayer().getName() + ", my friend. " +
                                           "\nRemember not to bring it home to a friend... " +
                                           "if you want to keep your friends that is.");
                        buyAnimal();
                    }
                    else{
                        notEnoughGold();
                    }
                    break;

                case 6:
                    leaveStore();
            }
        }while(!leaveStore);

    }

    public void leaveStore(){
        if(changedAnimals.isEmpty() && tempFood == null){
            System.out.println(shopKeeper + ": Don't let the door hit you on the way out!");
            HelperMethods.setValidChoice(false);
        }
        else{
            System.out.println(shopKeeper + ": Thank you for your patronage. " +
                               "Do come back again soon!");
            HelperMethods.setValidChoice(true);
        }
        leaveStore = true;
    }

    public void notEnoughGold(){
        unsuccessfulAttempts += 1;
        if(unsuccessfulAttempts<=3){
            System.out.println(shopKeeper + ": Don't try to cheat me! You don't have that much gold!");
        }
        else{
            System.out.println(shopKeeper + ": Really now. Nobody likes a liar or thief. " +
                    "\nIf you don't have any gold, it might be time for you to leave.");
        }

    }

    public void buyAnimal(){
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
                System.out.println("\nGamemaker: Come on now, i know the shopkeeper is a devil, " +
                        "but you still need to be nice to the shopkeeper and enter m or f" +
                        " to let them know which gender your new animal should be.");
                HelperMethods.setValidChoice(false);
            }
        } while(!HelperMethods.getValidChoice());

        // Let player name the animal
        System.out.println("Shopkeeper: You have only made excellent choices so far dear customer. " +
                "\nThink carefully now and decide on a name for your new animal friend. What should it be?");
        chosenName = HelperMethods.scan.nextLine();

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

        changedAnimals.add(tempAnimal);
        Game.getCurrentPlayer().addAnimal(tempAnimal);
        //TODO add tempAnimal = null; and use to check if choice is valid in leave store, instead of list

        System.out.println("\n" + shopKeeper + ": I'll just go ahead and give you " +
                chosenName + "'s amulet then. Anything else of interest to you?");
    }


    public void goToFoodStore(){
        tempFood =null;
        leaveStore=false;
        System.out.println("\nWELCOME TO THE MYTH STORE FOOD DEPARTMENT, " +
                Game.getCurrentPlayer().getName().toUpperCase() + "!");
        do{
            Game.printPlayerStats();

            System.out.println("\nWhat kind of food do you want to buy?" +
                               "\n|1| Seaweed.........." + seaweedPrice + " Gold/kg " +
                               "\n|2| Milk............." + milkPrice + " Gold/kg " +
                               "\n|3| Helpless human..." + helplessHumanPrice + " Gold/kg " +
                               "\n|4| None, leave store");

            do{
                HelperMethods.tryParseInt();
            }while(HelperMethods.getInputInt()==-1);

            switch(HelperMethods.getInputInt()){

                case 1:
                    tempFood = new Seaweed();
                    break;

                case 2:
                    tempFood = new Milk();
                    break;

                case 3:
                    tempFood = new HelplessHuman();
                    break;

                case 4:
                    leaveStore();
                    break;

                default:
                    System.out.println("You must make a choice by entering a number from the list.");
            }
            if(0 < HelperMethods.getInputInt() &&
               HelperMethods.getInputInt() < 4) {
                buyFood();
            }

        }while(!leaveStore);

    }

    public void buyFood(){
        // TODO
        //  If double reaches 0, remove foodItem from list completely
        // TODO Make method to add one item to food by adding one to the integer
        //  If the list does not already contain the food
        //  add the item and set integer to 1
        double playerGold = Game.getCurrentPlayer().getGoldAmount();
        double maxSeaweed = (double) Math.round(playerGold*2/(double)seaweedPrice)/2;
        double maxMilk = (double) Math.round(playerGold*2/(double)milkPrice)/2;
        double maxHelplessHuman = Math.round(playerGold*2/(double)helplessHumanPrice/2);
        int price;

        System.out.println("\n" + shopKeeper + ": We only sell food in increments of 0.5 kg. " +
                           "\nHow many kg of " + tempFood.getFoodType() + " would you like to buy?");
        HelperMethods.tryParseDouble();
        double wantedAmount = HelperMethods.getInputDouble();

        // If player tries to buy an amount they can afford
        // and amount is made of increments of 0.5 kg
        // let the player buy the food
        if(wantedAmount % 0.5 == 0){
            if(tempFood.getFoodType().equals(FoodType.SEAWEED.string()) &&
               wantedAmount <= maxSeaweed){
                price = (int)(wantedAmount * seaweedPrice);
                Game.getCurrentPlayer().removeGold(price);
                addFood(wantedAmount);
            }
            else if(tempFood.getFoodType().equals(FoodType.MILK.string()) &&
                    wantedAmount <= maxMilk){
                price = (int)(wantedAmount * milkPrice);
                Game.getCurrentPlayer().removeGold(price);
                addFood(wantedAmount);
            }
            else if(tempFood.getFoodType().equals(FoodType.HELPLESS_HUMAN.string()) &&
                    wantedAmount <= maxHelplessHuman){
                price = (int)(wantedAmount * helplessHumanPrice);
                Game.getCurrentPlayer().removeGold(price);
                addFood(wantedAmount);
            }
            else{
                notEnoughGold();
            }
        }
        else{
            HelperMethods.setValidChoice(false);
            System.out.println("You can only buy food in increments of 0.5 kg.");
        }


    }

    public void addFood(double kilogram){
        // If the player enters a number that is divisible by 2,
        // (tries to buy food in increments of 0.5 kg), proceed
            HelperMethods.setValidChoice(true);
            // Go through the players list of food items
            for(Food food : Game.getCurrentPlayer().getFoodList()){
                // For the item in the players list of foods,
                // that is of the same type as the food they bought,
                // add the amount of the food the player wanted
                if(food.getFoodType().equals(tempFood.getFoodType())){
                    food.addFoodAmount(kilogram);
                    System.out.println(shopKeeper + ": Thank you for your purchase, " +
                                       "I'm sure your animals will appreciate it!");
                    break;
                }
            }
    }

    public void sellAnimal(){
        int price = 0;
        changedAnimals.clear();

        do{
            leaveStore = false;
            Game.printPlayerStats();

            System.out.println("\nDo you still want to sell one of your animals? (y/n)");
            String answer = HelperMethods.scan.nextLine();

            switch(answer){
                case "y":
                    System.out.println("\nEnter the number of the animal you want to sell:");
                    HelperMethods.tryParseInt();
                    int input = HelperMethods.getInputInt();

                    if(1 <= input &&
                            input <= Game.getCurrentPlayer().getAnimalList().size()+1){
                        tempAnimal = Game.getCurrentPlayer().getAnimalList().get(input-1);
                        System.out.println("\n" + shopKeeper + ": I'll pay you " + getAnimalPrice() +
                                " gold for " + tempAnimal.getName() + ". ");
                        System.out.println( "\nEnter y to accept the offer.");

                        answer = HelperMethods.scan.nextLine();
                        if(answer.equals("y")){
                            changedAnimals.add(tempAnimal);
                            Game.getCurrentPlayer().addGold(getAnimalPrice());
                            Game.getCurrentPlayer().removeAnimal(input-1);
                            System.out.println("You have now sold " + tempAnimal.getName() + " to Myth Store.");
                        }
                        else{
                            System.out.println(shopKeeper + ": Shame, " +
                                    "but if you're happy, I'm happy, I suppose.");
                        }

                    }
                    else{
                        HelperMethods.invalidInput();
                    }

                    break;

                case "n":
                    leaveStore();
                    break;

                default:
                    // If player has not sold an animal, they have not made a move yet
                    if(changedAnimals.isEmpty()){
                        System.out.println("Gamemaker: Well, if you're not going to choose, " +
                                "I'm gonna choose for you. \nMay the odds be ever in your favour.");
                        //TODO add 1% chance of selling first animal in list?
                        HelperMethods.setValidChoice(false);
                    }
                    // If player has sold an animal, they have made a move.
                    // Leave the store and end their move.
                    else{
                        leaveStore();
                    }
                    break;
            }

        }while(!Game.getCurrentPlayer().getAnimalList().isEmpty() && !leaveStore);

    }

    public int getAnimalPrice(){
        int price = 0;
        double health;
        double decimal = 0.01;

        health = tempAnimal.getHealth();

        if(tempAnimal.getSpecies().equals("Nykur")){
            price = (int) Math.ceil(nykurPrice * health * decimal);
        }
        else if(tempAnimal.getSpecies().equals("Gloson")){
            price = (int) Math.ceil(glosonPrice * health * decimal);
        }
        else if(tempAnimal.getSpecies().equals("Kraken")){
            price = (int) Math.ceil(krakenPrice * health * decimal);
        }
        else if(tempAnimal.getSpecies().equals("Linnr")){
            price = (int) Math.ceil(linnrPrice * health * decimal);
        }
        else if(tempAnimal.getSpecies().equals("Tilberi")){
            price = (int) Math.ceil(tilberiPrice * health * decimal);
        }
        return price;
    }

    public void sellAllAnimals(){
        for(Animal animal : Game.getCurrentPlayer().getAnimalList()){
            tempAnimal = animal;
            Game.getCurrentPlayer().addGold(getAnimalPrice());
        }
        Game.getCurrentPlayer().getAnimalList().clear();

        System.out.println("\nSelling " + Game.getCurrentPlayer().getName() + "'s animals...");
        System.out.println("\n" + Game.getCurrentPlayer().getName() + " has " +
                Game.getCurrentPlayer().getGoldAmount() + " Gold. ");
    }

}
