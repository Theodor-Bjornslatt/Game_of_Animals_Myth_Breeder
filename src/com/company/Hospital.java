package com.company;

public class Hospital {
    private static final String doctor = "Doctor Artemis";
    private final static int treatmentCost = 5;
    private static boolean noTreatmentAvailable;
    private static boolean treatedAnimals;


    public static void goToHospital(){
        treatedAnimals = false;
        System.out.println("\n" + doctor + ":\n" +
                " Welcome, " + Game.getCurrentPlayer().getName() + ", to my hospital for mythological animals. ");

        while(true){
            System.out.println("\n Do you have a diseased animal that needs treatment? (y/n)");
            //TODO insert y/n method

            String answer = HelperMethods.scan.nextLine();
            if(answer.equals("y")){
                HelperMethods.printPlayerAnimals();
                System.out.println("\n" + doctor + ":\n Each treatment costs " + treatmentCost + " Gold. " +
                        "\n Which animal do you want me to try to cure?");
                HelperMethods.chooseAnimal();
                tryTreatment();
            }
            else if(answer.equals("n")){
                System.out.println("\n" + doctor + ":\n Goodbye! May good health be with you and yours!");
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
            System.out.println("\n" + doctor + ":\n Oh dear! It appears you don't have enough " +
                    "gold to pay for the treatment.\n I'm afraid you'll have to leave for now.");
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
                System.out.println("\n" + doctor + ":\n I am happy to tell you the treatment worked! " +
                        HelperMethods.chosenAnimal.getName() + " is now completely healthy and well fed.");

            }
            else{
                System.out.println(doctor + ":\n I am so sorry, but " + HelperMethods.chosenAnimal.getName() +
                        " did not respond to my treatment and has sadly passed away.");
            }
        }
        else{
            System.out.println("\n" + doctor + ":\n " + HelperMethods.chosenAnimal.getName() +
                    " appears to be perfectly healthy! I think my time is better spent elsewhere.");
        }
    }
}
