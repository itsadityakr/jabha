package _6_Conditional_Statements;

/**
 * Demonstrates the if, if-else, and if-else-if ladder in Java.
 *
 * Conditional statements let a program make decisions: a block of code runs
 * only when a boolean condition is true.
 */
public class _6_1_If_Else_Code {

    public static void main(String[] args) {

        int marks = 75;

        // Simple if: runs only when the condition is true.
        if (marks >= 33) {
            System.out.println("Result: Pass");
        }

        // if-else: one branch or the other always runs.
        int number = 7;
        if (number % 2 == 0) {
            System.out.println(number + " is even");
        } else {
            System.out.println(number + " is odd");
        }

        // if-else-if ladder: checks conditions in order, top to bottom.
        // The first true condition wins; the rest are skipped.
        char grade;
        if (marks >= 90) {
            grade = 'A';
        } else if (marks >= 75) {
            grade = 'B';
        } else if (marks >= 60) {
            grade = 'C';
        } else if (marks >= 33) {
            grade = 'D';
        } else {
            grade = 'F';
        }
        System.out.println("Grade: " + grade);

        // Nested if: an if inside another if.
        int age = 20;
        boolean hasId = true;
        if (age >= 18) {
            if (hasId) {
                System.out.println("Entry allowed");
            } else {
                System.out.println("ID required");
            }
        }
    }
}

/*
 * Output:
 * Result: Pass
 * 7 is odd
 * Grade: B
 * Entry allowed
 *
 * Explanation: marks = 75, so the first true branch in the ladder is
 * (marks >= 75), giving grade 'B'. Even though (marks >= 60) and
 * (marks >= 33) are also true, the ladder stops at the first match.
 */
