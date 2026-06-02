package _3_Datatypes;

/**
 * Demonstrates literals in Java.
 *
 * A literal is a fixed value written directly in the source code, such as 100,
 * 3.14, 'A', true, or "Hello". This file shows the different ways literals can
 * be written.
 */
public class _3_5_Datatypes_Literals_Code {

    public static void main(String[] args) {

        // ---- Integer literals in different number systems ----
        int decimal = 100;       // base 10 (normal)
        int binary = 0b1100100;  // base 2,  prefix 0b  -> 100
        int octal = 0144;        // base 8,  prefix 0   -> 100
        int hex = 0x64;          // base 16, prefix 0x  -> 100

        // Underscores can be used to group digits for readability.
        int million = 1_000_000;

        // ---- Floating-point literals ----
        double d = 3.14;
        double scientific = 1.5e3; // 1.5 x 10^3 = 1500.0
        float f = 2.5f;            // 'f' suffix makes it a float
        long big = 10000000000L;   // 'L' suffix makes it a long

        // ---- Character and string literals ----
        char ch = 'Z';
        char newline = '\n';       // escape sequence
        String text = "Literals in Java";

        // ---- Boolean literals ----
        boolean flag = true;

        System.out.println("decimal    : " + decimal);
        System.out.println("binary     : " + binary);
        System.out.println("octal      : " + octal);
        System.out.println("hex        : " + hex);
        System.out.println("million    : " + million);
        System.out.println("double     : " + d);
        System.out.println("scientific : " + scientific);
        System.out.println("float      : " + f);
        System.out.println("long       : " + big);
        System.out.println("char       : " + ch);
        System.out.println("string     : " + text);
        System.out.println("boolean    : " + flag);
        System.out.print("escape used: line1" + newline + "line2");
    }
}

/*
 * Output:
 * decimal    : 100
 * binary     : 100
 * octal      : 100
 * hex        : 100
 * million    : 1000000
 * double     : 3.14
 * scientific : 1500.0
 * float      : 2.5
 * long       : 10000000000
 * char       : Z
 * string     : Literals in Java
 * boolean    : true
 * escape used: line1
 * line2
 *
 * Explanation: 100, 0b1100100, 0144, and 0x64 are all the value 100 written in
 * different number systems. Suffixes (L, f) and prefixes (0b, 0, 0x) tell the
 * compiler how to read a literal. Underscores are ignored by the compiler.
 */
