package com.company;

import java.util.ArrayList;

public class Store {

    private ArrayList<Animal> changedAnimals = new ArrayList<>();
    private Animal tempAnimal;

    private String chosenSpecies;
    private String chosenGender;
    private String chosenName;

    private final int nykurPrice = 30;
    private final int glosonPrice = 40;
    private final int krakenPrice = 10;
    private final int linnrPrice = 20;
    private final int tilberiPrice = 50;

    private String shopKeeper1 = "Shopkeeper Lucifer";
    private int unsuccessfulAttempts;
    private boolean leaveStore;


    public void goToAnimalStore(){
        changedAnimals.clear();
        System.out.println("\nWELCOME TO MYTH STORE ANIMAL DEPARTMENT, " +
                Game.getCurrentPlayer().getName().toUpperCase() + "!");
        do{
            leaveStore=false;
            if(!Game.getCurrentPlayer().getAnimalList().isEmpty()){
                System.out.println("\nYou have the following animals at the moment:");
                HelperMethods.printPlayerAnimals();
            }

            System.out.println("\nWhich animal do you want to buy? \n" +
                               "|1| Nykur....." + nykurPrice + " Gold \n" +
                               "|2| Gloson...." + glosonPrice + " Gold \n" +
                               "|3| Kraken...." + krakenPrice + " Gold \n" +
                               "|4| Linnr....." + linnrPrice + " Gold \n" +
                               "|5| Tilberi..." + tilberiPrice + " Gold\n" +
                               "|6| None, leave store\n");

            do{
                HelperMethods.tryParseInt();
            }while(HelperMethods.getInput() == -1);

            switch(HelperMethods.getInput()){

                case 1:
                    if(Game.getCurrentPlayer().getGoldAmount() >= nykurPrice){
                        chosenSpecies = "Nykur";
                        System.out.println(shopKeeper1 +": Good choice my fine friend. " +
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
                        chosenSpecies = "Gloson";
                        System.out.println(shopKeeper1 + ": Lovely choice. Try not to pet it though, " +
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
                        chosenSpecies = "Kraken";
                        System.out.println(shopKeeper1 + ": Ah, I see you have great taste, " +
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
                        chosenSpecies = "Linnr";
                        System.out.println(shopKeeper1 + ": Excellent choice " +
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
                        chosenSpecies = "Tilberi";
                        System.out.println(shopKeeper1 + ": Truly inspired choice, " +
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
        if(changedAnimals.isEmpty()){
            System.out.println(shopKeeper1 + ": Don't let the door hit you on the way out!");
            HelperMethods.setValidChoice(false);
        }
        else{
            System.out.println(shopKeeper1 + ": Thank you for your patronage. " +
                               "Do come back again soon!");
            HelperMethods.setValidChoice(true);
        }
        leaveStore = true;
    }

    public void notEnoughGold(){
        unsuccessfulAttempts += 1;
        if(unsuccessfulAttempts<=3){
            System.out.println(shopKeeper1 + ": Don't try to cheat me! You don't have that much gold!");
        }
        else{
            System.out.println(shopKeeper1 + ": Really now. Nobody likes a liar or thief. " +
                    "\nIf you don't have any gold, it might be time for you to leave.");
        }

    }

    public void buyAnimal(){
        do{
            HelperMethods.setValidChoice(true);
            System.out.println("\nWhat gender would you prefer? Male or female? (m/f)");
            String gender = HelperMethods.scan.nextLine().toLowerCase();
            if(gender.equals("m")){
                chosenGender = "Male";
            }
            else if(gender.equals("f")){
                chosenGender = "Female";
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
                "Think carefully now and decide on a name for your new animal friend. What should it be?");
        chosenName = HelperMethods.scan.nextLine();

        if(chosenSpecies.equals("Nykur")){
            // Create the animal according to the player's choices
            tempAnimal = new Nykur(chosenName, chosenGender);
            // Make player pay for the animal
            Game.getCurrentPlayer().removeGold(nykurPrice);
        }
        else if(chosenSpecies.equals("Gloson")){
            tempAnimal = new Gloson(chosenName, chosenGender);
            Game.getCurrentPlayer().removeGold(glosonPrice);
        }
        else if(chosenSpecies.equals("Kraken")){
            tempAnimal = new Kraken(chosenName, chosenGender);
            Game.getCurrentPlayer().removeGold(krakenPrice);
        }
        else if(chosenSpecies.equals("Linnr")){
            tempAnimal = new Linnr(chosenName, chosenGender);
            Game.getCurrentPlayer().removeGold(linnrPrice);
        }
        else if(chosenSpecies.equals("Tilberi")){
            tempAnimal = new Tilberi(chosenName, chosenGender);
            Game.getCurrentPlayer().removeGold(tilberiPrice);
        }

        changedAnimals.add(tempAnimal);
        Game.getCurrentPlayer().addAnimal(tempAnimal);

        System.out.println("\n" + shopKeeper1 + ": I'll just go ahead and give you " +
                chosenName + "'s amulet then. Anything else of interest to you?");
    }


    public void buyFood(int weight){

    }

    public void sellAnimal(){
        int price = 0;
        changedAnimals.clear();

        do{
            leaveStore = false;
            System.out.println("\nYour animals:");
            HelperMethods.printPlayerAnimals();
            System.out.println("\nDo you still want to sell one of your animals? (y/n)");
            String answer = HelperMethods.scan.nextLine();

            switch(answer){
                case "y":
                    System.out.println("\nEnter the number of the animal you want to sell:");
                    HelperMethods.tryParseInt();
                    int input = HelperMethods.getInput();

                    if(1 <= input &&
                            input <= Game.getCurrentPlayer().getAnimalList().size()+1){

                        tempAnimal = Game.getCurrentPlayer().getAnimalList().get(input-1);
                        sellAnimal();
                        System.out.println("\n" + shopKeeper1 + ": I'll pay you " + price +
                                " gold for " + tempAnimal.getName() + ". ");
                        System.out.println( "\nEnter y to accept the offer.");

                        answer = HelperMethods.scan.nextLine();
                        if(answer.equals("y")){
                            changedAnimals.add(tempAnimal);
                            Game.getCurrentPlayer().removeGold(price);
                            Game.getCurrentPlayer().removeAnimal(input-1);
                            System.out.println("You have now sold " + tempAnimal.getName() + " to Myth Store.");
                        }
                        else{
                            System.out.println(shopKeeper1 + ": Shame, " +
                                    "but if you're happy, I'm happy, I suppose.");
                        }

                    }
                    else{
                        HelperMethods.invalidInput();
                    }

                    break;

                case "n":
                    //TODO leave store or go to goToStore
                    leaveStore();
                    break;

                default:
                    // If player has not sold an animal, they have not made a move yet
                    if(changedAnimals.isEmpty()){ //TODO leave store or go to goToStore
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

        System.out.println("\n" + Game.getCurrentPlayer().getName() + " has " +
                Game.getCurrentPlayer().getGoldAmount() + " Gold. ");
    }

    public ArrayList<Animal> getChangedAnimals() {
        return changedAnimals;
    }
}
