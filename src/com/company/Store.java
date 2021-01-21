package com.company;

public class Store {

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
    private boolean madeChange = true;


    public void goToAnimalStore(){
        leaveStore=false;
        tempFood = null;
        madeChange = false;
        System.out.println("\nWELCOME TO MYTH STORE ANIMAL DEPARTMENT, " +
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

            do{
                HelperMethods.tryParseInt();
            }while(HelperMethods.getInputInt() == -1);

            switch(HelperMethods.getInputInt()){

                case 1:
                    if(Game.getCurrentPlayer().getGoldAmount() >= nykurPrice){
                        chosenSpecies = Species.NYKUR;
                        System.out.println("\n" + shopKeeper +":\n Good choice my fine friend. " +
                                           "Just don't go swimming with it. I like my customers alive, " +
                                Game.getCurrentPlayer().getName());
                    }
                    else{
                        notEnoughGold();
                    }
                    break;

                case 2:
                    if(Game.getCurrentPlayer().getGoldAmount() >= glosonPrice){
                        chosenSpecies = Species.GLOSON;
                        System.out.println("\n" + shopKeeper + ":\n Lovely choice. Try not to pet it though, " +
                                Game.getCurrentPlayer().getName() +
                                           ", I don't want to see you get hurt. ");
                    }
                    else{
                        notEnoughGold();
                    }
                    break;

                case 3:
                    if(Game.getCurrentPlayer().getGoldAmount() >= krakenPrice){
                        chosenSpecies = Species.KRAKEN;
                        System.out.println("\n" + shopKeeper + ":\n Ah, I see you have great taste, " +
                                Game.getCurrentPlayer().getName() + "! " +
                                           "Watch out for it's ravenous appetite though. ");
                    }
                    else{
                        notEnoughGold();
                    }
                    break;

                case 4:
                    if(Game.getCurrentPlayer().getGoldAmount() >= linnrPrice){
                        chosenSpecies = Species.LINNR;
                        System.out.println("\n" + shopKeeper + ":\n Excellent choice " +
                                Game.getCurrentPlayer().getName() + ". I will not be held " +
                                           "responsible for what happens if you try to kiss it though. ");
                    }
                    else{
                        notEnoughGold();
                    }
                    break;

                case 5:
                    if(Game.getCurrentPlayer().getGoldAmount() >= tilberiPrice){
                        chosenSpecies = Species.TILBERI;
                        System.out.println("\n" + shopKeeper + ":\n Truly inspired choice, " +
                                Game.getCurrentPlayer().getName() + ", my friend. " +
                                           "\nRemember not to bring it home to a friend... " +
                                           "if you want to keep your friends that is.");
                    }
                    else{
                        notEnoughGold();
                    }
                    break;

                case 6:
                    leaveStore();
            }
            if(!leaveStore){
                buyAnimal();
            }
        }while(!leaveStore);
    }

    public void leaveStore(){
        if(!madeChange){
            System.out.println("\n" + shopKeeper + ":\nDon't let the door hit you on the way out!");
            HelperMethods.setValidChoice(false);
        }
        else{
            System.out.println("\n" + shopKeeper + ":\n Thank you for your patronage. " +
                               "Do come back again soon!");
            HelperMethods.setValidChoice(true);
        }
        leaveStore = true;
    }

    public void notEnoughGold(){
        unsuccessfulAttempts += 1;
        if(unsuccessfulAttempts<=3){
            System.out.println("\n" + shopKeeper + ":\nDon't try to cheat me! You don't have that much gold!");
        }
        else{
            System.out.println("\n" + shopKeeper + ":\nReally now. Nobody likes a liar or thief. " +
                    "\nIf you don't have any gold, it might be time for you to leave.");
        }
        madeChange = false;
    }

    public void buyAnimal(){
        // Let player choose the gender of the animal
        chooseGender();
        // Let player name the animal
        System.out.println("\n" + shopKeeper + ":\nYou have only made excellent choices so far dear customer. " +
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

        madeChange = true;
        Game.getCurrentPlayer().addAnimal(tempAnimal);

        System.out.println("\n" + shopKeeper + ":\n I'll just go ahead and give you " +
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
                System.out.println("\nGamemaker: Come on now, i know the shopkeeper is a devil, " +
                        "but you still need to be nice to the shopkeeper and enter m or f" +
                        " to let them know which gender your new animal should be.");
                HelperMethods.setValidChoice(false);
            }
        } while(!HelperMethods.getValidChoice());
    }


    public void goToFoodStore(){
        leaveStore=false;
        madeChange = false;
        tempFood = null;

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

            switch (HelperMethods.getInputInt()) {
                case 1 -> tempFood = new Seaweed();
                case 2 -> tempFood = new Milk();
                case 3 -> tempFood = new HelplessHuman();
                case 4 -> leaveStore();
                default -> System.out.println("You must make a choice by entering a " +
                        "number from the list.");
            }
            if(0 < HelperMethods.getInputInt() &&
               HelperMethods.getInputInt() < 4) {
                buyFood();
            }
        }while(!leaveStore);
    }

    public void buyFood(){

        double playerGold = Game.getCurrentPlayer().getGoldAmount();
        double maxSeaweed = playerGold/(double)seaweedPrice;
        double maxMilk = playerGold/(double)milkPrice;
        double maxHelplessHuman = playerGold/(double)helplessHumanPrice;

        System.out.println("\n" + shopKeeper + ": We only sell food in increments of 0.5 kg. " +
                           "\nHow many kg of " + tempFood.getFoodType().string() + " would you like to buy?");
        HelperMethods.tryParseDouble();
        wantedFoodAmount = HelperMethods.getInputDouble();

        // If player tries to buy an amount they can afford
        // and amount is made of increments of 0.5 kg
        // let the player buy the food
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
        // If the player enters a number that is divisible by 2,
        // (tries to buy food in increments of 0.5 kg), proceed
            HelperMethods.setValidChoice(true);
            // Go through the players list of food items
            for(Food food : Game.getCurrentPlayer().getFoodList()){
                // For the item in the players list of foods,
                // that is of the same type as the food they bought,
                // add the amount of the food the player wanted
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
                            madeChange = true;
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
                    if(madeChange){
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
