package com.company;
import java.util.ArrayList;
import java.util.Random;

public class Game {

    Animal animalToMate;
    Animal chosenAnimal;
    Animal offspring;
    private ArrayList<Player>players = new ArrayList<Player>();
    private static Player currentPlayer = null;
    Store mythStore = new Store();

    public int numOfRounds = -1;
    public int numOfPlayers = -1;
    private final int startingGold = 50;

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
            }while(HelperMethods.getInputInt()==-1);


            switch (HelperMethods.getInputInt()){
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
                    HelperMethods.setInputInt(1);
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
                    HelperMethods.setInputInt(-1);
                }
            }while(HelperMethods.getInputInt()==-1);
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
            } while(HelperMethods.getInputInt()==-1);

            // If the input number of players is within range, start adding players
            if(minPlayers <= HelperMethods.getInputInt() && HelperMethods.getInputInt() <= maxPlayers) {
                numOfPlayers = HelperMethods.getInputInt();
                for(int i = 0; i < HelperMethods.getInputInt(); i++){
                    System.out.println("\nEnter the name of player " + (i+1) + ":");
                    String name = HelperMethods.scan.nextLine();
                    players.add(new Player(name));
                    System.out.println(name + " is now player " + (i+1));
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
            } while(HelperMethods.getInputInt()==-1);
            // If the input number of players is within range, set numberOfRounds
            if(minRounds <= HelperMethods.getInputInt() && HelperMethods.getInputInt() <= maxRounds) {
                numOfRounds = HelperMethods.getInputInt();
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
            // Assign all players a starting amount of gold
            for(Player player : players){
                player.setGoldAmount(startingGold);
            }
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
                // Let each of the player's animals lose a random amount of
                // health at the start of the round
                for(Animal animal : player.getAnimalList()){
                    animal.loseHealth();
                }
                // Let the player have their round until they make a valid choice
                do{
                    // Print the name of the player and inform them that it's their turn
                    System.out.println("\nYOUR TURN, " + player.getName().toUpperCase() + "! ");
                    printPlayerStats();

                    // Make the player choose a valid number from the list before proceeding
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
                        if(HelperMethods.getInputInt() < 1 || 5 < HelperMethods.getInputInt()){
                            HelperMethods.invalidInput();
                            HelperMethods.setInputInt(-1);
                        }
                        // If player tries to perform an action on an animal when they don't have any
                        // Tell them they can't do said action and then ask them to make a new choice
                        else if(currentPlayer.getAnimalList().isEmpty() && HelperMethods.getInputInt()>2){
                            System.out.println("You can't perform that action as you don't have any animals");
                            HelperMethods.setInputInt(-1);
                        }
                        else if(HelperMethods.getInputInt() == 4 && currentPlayer.getAnimalList().size()<2){
                            System.out.println("You need to have two animals in order to try for offspring.");
                            HelperMethods.setInputInt(-1);
                        }
                        // Default value is -1
                    }while(HelperMethods.getInputInt()==-1);

                    switch (HelperMethods.getInputInt()){
                        case 1:
                            mythStore.goToAnimalStore();
                            break;

                        case 2:
                            mythStore.goToFoodStore();
                            break;

                        case 3:
                            HelperMethods.setValidChoice(true);
                            break;

                        case 4:
                            HelperMethods.setValidChoice(false);

                            System.out.println("Choose the mythological animal you want to mate.");
                            // Let the player choose an animal and let the animal be chosenAnimal
                            chooseAnimal();
                            // Let animalToMate be chosenAnimal
                            animalToMate = chosenAnimal;
                            System.out.println("Choose the mythological animal you want to mate with.");
                            // Let player choose a second animal and let that animal be chosenAnimal
                            chooseAnimal();

                            // If player tries to mate an animal with itself,
                            // let them make a new choice
                            if(animalToMate == chosenAnimal){
                                System.out.println("Your animal can't mate with itself.");
                            }
                            // If player tries to mate an animal with another of the same gender,
                            // let them make a new choice
                            else if(chosenAnimal.getGender().equals(animalToMate.getGender())){
                                System.out.println("Animals of the same gender can't procreate.");
                            }
                            // If player tries to mate an animal with one of the same species
                            // and opposite gender, proceed.
                            else if(chosenAnimal.getSpecies().equals(animalToMate.getSpecies())){
                                tryOffspring();
                                HelperMethods.setValidChoice(true);
                            }
                            else{
                                System.out.println("Animals of different species can't mate.");
                            }
                            animalToMate = null;
                            chosenAnimal = null;
                            break;

                        case 5:
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
            HelperMethods.setValidChoice(false);
            do{
                System.out.println("Enter the number of your animal to choose that animal.");
                HelperMethods.tryParseInt();
            }while(HelperMethods.getInputInt()==-1);

            int animalNum = HelperMethods.getInputInt();
            if(1 <= animalNum &&
                    animalNum <= currentPlayer.getAnimalList().size()){
                // Get the animal the player chooses from their list
                // and send it to chosenAnimal
                chosenAnimal = currentPlayer.getAnimalList().get(HelperMethods.getInputInt()-1);
                HelperMethods.setValidChoice(true);
            }
            else{
                System.out.println("You must enter a number corresponding to an animal in your list.");
            }
        }while(!HelperMethods.getValidChoice());

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
        System.out.println("\n......AND THE WINNER IS......" +
                "\n" + winnerName.toUpperCase());
    }

    public void tryOffspring(){
        boolean success = HelperMethods.fiftyPerChance();
        if(success){
            createOffspring();
        }
        else{
            System.out.println("You didn't get any babies! :( ");
        }
    }

    public void createOffspring(){
        Gender gender;
        String name;
        String species = chosenAnimal.getSpecies();

        Random random = new Random();
        int numberOfOffspring = 1 + random.nextInt(chosenAnimal.getMaxOffspring()+1);

        System.out.println("Wow! " + chosenAnimal.getName() + " and " + animalToMate.getName() + " had " +
                numberOfOffspring + " offspring! \nNow it's time to name them!");

        // Iterate through each offspring to be created
        for(int i = 1; i<=numberOfOffspring; i++){

            boolean female = HelperMethods.fiftyPerChance();
            if(female){
                gender = Gender.FEMALE;
            }
            else{
                gender = Gender.MALE;
            }

            // Prompt user to name the given baby
            System.out.println("Name baby number " + i + ", it's a " + gender.string() + "!" );
             name = HelperMethods.scan.nextLine();

            // Create an object of the same type as chosenAnimal
            if(species == Species.NYKUR.species){
                offspring = new Nykur(name, gender);
            }
            else if(species == Species.GLOSON.species){
                offspring = new Gloson(name, gender);
            }
            else if(species == Species.KRAKEN.species){
                offspring = new Kraken(name, gender);
            }
            else if(species == Species.LINNR.species){
                offspring = new Linnr(name, gender);
            }
            else if(species == Species.TILBERI.species){
                offspring = new Tilberi(name, gender);
            }
            // Add the new animal to the players list of animals
            currentPlayer.addAnimal(offspring);
        }
    }

    public void feedAnimals(){
        double foodAmount = 0;
        do{
            HelperMethods.setValidChoice(false);
            printPlayerStats();
            System.out.println("\nWhich animal do you want to feed?");
            //TODO if player has food animal eats, let them feed
            // else send them back with message
            chooseAnimal();
            HelperMethods.printPlayerFoodList();
            System.out.println("\n" + chosenAnimal.getSpecies() + "s eat the following types of food:");
            //TODO print list of foods animal eats
            System.out.println("\nWhich food do you want to feed your animal with?");
            HelperMethods.tryParseInt();

            switch(HelperMethods.getInputInt()){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
                    double restoredHealth = (double) chosenAnimal.getHunger() * foodAmount;




        }while(!HelperMethods.getValidChoice());
    }

    public void chooseFood(){
        do{
            HelperMethods.setValidChoice(false);
            do{
                System.out.println("Enter the number of your animal to choose that animal.");
                HelperMethods.tryParseInt();
            }while(HelperMethods.getInputInt()==-1);

            int animalNum = HelperMethods.getInputInt();
            if(1 <= animalNum &&
                    animalNum <= currentPlayer.getAnimalList().size()){
                // Get the animal the player chooses from their list
                // and send it to chosenAnimal
                chosenAnimal = currentPlayer.getAnimalList().get(HelperMethods.getInputInt()-1);
                HelperMethods.setValidChoice(true);
            }
            else{
                System.out.println("You must enter a number corresponding to an animal in your list.");
            }
        }while(!HelperMethods.getValidChoice());

    }

    public static void printPlayerStats(){
        // Print player gold
        System.out.println("\nGold: " + currentPlayer.getGoldAmount());

        //Print players animals
        HelperMethods.printPlayerAnimals();

        // Print the players food items
        System.out.println("\nFood: ");
        HelperMethods.printPlayerFoodList();

    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }
}
