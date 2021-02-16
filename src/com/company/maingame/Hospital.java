package com.company.maingame;

import com.company.animals.Animal;
import com.company.enums.HealthStatus;

public class Hospital {
    private static final String doctor = "Doctor Artemis";
    private static int treatmentCost;
    private static boolean noTreatmentAvailable;
    private static boolean treatedAnimals;


    public static void goToHospital(){
        treatedAnimals = false;
        noTreatmentAvailable=false;
        Helper.clearConsole();
        System.out.println("\n" + doctor + ":\n" +
                "Welcome, " + Game.getCurrentPlayer().getName() + ", to my hospital for mythological animals. ");

        while(true){

            if(noTreatmentAvailable){
                return;
            }

            String answer = Helper.yesOrNo(
                    "\nDo you have a diseased animal that needs treatment? (y/n)");
            Helper.clearConsole();

            if(answer.equals("y")){
                Helper.printPlayerAnimals();
                System.out.println("\n" + doctor + ":\nWhich animal do you want me to try to cure?");
                Helper.chooseAnimal();
                calculateTreatmentCost();
                answer = Helper.yesOrNo("\n" + doctor + ":\nTreating " + Helper.chosenAnimal.getName() +
                        " will cost " + treatmentCost + " Gold. Are you willing to pay this amount? (y/n)");
                if(answer.equals("y")) {
                    tryTreatment();
                }
                else{
                    System.out.println("\n" + doctor + ":\nAlright then, I won't treat " +
                            Helper.chosenAnimal.getName());
                }
            }
            else{
                Helper.clearConsole();
                System.out.println("\n" + doctor + ":\nGoodbye! May good health be with you and yours!");
                Helper.setValidChoice(treatedAnimals);
                return;
            }
        }
    }

    public static void calculateTreatmentCost(){
        double priceDouble = Store.findAnimalPrice(Helper.chosenAnimal) * 0.3;
        treatmentCost = (int) Math.ceil(priceDouble);
    }

    public static void tryTreatment(){
        if(Game.getCurrentPlayer().getGoldAmount() < treatmentCost ){
            Helper.clearConsole();
            System.out.println(doctor + ":\nOh dear! It appears you don't have enough " +
                    "gold to pay for a treatment.\nI'm afraid you'll have to leave for now.");
            Helper.setValidChoice(treatedAnimals);
            noTreatmentAvailable = true;
        }
        else if(Helper.chosenAnimal.getHealthStatus()== HealthStatus.DISEASED){
            boolean successful = Helper.fiftyPerChance();
            Animal treatedAnimal = Game.getCurrentPlayer().getAnimalList().get(Helper.getInputInt()-1);
            if(successful){
                Game.getCurrentPlayer().removeGold(treatmentCost);
                treatedAnimal.setHealthStatus(HealthStatus.HEALTHY);
                treatedAnimals = true;
                Helper.clearConsole();
                System.out.println("\n" + doctor + ":\nI am happy to tell you the treatment worked! " +
                        Helper.chosenAnimal.getName() + " is now healthy again.");

            }
            else{
                System.out.println(doctor + ":\nI am so sorry, but " + Helper.chosenAnimal.getName() +
                        " did not respond to my treatment and has sadly passed away.");
                Game.getCurrentPlayer().getAnimalList().remove(Helper.chosenAnimal);
                treatedAnimals = true;
            }
        }
        else{
            System.out.println("\n" + doctor + ":\n" + Helper.chosenAnimal.getName() +
                    " appears to be perfectly healthy! I think my time is better spent elsewhere.");
        }
    }
}
