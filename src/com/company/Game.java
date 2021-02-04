package com.company;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    private static Food chosenFood;
    private static int animalIndex = 0;
    private static boolean animalsChanged = false;

    Store mythStore = new Store();

    int startingGold;
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
                 |5| Open rulebook
                 |6| Exit game""");

            Helper.tryParseInt("", 1, 6);

            switch (Helper.getInputInt()) {
                case 1 -> tryAddPlayers();
                case 2 -> setNumberOfRounds();
                case 3 -> tryRunGame();
                case 4 -> loadGame();
                case 5 -> printRules();
                case 6 -> System.exit(0);
            }
        }
    }

    public void printRules(){
        Helper.clearConsole();
        Helper.setValidChoice(false);
        try {
            String ruleBook = new String(Files.readAllBytes(Paths.get("gameFiles/ruleBook")));
            System.out.println(ruleBook);
        } catch (IOException e) {
            System.out.println("The rulebook seems to be missing!");
        }
        System.out.println("\nPress Enter to return from the rulebook.");
        Helper.scan.nextLine();
        Helper.clearConsole();
    }

    public void loadGame(){
        int index;
        System.out.println("Saved Games:");
        File[] gameFiles = new File("gameFiles/savedGames").listFiles();
        List<File>savedFiles;
        if (gameFiles != null) {
            savedFiles = Arrays.asList(gameFiles);
        }
        else{
            System.out.println("Something went wrong!");
            return;
        }
        if(savedFiles.size() == 1){
            System.out.println("You have no saved games :(");
            return;
        }
        for(File gameFile : savedFiles){
            if (gameFile.getName().equals(".keep")) {
                continue;
            }
            System.out.println(savedFiles.indexOf(gameFile)+ ". " + gameFile.getName());
        }
        String answer = Helper.yesOrNo("Do you want to load one of these files? (y/n)");
        switch (answer){
            case "y":
                index = Helper.tryParseInt("Which file do you want to load?",
                        1, savedFiles.size()-1);
                Object deserializedObj = Serializer.deserialize("gameFiles/savedGames/" +
                        savedFiles.get(index).getName());

                if(deserializedObj instanceof Game){
                    Game savedGame = (Game) deserializedObj;
                    this.players = savedGame.players;
                    this.round = savedGame.round;
                    this.numOfRounds = savedGame.numOfRounds;
                    this.savedPlayer = savedGame.savedPlayer;
                    this.singlePlayer = savedGame.singlePlayer;
                    runGame();
                }
                break;

            case "n":
                break;
        }
    }

    public void tryAddPlayers(){
        if(players.isEmpty()){
            setPlayers();
        }
        else{
            Helper.clearConsole();
            String choice = Helper.yesOrNo("""

                    WARNING!!!
                    You have already added players to the game.\s
                    If you proceed, the current list of players will be deleted\s
                    and you will have to add any and all players again.
                    Do you want to proceed? (y/n)""");

            if(choice.equals("y")){
                players.clear();
                setPlayers();
            }
        }
    }

    public void setPlayers(){
        Helper.clearConsole();
        Helper.tryParseInt("\nPlease enter the number of players: | 1 | 2 | 3 | 4 |",
                1, 4);

        int numOfPlayers = Helper.getInputInt();
        for(int i = 0; i < numOfPlayers; i++){
            String name = Helper.assignName("\nEnter the name of player " + (i+1) + ":");
            players.add(new Player(name));
            System.out.println(name + " is now player " + (i+1));
        }
        singlePlayer = players.size() == 1;
    }

    public void setNumberOfRounds(){
        Helper.clearConsole();
        Helper.tryParseInt("Please enter how many rounds you want to play: " +
                "| 5 | 6 | ... | 29 | 30 |", 5, 30);
        numOfRounds = Helper.getInputInt();

        if(numOfRounds<8){
            startingGold = 100;
        }
        else if(numOfRounds<16){
            startingGold = 150;
        }
        else if(numOfRounds<20){
            startingGold = 200;
        }
        else{
            startingGold = 250;
        }
    }

    public void tryRunGame(){
        Helper.clearConsole();
        if (numOfRounds != 0 && !players.isEmpty()){
            for(Player player : players){
                player.setGoldAmount(startingGold);
                player.getAnimalList().clear();
            }
            round = 1;
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
        while(round <= numOfRounds){
            Helper.clearConsole();
            for (Player player : players){
                currentPlayer = player;
                if(!singlePlayer){
                    System.out.println("\n".repeat(10) + "----------------------------------------------" +
                            currentPlayer.getName() + ", press Enter to start your turn!" +
                            "\n----------------------------------------------");
                }
                // Removes sick animals from playerList,
                // lets all animals lose health
                // and flips coin for diseased status
                changeHealth();
                if(!singlePlayer){
                    Helper.scan.nextLine();
                }
                Helper.clearConsole();
                do{
                    do{
                        System.out.println("\nRound " + round + " of " + numOfRounds);
                        System.out.println("\nYOUR TURN, " + player.getName().toUpperCase() + "! ");
                        printPlayerStats();
                        chooseAction();
                    }while(!checkValidAction());

                    performChosenAction();

                }while(!Helper.isValidChoice());
            }
            checkPlayerStats();
            if(round!=numOfRounds){
                System.out.println("\nEND OF ROUND " + (round) + "!" +
                        "\nPress Enter to continue or press x to save and exit.");
                String action = Helper.scan.nextLine();
                if(action.equals("x")){
                    saveGame();
                }
            }
            round++;
        }
        findWinner();
    }

    public void saveGame(){
        round++;
        while (true) {
            String filePath = Helper.assignName("Name your save:");
            Helper.clearConsole();
            if(filePath.equals("") || filePath.contains(" ")){
                System.out.println("You must give your game a name to save it.");
                continue;
            }
            Game saveGame = this;
            Serializer.serialize(("gameFiles/savedGames/" + filePath + ".ser"), saveGame);
            Helper.clearConsole();
            System.out.println("Your game has been successfully saved. See you later!");
            System.exit(0);
        }
    }

    public void chooseAction(){
        System.out.println("\nMake your move " + currentPlayer.getName() + ":");
        Helper.tryParseInt(
                """
                        |1| Buy animals from Myth Store
                        |2| Buy food from Myth Store
                        |3| Feed your mythological animals
                        |4| Try your luck - try for offspring
                        |5| Sell one or more of your mythological animals
                        |6| Send an animal to the hospital
                        |7| Open the rulebook""",
                1, 7);
    }

    public boolean checkValidAction(){
       if(currentPlayer.getAnimalList().isEmpty() && Helper.getInputInt()>2 &&
               Helper.getInputInt()<5){
           Helper.clearConsole();
            System.out.println("You can't perform that action as you don't have any animals");
            return false;
       }
       else if(currentPlayer.getFoodList().isEmpty() && Helper.getInputInt()==3){
           Helper.clearConsole();
           System.out.println("You don't own any food!");
           return false;
       }
       else if(Helper.getInputInt() == 4 && currentPlayer.getAnimalList().size()<2){
           Helper.clearConsole();
            System.out.println("You need to have two animals in order to try for offspring.");
            return false;
       }
       else if(Helper.getInputInt()==5 &&
               currentPlayer.getAnimalList().isEmpty()){
           Helper.clearConsole();
           System.out.println("You can't sell any animals because you don't have any! :(");
           return false;
       }
       else if(Helper.getInputInt()==6 && currentPlayer.animals.isEmpty()){
           Helper.clearConsole();
           System.out.println("You don't have any animals and can't send one to the hospital!");
           return false;
       }
       else{
           return true;
       }
    }

    public void performChosenAction(){
        switch (Helper.getInputInt()) {
            case 1 -> mythStore.goToAnimalStore();
            case 2 -> mythStore.goToFoodStore();
            case 3 -> goToKitchen();
            case 4 -> mateAnimals();
            case 5 -> mythStore.sellAnimal();
            case 6 -> Hospital.goToHospital();
            case 7 -> printRules();
        }
    }

    public void checkPlayerStats(){
        if(!singlePlayer){
            for(int i = players.size()-1; i>=0; i--){
                if(players.get(i).animals.isEmpty() && players.get(i).getGoldAmount() < 10){
                    players.remove(i);
                    System.out.println("\n" + players.get(i).getName() +
                            " has no animals and can't afford new ones. " +
                            "\n" + players.get(i).getName() + " is out of the game :(");
                    System.out.println("\nPress Enter to continue.");
                    Helper.scan.nextLine();
                }
            }
            if(players.size()==1){
                findWinner();
                mainMenu();
            }
        }
        if(singlePlayer){
            if(currentPlayer.animals.isEmpty() && currentPlayer.getGoldAmount()==0){
                round = numOfRounds;
                Helper.clearConsole();
                System.out.println("Oh no! You have no Gold and no animals! You lost the game! :'(" +
                        "\nPress Enter to exit to Main Menu");
                Helper.scan.nextLine();
                clearGameData();
                mainMenu();
            }
        }
    }


    public void clearGameData(){
        currentPlayer = null;
        players.clear();
        round = 1;
    }

    public void findWinner(){
        Helper.clearConsole();
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
            System.out.println("\n......AND THE WINNER IS " + winnerName.toUpperCase() + "!......" +
                    "\nCongratulations! You won with " + winnerGold + " Gold!");
        }
        else{
            System.out.println("WOW, YOU WIN!" +
                    "\nGold left: " + currentPlayer.getGoldAmount() +
                    "\nAnimals left: " + currentPlayer.animals.size());
        }
        clearGameData();
        System.out.println("\nPress Enter to return to Main Menu");
        Helper.scan.nextLine();
    }

    public void mateAnimals(){
        animalsChanged = false;
        Helper.setValidChoice(false);
        Helper.printPlayerAnimals();
        System.out.println("\nChoose the mythological animal you want to mate.");
        Helper.chooseAnimal();
        animalToMate = Helper.chosenAnimal;
        System.out.println("\nChoose the mythological animal you want to mate " + animalToMate.getName() + " with.");
        Helper.chooseAnimal();
        Helper.clearConsole();
        if(animalToMate == Helper.chosenAnimal){
            System.out.println("Your animal can't mate with itself.");
        }
        else if(Helper.chosenAnimal.getGender().equals(animalToMate.getGender())){
            System.out.println("Animals of the same gender can't procreate.");
        }
        else if(Helper.chosenAnimal.getSpecies().equals(animalToMate.getSpecies())){
            tryOffspring();
            animalsChanged = true;
        }
        else{
            System.out.println("Animals of different species can't mate.");
        }
        Helper.setValidChoice(animalsChanged);
        animalToMate = null;
        Helper.chosenAnimal = null;
    }

    public void tryOffspring(){
        boolean success = Helper.fiftyPerChance();
        if(success){
            createOffspring();
        }
        else{
            System.out.println("You didn't get any babies! :( ");
        }
    }

    public void createOffspring(){

        chosenSpecies = Helper.chosenAnimal.getSpecies();

        Random random = new Random();
        int numberOfOffspring = 1 + random.nextInt(Helper.chosenAnimal.getMaxOffspring()+1);

        System.out.println("Wow! " + Helper.chosenAnimal.getName() + " and " + animalToMate.getName() + " had " +
                numberOfOffspring + " offspring!\nNow it's time to name them!");

        for(int i = 1; i<=numberOfOffspring; i++){

            boolean female = Helper.fiftyPerChance();
            if(female){
                chosenGender = Gender.FEMALE;
            }
            else{
                chosenGender = Gender.MALE;
            }
            chosenName = Helper.assignName("\nName baby number " + i + ", it's a " +
                    chosenGender.string().toLowerCase() + "!" );
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

    public void goToKitchen(){
        Helper.setValidChoice(false);
        animalsChanged = false;

        while(true){
            String answer;
            answer = Helper.yesOrNo("\nDo you want to feed your animals? (y/n)");
            Helper.clearConsole();
            if(answer.equals("n")){
                Helper.setValidChoice(animalsChanged);
                return;
            }

            printPlayerStats();
            System.out.println("""

                    WELCOME TO THE KITCHEN!\s
                    Here, you can feed your animals with 0.5kg portions of any food you own, until you and they are satisfied
                    """);

            do{
                Helper.chooseAnimal();

                if(Helper.chosenAnimal.getHealth()<100){
                    Helper.setValidChoice(true);
                    animalIndex = Helper.getInputInt()-1;
                }
                else{
                    System.out.println("\nYou can't feed " + Helper.chosenAnimal.getName() +
                            " as they are already at full health.");
                    Helper.setValidChoice(false);
                }
            }while(!Helper.isValidChoice());

            chooseFood();

            if(Helper.isValidChoice()){
                feedAnimals();
                if(fedAllAnimals()){
                    return;
                }
            }
        }
    }

    public void chooseFood(){
            Helper.setValidChoice(true);
            System.out.println("\nWhich food do you want to feed your animal with?");
            Helper.printOnlyEat();
            Helper.tryParseInt("", 1, 3);

            switch(Helper.getInputInt()){
                case 1:
                    chosenFood = Helper.chosenAnimal.getOnlyEat().get(0);
                    break;

                case 2:
                    if(Helper.chosenAnimal.getOnlyEat().size()>=2) {
                        chosenFood = Helper.chosenAnimal.getOnlyEat().get(1);
                    }
                    else{
                        System.out.println("The number does not correspond to " +
                                "the list of foods the animal eats.");
                        Helper.setValidChoice(false);
                    }
                    break;

                case 3:
                    if(Helper.chosenAnimal.getOnlyEat().size()>=3){
                        chosenFood = Helper.chosenAnimal.getOnlyEat().get(2);
                    }
                    else{
                        System.out.println("The number does not correspond to " +
                                "the list of foods the animal eats.");
                        Helper.setValidChoice(false);
                    }
            }
    }

    public void feedAnimals(){
        double restoredHealth;
        double foodGain;
        double foodToRemove = 0.5;
        Helper.setValidChoice(Food.removeFood(chosenFood, foodToRemove));
        if(!Helper.isValidChoice()){
            System.out.println("Your animal can't be fed :(");
        }
        else{
            animalsChanged = true;

            foodGain = (double) Helper.chosenAnimal.getHungerSatisfaction() *
                    foodToRemove * chosenFood.getFoodType().foodValue;

             restoredHealth = currentPlayer.animals.get(animalIndex).gainHealth((int) foodGain);

            System.out.println("\n" + Helper.chosenAnimal.getName() + " has restored " +
                    restoredHealth + " health points!");
        }
    }

    public boolean fedAllAnimals(){
        int healthyAnimals = 0;
        for(Animal animal : currentPlayer.animals){
            if(animal.getHealth()==100){
                healthyAnimals +=1;
            }
        }
        if(healthyAnimals == currentPlayer.animals.size()){
            System.out.println("Wow! All your animals are at full health and you can't feed them!");
            return true;
        }
        else{
            return false;
        }
    }

    public void changeHealth(){
        for(int i =currentPlayer.animals.size()-1; i>=0; i--){
            if(currentPlayer.animals.get(i).getHealthStatus()==HealthStatus.DISEASED){
                System.out.println("\n" + currentPlayer.animals.get(i).getName() +
                        " has passed away as a result of their disease!");
                currentPlayer.animals.remove(i);
                continue;
            }

            currentPlayer.animals.get(i).loseHealth();

            if(currentPlayer.animals.get(i).getHealth()==0){
                System.out.println("\n" + currentPlayer.animals.get(i).getName() +
                        " has reached 0 health points and died!" );
                currentPlayer.animals.remove(i);
            }

            boolean diseased = Helper.diseaseChance();
            if(diseased){
                currentPlayer.animals.get(i).setHealthStatus(HealthStatus.DISEASED);
            }
        }
    }

    public static void printPlayerStats(){
        System.out.println("\nGold: " + currentPlayer.getGoldAmount());

        Helper.printPlayerAnimals();

        Helper.printPlayerFoodList();

    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }
}
