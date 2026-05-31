package _13_Casting;

// Type casting (for PRIMITIVES) means converting a value of one primitive type
// into another. There are two kinds:
//
//   1. Widening  (implicit / automatic): a smaller type fits into a larger one,
//      so Java does it for you. No data is lost.
//          byte -> short -> int -> long -> float -> double
//                          char -> int
//
//   2. Narrowing (explicit / manual): a larger type is forced into a smaller
//      one, so YOU must write a cast (type). Data may be lost (truncation or
//      overflow), which is why the compiler makes you ask for it explicitly.
public class _13_1_TypeCasting_Code {
    public static void main(String[] args) {

        // ---------- Widening (implicit) ----------
        // Each step moves to a type that can hold every value of the previous
        // one, so no cast is needed and no information is lost.
        int a = 10;
        long b = a;        // int  -> long   (automatic)
        double c = b;      // long -> double (automatic)

        System.out.println("Widening:");
        System.out.println("int a    = " + a); // 10
        System.out.println("long b   = " + b); // 10
        System.out.println("double c = " + c); // 10.0  (note the .0)

        // ---------- Narrowing (explicit) ----------
        // Going from a larger type to a smaller one requires an explicit cast,
        // and may lose data.

        // double -> int : the fractional part is DROPPED (not rounded).
        double d = 9.99;
        int e = (int) d;  // becomes 9, the .99 is truncated
        System.out.println("\nNarrowing (double -> int):");
        System.out.println("double d = " + d); // 9.99
        System.out.println("int e    = " + e); // 9

        // int -> byte : a byte only holds -128..127, so a bigger value OVERFLOWS
        // by wrapping around (257 wraps to 1).
        int big = 257;
        byte sb = (byte) big;
        System.out.println("\nNarrowing (int -> byte, overflow):");
        System.out.println("int big = " + big); // 257
        System.out.println("byte sb = " + sb);  // 1

        // int -> char : an int code point becomes the matching character.
        int code = 65;
        char ch = (char) code; // 65 is the Unicode/ASCII code for 'A'
        System.out.println("\nNarrowing (int -> char):");
        System.out.println("int code = " + code); // 65
        System.out.println("char ch  = " + ch);   // A
    }
}

/*
 * Output:
 * Widening:
 * int a    = 10
 * long b   = 10
 * double c = 10.0
 *
 * Narrowing (double -> int):
 * double d = 9.99
 * int e    = 9
 *
 * Narrowing (int -> byte, overflow):
 * int big = 257
 * byte sb = 1
 *
 * Narrowing (int -> char):
 * int code = 65
 * char ch  = A
 *
 * Explanation:
 * - Widening (int -> long -> double) happens automatically; no value is lost.
 * - Narrowing needs an explicit (type) cast because data may be lost:
 *     - double -> int drops the fraction (9.99 -> 9), it does NOT round.
 *     - int -> byte overflows because 257 does not fit in -128..127 (wraps to 1).
 *     - int -> char maps a numeric code to its character (65 -> 'A').
 */
