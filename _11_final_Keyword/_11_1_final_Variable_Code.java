package _11_final_Keyword;

final class Base {
    public final void show() {
        System.out.println("Base show");
    }
}

/*
 * class Derived extends Base {
 * // ERROR: Cannot override final method
 * public void show() {
 * System.out.println("Derived show");
 * }
 * }
 */

public class _11_1_final_Variable_Code {
    final int MAX = 100; // Compile-time constant

    public static void main(String[] args) {
        final int AGE = 20; // Reusable constant

        // AGE = 30; // ERROR: final variable cannot be reassigned
        // MAX = 50; // ERROR: final variable cannot be reassigned

        // Example: Final variable in a method
        final double PI = 3.14159;

        System.out.println("Value of PI: " + PI);

        // Final variables can be used in expressions
        double circumference = 2 * PI * 5;
        System.out.println("Circumference: " + circumference);

        // Example: Final variables with object references
        final StringBuilder sb = new StringBuilder("Hello");
        sb.append(" World"); // OK: modifying content
        System.out.println(sb);

        // sb = new StringBuilder("Goodbye"); // ERROR: final reference cannot be
        // reassigned
    }
}

/*
 * Output:
 * Value of PI: 3.14159
 * Circumference: 31.4159
 * Hello World
 *
 * Explanation: final variables (PI) are read normally and used in expressions.
 * The final StringBuilder reference cannot be reassigned, but its contents can
 * still be modified, so append(" World") produces "Hello World".
 */
