import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        if (isEquationValid(userInput)) {
            System.out.println("Answer: " + calculate(userInput));
        } else {
            throw new RuntimeException("Invalid Equation");
        }
    }

    public static double calculate(String sum) {

        // remove empty spaces
        sum = sum.replaceAll("\\s", "");

        while (sum.contains("(")) {
            for (int i = 0; i < sum.length(); i++) {
                if (sum.charAt(i) == '(') {
                    int closingParenthesesIndex = findMatchingParenthesesIndex(i, sum);
                    String equationWithBracket = sum.substring(i, closingParenthesesIndex + 1); // with bracket e.g (2*(5+4))
                    String equation = sum.substring(i + 1, closingParenthesesIndex); // without bracket e.g 2*(5+4)

                    if (!isParenthesesPresent(equation)) {
                        double answer = calculateBasicOperations(equation);
                        sum = sum.replace(equationWithBracket, String.valueOf(answer));
                    }
                }
            }
        }

        return calculateBasicOperations(sum);
    }


    private static double calculateBasicOperations(String sum) {
        if(sum.contains("--")){
            // to handle equations like 10 - ( 2 - ( 20 - 8 ) )
            sum = sum.replace("--","+");
        }

        LinkedList<String> equationList = new LinkedList<>();
        char[] charList = sum.toCharArray();

        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < charList.length; i++) {
            temp.append(charList[i]);
            if (isOperator(String.valueOf(charList[i]))) {
                temp.deleteCharAt(temp.length() - 1);
                equationList.add(temp.toString());
                equationList.add(String.valueOf(charList[i]));
                temp.setLength(0);
            }

            if (i == charList.length - 1) {
                equationList.add(temp.toString());
            }
        }

        // solves multiplication and division
        while (equationList.contains("*") || equationList.contains("/")) {
            int index = multiplicationOrDivisionIndex(equationList);
            double result = operation(equationList.get(index),
                    Double.parseDouble(equationList.get(index - 1)),
                    Double.parseDouble(equationList.get(index + 1)));
            equationList.set(index, String.valueOf(result));
            equationList.remove(index - 1);
            equationList.remove(index);
        }

        // solves addition and subtraction
        while (equationList.contains("+") || equationList.contains("-")) {
            int index = additionOrSubtractionIndex(equationList);
            double result = operation(equationList.get(index),
                    Double.parseDouble(equationList.get(index - 1)), Double.parseDouble(equationList.get(index + 1)));
            equationList.set(index, String.valueOf(result));
            equationList.remove(index - 1);
            equationList.remove(index);
        }

        if(equationList.size()==1){
            return Double.parseDouble(equationList.get(0));
        } else {
            throw new RuntimeException("Error in calculation");
        }
    }


    private static boolean isParenthesesPresent(String equation) {
        return equation.contains("(");
    }

    private static boolean isEquationValid(String input) {
        boolean operator = input.contains("+") || input.contains("-") || input.contains("*") || input.contains("/");
        int openingParenthesesCount = input.length() - input.replace("(", "").length();
        int closingParenthesesCount = input.length() - input.replace(")", "").length();
        return operator && openingParenthesesCount == closingParenthesesCount;

    }

    private static boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
    }


    private static double operation(String operator, double first, double second) {
        switch (operator) {
            case "+":
                return first + second;
            case "-":
                return first - second;
            case "*":
                return first * second;
            case "/":
                return first / second;
            default:
                throw new RuntimeException("Invalid Operator");
        }
    }

    // To determine the precedence of multiplication or division following PEMDAS
    private static int multiplicationOrDivisionIndex(List<String> equationList) {
        int multiply = equationList.indexOf("*");
        int division = equationList.indexOf("/");
        if (multiply > 0 && division > 0) {
            return Math.min(multiply, division);
        } else if (multiply > 0) {
            return multiply;
        } else {
            return division;
        }
    }

    // To determine the precedence of addition or subtraction following PEMDAS
    private static int additionOrSubtractionIndex(List<String> equationList) {
        int addition = equationList.indexOf("+");
        int subtraction = equationList.indexOf("-");
        if (addition > 0 && subtraction > 0) {
            return Math.min(addition, subtraction);
        } else if (addition > 0) {
            return addition;
        } else {
            return subtraction;
        }
    }

    public static int findMatchingParenthesesIndex(int openingBracketIndex, String userInput) {
        int counter = 1;
        while (counter > 0) {
            for (int i = openingBracketIndex + 1; i < userInput.length(); i++) {
                openingBracketIndex++;
                if (userInput.charAt(i) == '(') {
                    counter = counter + 1;
                } else if (userInput.charAt(i) == ')') {
                    counter = counter - 1;
                }
                if (counter == 0) break;
            }
        }
        return openingBracketIndex;
    }


}