package com.company;
import java.util.ArrayList;

public class Game {

    Animal chosenAnimal = null;
    private ArrayList<Player>players = new ArrayList<>();
    private static Player currentPlayer = null;

    public int numOfRounds = -1;
    public int numOfPlayers = -1;

    public Game(){
        mainMenu();
    }

    public void mainMenu(){
        while(true){

            System.out.println("""
                
                GAME OF ANIMALS: MYTH BREEDER\s
                 |1| Add player(s)
                 |2| Set number of rounds
                 |3| Start new game
                 |4| Exit game""");
            do{
                HelperMethods.tryParseInt();
            }while(HelperMethods.getInput()==-1);


            switch (HelperMethods.getInput()){
                case 1:
                    tryAddPlayers();
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

                default:
                    HelperMethods.invalidInput();

            }

        }

    }

    public void tryAddPlayers(){
        // If user want to add players and none have been added, proceed
        if(players.isEmpty()){
            setPlayers();
        }
        // Else if user wants to add players but players have already been added
        else{
            System.out.println("\nWARNING!!! " +
                    "\nYou have already added players to the game. " +
                    "If you proceed, the current list of players will be deleted " +
                    "and you will have to add any and all players again. " +
                    "\nDo you want to proceed? (y/n)");


            do{
                String choice = HelperMethods.scan.nextLine().toLowerCase();
                // Make user choose between clearing the list of players and adding new ones
                if(choice.equals("y")){
                    players.clear();
                    setPlayers();
                    HelperMethods.setInput(1);
                }
                // or doing nothing and returning to mainMenu
                else if(choice.equals("n")){
                    return;
                }
                // If user doesn't make an explicit choice, prompt them to make a choice
                else{
                    System.out.println("You must make a choice to continue. " +
                            "Press y to delete all players and create a new list of players. " +
                            "Press n to return to Main Menu without making any changes.");
                    HelperMethods.setInput(-1);
                }
            }while(HelperMethods.getInput()==-1);
        }
    }

    public void setPlayers(){
        int minPlayers = 1;
        int maxPlayers = 4;
        while(true){
            do{
                System.out.println("\nPlease enter the number of players: | 1 | 2 | 3 | 4 |");
                // call to method within class
                HelperMethods.tryParseInt();
            } while(HelperMethods.getInput()==-1);

            // If the input number of players is within range, start adding players
            if(minPlayers <= HelperMethods.getInput() && HelperMethods.getInput() <= maxPlayers) {
                numOfPlayers = HelperMethods.getInput();
                for(int i = 0; i < HelperMethods.getInput(); i++){
                    System.out.println("\nEnter the name of player " + (i+1) + ":");
                    String name = HelperMethods.scan.nextLine();
                    players.add(new Player(name));
                    System.out.println(name + " is now player " + (i+1));
                }
                // Assign all players a starting amount of gold
                for(Player player : players){
                    player.setGoldAmount(20);
                }
                //Check methods for adding moneyAmount and getting moneyAmount are working
                players.get(0).addGold(10);
                players.get(1).removeGold(10);
                for(int i = 0; i < numOfPlayers; i++){
                    System.out.println("Money of " + players.get(i).getName() + ": " +
                            players.get(i).getGoldAmount());
                }
                break;
            }
            // If input number of players is not within range, tell the user to input a valid number.
            else{
                HelperMethods.invalidInput();
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
                HelperMethods.tryParseInt();
            } while(HelperMethods.getInput()==-1);
            // If the input number of players is within range, set numberOfRounds
            if(minRounds <= HelperMethods.getInput() && HelperMethods.getInput() <= maxRounds) {
                numOfRounds = HelperMethods.getInput();
                break;
            }
            else{
                HelperMethods.invalidInput();
            }

        }

    }

    public void startGame(){
        //If players have been added and the number of rounds has been chosen
        //run the game
        if (numOfRounds!=-1 && numOfPlayers != -1){
            runGame();
        }
        // If players have not been added and the number of rounds has not been chosen
        // print message
        else if(numOfRounds == -1 && numOfPlayers == -1){
            System.out.println("Sorry, you have to add players and set the number " +
                    "of rounds you want to play, in order to start a new game.");

        }
        // If the number of rounds has not been chosen, print message
        else if(numOfRounds == -1){
            System.out.println("Sorry, you can't start a game without setting " +
                    "the number of rounds.");
        }
        // If players have not been added to the game, print message
        else{
            System.out.println("Sorry, you can't start a game without adding players.");
        }
    }

    public void runGame(){

        Store mythStore = new Store();
        Animal tempAnimal = null;
        int counter = 1;

        while(counter <= numOfRounds){
            // As long as user doesn't make a valid choice, do the following
            do{
                //As a default, assume the player makes choices
                // that are possible with their given means
                HelperMethods.setValidChoice(true);

                System.out.println("\nRound " + counter);
                //Iterate through each player and let them play their round
                for (Player player : players){
                    currentPlayer = player;
                    // Print the name of the player and inform them that it's their turn
                    System.out.println("\nYOUR TURN, " + player.getName().toUpperCase() + "! ");
                    System.out.println("\nYour animals: ");
                    // If the player has no animals, let them know
                    if(currentPlayer.getAnimalList().isEmpty()){
                        System.out.println("\nOh no, you have no animals :'( " +
                                "\nMaybe you can get some from Myth Store?");
                    }
                    // Else, print a list of their animals
                    else{
                        printAnimals();
                    }

                    do{
                        System.out.println("\nMake your move " + player.getName() + ":");
                        System.out.println("""
                           |1| Buy animals from Myth Store\s
                           |2| Buy food from Myth Store
                           |3| Feed your mythological animals
                           |4| Try your luck - try for offspring
                           |5| Sell one or more of your mythological animals""");
                        //TODO randomize the number of offspring for each animal to be within specific ranges
                        HelperMethods.tryParseInt();
                        // If player tries to make a choice that's not defined in menu
                        // Tell player their choice is not valid
                        //Then present player with menu again and ask them to make a new choice
                        if(HelperMethods.getInput() < 1 || 5 < HelperMethods.getInput()){
                            HelperMethods.invalidInput();
                            HelperMethods.setInput(-1);
                        }
                        // If player tries to perform an action on an animal when they don't have any
                        // Tell them they can't do said action and then ask them to make a new choice
                        else if(currentPlayer.getAnimalList().isEmpty() && HelperMethods.getInput()>2){
                            System.out.println("You can't perform that action as you don't have any animals");
                            HelperMethods.setInput(-1);
                        }
                    }while(HelperMethods.getInput()==-1);

                    switch (HelperMethods.getInput()){
                        case 1:
                            mythStore.goToAnimalStore();
                            if(mythStore.getBoughtAnimals().isEmpty()){
                                HelperMethods.setValidChoice(false);
                            }
                            else{
                                currentPlayer.getAnimalList().addAll(mythStore.getBoughtAnimals());
                            }
                            break;

                        case 2:
                            break;

                        case 3:
                            break;

                        case 4:
                            // if player has two animals of same species and opposite genders, breed
                            int minNum;
                            chooseAnimal();
                            if(1 <= HelperMethods.getInput() &&
                               HelperMethods.getInput() <= currentPlayer.getAnimalList().size()+1){
                                //If there is an animal with input number in the list, proceed.
                            }


                            break;
                        case 5:
                            System.out.println("");
                            if(1 <= HelperMethods.getInput() &&
                               HelperMethods.getInput() <= currentPlayer.getAnimalList().size()+1){
                                //If there is an animal with input number in the list, proceed.
                                mythStore.sellAnimal();
                            }
                            else{
                                System.out.println("You must enter a valid number to proceed.");
                            }
                            break;
                    }

                }
            }while(!HelperMethods.getValidChoice());

            counter++;

        }
        //TODO once all rounds have been played, sell all animals and add the money to players.
        // Then see who has most money and let players know who won
    }

    public void printAnimals(){
        for(Animal animal : currentPlayer.getAnimalList()){
            System.out.println("|" + (currentPlayer.getAnimalList().indexOf(animal) + 1) + "| " +
                               animal.getName() + " the " + animal.getGender() + " " + animal.getClass().getSimpleName());
        }
    }

    public void chooseAnimal(){
        do{
            System.out.println("Enter the number of your animal to choose that animal.");
            HelperMethods.tryParseInt();
        }while(HelperMethods.getInput()==-1);

        try{
            chosenAnimal = currentPlayer.getAnimalList().get(HelperMethods.getInput()-1);
        }
        catch(Exception e){
            System.out.println("You must enter a number corresponding to an animal in your list.");
        }
    }


    public void endGame(){
        //TODO Sell all animals the player owns
        // for each player, check how much money they have
        // let the player with the most money win
        // Then enter to return to main menu (break;)
    }

    public void playerStats(){
        currentPlayer.getGoldAmount();
        // TODO print the animals the player has - name and getClass
        //  as well as health and healthChange (.loseHealth()) since last round
        //  print the food the player owns
        //  print the amount of money the player owns

    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }
}
