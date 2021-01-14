package com.company;

import java.util.Scanner;

public class Store {

    public void buyAnimal(){
        Scanner scan = new Scanner(System.in);
        Animal boughtAnimal = null;

        System.out.println("""
                Which animal do you want to buy?\s
                |1| Nykur\s
                |2| Gloson\s
                |3| Kraken\s
                |4| Linnr\s
                |5| Tilberi""");

        do{
            HelperMethods.tryParseInt();
        }while(HelperMethods.getInput() == -1);



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

}
