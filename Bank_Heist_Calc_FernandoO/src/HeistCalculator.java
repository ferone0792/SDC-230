import java.util.Scanner;
import java.util.Random;

/*Fernando ONeil
 * Week 4 Project
 *02/23/2026
 * HeistCalculator.java
 * -Main menu class and calls correct option depending the user's Choice*/
public class HeistCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner for the whole program
        Random random = new Random(); //Random used for the unpredictable costs
        PostfixEvaluator evaluator = new PostfixEvaluator();//Evaluator does the postfix for week 3 and infix to postfix conversion (week 4)
        HeistContext ctx = new HeistContext(); //Stores data for money and crew
        System.out.println("========================================================="); //Header
        System.out.println("        4.3: Bank Heist Project- Fernando O'Neil         ");
        System.out.println("==========================================================");
        System.out.println("Hey scumbag! Nice job with the bank heist!");
        System.out.println("INSTRUCTIONS: Select an option from the menu to manage your heist money and costs!");
        System.out.println();

        //Asks for starting money and crew count then stores it
        ctx.money = readDoubleMin(scanner, "1) How much money did you make during your heist? $", 0.0);
        ctx.crewCount = readIntMin(scanner, "2) How many people are in your crew? ", 0);

        // Create each class for the menu options
        Option1EquipmentShop option1 = new Option1EquipmentShop();
        Option2UnexpectedCosts option2 = new Option2UnexpectedCosts();
        Option3AdditionalMoney option3 = new Option3AdditionalMoney();
        Option4CrewCut option4 = new Option4CrewCut();
        Option5FbiScrambler option5 = new Option5FbiScrambler();
        Option6InsideManLoyalty option6 = new Option6InsideManLoyalty();
        boolean running = true;

        while (running) { //Menu loop
            ctx.printStatus(); //Current money and crew will always be displayed for reference            
            System.out.println("--- MAIN MENU ---"); //Prints menu
            System.out.println("1. Additional Equipment Purchased");
            System.out.println("2. Unexpected Costs");
            System.out.println("3. Additional Money Gained During Heist");
            System.out.println("4. Cut of Crew");
            System.out.println("5. FBI Scrambler");
            System.out.println("6. Inside Man Loyalty Meter"); //Week 4 infix to postfix option
            System.out.println("7. Quit");
            int choice = readIntInRange(scanner, "Choose an option (1-7): ", 1, 7);
            System.out.println();

            switch (choice) {
                case 1:
                    option1.run(scanner, ctx);
                    break;
                case 2:
                    option2.run(random, ctx);
                    break;
                case 3:
                    option3.run(scanner, ctx);
                    break;
                case 4:
                    option4.run(scanner, ctx);
                    break;
                case 5:
                    option5.run(scanner, evaluator);
                    break;
                case 6:
                    option6.run(scanner, evaluator, ctx);//calls convert to postfix
                    break;
                case 7:
                    running = false; //Ends loop
                    break;
            }
            System.out.println();
        }

        // Exit message
        System.out.println("Thank you for using this ya sicko! Hope you made some good money on your heist!");
        System.out.println("==========================================================");
        scanner.close();
    }

    //Needs a proper input that is within a certain range
    private static int readIntInRange(Scanner scanner, String prompt, int min, int max) {
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

    //Reads an int that must be a >= min and <= max for menu options
    private static int readIntMin(Scanner scanner, String prompt, int min) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value < min) {
                    System.out.println("Error: Enter a number greater than or equal to " + min + ".");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid whole number.");
            }
        }
    }

    //Reads a double
    private static double readDoubleMin(Scanner scanner, String prompt, double min) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value < min) {
                    System.out.printf("Error: Enter a number greater than or equal to %.2f.%n", min);
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number (example: 1500 or 1500.50).");
            }
        }
    }
}