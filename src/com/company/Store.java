package com.company;

import java.util.ArrayList;

public class Store {

    private ArrayList<Animal>boughtAnimals = new ArrayList<>();

    private Nykur newNykur = new Nykur("noName", "noGender");
    private Gloson newGloson = new Gloson("noName", "noGender");
    private Kraken newKraken = new Kraken("noName", "noGender");
    private Linnr newLinnr = new Linnr("noName", "noGender");
    private Tilberi newTilberi = new Tilberi("noName", "noGender");

    private final int nykurPrice = 30;
    private final int glosonPrice = 40;
    private final int krakenPrice = 10;
    private final int linnrPrice = 20;
    private final int tilberiPrice = 50;
    private String chosenSpecies;
    private String chosenGender;
    private String chosenName;

    private boolean leaveStore = false;

    public void goToAnimalStore(){
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
                        chooseAnimal();
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
                        chooseAnimal();
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
                        chooseAnimal();
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
                        chooseAnimal();
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
                        chooseAnimal();
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

    public void buyAnimal(){
        if(chosenSpecies.equals("Nykur")){
            //Make sure the animal gets the name and
            // gender the player has chosen
            newNykur.setName(chosenName);
            newNykur.setGender(chosenGender);
            // Make player pay for the animal
            Game.getCurrentPlayer().removeGold(nykurPrice);
            // Then add animal to the list of boughtAnimals and to the players list of animals
            boughtAnimals.add(newNykur);
            Game.getCurrentPlayer().addAnimal(newNykur);
        }
        else if(chosenSpecies.equals("Gloson")){
            newGloson.setName(chosenName);
            newGloson.setGender(chosenGender);
            Game.getCurrentPlayer().removeGold(glosonPrice);
            boughtAnimals.add(newGloson);
            Game.getCurrentPlayer().addAnimal(newGloson);
        }
        else if(chosenSpecies.equals("Kraken")){
            newKraken.setName(chosenName);
            newKraken.setGender(chosenGender);
            Game.getCurrentPlayer().removeGold(krakenPrice);
            boughtAnimals.add(newKraken);
            Game.getCurrentPlayer().addAnimal(newKraken);
        }
        else if(chosenSpecies.equals("Linnr")){
            newLinnr.setName(chosenName);
            newLinnr.setGender(chosenGender);
            Game.getCurrentPlayer().removeGold(linnrPrice);
            boughtAnimals.add(newLinnr);
            Game.getCurrentPlayer().addAnimal(newLinnr);
        }
        else if(chosenSpecies.equals("Tilberi")){
            newTilberi.setGender(chosenGender);
            newTilberi.setName(chosenName);
            Game.getCurrentPlayer().removeGold(tilberiPrice);
            boughtAnimals.add(newTilberi);
            Game.getCurrentPlayer().addAnimal(newTilberi);
        }
        System.out.println("\nShopkeeper: I'll just go ahead and give you the amulet that's" +
                           "tied to " + chosenName + " then. Anything else of interest to you?");
    }

    public void chooseAnimal(){
        chooseGender();
        chooseName();
    }

    public void notEnoughGold(){
        System.out.println("Shopkeeper: Don't try to cheat me! You don't have that much gold!");
    }

    public void chooseName(){
        System.out.println("Shopkeeper: You have only made excellent choices so far dear customer. " +
                "Think carefully now and decide on a name for your new animal friend. What should it be?");
        chosenName = HelperMethods.scan.nextLine();
    }

    public void chooseGender(){
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
