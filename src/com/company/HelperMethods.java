package com.company;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class HelperMethods {
    static Random randomNum = new Random();
    static Scanner scan = new Scanner(System.in);
    private static int inputInt = -1;
    private static double inputDouble = -1;
    private static boolean validChoice = true;
    public static Animal chosenAnimal = null;

    public static String tryPrintPlayerName(){
        if(Game.getCurrentPlayer()!=null){
            return ", " + Game.getCurrentPlayer().getName() + ".";
        }
        else{
            return ".";
        }
    }

    public static int tryParseInt(String message, int min, int max){
        do{
            System.out.println(message);
            validChoice=false;
            try{
                String input = scan.nextLine();
                inputInt = Integer.parseInt(input);
                if(inputInt < min || inputInt > max){
                    System.out.println("You must enter a number within the given range to continue" +
                                       tryPrintPlayerName());
                }
                else{
                    validChoice = true;
                }
            }
            catch(NumberFormatException e){
                System.out.println("You have to enter a number to continue" + tryPrintPlayerName());
            }
        }while(!validChoice);
        return inputInt;
    }

    public static double tryParseDouble(String message, double min){
        do{
            validChoice = false;
            System.out.println(message);
            try{
                String input = scan.nextLine();
                if(input.contains(",")){
                    System.out.println("To enter fractions, please use a dot (.) instead of a comma (,) ");
                }
                else{
                    inputDouble = Double.parseDouble(input);
                    if(inputDouble<min){
                        System.out.println("You must enter a valid number to continue" + tryPrintPlayerName());
                    }
                    else{
                        validChoice = true;
                    }
                }
            }
            catch(NumberFormatException e){
                System.out.println("You have to enter a number to continue" + tryPrintPlayerName());
            }
        }while(!validChoice);
        return inputDouble;
    }

    public static void printPlayerAnimals(){
        System.out.println("\nMythological Animals: ");
        if(Game.getCurrentPlayer().animals.isEmpty()){
            System.out.println("\nOh no, you have no animals :'( ");
        }
        else{
            for(int i = Game.getCurrentPlayer().getAnimalList().size()-1; i>=0; i--){
                if(Game.getCurrentPlayer().getAnimalList().get(i).getHealth()==0){
                    Game.getCurrentPlayer().getAnimalList().remove(i);
                    System.out.println("\nOh no! It looks like " +
                            Game.getCurrentPlayer().animals.get(i).getName() + " has died :'(" );
                }
            }
            for(Animal animal : Game.getCurrentPlayer().animals){
                System.out.println("\n|" + (Game.getCurrentPlayer().animals.indexOf(animal) + 1) +
                        "| " + animal.getName() + " the " + animal.getGender() + " " +
                        animal.getClass().getSimpleName() + "\nHealth: " + animal.getHealth() +
                        " (" + animal.getLostHealth() + " lost since last round.)" +
                        "\n" + animal.getHealthStatus());
            }

        }
    }

    public static void printOnlyEat(){
        System.out.println(chosenAnimal.getSpecies().string() + "s only eat:");
        for(int i = 0; i<=chosenAnimal.getOnlyEat().size()-1; i++){
            System.out.println("|" + (i+1) + "| " + chosenAnimal.getOnlyEat().get(i).foodType);
        }
    }

    public static void printPlayerFoodList(){
        int skipped = 0;
        ArrayList<Food>playerFoods = Game.getCurrentPlayer().getFoodList();
        System.out.println("\nFood: ");
        for(int i = 0; i<=playerFoods.size()-1; i++){
            if(playerFoods.get(i).getFoodAmount() == 0){
                skipped += 1;
            }
            else{
                System.out.println("|" + (i + 1 - skipped) + "| " +
                                   playerFoods.get(i).getFoodType().string() +
                                   " (" + playerFoods.get(i).getFoodAmount() + " kg)");
            }
            // If skipped is the same number as the number of existing food types
            // Tell player they have no food
            if(skipped == 3){
                System.out.println("\nIt seems you don't own any food! But not to worry, " +
                                   "Myth Store might just have some.");
            }
        }
    }

    public static void invalidInput(){
        System.out.println("You pressed " + inputInt + ". This is not a valid number. " +
                "Please try again and enter a number within the specified range.");
        inputInt=-1;
        validChoice = false;
    }

    public static void chooseAnimal(){
        ArrayList<Animal>playerAnimals = Game.getCurrentPlayer().animals;

        tryParseInt("\nEnter the number of your animal to choose that animal.",
                    1, playerAnimals.size());

        chosenAnimal = playerAnimals.get(inputInt-1);
    }

    public static boolean fiftyPerChance(){
        boolean successful = false;
        int result = randomNum.nextInt(100);

        if(result >=50){
            successful = true;
        }
        return successful;
    }

    public static boolean diseaseChance(){
        boolean diseased = false;
        int diseaseChance = randomNum.nextInt(100);

        if(diseaseChance>80){
            diseased = true;
        }
        return diseased;
    }

    public static void clearConsole(){
        System.out.println("\n".repeat(60));
    }

    public static void setInputInt(int newInput){
        inputInt = newInput;
    }

    public static int getInputInt(){
        return inputInt;
    }

    public static void setValidChoice(boolean bool){
        validChoice = bool;
    }

    public static boolean isValidChoice(){
        return validChoice;
    }

}
