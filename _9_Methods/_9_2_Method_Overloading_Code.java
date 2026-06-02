package _9_Methods;

/**
 * Demonstrates METHOD OVERLOADING in Java.
 *
 * Overloading means having several methods with the SAME name but DIFFERENT
 * parameter lists (different number, types, or order of parameters). Java
 * decides which one to call based on the arguments you pass. This is resolved
 * at COMPILE time and is a form of compile-time (static) polymorphism.
 */
public class _9_2_Method_Overloading_Code {

    // Same name 'add', different parameter lists:

    // 1) two ints
    static int add(int a, int b) {
        return a + b;
    }

    // 2) three ints (different NUMBER of parameters)
    static int add(int a, int b, int c) {
        return a + b + c;
    }

    // 3) two doubles (different TYPE of parameters)
    static double add(double a, double b) {
        return a + b;
    }

    // 4) an int and a String (different ORDER/types)
    static String add(int a, String b) {
        return a + b; // number is converted to text and joined
    }

    public static void main(String[] args) {
        System.out.println("add(2, 3)        = " + add(2, 3));
        System.out.println("add(2, 3, 4)     = " + add(2, 3, 4));
        System.out.println("add(2.5, 3.5)    = " + add(2.5, 3.5));
        System.out.println("add(5, \"th\")     = " + add(5, "th"));
    }
}

/*
 * Output:
 * add(2, 3)        = 5
 * add(2, 3, 4)     = 9
 * add(2.5, 3.5)    = 6.0
 * add(5, "th")     = 5th
 *
 * Explanation: All four methods are named 'add' but have different parameter
 * lists, so the compiler picks the matching one based on the arguments. NOTE:
 * the return type alone cannot distinguish overloaded methods -- the parameter
 * lists must differ.
 */
