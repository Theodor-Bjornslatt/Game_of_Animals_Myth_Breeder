package com.company;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private ArrayList<Player>players = new ArrayList<>();
    Scanner scan = new Scanner(System.in);

    int input;
    int numOfRounds = -1;

    public Game(){
        game();
    }

    public void game (){
        setPlayers();
        setNumberOfRounds();
    }

    public void setPlayers(){
        int minPlayers = 1;
        int maxPlayers = 4;
        while(true){
            do{
                System.out.println("Please enter the number of players: | 1 | 2 | 3 | 4 |");
                // call to method within class
                tryParseInt();
            } while(input==-1);

            // If the input number of players is within range, start adding players
            if(minPlayers <= input && input <= maxPlayers) {
                for(int i = 0; i < input; i++){
                    System.out.println("Enter the name of player " + (i+1) + ":");
                    String name = scan.nextLine();
                    players.add(new Player(name));
                    System.out.println(name + " is now player " + (i+1));
                }
                //Check methods for setting moneyAmount and getting moneyAmount are working
                players.get(0).setMoneyAmount();
                for(int i = 0; i < input; i++){
                    System.out.println("Money of " + players.get(i).getName() + ": " +
                            players.get(i).getMoneyAmount());
                }
                break;
            }
            // If input number of players is not within range, tell the user to input a valid number.
            else{
                invalidInput();
            }


        }
    }

    public void setNumberOfRounds(){
        int minRounds = 5;
        int maxRounds = 30;

        while(true){
            do{
                // Ask the user to enter the number of rounds and wait for their input.
                System.out.println("Please enter how many rounds you want to play: | 5 | 6 | ... | 29 | 30 |");
                tryParseInt();
            } while(input==-1);
            // If the input number of players is within range, set numberOfRounds
            if(minRounds <= input && input <= maxRounds) {
                numOfRounds = input;
                break;
            }
            else{
                invalidInput();
            }

        }

    }

    public void tryParseInt(){
        input = -1;
        try{
            input = Integer.parseInt(scan.nextLine());
        }
        catch(NumberFormatException e){
            System.out.println("You must enter a number.");
        }
    }

    public void invalidInput(){
        System.out.println("You pressed " + input + ". This is not a valid number. " +
                "Please try again and enter a number within the specified range.");
    }

    public void endGame(){
        // Sell all animals the player owns
        // for each player, check how much money they have
        // let the player with the most money win
    }

    public void playerStats(){
        // print the animals the player has - name and getClass
        // as well as health and healthChange (.loseHealth()) since last round
        // print the food the player owns
        // print the amount of money the player owns

    }

    int counter = 1;

    int numberOfRounds;
}
