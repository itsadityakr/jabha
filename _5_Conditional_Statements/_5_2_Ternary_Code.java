package _5_Conditional_Statements;

/**
 * Demonstrates the ternary (conditional) operator in Java.
 *
 * The ternary operator is a compact form of if-else that produces a value:
 *
 *     result = condition ? valueIfTrue : valueIfFalse;
 *
 * It is the only operator in Java that takes three operands.
 */
public class _5_2_Ternary_Code {

    public static void main(String[] args) {

        int a = 10;
        int b = 20;

        // Pick the larger of two numbers.
        int max = (a > b) ? a : b;
        System.out.println("max         : " + max);

        // Decide even or odd as a String.
        int number = 7;
        String type = (number % 2 == 0) ? "even" : "odd";
        System.out.println("7 is        : " + type);

        // The same logic written as if-else for comparison:
        // String type;
        // if (number % 2 == 0) type = "even"; else type = "odd";

        // Ternary can be nested, but keep it readable.
        int marks = 82;
        char grade = (marks >= 90) ? 'A'
                   : (marks >= 75) ? 'B'
                   : (marks >= 60) ? 'C'
                   : 'F';
        System.out.println("grade       : " + grade);

        // Using a ternary directly inside a print statement.
        int age = 16;
        System.out.println("category    : " + (age >= 18 ? "Adult" : "Minor"));
    }
}

/*
 * Output:
 * max         : 20
 * 7 is        : odd
 * grade       : B
 * category    : Minor
 *
 * Explanation: The ternary operator evaluates the condition; if it is true the
 * value before the colon is used, otherwise the value after the colon is used.
 * It returns a value, unlike a plain if statement.
 */
