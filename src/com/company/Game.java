package com.company;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    private ArrayList<Player>players = new ArrayList<>();

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
        if(players.isEmpty()){
            setPlayers();
        }
        else{
            System.out.println("""

                    WARNING!!!\s
                    
                    You have already added players to the game. 
                    If you proceed, the current list of players will be deleted 
                    and you will have to add any and all players again.\s
                    
                    Do you want to proceed? (y/n)""");

            do{
                String choice = HelperMethods.scan.nextLine().toLowerCase();//TODO y/n method
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
                HelperMethods.tryParseInt();
            } while(HelperMethods.getInputInt()==-1);

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
                System.out.println("Please enter how many rounds you want to play: | 5 | 6 | ... | 29 | 30 |");
                HelperMethods.tryParseInt();
            } while(HelperMethods.getInputInt()==-1);

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
        if (numOfRounds!=-1 && numOfPlayers != -1){
            for(Player player : players){
                int startingGold = 50;
                player.setGoldAmount(startingGold);
                player.getAnimalList().clear();
            }
            mainGame();
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

    public void mainGame(){
        int round = 1;
        while(round <= numOfRounds){

            for (Player player : players){
                //Assume valid choice will be made
                HelperMethods.setValidChoice(true);
                currentPlayer = player;
                killDiseasedAnimals();
                setDiseasedStatus();
                do{
                    do{
                        System.out.println("\nRound " + round);
                        System.out.println("\nYOUR TURN, " + player.getName().toUpperCase() + "! ");
                        printPlayerStats();
                        printMenu();
                        checkMenuChoice();
                        // Default value is -1
                    }while(HelperMethods.getInputInt()==-1);

                    makeMenuChoice();

                }while(!HelperMethods.isValidChoice());
            }
            for(int i = players.size()-1; i>0; i--){
                if(players.get(i).getAnimalList().isEmpty() && players.get(i).getGoldAmount() == 0){
                    players.remove(i);
                    System.out.println("\n" + players.get(i).getName() + " owns nothing of value anymore " +
                            "and is out of the game :(");
                }
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
                          |5| Sell one or more of your mythological animals
                          |6| Send an animal to the hospital""");
        HelperMethods.tryParseInt();
    }

    public void checkMenuChoice(){
       if(currentPlayer.getAnimalList().isEmpty() && HelperMethods.getInputInt()>2 &&
               HelperMethods.getInputInt()<5){
            System.out.println("You can't perform that action as you don't have any animals");
            HelperMethods.setInputInt(-1);
       }
       else if(HelperMethods.getInputInt() == 4 && currentPlayer.getAnimalList().size()<2){
            System.out.println("You need to have two animals in order to try for offspring.");
            HelperMethods.setInputInt(-1);
       }
       else if(HelperMethods.getInputInt()>=5 && HelperMethods.getInputInt()<=6 &&
               currentPlayer.getAnimalList().isEmpty()){
           System.out.println("You can't sell any animals because you don't have any! :(");
           HelperMethods.setInputInt(-1);
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
            default -> {
                HelperMethods.invalidInput();
                HelperMethods.setInputInt(-1);
            }
        }
    }

    public void checkAction(){
        if(animalsChanged) {
            HelperMethods.setValidChoice(true);
        }
        if(!animalsChanged) {
            HelperMethods.setValidChoice(false);
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
        System.out.println("\nEND OF GAME!" +
                "\n\n......AND THE WINNER IS......" +
                "\n" + winnerName.toUpperCase());
    }

    public void mateAnimals(){
        animalsChanged = false;
        HelperMethods.setValidChoice(false);
        HelperMethods.printPlayerAnimals();
        System.out.println("\nChoose the mythological animal you want to mate.");
        HelperMethods.chooseAnimal();
        animalToMate = HelperMethods.chosenAnimal;
        System.out.println("\nChoose the mythological animal you want to mate with.");
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
            HelperMethods.tryParseInt();

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
                        ", has passed away as a result of their disease :'(");
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
