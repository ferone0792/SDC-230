
/*This Generates up to 20 crew names based on crewCount, the user, investigates one person at a time, program
 *generates stats and calculates a loyalty Score using convertToPostFix and evaluatePostFix. After scoring the user
 * use the numbers that the system gives them or they can type whatver numbers they want for the formula
 * Afterwards, the user chooses whether the person they selected is the inside man. If they say no they can
 * keep investigating which removes the person from the menu, or return to the main menu. The third option is if
 *  the user is unsure. They can return later and their saves score and exits option 6
 * Week 4*/

import java.util.Scanner;
import java.util.EmptyStackException;
import java.util.Random;
public class Option6InsideManLoyalty {
    private static final String[] NAMES = { //Up to 20 names for your crew members you typed in the beginning of the program
            "Rico", "Mason", "Viktor", "Dante", "Luca",
            "Sergio", "Niko", "Axel", "Marco", "Jax",
            "Ivan", "Sal", "Ghost", "Chains", "Wolf",
            "Nova", "Diesel", "Cobra", "Viper", "Bolt"
    };

    private static final Random random = new Random();    
    private boolean hasSavedScore = false; //Saves last score if user picks unsure option
    private String savedName = "";
    private double savedScore = 0.0;
    public void run(Scanner scanner, PostfixEvaluator evaluator, HeistContext ctx) {
        System.out.println("=== Inside Man Loyalty Meter (Investigation Mode) ===");        
        if (hasSavedScore) {
            System.out.println("NOTE: You have a saved investigation result:");
            System.out.printf("Saved Suspect: %s | Saved Loyalty Score: %.2f%n", savedName, savedScore);
            System.out.println("----------------------------------------------------------");
        }

        int listSize = ctx.crewCount; //Figures out names based on earlier crew count
        if (listSize > 20) listSize = 20;
        if (listSize <= 0) { //If crewCount is 0, it won't let you do this option
            System.out.println("You have no crew... why'd you pick this button?");
            return;
        }

        //This tracks who has been removed from investigation list
        boolean[] eliminated = new boolean[listSize];
        int remainingSuspects = listSize; //Tracks how many suspects are still available
        boolean investigating = true; //Main investigation loop
        while (investigating) {            
            if (remainingSuspects == 0) { //Stops if everyone is eliminated
                System.out.println("You eliminated everyone from suspicion... so everyone is loyal! Nice!!!");
                System.out.println("Returning to main menu.");
                return;
            }
           
            System.out.println(); //Prints remaining suspects
            System.out.println("Suspects Remaining:");
            for (int i = 0; i < listSize; i++) {
                if (!eliminated[i]) {
                    System.out.println((i + 1) + ". " + NAMES[i]);
                }
            }
            
            int choice = readIntInRange(scanner, //User picks suspects by number
                    "Choose someone to investigate (1-" + listSize + "): ", 1, listSize);
            if (eliminated[choice - 1]) { //Can't pick remaining suspects
                System.out.println("Error: That suspect is already eliminated from the list.");
                continue;
            }

            String suspectName = NAMES[choice - 1];
            int stress = rand1to10(); // builds stats for loyalty and prints them
            int payment = rand1to10();
            int policePressure = rand1to10();
            int greed = rand1to10();
            int trust = rand1to10();
            System.out.println();
            System.out.println("Investigating: " + suspectName);
            System.out.println("Stats (1 to 10):");
            System.out.println("Stress Level: " + stress);
            System.out.println("Payment Offered: " + payment);
            System.out.println("Police Pressure: " + policePressure);
            System.out.println("Greed Level: " + greed);
            System.out.println("Trust Factor: " + trust);
            System.out.println();

            //Example formula
            System.out.println("Suggested loyalty formula:");
            System.out.println("( stress * 5 ) + ( payment offered * 4 ) - ( police pressure * 3 ) - ( greeed * 4 ) - ( trust * 2 )");
            System.out.println();
            System.out.println("Your values to plug in:");
            System.out.println("payment=" + payment + " trust=" + trust + " stress=" + stress
                    + " police=" + policePressure + " greed=" + greed);
            System.out.println();

            // Ask for infix expression
            System.out.print("Enter your INFIX loyalty expression: ");
            String infix = scanner.nextLine();            
            double score; //results for score and used later
            try {
                //Converts to postfix and evaluates, prints score
                System.out.println("Step 1: Converting to Postfix...");
                String postfix = evaluator.convertToPostfix(infix);
                System.out.println("Postfix: " + postfix);

                //Evaluates the postfix
                System.out.println("Step 2: Evaluating...");
                score = evaluator.evaluatePostfix(postfix);

                //Prints score
                System.out.printf("Loyalty Score for %s: %.2f%n out of 50", suspectName, score);

            } catch (IllegalArgumentException | ArithmeticException | EmptyStackException e) {
                System.out.println("Error: " + e.getMessage()); //Error message and no crash
                System.out.println("Returning to main menu so you can try again.");
                return;
            }

            System.out.println(); // After score, asks user's verdict
            System.out.println("Decision:");
            System.out.println("1. YES - I think this person is the inside man.");
            System.out.println("2. NO  - I don't think this person is the inside man.");
            System.out.println("3. Unsure, I will return later.");
            int decision = readIntInRange(scanner, "Choose (1-3): ", 1, 3);

            //If yes they're an inside man, removes them from list, new crew cut is calcilated and printed
            if (decision == 1) {
                System.out.println();
                System.out.println("You marked " + suspectName + " as the INSIDE MAN.");
                eliminated[choice - 1] = true;
                remainingSuspects--;
                if (ctx.crewCount > 0) {
                    ctx.crewCount--;
                }

                System.out.println();
                System.out.println("=== New Crew Cut After Removing Inside Man ===");
                if (ctx.crewCount <= 0) {
                    System.out.println("No crew members left to split money with.");
                    System.out.printf("You keep: $%.2f%n", ctx.money);
                } else {
                    double each = ctx.money / ctx.crewCount; //In case less than 1 dollar per person; error
                    if (each < 1.0) {
                        System.out.println("Error: Each share would be less than $1.00. Not splitting.");
                    } else {
                        System.out.printf("Remaining Crew Members: %d%n", ctx.crewCount);
                        System.out.printf("Each remaining member gets: $%.2f%n", each);
                    }
                }

                System.out.println("Returning to main menu.");
                return;
            }

            //If no, not  an inside man, asks if you want to continue invesigating or return to main menu
            //Eliminaes sispect from list
            if (decision == 2) {
                System.out.println();
                System.out.println("You decided " + suspectName + " is NOT the inside man.");
                System.out.println("1. Continue investigating");
                System.out.println("2. Return to main menu");
                int next = readIntInRange(scanner, "Choose (1-2): ", 1, 2);
                if (next == 1) {
                    eliminated[choice - 1] = true;
                    remainingSuspects--;
                    System.out.println(suspectName + " has been eliminated from the suspect list.");
                    // Loop continues
                } else {
                    System.out.println("Returning to main menu.");
                    return;
                }
            }

            //If unsure, saves the score for later
            if (decision == 3) {
                hasSavedScore = true;
                savedName = suspectName;
                savedScore = score;
                System.out.println();
                System.out.println("Saved your investigation for later:");
                System.out.printf("Saved Suspect: %s | Saved Loyalty Score: %.2f%n", savedName, savedScore);
                System.out.println("Returning to main menu.");
                return;
            }
        }
    }
   
    private int rand1to10() { //Random number from 1 to 10
        return random.nextInt(10) + 1;
    }
    //Simple input helper for whole numbers in a range
    private int readIntInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);

                if (value < min || value > max) {
                    System.out.println("Error: Enter a number between " + min + " and " + max + ".");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid whole number.");
            }
        }
    }
}