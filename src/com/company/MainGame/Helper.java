package com.company.MainGame;

import com.company.Animals.Animal;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Helper {
    static Random randomNum = new Random();
    static Scanner scan = new Scanner(System.in);
    private static int inputInt = -1;
    private static double inputDouble = -1;
    private static boolean validChoice = true;
    public static Animal chosenAnimal = null;

    public static String yesOrNo(String question){
        while(true){
            System.out.println(question);
            String answer = scan.nextLine();
            if(answer.equals("n") || answer.equals("y")){
                return answer;
            }
            else{
                System.out.println("You must enter y or n to continue.");
            }
        }

    }

    public static String tryPrintPlayerName(){
        if(Game.getCurrentPlayer()!=null){
            return ", " + Game.getCurrentPlayer().getName() + ".";
        }
        else{
            return ".";
        }
    }

    public static String assignName(String message){
        while(true){
            System.out.println(message);
            String name = scan.nextLine();
            if(name.trim().isEmpty()){
                System.out.println("You must enter a name to continue.");
            }
            else{
                return name;
            }
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
                    System.out.println("\nYou must enter a number within the given range to continue" +
                                       tryPrintPlayerName());
                }
                else{
                    validChoice = true;
                }
            }
            catch(NumberFormatException e){
                System.out.println("\nYou have to enter a number to continue" + tryPrintPlayerName());
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
            for(Animal animal : Game.getCurrentPlayer().animals){
                System.out.println("\n|" + (Game.getCurrentPlayer().animals.indexOf(animal) + 1) +
                        "| " + animal.getName() + " the " + animal.getGender().gender + " " +
                        animal.getSpecies().species + "\nHealth: " + animal.getHealth() +
                        " (" + animal.getLostHealth() + " lost since last round.)" +
                        "\n" + animal.getHealthStatus());
            }

        //}
    }

    public static void printOnlyEat(){
        System.out.println(chosenAnimal.getSpecies().species + "s only eat:");
        for(int i = 0; i<=chosenAnimal.getOnlyEat().size()-1; i++){
            System.out.println("|" + (i+1) + "| " + chosenAnimal.getOnlyEat().get(i).getFoodType().foodType);
        }
    }

    public static void printPlayerFoodList(){
        System.out.println("\nFood: ");
        if(Game.getCurrentPlayer().getFoodList().isEmpty()){
            System.out.println("You don't have any food!");
        }
        else{
            for(int i = 0; i<=Game.getCurrentPlayer().getFoodList().size()-1; i++){
                System.out.println("|" + (i + 1) + "| " +
                        Game.getCurrentPlayer().getFoodList().get(i).getFoodType().foodType +
                        " (" + Game.getCurrentPlayer().getFoodList().get(i).getFoodAmount() + " kg)");
            }
        }

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
