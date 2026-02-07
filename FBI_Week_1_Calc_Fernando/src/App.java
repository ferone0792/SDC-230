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
    private static int readInt(Scanner input, String prompt) {
    //Will keep asking user to enter a valid whole number    
        while (true) {
            System.out.print(prompt);//displays message
            if (input.hasNextInt()) {//checks if input is whole number
                int value = input.nextInt();
                input.nextLine();
                return value;
            } else {
                //error message if input isn't a number
                System.out.println("Wrong input Schmuck! Please enter a whole number.");
                input.nextLine();
            }
        }
    }

    /* Asks the user to enter a number with decimals */
    private static double readDouble(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (input.hasNextDouble()) {
                double value = input.nextDouble();
                input.nextLine(); // consume leftover newline
                return value;
            } else {
                System.out.println("Really Smhuck? Wrong input. Please enter a number that can have a decimal!");
                input.nextLine(); // discard invalid input
            }
        }
    }

    /*Dispalys main menu*/
    private static void showMenu(String userName, double remainingBudget) {
        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.printf("User: %s | Remaining Budget: $%.2f%n", userName, remainingBudget);
        System.out.println("--------------------------------------------------");
        System.out.println("1) Crew Counter");
        System.out.println("2) Time Window Calculator");
        System.out.println("3) Exit");
        System.out.println();
    }

    // Main Program 

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Header line
        System.out.println("Week 1 - 1.5 Project: User Interactions and I/O");
        System.out.println("Heist Calculator");
        System.out.println("Fernando O'Neil");
        System.out.println();

        // 2) Welcome message and instructions
        System.out.println("This program is to calculate for the upcoming bank heist! Choose");
        System.out.println("options from the menu and punch in info correctly to get the best results.");
        System.out.println();

        // Ask user for their name
        System.out.print("Enter your name ya schmuck: ");
        String userName = input.nextLine().trim();
        if (userName.isEmpty()) {
            userName = "User";
        }

        System.out.printf("Hello, %s! Let's start planning .%n", userName);
        System.out.println();

        // Ask for starting budget. The budget counter will be visible at all times
        // so you can know what youre spending
        double remainingBudget = readDouble(input, "Enter your starting budget: $");

        boolean running = true;

        while (running) {
            showMenu(userName, remainingBudget);

            int choice = readInt(input, "Choose an either of the 3 options or I'll throw my cannoli at ya: ");

            switch (choice) {
                case 1:
                    // Whole numbers for the crews
                    System.out.println();
                    System.out.println("Crew Counter");
                    int teamA = readInt(input, "Enter crew members for Team A: ");
                    int teamB = readInt(input, "Enter crew members for Team B: ");
                    int totalCrew = teamA + teamB;//Adds the two terams togther

                    System.out.printf("Crew Members: %d + %d = %d%n", teamA, teamB, totalCrew);
                    System.out.printf("Remaining Budget: $%.2f%n", remainingBudget);
                    break;

                case 2:
                    // Subtracts the time thats delayed from the remaining time
                    System.out.println();
                    System.out.println("Time Window Calculator");
                    double delayMinutes = readDouble(input, "Enter delay time in minutes (Ex: 2.13): ");
                    double totalMinutes = readDouble(input, "Enter total time window in minutes (ex 18.59): ");
                    //delayMinutes = delay time, totalMinutes = total time for the heist 
                    double remainingTime = totalMinutes - delayMinutes;
                    System.out.printf("Time Window: %.2f - %.2f = %.2f minutes%n",
                            totalMinutes, delayMinutes, remainingTime);
                    //Result

                    if (remainingTime < 0) {
                        System.out.println("Warning: Your delay was longer than the available time window.");
                    }

                    System.out.printf("Remaining Budget: $%.2f%n", remainingBudget);
                    break;

                case 3:
                    // Exit
                    running = false;
                    break;

                default:
                    System.out.println("SCHMUCK!!!! Wrong input. Please choose 1, 2, or 3.");
                    break;
            }
        }

        System.out.println();
        System.out.printf("Thanks for using the Heist Calculator, %s!%n", userName);
        System.out.println("Program ending now. Goodbye!");

        input.close();
    }
}
