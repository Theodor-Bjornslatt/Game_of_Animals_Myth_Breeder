package com.company;

public class Hospital {
    private static final String doctor = "Doctor Artemis";
    private final static int treatmentCost = 5;
    private static boolean noTreatmentAvailable;
    private static boolean treatedAnimals;


    public static void goToHospital(){
        treatedAnimals = false;
        HelperMethods.clearConsole();
        System.out.println("\n" + doctor + ":\n" +
                "Welcome, " + Game.getCurrentPlayer().getName() + ", to my hospital for mythological animals. ");

        while(true){
            System.out.println("\nDo you have a diseased animal that needs treatment? (y/n)");
            //TODO insert y/n method

            String answer = HelperMethods.scan.nextLine();
            if(answer.equals("y")){
                HelperMethods.printPlayerAnimals();
                System.out.println("\n" + doctor + ":\nEach treatment costs " + treatmentCost + " Gold. " +
                        "\nWhich animal do you want me to try to cure?");
                HelperMethods.chooseAnimal();
                tryTreatment();
            }
            else if(answer.equals("n")){
                HelperMethods.clearConsole();
                System.out.println("\n" + doctor + ":\nGoodbye! May good health be with you and yours!");
                HelperMethods.setValidChoice(treatedAnimals);
                return;
            }
            else{
                System.out.println("You must enter y or n to make a choice.");
            }
            if(noTreatmentAvailable){
                noTreatmentAvailable=false;
                return;
            }
        }

    }

    public static void tryTreatment(){
        if(Game.getCurrentPlayer().getGoldAmount() < treatmentCost ){
            HelperMethods.clearConsole();
            System.out.println(doctor + ":\nOh dear! It appears you don't have enough " +
                    "gold to pay for the treatment.\nI'm afraid you'll have to leave for now.");
            HelperMethods.setValidChoice(treatedAnimals);
            noTreatmentAvailable = true;
        }
        else if(HelperMethods.chosenAnimal.getHealthStatus()==HealthStatus.DISEASED){
            boolean successful = HelperMethods.fiftyPerChance();
            Animal treatedAnimal = Game.getCurrentPlayer().getAnimalList().get(HelperMethods.getInputInt()-1);
            if(successful){
                Game.getCurrentPlayer().removeGold(treatmentCost);
                treatedAnimal.setHealthStatus(HealthStatus.HEALTHY);
                treatedAnimal.gainHealth(100);
                treatedAnimals = true;
                System.out.println("\n" + doctor + ":\nI am happy to tell you the treatment worked! " +
                        HelperMethods.chosenAnimal.getName() + " is now completely healthy and well fed.");

            }
            else{
                System.out.println(doctor + ":\nI am so sorry, but " + HelperMethods.chosenAnimal.getName() +
                        " did not respond to my treatment and has sadly passed away.");
                treatedAnimals = true;
            }
        }
        else{
            System.out.println("\n" + doctor + ":\n" + HelperMethods.chosenAnimal.getName() +
                    " appears to be perfectly healthy! I think my time is better spent elsewhere.");
        }
    }
}
