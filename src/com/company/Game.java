package com.company;
import java.util.ArrayList;
import java.util.Random;

public class Game {

    Animal chosenAnimal = null;
    Animal offspring = null;
    private ArrayList<Player>players = new ArrayList<>();
    private static Player currentPlayer = null;
    Store mythStore = new Store();

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

        int round = 1;
        while(round <= numOfRounds){
            // As long as user doesn't make a valid choice, do the following
                //As a default, assume the player makes choices
                // that are possible with their given means
                HelperMethods.setValidChoice(true);
                System.out.println("\nRound " + round);
                //Iterate through each player and let them play their round
            for (Player player : players){
                currentPlayer = player;
                do{
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
                        HelperMethods.printPlayerAnimals();
                    }

                    do{
                        System.out.println("\nMake your move " + player.getName() + ":");
                        System.out.println("""
                          |1| Buy animals from Myth Store\s
                          |2| Buy food from Myth Store
                          |3| Feed your mythological animals
                          |4| Try your luck - try for offspring
                          |5| Sell one or more of your mythological animals""");
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
                        else if(HelperMethods.getInput() == 4 && currentPlayer.getAnimalList().size()<2){
                            System.out.println("You need to have two animals in order to try for offspring.");
                        }

                    }while(HelperMethods.getInput()==-1);

                    switch (HelperMethods.getInput()){
                        case 1:
                            mythStore.goToAnimalStore();
                            //TODO write method to check if action has been made
                            break;

                        case 2:
                            HelperMethods.setValidChoice(true);
                            break;

                        case 3:
                            HelperMethods.setValidChoice(true);
                            break;

                        case 4:
                            // if player has two animals of same species and opposite genders, breed
                            Animal animalToMate;

                            System.out.println("Choose the mythological animal you want to mate.");
                            chooseAnimal();
                            animalToMate = chosenAnimal;
                            System.out.println("Choose the mythological animal you want to mate with.");
                            chooseAnimal();

                            // If player tries to nate an animal with itself,
                            // let them make a new choice
                            if(animalToMate == chosenAnimal){
                                System.out.println("Your animal can't mate with itself.");
                                HelperMethods.setValidChoice(false);
                            }
                            // If player tries to mate an animal with another of the same gender,
                            // let them make a new choice
                            else if(chosenAnimal.getGender().equals(animalToMate.getGender())){
                                System.out.println("Animals of the same gender can't procreate.");
                                HelperMethods.setValidChoice(false);
                            }
                            // If player tries to mate an animal with one of the same species
                            // and opposite gender, proceed.
                            else if(chosenAnimal.getSpecies().equals(animalToMate.getSpecies())){
                                tryOffspring();
                                HelperMethods.setValidChoice(true);
                            }
                            else{
                                System.out.println("Animals of different species can't mate.");
                                HelperMethods.setValidChoice(false);
                            }
                            break;

                        case 5:
                            System.out.println("");
                                //If there is an animal with input number in the list, proceed.
                                mythStore.sellAnimal();
                            break;
                    }

                }while(!HelperMethods.getValidChoice());
            }
            round++;
        }
        endGame();
    }

    public void chooseAnimal(){
        do{
            System.out.println("Enter the number of your animal to choose that animal.");
            HelperMethods.tryParseInt();
        }while(HelperMethods.getInput()==-1);

        int animalNum = HelperMethods.getInput();
        if(1 <= animalNum &&
                animalNum <= currentPlayer.getAnimalList().size()){
            chosenAnimal = currentPlayer.getAnimalList().get(HelperMethods.getInput()-1);
        }
       else{
            System.out.println("You must enter a number corresponding to an animal in your list.");
        }
    }


    public void endGame(){
        //  TODO press enter to return to main menu (break;)
        int winnerGold = 0;
        String winnerName = "";

        for(Player player : players){
            currentPlayer = player;
            mythStore.sellAllAnimals();
            if(player.getGoldAmount() > winnerGold){
                winnerGold = player.getGoldAmount();
                winnerName = player.getName();
            }
        }
        System.out.println("......AND THE WINNER IS......" +
                "\n" + winnerName.toUpperCase());
    }

    public void tryOffspring(){
        boolean success = HelperMethods.fiftyPerChance();
        if(success = true){
            createOffspring();
        }
        else{
            System.out.println("You didn't get any babies! :( ");
        }
    }

    public void createOffspring(){
        String gender;
        String name;
        String species = chosenAnimal.getSpecies();

        Random random = new Random();
        int numberOfOffspring = 1 + random.nextInt(chosenAnimal.getMaxOffspring()+1);

        System.out.println("Wow! Your " + chosenAnimal.getSpecies() + "s have created " +
                numberOfOffspring + " offspring! \nNow it's time to name them!");
        for(int i = 1; i<=numberOfOffspring; i++){

            int randomGender = random.nextInt(2);
            if(randomGender == 0){
                gender = "Male";
            }
            else{
                gender = "Female";
            }

            System.out.println("Name baby number" + i + ", it's a " + gender.toLowerCase() + "!" );
             name = HelperMethods.scan.nextLine();

            if(species.equals("Nykur")){
                offspring = new Nykur(name, gender);
            }
            else if(species.equals("Gloson")){
                offspring = new Gloson(name, gender);
            }
            else if(species.equals("Kraken")){
                offspring = new Kraken(name, gender);
            }
            else if(species.equals("Linnr")){
                offspring = new Linnr(name, gender);
            }
            else if(species.equals("Tilberi")){
                offspring = new Tilberi(name, gender);
            }
            currentPlayer.addAnimal(offspring);
        }
    }

    public void playerStats(){
        System.out.println("Gold: " + currentPlayer.getGoldAmount());
        // TODO print the animals the player has - name and getClass
        //  as well as health and healthChange (.loseHealth()) since last round
        //  print the food the player owns
        //  print the amount of money the player owns

    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }
}
