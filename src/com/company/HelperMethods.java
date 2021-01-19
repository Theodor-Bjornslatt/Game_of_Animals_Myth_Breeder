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

    public static void tryParseInt(){
        inputInt = -1;
        try{
            String input = scan.nextLine();
            if(input.contains(",")){
                System.out.println("To buy fractions, please use a dot (.) instead of a comma (,) ");
            }
            else{
                inputInt = Integer.parseInt(input);
            }
        }
        catch(NumberFormatException e){
            System.out.println("You have to enter a number to continue");
        }
    }

    public static void tryParseDouble(){
        inputDouble = -1;
        try{
            inputDouble = Double.parseDouble(scan.nextLine());
        }
        catch(NumberFormatException e){
            System.out.println("You have to enter a number to continue");
        }
    }

    public static void printPlayerAnimals(){
        for(Animal animal : Game.getCurrentPlayer().getAnimalList()){
            System.out.println("|" + (Game.getCurrentPlayer().getAnimalList().indexOf(animal) + 1) + "| " +
                    animal.getName() + " the " + animal.getGender() + " " + animal.getClass().getSimpleName() +
                    " ... Health: " + animal.getHealth());
        }
    }

    public static void printPlayerFoodList(){

        int skipped = 0;
        ArrayList<Food>playerFoods = Game.getCurrentPlayer().getFoodList();
        for(int i = 0; i<=playerFoods.size()-1; i++){
            if(playerFoods.get(i).getFoodAmount() == 0){
                skipped += 1;
            }
            else{
                System.out.println("|" + (i + 1 - skipped) + "| " +
                                   playerFoods.get(i).getFoodType() +
                                   " (" + playerFoods.get(i).getFoodAmount() + " kg)");
            }
            // If the player had 0 kg of all 3 food types, tell them
            if(skipped == 3){
                System.out.println("\nIt seems you don't own any food! But not to worry, " +
                                   "Myth Store might just have some.");
            }

        }

    }

    public static void invalidInput(){
        System.out.println("You pressed " + inputInt + ". This is not a valid number. " +
                "Please try again and enter a number within the specified range.");
    }

    public static boolean fiftyPerChance(){
        boolean successful = false;
        int result = randomNum.nextInt(100);

        if(result >=50){
            successful = true;
        }
        return successful;
    }

    public static void setInputInt(int newInput){
        inputInt = newInput;
    }

    public static int getInputInt(){
        return inputInt;
    }
    public static void setInputDouble(double newInput){
        inputDouble = newInput;
    }

    public static double getInputDouble(){
        return inputDouble;
    }

    public static void setValidChoice(boolean bool){
        validChoice = bool;
    }

    public static boolean getValidChoice(){
        return validChoice;
    }

}
