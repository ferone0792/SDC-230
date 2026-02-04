import java.util.Scanner;

/*
 * 1.5 Project - User Interactions and I/O Week 1
 * Bank Heist Calculator
 * Name: Fernando O'Neil
 * This program calculates how much money would it take to
 * complete a heist and how much money would be left in the pot 
 * after the heist and that would dtermine if the heist was a success or failure
 */

public class App {
    /* Reads an int from the user with validation*/
    private static int readInt(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (input.hasNextInt()) {
                int value = input.nextInt();
                input.nextLine(); // consume leftover newline
                return value;
            } else {
                System.out.println("Invalid input. Please enter a whole number (example: 5).");
                input.nextLine(); // discard invalid input
            }
        }
    }

    /* Reads a double from the user with validation */
    private static double readDouble(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (input.hasNextDouble()) {
                double value = input.nextDouble();
                input.nextLine(); // consume leftover newline
                return value;
            } else {
                System.out.println("Invalid input. Please enter a number (example: 12.50).");
                input.nextLine(); // discard invalid input
            }
        }
    }

    /*Displays the main menu*/
    private static void showMenu(String userName, double remainingBudget) {
        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.printf("User: %s | Remaining Budget: $%.2f%n", userName, remainingBudget);
        System.out.println("--------------------------------------------------");
        System.out.println("1) Crew Counter (Integer Addition)");
        System.out.println("2) Time Window Calculator (Double Subtraction)");
        System.out.println("3) Exit");
        System.out.println();
    }

    // ---------- Main Program ----------

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // 1) Project header line (required)
        System.out.println("Week 1 - 1.5 Project: User Interactions and I/O");
        System.out.println("Heist Calculator (Fictional Simulator)");
        System.out.println("Fernando O'Neil");
        System.out.println();

        // 2) Welcome message + instructions (required)
        System.out.println("Welcome! This program is a fictional heist-planning calculator.");
        System.out.println("You will choose an option from the menu and enter numbers when prompted.");
        System.out.println("It will perform basic calculations and display results clearly.");
        System.out.println();

        // Ask user for their name (Part 1 feature)
        System.out.print("Enter your name: ");
        String userName = input.nextLine().trim();
        if (userName.isEmpty()) {
            userName = "User";
        }

        System.out.printf("Hello, %s! Let's start planning (fictionally).%n", userName);
        System.out.println();

        // Ask for starting budget (so we can display remaining budget at all times)
        double remainingBudget = readDouble(input, "Enter your starting budget (example: 5000.00): $");

        boolean running = true;

        while (running) {
            showMenu(userName, remainingBudget);

            int choice = readInt(input, "Choose an option (1-3): ");

            switch (choice) {
                case 1:
                    // Integer operation: enter two ints, add them, display formatted result
                    System.out.println();
                    System.out.println("Crew Counter (Integer Addition)");
                    int teamA = readInt(input, "Enter crew members for Team A (whole number): ");
                    int teamB = readInt(input, "Enter crew members for Team B (whole number): ");
                    int totalCrew = teamA + teamB;

                    System.out.printf("Crew Members: %d + %d = %d%n", teamA, teamB, totalCrew);
                    System.out.printf("Remaining Budget: $%.2f%n", remainingBudget);
                    break;

                case 2:
                    // Floating-point operation: enter two doubles, subtract first from second, display to 2 decimals
                    System.out.println();
                    System.out.println("Time Window Calculator (Double Subtraction)");
                    double delayMinutes = readDouble(input, "Enter delay time in minutes (example: 3.25): ");
                    double totalMinutes = readDouble(input, "Enter total time window in minutes (example: 12.50): ");

                    double remainingTime = totalMinutes - delayMinutes;

                    System.out.printf("Time Window: %.2f - %.2f = %.2f minutes%n",
                            totalMinutes, delayMinutes, remainingTime);

                    if (remainingTime < 0) {
                        System.out.println("Warning: Your delay was longer than the available time window.");
                    }

                    System.out.printf("Remaining Budget: $%.2f%n", remainingBudget);
                    break;

                case 3:
                    // Exit (required closing message)
                    running = false;
                    break;

                default:
                    System.out.println("Invalid menu option. Please choose 1, 2, or 3.");
                    break;
            }
        }

        System.out.println();
        System.out.printf("Thanks for using the Heist Calculator, %s!%n", userName);
        System.out.println("Program ending now. Goodbye!");

        input.close();
    }
}
