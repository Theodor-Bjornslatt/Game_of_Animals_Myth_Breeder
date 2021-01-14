package com.company;

import java.util.Scanner;

public class HelperMethods {
    static Scanner scan = new Scanner(System.in);
    private static int input = -1;
    private static boolean validChoice = true;

    public static void tryParseInt(){
        input = -1;
        try{
            input = Integer.parseInt(scan.nextLine());
        }
        catch(NumberFormatException e){
            System.out.println("You have to enter a number to continue");
        }
    }

    public static void invalidInput(){
        System.out.println("You pressed " + input + ". This is not a valid number. " +
                "Please try again and enter a number within the specified range.");
    }

    //TODO add tryParseInt() method

    public static void setInput(int newInput){
        input = newInput;
    }

    public static int getInput(){
        return input;
    }

    public static void setValidChoice(boolean bool){
        validChoice = bool;
    }

    public static boolean getValidChoice(){
        return validChoice;
    }

}
