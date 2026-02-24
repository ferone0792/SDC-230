/* Postfix evaluation game for FBI Scrambler option, user has 3 attempts to enter a number divisible by 12 
* using a postfix expression
*Used "12" because people call the police "12" all the time*/
import java.util.Scanner;
import java.util.EmptyStackException;
public class Option5FbiScrambler {
    public void run(Scanner scanner, PostfixEvaluator evaluator) {
        System.out.println("WARNING! FBI CLOSING IN ON YOUR POSITION!!!!");
        System.out.println("SCRAMBLE THEIR SYSTEMS BY USING OUR STACK METHOD!");
        System.out.println("FINAL RESULT MUST BE DIVISIBLE BY 12.");
        System.out.println("You have 3 chances.");
        System.out.println("Example postfix: 24 12 + 120 *");
        System.out.println();
        int attemptsLeft = 3;
        while (attemptsLeft > 0) {
            if (attemptsLeft == 2) {
                System.out.println("They have your position within a 5 mile radius!");
            } else if (attemptsLeft == 1) {
                System.out.println("Last chance! They are within 1 mile of your position!!!");
            }

            System.out.print("Postfix: ");
            String expression = scanner.nextLine().trim();

            try {
                double result = evaluator.evaluatePostfix(expression); //warning prompts
                System.out.printf("Final Result: %.2f%n", result);
                if (isDivisibleBy12(result)) {
                    System.out.println("Good job, the FBI went to the wrong address and arrested a granny instead! Youre clear!");
                    return;
                } else {
                    attemptsLeft--;
                    if (attemptsLeft == 0) break;
                }
            } catch (IllegalArgumentException | ArithmeticException | EmptyStackException e) {
                attemptsLeft--;
                System.out.println("Error: " + e.getMessage());
                if (attemptsLeft == 0) break;
            }

            System.out.println();
        }
        System.out.println("They found your location... sucks to suck.");
    }

    private boolean isDivisibleBy12(double value) {
        double nearest = Math.rint(value);
        if (Math.abs(value - nearest) > 1e-9) return false;
        return ((long) nearest) % 12 == 0;
    }
}