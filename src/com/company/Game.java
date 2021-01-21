package com.company;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    private ArrayList<Player>players = new ArrayList<>();

    private static Player currentPlayer = null;

    private static Animal animalToMate;
    private static Animal chosenAnimal;
    private static Animal offspring;

    private static String chosenName;
    private static Species chosenSpecies;
    private static Gender chosenGender;
    private static FoodType chosenFood;
    private boolean animalsFed = false;
    private final double foodRemove = 0.5;

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
            }while(HelperMethods.getInputInt()==-1);

            switch (HelperMethods.getInputInt()) {
                case 1 -> tryAddPlayers();
                case 2 -> setNumberOfRounds();
                case 3 -> startGame();
                case 4 -> System.exit(0);
                default -> HelperMethods.invalidInput();
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
            System.out.println("""

                    WARNING!!!\s
                    
                    You have already added players to the game. 
                    If you proceed, the current list of players will be deleted 
                    and you will have to add any and all players again.\s
                    
                    Do you want to proceed? (y/n)""");

            do{
                String choice = HelperMethods.scan.nextLine().toLowerCase();
                // Make user choose between clearing the list of players and
                // adding new ones or doing nothing and returning to mainMenu
                // If user doesn't make an explicit choice, prompt them to make a choice
                if(choice.equals("y")){
                    players.clear();
                    setPlayers();
                    HelperMethods.setInputInt(1);
                }
                else if(choice.equals("n")){
                    return;
                }
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
                int startingGold = 50;
                player.setGoldAmount(startingGold);
            }
            mainGame();
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

    public void mainGame(){
        int round = 1;
        while(round <= numOfRounds){
            //Assume valid choice will be made
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

                    // Make the player choose a valid number from the list
                    // before moving on to next player
                    do{
                        printMenu();
                        // Default value is -1
                    }while(HelperMethods.getInputInt()==-1);

                    checkMenuChoice();
                    makeMenuChoice();
                }while(!HelperMethods.getValidChoice());
            }
            round++;
        }
        endGame();
    }

    public void printMenu(){
        System.out.println("\nMake your move " + currentPlayer.getName() + ":");
        System.out.println("""
                          |1| Buy animals from Myth Store\s
                          |2| Buy food from Myth Store
                          |3| Feed your mythological animals
                          |4| Try your luck - try for offspring
                          |5| Sell one or more of your mythological animals""");
        HelperMethods.tryParseInt();
    }

    public void checkMenuChoice(){
        // If player tries to perform an action on an animal when they don't have any
        // Tell them they can't do said action and then ask them to make a new choice
       if(currentPlayer.getAnimalList().isEmpty() && HelperMethods.getInputInt()>2){
            System.out.println("You can't perform that action as you don't have any animals");
            HelperMethods.setInputInt(-1);
       }
       else if(HelperMethods.getInputInt() == 4 && currentPlayer.getAnimalList().size()<2){
            System.out.println("You need to have two animals in order to try for offspring.");
            HelperMethods.setInputInt(-1);
       }
    }

    public void makeMenuChoice(){
        switch (HelperMethods.getInputInt()) {
            case 1 -> mythStore.goToAnimalStore();
            case 2 -> mythStore.goToFoodStore();
            case 3 -> {
                feedAnimals();
                if (animalsFed) {
                    HelperMethods.setValidChoice(true);
                }
                if (!animalsFed) {
                    HelperMethods.setValidChoice(false);
                }
            }
            case 4 -> mateAnimals();
            case 5 -> mythStore.sellAnimal();
            default -> {
                HelperMethods.invalidInput();
                HelperMethods.setInputInt(-1);
            }
        }
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

    public void mateAnimals(){
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

        chosenSpecies = chosenAnimal.getSpecies();

        Random random = new Random();
        int numberOfOffspring = 1 + random.nextInt(chosenAnimal.getMaxOffspring()+1);

        System.out.println("Wow! " + chosenAnimal.getName() + " and " + animalToMate.getName() + " had " +
                numberOfOffspring + " offspring! \nNow it's time to name them!");

        // Iterate through each offspring to be created
        for(int i = 1; i<=numberOfOffspring; i++){

            boolean female = HelperMethods.fiftyPerChance();
            if(female){
                chosenGender = Gender.FEMALE;
            }
            else{
                chosenGender = Gender.MALE;
            }

            // Prompt user to name the given baby
            System.out.println("Name baby number " + i + ", it's a " +
                    chosenGender.string().toLowerCase() + "!" );
             chosenName = HelperMethods.scan.nextLine();

            createAnimal();
        }
    }

    public void createAnimal(){
        // Create an object of the same type as chosenAnimal
        if(chosenSpecies == Species.NYKUR){
            offspring = new Nykur(chosenName, chosenGender);
        }
        else if(chosenSpecies == Species.GLOSON){
            offspring = new Gloson(chosenName, chosenGender);
        }
        else if(chosenSpecies == Species.KRAKEN){
            offspring = new Kraken(chosenName, chosenGender);
        }
        else if(chosenSpecies == Species.LINNR){
            offspring = new Linnr(chosenName, chosenGender);
        }
        else if(chosenSpecies == Species.TILBERI){
            offspring = new Tilberi(chosenName, chosenGender);
        }
        // Add the new animal to the players list of animals
        currentPlayer.addAnimal(offspring);
    }

    public void feedAnimals(){
        //TODO only proceed to feed if players list of foods contains a type
        // that the animal eats and only feed the animal if a valid food
        // has been chosen
        double restoredHealth;
        int animalIndex = 0;
        animalsFed = false;

        while(true){
            // Give the player the choice to keep going or to break out of the method
            String answer;
            do{
                System.out.println("\nDo you want to feed your animals? (y/n)");
                answer = HelperMethods.scan.nextLine();
                if(answer.equals("n")){
                    return;
                }
                else if(!answer.equals("y")){
                    System.out.println("You must enter y or n to proceed.");
                }
            }while(!answer.equals("y"));
            // TODO yes or no method

            printPlayerStats();
            System.out.println("""

                    Welcome to the kitchen!\s
                    Here you can feed your animals with 0.5 kg food at a time until they and you are satisfied.
                    
                    """);

            // Wait for the player to choose an animal that does not have full health
            do{
                chooseAnimal();

                if(chosenAnimal.getHealth()<100){
                    HelperMethods.setValidChoice(true);
                    animalIndex = HelperMethods.getInputInt()-1;
                }
                else{
                    System.out.println("\nYou can't feed " + chosenAnimal.getName() +
                            " as they are already at full health.");
                    HelperMethods.setValidChoice(false);
                }
            }while(!HelperMethods.getValidChoice());

            chooseFood();
            if(HelperMethods.getValidChoice()){
                removeFood();
            }
            if(!HelperMethods.getValidChoice() || !animalsFed){
                continue;
            }

            // Change the health of the animal the player chose
            // at index animalIndex
            restoredHealth = (double) chosenAnimal.getHunger() * foodRemove * chosenFood.foodValue;
            currentPlayer.getAnimalList().get(animalIndex).gainHealth((int) restoredHealth);

            System.out.println("\n" + chosenAnimal.getName() + " has restored " + restoredHealth +
                    " healthpoints!");

            // Iterate through the players list of animals and see
            // if there is at least one animal that needs feeding.
            // If there is, break out of the method.
            int healthyAnimals = 0;
            for(Animal animal : currentPlayer.getAnimalList()){
                if(animal.getHealth()==100){
                    healthyAnimals +=1;
                }
            }
            if(healthyAnimals == currentPlayer.getAnimalList().size()){
                System.out.println("Yay! All your animals are healthy and you can't feed them!");
                return;
            }

        }
    }

    public void chooseFood(){
            HelperMethods.setValidChoice(true);
            System.out.println("\nWhich food do you want to feed your animal with?");
            HelperMethods.printOnlyEat();
            HelperMethods.tryParseInt();

            switch(HelperMethods.getInputInt()){
                case 1:
                    chosenFood = chosenAnimal.getOnlyEat().get(0);
                    break;

                case 2:
                    if(chosenAnimal.getOnlyEat().size()>=2) {
                        chosenFood = chosenAnimal.getOnlyEat().get(1);
                    }
                    else{
                        System.out.println("The number does not correspond to " +
                                "the list of foods the animal eats.");
                        HelperMethods.setValidChoice(false);
                    }
                    break;

                case 3:
                    if(chosenAnimal.getOnlyEat().size()>=3){
                        chosenFood = chosenAnimal.getOnlyEat().get(2);
                    }
                    else{
                        System.out.println("The number does not correspond to " +
                                "the list of foods the animal eats.");
                        HelperMethods.setValidChoice(false);
                    }
            }
    }

    public void removeFood(){
        for(Food food : currentPlayer.getFoodList()){
            if(food.getFoodType()==chosenFood && food.getFoodAmount()==0){
                System.out.println("You can't feed " + chosenAnimal.getName() +
                        " with " + chosenFood.foodType.toLowerCase() +
                        " as you don't have enough of that food.");
                HelperMethods.setValidChoice(false);
                return;
            }
            else if(chosenFood==FoodType.SEAWEED && food instanceof Seaweed){
                food.removeFoodAmount(foodRemove);
            }
            else if(chosenFood==FoodType.MILK && food instanceof Milk){
                food.removeFoodAmount(foodRemove);
            }
            else if(chosenFood==FoodType.HELPLESS_HUMAN && food instanceof HelplessHuman){
                food.removeFoodAmount(foodRemove);
            }
            animalsFed = true;
        }
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

    public static Animal getChosenAnimal(){
        return chosenAnimal;
    }

}
