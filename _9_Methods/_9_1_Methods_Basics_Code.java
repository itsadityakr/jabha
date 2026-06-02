package _9_Methods;

/**
 * Demonstrates the basics of methods in Java:
 *   - declaring a method (name, parameters, return type)
 *   - calling a method
 *   - return values vs void
 *   - static methods (callable without an object)
 *
 * A method is a named block of code that performs a task. Methods let you
 * write logic once and reuse it many times.
 */
public class _9_1_Methods_Basics_Code {

    // A method that returns a value. Return type is int.
    static int add(int num1, int num2) {
        return num1 + num2;
    }

    // A method that returns nothing. Return type is void.
    static void greet(String name) {
        System.out.println("Hello, " + name + "!");
    }

    // A method with no parameters that returns a value.
    static double pi() {
        return 3.14159;
    }

    public static void main(String[] args) {

        // Call add() and use its returned value.
        int sum = add(5, 3);
        System.out.println("add(5, 3)  = " + sum);

        // Call a void method (it acts, it does not return a value).
        greet("Aditya");

        // Call a no-argument method.
        System.out.println("pi()       = " + pi());

        // Methods can be called as part of a larger expression.
        System.out.println("add nested = " + add(add(1, 2), 4));
    }
}

/*
 * Output:
 * add(5, 3)  = 8
 * Hello, Aditya!
 * pi()       = 3.14159
 * add nested = 7
 *
 * Explanation: add() returns an int that we store or print. greet() returns
 * void, so it only performs an action. Methods can be combined, like
 * add(add(1, 2), 4) which is add(3, 4) = 7.
 */
