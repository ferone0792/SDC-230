/*Uses nultiplication, lets user buy items from a list and how many of the item,
*subtracts cost from money and prevents money from going negative*/
import java.util.Scanner;
public class Option1EquipmentShop {
    public void run(Scanner scanner, HeistContext ctx) {
        boolean shopping = true;
        while (shopping) {
            ctx.printStatus();
            System.out.println("=== EQUIPMENT SHOP ===");
            System.out.println("1. Bribe Guards ($1000 each)");
            System.out.println("2. TNT ($250 each)");
            System.out.println("3. Wall Climbing Toilet Plungers ($21 each)");
            System.out.println("4. Drone ($300 each)");
            System.out.println("5. Banana Peels ($2 each)");
            System.out.println("6. Gas Masks ($32 each)");
            System.out.println("7. Phone Jammer ($2000 each)");
            System.out.println("8. Back to Main Menu");
            int itemChoice = readIntInRange(scanner, "Select an item (1-8): ", 1, 8);
            if (itemChoice == 8) {
                return; // back to main menu
            }

            double price = getItemPrice(itemChoice);
            String itemName = getItemName(itemChoice);
            int qty = readIntMin(scanner, "How many \"" + itemName + "\" did you buy? ", 0);
            double totalCost = price * qty;

            if (ctx.money - totalCost < 0) {//Prevents it from going into the negative
                System.out.printf("Error: That would make your money negative. Purchase cancelled. Total was $%.2f%n", totalCost);
            } else {
                ctx.money -= totalCost;
                System.out.printf("Purchased %d %s for $%.2f total.%n", qty, itemName, totalCost);
                System.out.printf("Updated Money: $%.2f%n", ctx.money);
            }

            int again = readIntInRange(scanner, "Buy more items? (1=Yes, 2=No): ", 1, 2);
            shopping = (again == 1);
            System.out.println();
        }
    }

    private double getItemPrice(int itemChoice) {//price list for each item
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

    private String getItemName(int itemChoice) {
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

    private int readIntMin(Scanner scanner, String prompt, int min) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value < min) {
                    System.out.println("Error: Enter a number >= " + min + ".");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid whole number.");
            }
        }
    }
}