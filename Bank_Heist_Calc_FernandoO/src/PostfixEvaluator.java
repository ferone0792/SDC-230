/*Fernando ONeil
* 02/24/2026
* Week 4: Shunting-Yard & Exception Handling
* convertToPostfix(String infix)
* evaluatePostfix(String expression) */ 

import java.util.EmptyStackException;
import java.util.Stack;
public class PostfixEvaluator {

    /* This uses the Shunting-Yard Algorithm so the numbers go dtraight to the output
     *operators go to the stack based on importance, parentheses control when we pop operators to the output and
     * throws IllegalArgumentException for invalid tokens or mismatched parentheses*/
    public String convertToPostfix(String infix) throws IllegalArgumentException {
        if (infix == null || infix.trim().isEmpty()) { //error if nothing is inoutted
            throw new IllegalArgumentException("Infix expression is empty.");
        }

        Stack<Character> operators = new Stack<>(); //stack to hold operators and parentheses during conversion        
        StringBuilder output = new StringBuilder(); // StringBuilder for building the postfix output
        int i = 0; //scans input string one character at a time
        boolean expectNumber = true;
        while (i < infix.length()) {
            char c = infix.charAt(i);
            // Ignore spaces so user can type "5 + 3" or "5+3"
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            /* Builds full number token and adds it to output if its a number*/
            if (Character.isDigit(c) || c == '.' || (c == '-' && expectNumber)) {
                StringBuilder number = new StringBuilder();
                if (c == '-' && expectNumber) {
                    number.append('-');
                    i++;
                    if (i >= infix.length()) {
                        throw new IllegalArgumentException("Dangling '-' with no number after it.");
                    }
                }

                boolean hasDot = false; //Keeps reading digits and/or decimals until the number ends              
                while (i < infix.length()) {
                    char nc = infix.charAt(i);
                    if (Character.isDigit(nc)) {
                        number.append(nc);
                        i++;
                    } else if (nc == '.') {
                        if (hasDot) {
                            throw new IllegalArgumentException("Invalid number: too many decimal points.");
                        }
                        hasDot = true;
                        number.append(nc);
                        i++;
                    } else {
                        break;
                    }
                }

                String numText = number.toString(); //checks if number is valid
                if (numText.equals("-") || numText.equals(".") || numText.equals("-.")) {
                    throw new IllegalArgumentException("Invalid number found in expression.");
                }               
                output.append(numText).append(' '); //Adds this number to postfix output
                expectNumber = false; // After a number, we should expect an operator or ')'
                continue;
            }

            if (c == '(') { // If the user typed '(' we push it to stack
                operators.push(c);
                i++;
                expectNumber = true; // after '(' we expect a number
                continue;
            }

            //If the user typed ')', pop operators to output until we find '('
            if (c == ')') {
                boolean foundLeftParen = false;
                while (!operators.isEmpty()) {
                    char top = operators.pop();
                    if (top == '(') {
                        foundLeftParen = true;
                        break;
                    }                    
                    output.append(top).append(' '); //Adds the popped operator to output
                }

               
                if (!foundLeftParen) { // If '(' isnt found then parentheses are mismatched
                    throw new IllegalArgumentException("Mismatched parentheses detected.");
                }
                i++;
                expectNumber = false;
                continue;
            }

            //For any operator (+ - * /), precedencce is organized by popping higher or equal precedence
            if (isOperatorChar(c)) {                
                while (!operators.isEmpty()
                        && isOperatorChar(operators.peek())
                        && precedence(operators.peek()) >= precedence(c)) {
                    output.append(operators.pop()).append(' ');
                }

                operators.push(c);
                i++;
                expectNumber = true;//Expects a number after an operator
                continue;
            }
           
            throw new IllegalArgumentException("Invalid token: '" + c + "'"); //invalid characters
        }

        //Pop any remaining operators from the stack to the output
        while (!operators.isEmpty()) {
            char top = operators.pop();
            if (top == '(') {
                throw new IllegalArgumentException("Mismatched parentheses detected.");
            }
            output.append(top).append(' ');
        }

        //trims any blank spaces
        return output.toString().trim();
    }

    /*evaluatePostfix(String expression) which uses a Stack to push numbers when operators
     *appears, pop 2 numbers, calculate, pushes result, throws arithmeticException for division by zero
     * "IllegalArgumentException" for bad tokens or malformed expressions*/
    public double evaluatePostfix(String expression) throws ArithmeticException {        
        if (expression == null || expression.trim().isEmpty()) { // Empty input is not allowed
            throw new IllegalArgumentException("Expression is empty.");
        }

        String[] tokens = expression.trim().split("\\s+"); //Split by spaces
        Stack<Double> stack = new Stack<>();
        for (String token : tokens) {
            if (isOperatorToken(token)) {//matches top 2 numbers with operator and pushes result back to stack                
                if (stack.size() < 2) {//Must have 2 numbers available for an operation
                    throw new EmptyStackException();
                }
                
                double y = stack.pop(); //Pop in correct order:
                double x = stack.pop();
                double result;
                switch (token) {
                    case "+":
                        result = x + y;
                        break;
                    case "-":
                        result = x - y;
                        break;
                    case "*":
                        result = x * y;
                        break;
                    case "/":                        
                        if (y == 0) { //Division by zero check
                            throw new ArithmeticException("Division by zero is not allowed.");
                        }
                        result = x / y;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operator.");
                }
               
                stack.push(result); //Push the answer back onto the stack
            } else {
                try {
                    stack.push(Double.parseDouble(token));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid token: " + token);
                }
            }
        }

        if (stack.size() != 1) { //At the end, there should be exactly 1 value left
            throw new IllegalArgumentException("Malformed Expression (Insufficient operands).");
        }
        return stack.pop();
    }

    private boolean isOperatorToken(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    private boolean isOperatorChar(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int precedence(char op) { //Orders operates by precedenc
        if (op == '*' || op == '/') return 2;
        if (op == '+' || op == '-') return 1;
        return 0;
    }
}