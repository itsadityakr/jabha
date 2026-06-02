package _5_Operators;

/**
 * Demonstrates the assignment operators in Java.
 *
 * The basic assignment operator is '='. Compound assignment operators
 * (+=, -=, *=, /=, %=) combine an arithmetic operation with assignment.
 */
public class _5_2_Assignment_Operators_Code {

    public static void main(String[] args) {

        // Simple assignment: store a value in a variable.
        int num = 10;
        System.out.println("start       : " + num);

        // num = num + 5  written shortly as num += 5
        num += 5; // 15
        System.out.println("num += 5    : " + num);

        num -= 3; // 12
        System.out.println("num -= 3    : " + num);

        num *= 2; // 24
        System.out.println("num *= 2    : " + num);

        num /= 4; // 6
        System.out.println("num /= 4    : " + num);

        num %= 4; // 2  (remainder of 6 / 4)
        System.out.println("num %= 4    : " + num);

        // Assignment also works for Strings using +=
        String text = "Java";
        text += " Programming";
        System.out.println("text        : " + text);
    }
}

/*
 * Output:
 * start       : 10
 * num += 5    : 15
 * num -= 3    : 12
 * num *= 2    : 24
 * num /= 4    : 6
 * num %= 4    : 2
 * text        : Java Programming
 *
 * Explanation: A compound operator like num += 5 is shorthand for
 * num = num + 5. The same idea works for -, *, /, and %, and += also
 * concatenates Strings.
 */
