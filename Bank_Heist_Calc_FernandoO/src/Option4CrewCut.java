//Division of loot among crew and can only be done once

import java.util.Scanner;
public class Option4CrewCut {
    public void run(Scanner scanner, HeistContext ctx) {
        if (ctx.crewCutCalculated) {
            System.out.println("Crew cut already calculated. You can only do this once.");
            return;
        }

        int ready = readIntInRange(scanner,//This prompt makes user user has done all previous steps before this one
                "Have you calculated all costs and loot gained? (1=Yes, 2=No): ", 1, 2);
        if (ready == 2) {
            System.out.println("Okay. Go back and finish costs/loot first.");
            return;
        }

        ctx.printStatus();
        System.out.println("=== Crew Cut ===");

        if (ctx.crewCount == 0) {
            System.out.println("Solo heist. You keep everything!");
            System.out.printf("Your cut: $%.2f%n", ctx.money);
            ctx.crewCutCalculated = true;
            return;
        }

        double each = ctx.money / ctx.crewCount;

        if (each < 1.0) {
            System.out.println("Error: Division result cannot be less than 1. Crew cut not calculated.");
            return;
        }

        System.out.printf("Each crew member gets: $%.2f%n", each);
        ctx.crewCutCalculated = true;
    }

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