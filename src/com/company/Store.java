package com.company;

import java.util.ArrayList;

public class Store {

    private ArrayList<Animal>boughtAnimals = new ArrayList<>();

    private Animal newAnimal;

    private String chosenSpecies;
    private String chosenGender;
    private String chosenName;

    private final int nykurPrice = 30;
    private final int glosonPrice = 40;
    private final int krakenPrice = 10;
    private final int linnrPrice = 20;
    private final int tilberiPrice = 50;

    private boolean leaveStore;

    public void goToAnimalStore(){
        boughtAnimals.clear();
        System.out.println("WELCOME TO MYTH STORE, " + Game.getCurrentPlayer().getName() + "!");
        do{
            leaveStore=false;
            System.out.println("Which animal do you want to buy? \n" +
                               "|1| Nykur....." + nykurPrice + " Gold \n" +
                               "|2| Gloson...." + glosonPrice + " Gold \n" +
                               "|3| Kraken...." + krakenPrice + " Gold \n" +
                               "|4| Linnr....." + linnrPrice + " Gold \n" +
                               "|5| Tilberi..." + tilberiPrice + " Gold\n" +
                               "|6| Leave store\n");

            do{
                HelperMethods.tryParseInt();
            }while(HelperMethods.getInput() == -1);

            switch(HelperMethods.getInput()){

                case 1:
                    if(Game.getCurrentPlayer().getGoldAmount() >= nykurPrice){
                        chosenSpecies = "Nykur";
                        System.out.println("Shopkeeper: Good choice my fine friend. " +
                                           "Just don't go swimming with it. I like my customers alive.");
                        buyAnimal();
                    }
                    else{
                        notEnoughGold();
                    }
                    break;

                case 2:
                    if(Game.getCurrentPlayer().getGoldAmount() >= glosonPrice){
                        chosenSpecies = "Gloson";
                        System.out.println("Shopkeeper: Lovely choice. Try not to pet it though, " +
                                           "I don't want you to get hurt. ");
                        buyAnimal();
                    }
                    else{
                        notEnoughGold();
                    }
                    break;

                case 3:
                    if(Game.getCurrentPlayer().getGoldAmount() >= krakenPrice){
                        chosenSpecies = "Kraken";
                        System.out.println("Shopkeeper: Ah, I see you have great taste. " +
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
                        System.out.println("Shopkeeper: Excellent choice. I will not be held " +
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
                        System.out.println("Shopkeeper: Truly inspired choice my friend. " +
                                           "Remember not to bring it home to a friend... " +
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
        if(boughtAnimals.isEmpty()){
            System.out.println("Shopkeeper: Don't let the door hit you on the way out!");
        }
        else{
            System.out.println("Shopkeeper: Thank you for your patronage. " +
                               "Do come back again soon!");
        }
        leaveStore = true;
    }

    public void notEnoughGold(){
        System.out.println("Shopkeeper: Don't try to cheat me! You don't have that much gold!");
    }

    public void buyAnimal(){
        chooseAnimal();
        payForAnimal();
        boughtAnimals.add(newAnimal);
        Game.getCurrentPlayer().addAnimal(newAnimal);
        System.out.println("\nShopkeeper: I'll just go ahead and give you the amulet that's" +
                           "tied to " + chosenName + " then. Anything else of interest to you?");
    }

    public void chooseAnimal(){
        // Let player choose gender for the animal
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

    }

    public void payForAnimal(){
        if(chosenSpecies.equals("Nykur")){
            // Create the animal according to the player's choices
            newAnimal = new Nykur(chosenName, chosenGender);
            // Make player pay for the animal
            Game.getCurrentPlayer().removeGold(nykurPrice);
        }
        else if(chosenSpecies.equals("Gloson")){
            newAnimal = new Gloson(chosenName, chosenGender);
            Game.getCurrentPlayer().removeGold(glosonPrice);
        }
        else if(chosenSpecies.equals("Kraken")){
            newAnimal = new Kraken(chosenName, chosenGender);
            Game.getCurrentPlayer().removeGold(krakenPrice);
        }
        else if(chosenSpecies.equals("Linnr")){
            newAnimal = new Linnr(chosenName, chosenGender);
            Game.getCurrentPlayer().removeGold(linnrPrice);
        }
        else if(chosenSpecies.equals("Tilberi")){
            newAnimal = new Tilberi(chosenName, chosenGender);
            Game.getCurrentPlayer().removeGold(tilberiPrice);
        }
    }


    public void buyFood(int weight){

    }

    public int sellAnimal(){
        int health = 0; //TODO make health be health of animal sent to method
        int price = 0;
        if(getClass().getSimpleName().equals("Nykur")){
            price = 30;
        }
        else if(getClass().getSimpleName().equals("Gloson")){
            price = 40;
        }
        else if(getClass().getSimpleName().equals("Kraken")){
            price = 10;
        }
        else if(getClass().getSimpleName().equals("Linnr")){
            price = 20;
        }
        else if(getClass().getSimpleName().equals("Tilberi")){
            price = 50;
        }
        price *= health;
        return price;

        // TODO If the class of the animal is given value
        //  Return price
    }

    public void sellAllAnimals(){

    }

    public ArrayList<Animal> getBoughtAnimals() {
        return boughtAnimals;
    }
}
