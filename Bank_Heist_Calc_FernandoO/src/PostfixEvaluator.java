import java.util.Stack;

/* Fernando ONeil
 * Week 3: Postfix Integration (Stacks)
 * PostfixEvaluator class
 * Contains stack logic for evaluating postfix expressions */
public class PostfixEvaluator {

    public static double evaluatePostfix(String expression) {

        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Expression is empty.");
        }

        String[] tokens = expression.trim().split("\\s+");
        Stack<Double> stack = new Stack<>();

        for (String token : tokens) {

            if (isOperator(token)) {

                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Malformed Expression (Insufficient operands).");
                }

                double y = stack.pop();
                double x = stack.pop();
                double result;

                switch (token) {
                    case "+": result = x + y; break;
                    case "-": result = x - y; break;
                    case "*": result = x * y; break;
                    case "/":
                        if (y == 0) {
                            throw new IllegalArgumentException("Division by zero.");
                        }
                        result = x / y;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operator.");
                }

                stack.push(result);

            } else {
                try {
                    stack.push(Double.parseDouble(token));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid token.");
                }
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Malformed Expression (Insufficient operands).");
        }

        return stack.pop();
    }

    private static boolean isOperator(String token) {
        return token.equals("+") ||
               token.equals("-") ||
               token.equals("*") ||
               token.equals("/");
    }
}