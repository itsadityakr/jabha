package _3_Datatypes;

/**
 * Demonstrates the char primitive type in Java.
 *
 * A char stores a single 16-bit Unicode character. Internally it is a number
 * (the Unicode code point), which is why a char can be used in arithmetic.
 */
public class _3_3_Datatypes_Char_Code {

    public static void main(String[] args) {

        // A char literal is written using single quotes.
        char letter = 'A';

        // Every char has an underlying integer value (its Unicode/ASCII code).
        int code = letter; // 'A' is 65

        // You can also create a char directly from a number.
        char fromCode = 66; // 66 is 'B'

        // A Unicode escape sequence also produces a char ('A' is 'A').
        char unicodeChar = 'A'; // 'A'

        System.out.println("letter        : " + letter);
        System.out.println("code of 'A'   : " + code);
        System.out.println("char of 66    : " + fromCode);
        System.out.println("unicode esc   : " + unicodeChar);

        // Because a char is a number, arithmetic moves along the character set.
        char next = (char) (letter + 1); // 65 + 1 = 66 -> 'B'
        System.out.println("letter + 1    : " + next);
    }
}

/*
 * Output:
 * letter        : A
 * code of 'A'   : 65
 * char of 66    : B
 * unicode esc   : A
 * letter + 1    : B
 *
 * Explanation: A char is really a small integer (its Unicode code point).
 * 'A' = 65, 'B' = 66. Adding 1 to 'A' gives the code 66, which is 'B'. The
 * cast (char) is needed because char + int produces an int.
 */
