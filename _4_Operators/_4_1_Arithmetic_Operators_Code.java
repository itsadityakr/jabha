package _4_Operators;

/**
 * Demonstrates the arithmetic operators in Java.
 *
 * Arithmetic operators perform basic mathematical calculations:
 * + (add), - (subtract), * (multiply), / (divide), % (modulus/remainder).
 */
public class _4_1_Arithmetic_Operators_Code {

    public static void main(String[] args) {

        int a = 10;
        int b = 3;

        System.out.println("a + b = " + (a + b)); // addition
        System.out.println("a - b = " + (a - b)); // subtraction
        System.out.println("a * b = " + (a * b)); // multiplication
        System.out.println("a / b = " + (a / b)); // integer division
        System.out.println("a % b = " + (a % b)); // remainder

        // Integer division drops the decimal part.
        // To keep the decimals, use a floating-point type.
        double x = 10;
        double y = 3;
        System.out.println("x / y = " + (x / y)); // real division

        // Unary operators: increment (++) and decrement (--).
        int count = 5;
        count++; // now 6
        count--; // back to 5
        System.out.println("count = " + count);

        // Pre vs post increment.
        int p = 5;
        System.out.println("p++ gives   : " + (p++)); // uses 5, then becomes 6
        System.out.println("now p is    : " + p);     // 6
        System.out.println("++p gives   : " + (++p)); // becomes 7, then uses 7
    }
}

/*
 * Output:
 * a + b = 13
 * a - b = 7
 * a * b = 30
 * a / b = 3
 * a % b = 1
 * x / y = 3.3333333333333335
 * count = 5
 * p++ gives   : 5
 * now p is    : 6
 * ++p gives   : 7
 *
 * Explanation: 10 / 3 is 3 with integer division because the decimal part is
 * dropped. 10 % 3 is 1 (the remainder). Using double keeps the decimals.
 * p++ returns the old value first, then increases; ++p increases first.
 */
