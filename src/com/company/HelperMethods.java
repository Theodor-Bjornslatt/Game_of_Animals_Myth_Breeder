package com.company;

import java.util.Scanner;

public class HelperMethods {
    Scanner scan = new Scanner(System.in);
    private int input = -1;

    //TODO add tryParseInt() method

    public void setInput(int input){
        this.input = input;
    }

    public int getInput(){
        return input;
    }
}
