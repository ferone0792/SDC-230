import java.util.Random;
import java.util.Scanner;

/* Fernando ONeil
 * 02/21/2026
 * Week 3: Postfix Integration (Stacks)
 * 3.3 Project: Java Arithmetic Project
 * Heist Budget Calculator with menu and logic */
public class HeistCalculator {

    // Scanner
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    //These will display thoughout the whole program
    private static double money = 0.0;
    private static int crewCount = 0;

    // These options can only be selected once
    private static boolean unexpectedCostsApplied = false;
    private static boolean crewCutCalculated = false;

    public static void main(String[] args) {
        System.out.println("========================================================="); //Header
        System.out.println("3.3: Java Arithmetic Project- Fernando O'Neil");
        System.out.println("==========================================================");
        System.out.println("Hey scumbag! Nice job with the bank heist! ."); //Welcome message
        System.out.println("INSTRUCTIONS: Select an option from the menu to manage your heist money and costs!");
        System.out.println();

        //First inouts that will display the whole time you use the program
        money = readDoubleMin("1) How much money did you make during your heist? $", 0.0);
        crewCount = readIntMin("2) How many people are in your crew? ", 0);

        //Loop for main menu
        boolean running = true;
        while (running) {
            printStatus();
            System.out.println("--- MAIN MENU ---");
            System.out.println("1. Additional Equipment Purchased");
            System.out.println("2. Unexpected Costs");
            System.out.println("3. Additional Money Gained During Heist");
            System.out.println("4. Cut of Crew");
            System.out.println("5. FBI Scrambler"); //Postfix Evaluator and stacks
            System.out.println("6. Quit");

            int choice = readIntInRange("Choose an option (1-6): ", 1, 6);
            System.out.println();

            switch (choice) {
                case 1:
                    runEquipmentShop(); //multiplication
                    break;
                case 2:
                    runUnexpectedCosts(); //subtraction
                    break;
                case 3:
                    addLootGained(); //addition
                    break;
                case 4:
                    calculateCrewCut(); //Division
                    break;
                case 5:
                    runFbiScrambler(); // Week 3 postfix evaluator
                    break;
                case 6: //exit
                    running = false;
                    break;
            }

            System.out.println();
        }

        // Exit message
        System.out.println("Thank you for using this ya sicko! Hope you made some good money on your heist!");
        System.out.println("==========================================================");
    }

    //SHows your budget at all times
    private static void printStatus() {
        System.out.println("----------------------------------------------------------");
        System.out.printf("Current Money: $%.2f%n", money);
        System.out.println("Crew Members: " + crewCount);
        System.out.println("----------------------------------------------------------");
    }

    //Inventory shop which multiplies and loops if you need to make more than one purchase
    private static void runEquipmentShop() {
        boolean shopping = true;

        while (shopping) {
            printStatus();
            System.out.println("=== EQUIPMENT SHOP ===");
            System.out.println("Choose items you purchased:");
            System.out.println("1. Bribe Guards ($1000 each)");
            System.out.println("2. TNT ($250 each)");
            System.out.println("3. Wall Climbing Toilet Plungers ($21 each)");
            System.out.println("4. Drone ($300 each)");
            System.out.println("5. Banana Peels ($2 each)");
            System.out.println("6. Gas Masks ($32 each)");
            System.out.println("7. Phone Jammer ($2000 each)");
            System.out.println("8. Back to Main Menu");

            int itemChoice = readIntInRange("Select an item (1-8): ", 1, 8);
            if (itemChoice == 8) {
                return; // back to main menu
            }

            double price = getItemPrice(itemChoice);
            String itemName = getItemName(itemChoice);

            int qty = readIntMin("How many \"" + itemName + "\" did you buy? ", 0);
            double totalCost = price * qty;

            if (money - totalCost < 0) { //You can't go into the negatives
                System.out.printf("Error! Purchase total $%.2f would make money negative. You suck at heists!%n", totalCost);
            } else {
                money -= totalCost;
                System.out.printf("Purchased %d %s for $%.2f total.%n", qty, itemName, totalCost);
                System.out.printf("Updated Money: $%.2f%n", money);
            }

            int again = readIntInRange("Did you purchase other items? (1=Yes, 2=No): ", 1, 2);
            shopping = (again == 1);
            System.out.println();
        }
    }

    private static double getItemPrice(int itemChoice) {
        switch (itemChoice) {
            case 1: return 1000.0;
            case 2: return 250.0;
            case 3: return 21.0;
            case 4: return 300.0;
            case 5: return 2.0;
            case 6: return 32.0;
            case 7: return 2000.0;
            default: return 0.0;
        }
    }

    private static String getItemName(int itemChoice) {
        switch (itemChoice) {
            case 1: return "Bribe Guards";
            case 2: return "TNT";
            case 3: return "Wall Climbing Toilet Plungers";
            case 4: return "Drone";
            case 5: return "Banana Peels";
            case 6: return "Gas Masks";
            case 7: return "Phone Jammer";
            default: return "Unknown Item";
        }
    }

    // Subtraction and  division can only be selected once
    private static void runUnexpectedCosts() {

        if (unexpectedCostsApplied) {
            System.out.println("You already applied the unexpected costs... you want to be in the bottom of a river?");
            return;
        }

        printStatus();
        System.out.println("=== Unexpected costs ===");
        System.out.println("All unexpected cost categories are applied ONCE and subtracted from your money.");
        System.out.println();

        double vaultDrillRepairs = randomIntInRange(1000, 3500);
        double vehicleDamage = randomIntInRange(250, 1800);
        double disguises = randomIntInRange(12, 70);
        double bankMap = 800.0;
        double cctvHack = 700.0;
        double sleepingGas = randomIntInRange(50, 500);

        System.out.printf("1) Vault drill repairs: $%.2f%n", vaultDrillRepairs);
        System.out.printf("2) Vehicle damage:      $%.2f%n", vehicleDamage);
        System.out.printf("3) Disguises:           $%.2f%n", disguises);
        System.out.printf("4) Bank map:            $%.2f%n", bankMap);
        System.out.printf("5) CCTV hack:           $%.2f%n", cctvHack);
        System.out.printf("6) Sleeping gas:        $%.2f%n", sleepingGas);

        double totalUnexpected = vaultDrillRepairs + vehicleDamage + disguises + bankMap + cctvHack + sleepingGas;

        System.out.println("----------------------------------------------------------");
        System.out.printf("Total unexpected costs: $%.2f%n", totalUnexpected);

        if (money - totalUnexpected < 0) {
            System.out.println("Total can't be negative. How did the cops not catch you??? Costs won't be applied.");
            return;
        }

        money -= totalUnexpected;
        unexpectedCostsApplied = true;
        System.out.printf("Updated Money: $%.2f%n", money);
    }

    // Add loot gained that wasn't expected
    private static void addLootGained() {
        printStatus();
        System.out.println("=== Additional money ===");
        System.out.println("Any extra money you gained from heist! (purses, wallets, jewelry, watches, etc) can be added here.");

        double extra = readDoubleMin("How much extra money did you gain during the heist? $", 0.0);
        money += extra;

        System.out.printf("Added $%.2f to your money.%n", extra);
        System.out.printf("Updated Money: $%.2f%n", money);
    }

    // Divides crew cut and can only be done once
    private static void calculateCrewCut() {
        if (crewCutCalculated) {
            System.out.println("Crew cut already calculated. You can only do this once per heist.");
            return;
        }

        int ready = readIntInRange("Have you calculated all costs and loot gained? (1=Yes, 2=No): ", 1, 2);
        if (ready == 2) {
            System.out.println("Okay. Go back and finish costs/loot first.");
            return;
        }

        printStatus();
        System.out.println("=== Cut for crew ===");

        if (crewCount == 0) {
            System.out.println("You have 0 crew members (solo heist). You keep the full amount!");
            System.out.printf("Your cut: $%.2f%n", money);
            crewCutCalculated = true;
            return;
        }

        double eachCut = money / crewCount;

        if (eachCut < 1.0) {
            System.out.println("ERROR!!! Division result cannot be less than 1. Crew cut not calculated.");
            return;
        }

        System.out.printf("Total money: $%.2f%n", money);
        System.out.println("Crew members: " + crewCount);
        System.out.printf("Each crew member gets: $%.2f%n", eachCut);

        crewCutCalculated = true;
    }

    //Week 3 FBI Scrambler which is postfix evaluator and stacks
    private static void runFbiScrambler() {

        System.out.println("WARNING! FBI CLOSING IN ON YOUR POSITION!!!!");
        System.out.println("SCRAMBLE THEIR SYSTEMS BY USING OUR STACK METHOD!");
        System.out.println("FINAL RESULT MUST BE DIVISIBLE BY 12.");
        System.out.println("You have 3 chances.");
        System.out.println("Example postfix: 24 12 + 120 *");
        System.out.println();

        int attemptsLeft = 3;

        while (attemptsLeft > 0) {

            if (attemptsLeft == 2) { //warning 1
                System.out.println("Don't worry, they'll know you just wet your bed until you were 16 and have you within a 25 mile radius.");
            } else if (attemptsLeft == 1) {
                System.out.println("Last chance sucka!");
            }

            System.out.print("Postfix: ");
            String expression = scanner.nextLine().trim();

            try {
                double result = PostfixEvaluator.evaluatePostfix(expression);
                System.out.printf("Final Result: %.2f%n", result);

                if (isDivisibleBy12(result)) { //If you succeeed against the scrambler
                    System.out.println("Good job, the FBI accidentally went to the wrong address and arrested a granny!");
                    return;
                } else {
                    attemptsLeft--;

                    if (attemptsLeft == 0) break;

                    if (attemptsLeft == 2) {
                        int again = readIntInRange("Try again? 1 = YES, 2 = NO, I'M ESCAPING: ", 1, 2);
                        if (again == 2) {
                            System.out.println("Okay, you're escaping.");
                            return;
                        }
                    } else if (attemptsLeft == 1) { //warning 3
                        System.out.println("Oh no you only have 1 more chance, and they have you within a 10 mile radius!");
                    }
                }

            } catch (IllegalArgumentException e) {
                attemptsLeft--;
                System.out.println("Error: " + e.getMessage());

                if (attemptsLeft == 0) break;

                if (attemptsLeft == 2) { //warning 1
                    System.out.println("Don't worry, they'll know you just wet your bed until you were 16 and have you within a 25 mile radius.");
                    int again = readIntInRange("Try again? 1 = YES, 2 = NO, I'M ESCAPING: ", 1, 2);
                    if (again == 2) {
                        System.out.println("Okay, you're escaping.");
                        return;
                    }
                } else if (attemptsLeft == 1) {
                    System.out.println("Oh no you only have 1 more chance, and they have you within a 10 mile radius!");
                }
            }

            System.out.println();
        }

        System.out.println("Yeah they have your location and social security number... sucks to suck!!!");
        System.out.println("Now hit this computer with a hammer and try to run");
    }

    private static boolean isDivisibleBy12(double value) {
        double nearestInt = Math.rint(value); // nearest integer
        if (Math.abs(value - nearestInt) > 1e-9) {
            return false; // must be basically an integer
        }
        long asLong = (long) nearestInt;
        return asLong % 12 == 0;
    }

    private static int randomIntInRange(int min, int max) {
        // inclusive range
        return random.nextInt((max - min) + 1) + min;
    }

    private static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.println("ERROR!!! Enter a number between " + min + " and " + max + ".");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR!!! Please enter a valid whole number.");
            }
        }
    }

    private static int readIntMin(String prompt, int min) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value < min) {
                    System.out.println("ERROR!!! Enter a number greater than or equal to " + min + ".");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR!!! Please enter a valid whole number.");
            }
        }
    }

    private static double readDoubleMin(String prompt, double min) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value < min) {
                    System.out.printf("ERROR!!! Enter a number greater than or equal to %.2f.%n", min);
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR!!! Please enter a valid number (example: 1500 or 1500.50).");
            }
        }
    }
}