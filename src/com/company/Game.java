package com.company;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.io.Serializable;
import java.io.File;

public class Game implements Serializable {
    private ArrayList<Player>players = new ArrayList<>();
    boolean singlePlayer;

    private Player savedPlayer = null;
    private static Player currentPlayer = null;

    private static Animal animalToMate;
    private static Animal offspring;

    private static String chosenName;
    private static Species chosenSpecies;
    private static Gender chosenGender;
    private static FoodType chosenFood;
    private boolean animalsChanged = false;
    private final double foodRemove = 0.5;

    Store mythStore = new Store();
    public int round;
    public int numOfRounds = 0;


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
                 |4| Load saved game
                 |5| Exit game""");

            HelperMethods.tryParseInt("", 1, 5);

            switch (HelperMethods.getInputInt()) {
                case 1 -> tryAddPlayers();
                case 2 -> setNumberOfRounds();
                case 3 -> tryRunGame();
                case 4 -> loadGame();
                case 5 -> System.exit(0);
                default -> HelperMethods.invalidInput();
            }
        }
    }

    public void loadGame(){
        int index;
        System.out.println("Saved Games:");
        File[] gameFiles = new File("savedGames").listFiles();
        List<File>savedFiles = Arrays.asList(gameFiles);
        for(File gameFile : savedFiles){
            if (gameFile.getName().equals(".keep")) {
                continue;
            }
            System.out.println(savedFiles.indexOf(gameFile)+ ". " + gameFile.getName());
        }
        System.out.println("Do you want to load one of these files?");
        //Todo y/n method, if n, break
        // if(answer.equals("n") return;
        index = HelperMethods.tryParseInt("What file do you want to load?", 1, savedFiles.size()-1);
        Game savedGame = (Game) Serializer.deserialize("savedGames/" + savedFiles.get(index).getName());
        this.players = savedGame.players;
        this.round = savedGame.round;
        this.numOfRounds = savedGame.numOfRounds;
        this.savedPlayer = savedGame.savedPlayer;
        this.singlePlayer = savedGame.singlePlayer;
        runGame();
    }

    public void tryAddPlayers(){
        if(players.isEmpty()){
            setPlayers();
        }
        else{
            HelperMethods.clearConsole();
            System.out.println("""

                    WARNING!!!\s
                    
                    You have already added players to the game. 
                    If you proceed, the current list of players will be deleted 
                    and you will have to add any and all players again.\s
                    
                    Do you want to proceed? (y/n)""");

            do{
                HelperMethods.setValidChoice(false);
                String choice = HelperMethods.scan.nextLine().toLowerCase();//TODO y/n method
                if(choice.equals("y")){
                    players.clear();
                    setPlayers();
                    HelperMethods.setValidChoice(true);
                }
                else if(choice.equals("n")){
                    return;
                }
                else{
                    System.out.println("You must make a choice to continue. " +
                            "Press y to delete all players and create a new list of players. " +
                            "Press n to return to Main Menu without making any changes.");
                }
            }while(!HelperMethods.isValidChoice());
        }
    }

    public void setPlayers(){
        HelperMethods.clearConsole();
        HelperMethods.tryParseInt("\nPlease enter the number of players: | 1 | 2 | 3 | 4 |",
                1, 4);

        int numOfPlayers = HelperMethods.getInputInt();
        for(int i = 0; i < numOfPlayers; i++){
            System.out.println("\nEnter the name of player " + (i+1) + ":");
            String name = HelperMethods.scan.nextLine();
            players.add(new Player(name));System.out.println(name + " is now player " + (i+1));
        }
        singlePlayer = players.size() == 1;
    }

    public void setNumberOfRounds(){
        HelperMethods.clearConsole();
        HelperMethods.tryParseInt("Please enter how many rounds you want to play: " +
                "| 5 | 6 | ... | 29 | 30 |", 5, 30);
        numOfRounds = HelperMethods.getInputInt();
    }

    public void tryRunGame(){
        HelperMethods.clearConsole();
        if (numOfRounds != 0 && !players.isEmpty()){
            for(Player player : players){
                int startingGold = 50;
                player.setGoldAmount(startingGold);
                player.getAnimalList().clear();
            }
            runGame();
        }
        else if(numOfRounds == 0 && players.isEmpty()){
            System.out.println("Sorry, you have to add players and set the number " +
                    "of rounds you want to play, in order to start a new game.");

        }
        else if(numOfRounds == 0){
            System.out.println("Sorry, you can't start a game without setting " +
                    "the number of rounds.");
        }
        else{
            System.out.println("Sorry, you can't start a game without adding players.");
        }
    }

    public void runGame(){
        round = 1;
        while(round <= numOfRounds){
            HelperMethods.clearConsole();
            for (Player player : players){
                //Assume valid choice will be made
                HelperMethods.setValidChoice(true);
                currentPlayer = player;
                killDiseasedAnimals();
                setDiseasedStatus();
                do{
                    do{
                        System.out.println("\nRound " + round + " of " + numOfRounds);
                        System.out.println("\nYOUR TURN, " + player.getName().toUpperCase() + "! ");
                        printPlayerStats();
                        printMenu();
                        checkMenuChoice();
                    }while(!HelperMethods.isValidChoice());

                    makeMenuChoice();

                }while(!HelperMethods.isValidChoice());
            }
            checkPlayerStats();
            if(round!=numOfRounds){
                System.out.println("\nEND OF ROUND " + round + "!" +
                        "\nPress Enter to continue or press x to save and exit.");
                String action = HelperMethods.scan.nextLine();
                if(action.equals("x")){
                    saveGame();
                }
            }
            round++;
        }
        findWinner();
    }

    public void saveGame(){ //TODO check if input is empty string
        System.out.println("Name your save file:");
        String filePath = HelperMethods.scan.nextLine();
        Game saveGame = this;
        Serializer.serialize(("savedGames/" + filePath + ".ser"), saveGame);
        HelperMethods.clearConsole();
        System.out.println("Your game has been successfully saved. See you later!");
        System.exit(0);
    }

    public void printMenu(){
        System.out.println("\nMake your move " + currentPlayer.getName() + ":");
        HelperMethods.tryParseInt("|1| Buy animals from Myth Store\n" +
                "|2| Buy food from Myth Store\n" +
                "|3| Feed your mythological animals\n" +
                "|4| Try your luck - try for offspring\n" +
                "|5| Sell one or more of your mythological animals\n" +
                "|6| Send an animal to the hospital",
                1, 6);
    }

    public void checkMenuChoice(){
       if(currentPlayer.getAnimalList().isEmpty() && HelperMethods.getInputInt()>2 &&
               HelperMethods.getInputInt()<5){
            System.out.println("You can't perform that action as you don't have any animals");
            HelperMethods.setValidChoice(false);
       }
       else if(HelperMethods.getInputInt() == 4 && currentPlayer.getAnimalList().size()<2){
            System.out.println("You need to have two animals in order to try for offspring.");
            HelperMethods.setValidChoice(false);
       }
       else if(HelperMethods.getInputInt()==5 &&
               currentPlayer.getAnimalList().isEmpty()){
           System.out.println("You can't sell any animals because you don't have any! :(");
           HelperMethods.setValidChoice(false);
       }
       else if(HelperMethods.getInputInt()==6 && currentPlayer.animals.isEmpty()){
           System.out.println("You don't have any animals and can't send one to the hospital!");
           HelperMethods.setValidChoice(false);
       }
    }

    public void makeMenuChoice(){
        switch (HelperMethods.getInputInt()) {
            case 1 -> mythStore.goToAnimalStore();
            case 2 -> mythStore.goToFoodStore();
            case 3 -> {
                feedAnimals();
                checkAction();
            }
            case 4 -> {
                mateAnimals();
                checkAction();
            }
            case 5 -> mythStore.sellAnimal();
            case 6 -> Hospital.goToHospital();
        }
    }

    public void checkPlayerStats(){
        if(!singlePlayer){
            for(int i = players.size()-1; i>0; i--){
                if(players.get(i).animals.isEmpty() && players.get(i).getGoldAmount() == 0){
                    players.remove(i);
                    System.out.println("\n" + players.get(i).getName() + " owns nothing of value anymore " +
                            "and is out of the game :(");
                }
            }
            if(players.size()==1){
                round = numOfRounds;
            }
        }
        if(singlePlayer){
            if(currentPlayer.animals.isEmpty() && currentPlayer.getGoldAmount()==0){
                round = numOfRounds;
                HelperMethods.clearConsole();
                System.out.println("Oh no! You have no Gold and no animals! You lost the game! :'(" +
                        "\nPress Enter to exit to Main Menu");
                HelperMethods.scan.nextLine();
                clearGameData();
                mainMenu();
            }
        }
    }

    public void checkAction(){
     HelperMethods.setValidChoice(animalsChanged);
    }

    public void clearGameData(){
        currentPlayer = null;
        players.clear();
        round = 1;
    }

    public void findWinner(){
        HelperMethods.clearConsole();
        if(!singlePlayer){
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
            System.out.println("\nEND OF GAME!" +
                    "\n\n......AND THE WINNER IS......" +
                    "\n" + winnerName.toUpperCase());
        }
        else{
            System.out.println("WOW, YOU WIN!" +
                    "\nGold left: " + currentPlayer.getGoldAmount() +
                    "\nAnimals left: " + currentPlayer.animals.size());
        }
        clearGameData();
        System.out.println("Press Enter to return to Main Menu");
        HelperMethods.scan.nextLine();
    }

    public void mateAnimals(){
        animalsChanged = false;
        HelperMethods.setValidChoice(false);
        HelperMethods.printPlayerAnimals();
        System.out.println("\nChoose the mythological animal you want to mate.");
        HelperMethods.chooseAnimal();
        animalToMate = HelperMethods.chosenAnimal;
        System.out.println("\nChoose the mythological animal you want to mate " + animalToMate.getName() + " with.");
        HelperMethods.chooseAnimal();

        if(animalToMate == HelperMethods.chosenAnimal){
            System.out.println("Your animal can't mate with itself.");
        }
        else if(HelperMethods.chosenAnimal.getGender().equals(animalToMate.getGender())){
            System.out.println("Animals of the same gender can't procreate.");
        }
        else if(HelperMethods.chosenAnimal.getSpecies().equals(animalToMate.getSpecies())){
            tryOffspring();
            HelperMethods.setValidChoice(true);
            animalsChanged = true;
        }
        else{
            System.out.println("Animals of different species can't mate.");
        }
        animalToMate = null;
        HelperMethods.chosenAnimal = null;
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

        chosenSpecies = HelperMethods.chosenAnimal.getSpecies();

        Random random = new Random();
        int numberOfOffspring = 1 + random.nextInt(HelperMethods.chosenAnimal.getMaxOffspring()+1);

        System.out.println("Wow! " + HelperMethods.chosenAnimal.getName() + " and " + animalToMate.getName() + " had " +
                numberOfOffspring + " offspring! \nNow it's time to name them!");

        for(int i = 1; i<=numberOfOffspring; i++){

            boolean female = HelperMethods.fiftyPerChance();
            if(female){
                chosenGender = Gender.FEMALE;
            }
            else{
                chosenGender = Gender.MALE;
            }

            System.out.println("Name baby number " + i + ", it's a " +
                    chosenGender.string().toLowerCase() + "!" );

            chosenName = HelperMethods.scan.nextLine();
            createAnimal();
        }
    }

    public void createAnimal(){
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
        currentPlayer.addAnimal(offspring);
    }

    public void feedAnimals(){
        double restoredHealth;
        int animalIndex = 0;
        animalsChanged = false;
        HelperMethods.setValidChoice(false);

        while(true){
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
            do{
                HelperMethods.chooseAnimal();

                if(HelperMethods.chosenAnimal.getHealth()<100){
                    HelperMethods.setValidChoice(true);
                    animalIndex = HelperMethods.getInputInt()-1;
                }
                else{
                    System.out.println("\nYou can't feed " + HelperMethods.chosenAnimal.getName() +
                            " as they are already at full health.");
                    HelperMethods.setValidChoice(false);
                }
            }while(!HelperMethods.isValidChoice());

            chooseFood();
            if(HelperMethods.isValidChoice()){
                removeFood();
            }
            if(!HelperMethods.isValidChoice() || !animalsChanged){
                continue;
            }

            // Change the health of the animal the player chose
            // that is at index animalIndex in players animal list
            restoredHealth = (double) HelperMethods.chosenAnimal.getHunger() * foodRemove * chosenFood.foodValue;
            currentPlayer.getAnimalList().get(animalIndex).gainHealth((int) restoredHealth);

            System.out.println("\n" + HelperMethods.chosenAnimal.getName() + " has restored " + restoredHealth +
                    " healthpoints!");

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
            HelperMethods.tryParseInt("", 1, 3);

            switch(HelperMethods.getInputInt()){
                case 1:
                    chosenFood = HelperMethods.chosenAnimal.getOnlyEat().get(0);
                    break;

                case 2:
                    if(HelperMethods.chosenAnimal.getOnlyEat().size()>=2) {
                        chosenFood = HelperMethods.chosenAnimal.getOnlyEat().get(1);
                    }
                    else{
                        System.out.println("The number does not correspond to " +
                                "the list of foods the animal eats.");
                        HelperMethods.setValidChoice(false);
                    }
                    break;

                case 3:
                    if(HelperMethods.chosenAnimal.getOnlyEat().size()>=3){
                        chosenFood = HelperMethods.chosenAnimal.getOnlyEat().get(2);
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
                System.out.println("You can't feed " + HelperMethods.chosenAnimal.getName() +
                        " with " + chosenFood.foodType.toLowerCase() +
                        " as you don't have enough of that food.");
                HelperMethods.setValidChoice(false);
                return;
            }
            else if(chosenFood==FoodType.SEAWEED && food instanceof Seaweed){
                food.removeFoodAmount(foodRemove);
                animalsChanged = true;
            }
            else if(chosenFood==FoodType.MILK && food instanceof Milk){
                food.removeFoodAmount(foodRemove);
                animalsChanged = true;
            }
            else if(chosenFood==FoodType.HELPLESS_HUMAN && food instanceof HelplessHuman){
                food.removeFoodAmount(foodRemove);
                animalsChanged = true;
            }

        }
    }

    public void killDiseasedAnimals(){
        for(int i =currentPlayer.animals.size()-1; i>=0; i--){
            if(currentPlayer.animals.get(i).getHealthStatus()==HealthStatus.DISEASED){
                System.out.println(currentPlayer.animals.get(i).getName() +
                        " has passed away as a result of their disease :'(");
                currentPlayer.animals.remove(i);
            }
        }
    }

    public void setDiseasedStatus(){
        for(Animal animal : currentPlayer.animals){
            animal.loseHealth();
            boolean diseased = HelperMethods.diseaseChance();
            if(diseased){
                animal.setHealthStatus(HealthStatus.DISEASED);
            }
        }
    }

    public static void printPlayerStats(){
        System.out.println("\nGold: " + currentPlayer.getGoldAmount());

        HelperMethods.printPlayerAnimals();

        HelperMethods.printPlayerFoodList();

    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }
}
