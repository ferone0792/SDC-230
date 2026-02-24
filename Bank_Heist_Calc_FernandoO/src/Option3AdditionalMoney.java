//*Addition, adds any extra money gained during the heist like stolen items
import java.util.Scanner;
public class Option3AdditionalMoney {
    public void run(Scanner scanner, HeistContext ctx) {
        ctx.printStatus();
        System.out.println("=== Additional Money Gained ===");
        double extra = readDoubleMin(scanner, "How much extra money did you gain? $", 0.0);
        ctx.money += extra;
        System.out.printf("Added $%.2f%n", extra);
        System.out.printf("Updated Money: $%.2f%n", ctx.money);
    }

    private double readDoubleMin(Scanner scanner, String prompt, double min) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value < min) {
                    System.out.printf("Error: Enter a number >= %.2f%n", min);
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }
}