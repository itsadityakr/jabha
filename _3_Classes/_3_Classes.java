package _3_Classes;

/**
 * A simple Calculator class to demonstrate object-oriented programming.
 */
class Calculator {

    int a;

    /**
     * Adds two integers.
     * @param num1 First number
     * @param num2 Second number
     * @return The sum of num1 and num2
     */
    public int add(int num1, int num2) {
        return num1 + num2;
    }
}

/**
 * Main class to demonstrate the usage of the Calculator class.
 */
public class _3_Classes {

    public static void main(String[] args) {
        int n1 = 2,
            n2 = 4;

        // Creating an object of the Calculator class
        Calculator calc = new Calculator();

        // Calling the add method
        int add = calc.add(n1, n2);

        System.out.println(add);
    }
}

/*
 * Output:
 * 6
 *
 * Explanation: calc.add(2, 4) returns 2 + 4 = 6.
 */
