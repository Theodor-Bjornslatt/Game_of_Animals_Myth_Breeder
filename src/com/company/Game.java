package com.company;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private ArrayList<Player>players = new ArrayList<>();
    Scanner scan = new Scanner(System.in);

    public int input; //TODO create helper class and let int input be inside that.
                      // Create static methods inside to tryParse etc.
                      // Create getter and setter for int input inside helper class.
                      // Replace input in Game with getters and setters.
    public int numOfRounds = -1;
    public int numOfPlayers = -1;

    public Game(){
        mainMenu();
    }

    public void mainMenu(){
        //TODO either move setplayers and setnumberofrounds to sequence with startgame
        // or fix so players can or can't be added after the fact
        // possibly just clear all players if trying to add player again?
        while(true){

            System.out.println("""
                
                GAME OF ANIMALS: MYTH BREEDER\s
                 |1| Add player(s)
                 |2| Set number of rounds
                 |3| Start new game
                 |4| Exit game              
                 """);
            do{
                tryParseInt();
            }while(input==-1);

            switch (input){
                case 1:
                    setPlayers();
                    break;

                case 2:
                    setNumberOfRounds();
                    break;

                case 3:
                    startGame();
                    break;

                case 4:
                    System.exit(0);
                    break;

            }

        }

    }

    public void setPlayers(){
        int minPlayers = 1;
        int maxPlayers = 4;
        while(true){
            do{
                input = -1;
                System.out.println("\nPlease enter the number of players: | 1 | 2 | 3 | 4 |");
                // call to method within class
                tryParseInt();
            } while(input==-1);

            // If the input number of players is within range, start adding players
            if(minPlayers <= input && input <= maxPlayers) {
                numOfPlayers = input;
                for(int i = 0; i < input; i++){
                    System.out.println("\nEnter the name of player " + (i+1) + ":");
                    String name = scan.nextLine();
                    players.add(new Player(name));
                    System.out.println(name + " is now player " + (i+1));
                }
                //Check methods for adding moneyAmount and getting moneyAmount are working
                players.get(0).addMoneyAmount(10);
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
                input = -1;
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

    public void startGame(){
        if (numOfRounds!=-1 && numOfPlayers != -1){
            runGame();
        }
        else if(numOfRounds == -1 && numOfPlayers == -1){
            System.out.println("Sorry, you have to add players and set the number " +
                    "of rounds you want to play, in order to start a new game.");

        }
        else if(numOfRounds == -1){
            System.out.println("Sorry, you can't start a game without setting " +
                    "the number of rounds.");
        }
        else{
            System.out.println("Sorry, you can't start a game without adding players.");
        }
    }

    public void runGame(){

        int counter = 1;
        Store mythStore = new Store();
        Animal tempAnimal = null;

        while(counter <= numOfRounds){
            System.out.println("Round " + counter);

            for (Player player : players){
                ArrayList<Animal>playerAnimals = new ArrayList<>(player.getAnimals());
                // Print the name of the player and inform them that it's their turn
                System.out.println("\nYOUR TURN, " + player.getName().toUpperCase() + "! ");
                   System.out.println("\nYou have the following animals: ");
                   // TODO for each player present a list of their animals
                   //  if a round has passed by (or if their list of animals
                   //  contains any)
                for(Animal animal : playerAnimals){
                        System.out.println("|" + (playerAnimals.indexOf(animal)+1) + "| " +
                                animal.getName() + animal.getGender() + animal.getClass().getSimpleName());
                }

                do{
                    System.out.println("""
                           Make your move:
                           |1| Buy mythological animals\s
                           |2| Buy food
                           |3| Feed your mythological animals
                           |4| Try your luck - try for offspring
                           |5| Sell one or more of your mythological animals""");
                    tryParseInt();
                }while(input==-1);
                //TODO check if player has animals in list when making choice 3, 4 or 5
                // otherwise return to "make your move"
                switch (input){
                    case 1:
                        mythStore.buyAnimal();
                        continue;

                    case 2:
                        break;

                    case 3:
                        break;

                    case 4:
                        // if player has two animals of same species and opposite genders, breed
                        int minNum =
                        chooseAnimal();
                        if(1 >= input && input <= playerAnimals.size()+1){
                            //TODO If there is an animal with input number in the list, proceed.
                        }
                        else{
                            System.out.println("You must enter a valid number to proceed.");
                        }


                            break;
                    case 5:
                        System.out.println("");
                        if(1 >= input && input <= playerAnimals.size()+1){
                            //TODO If there is an animal with input number in the list, proceed.
                            mythStore.sellAnimal();
                        }
                        else{
                            System.out.println("You must enter a valid number to proceed.");
                        }
                        break;
                }

            }
            counter++;

        }
    }

    public int chooseAnimal(){
        do{
            System.out.println("Enter the number of your animal to choose that animal.");
            tryParseInt();
        }while(input==-1);

        return input;
    }

    public void endGame(){
        //TODO Sell all animals the player owns
        // for each player, check how much money they have
        // let the player with the most money win
        // Then enter to return to main menu (break;)
    }

    public void playerStats(){
        // TODO print the animals the player has - name and getClass
        //  as well as health and healthChange (.loseHealth()) since last round
        //  print the food the player owns
        //  print the amount of money the player owns

    }

}
