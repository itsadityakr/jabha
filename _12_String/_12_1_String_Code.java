package _12_String;

/**
 * Demonstrates the String class in Java and its key idea: IMMUTABILITY.
 *
 * A String is a sequence of characters. Strings are immutable — every method
 * that looks like it changes a String actually returns a NEW String, leaving
 * the original untouched.
 */
public class _12_1_String_Code {

    public static void main(String[] args) {

        // Two ways to create a String.
        String a = "Hello";              // string literal (goes to the String Pool)
        String b = new String("Hello");  // explicit object on the heap

        // == compares references; equals() compares the actual text.
        System.out.println("a == b        : " + (a == b));        // false
        System.out.println("a.equals(b)   : " + a.equals(b));     // true

        String text = "Java Programming";

        // Common String methods (all return new values; text is unchanged).
        System.out.println("length        : " + text.length());
        System.out.println("toUpperCase   : " + text.toUpperCase());
        System.out.println("toLowerCase   : " + text.toLowerCase());
        System.out.println("charAt(5)     : " + text.charAt(5));
        System.out.println("indexOf 'Prog': " + text.indexOf("Prog"));
        System.out.println("substring(0,4): " + text.substring(0, 4));
        System.out.println("replace a->A  : " + text.replace('a', 'A'));
        System.out.println("contains Java : " + text.contains("Java"));
        System.out.println("trim          : '" + "  spaced  ".trim() + "'");

        // Proof of immutability: text is still the original after all the calls.
        System.out.println("original text : " + text);

        // Concatenation creates yet another new String.
        String greet = "Hi, " + "there";
        System.out.println("concatenated  : " + greet);
    }
}

/*
 * Output:
 * a == b        : false
 * a.equals(b)   : true
 * length        : 16
 * toUpperCase   : JAVA PROGRAMMING
 * toLowerCase   : java programming
 * charAt(5)     : P
 * indexOf 'Prog': 5
 * substring(0,4): Java
 * replace a->A  : JAvA ProgrAmming
 * contains Java : true
 * trim          : 'spaced'
 * original text : Java Programming
 *
 * Explanation: Methods like toUpperCase() and replace() return brand-new
 * Strings; "original text" proves the source String never changed. Use
 * equals() (not ==) to compare text.
 */
