package _14_WrapperClasses;

// A WRAPPER CLASS wraps (boxes) a primitive value inside an object. Every
// primitive type has a matching wrapper class in java.lang:
//
//     byte    -> Byte        short  -> Short
//     int     -> Integer     long   -> Long
//     float   -> Float       double -> Double
//     char    -> Character   boolean-> Boolean
//
// Why do we need them?
//   - Collections (ArrayList, HashMap, ...) store OBJECTS, not primitives, so a
//     list of numbers is really a list of Integer/Double objects.
//   - Wrappers add useful static helpers (Integer.parseInt, Integer.MAX_VALUE,
//     Integer.valueOf, ...) and let a value be null.
//
// Boxing   : wrapping a primitive into its wrapper object (int -> Integer).
// Unboxing : extracting the primitive back out (Integer -> int).
// Java does both automatically since Java 5 ("autoboxing" / "auto-unboxing").
public class _14_WrapperClasses_Code {
    public static void main(String[] args) {

        // ---------- Boxing & Autoboxing ----------
        int num = 7; // a plain primitive int

        // Integer num1 = new Integer(8); // manual boxing -- DEPRECATED, don't use
        Integer num1 = 7; // Autoboxing: int 7 is wrapped into an Integer object

        // ---------- Unboxing & Auto-unboxing ----------
        // int num2 = num1.intValue(); // manual unboxing using intValue()
        int num2 = num1; // Auto-unboxing: the Integer is unwrapped back to int

        System.out.println("num  (primitive int)      = " + num);
        System.out.println("num2 (auto-unboxed)       = " + num2);

        // ---------- String -> int with Integer.parseInt ----------
        // parseInt() returns the PRIMITIVE int form of a numeric string.
        String str = "10";
        int num3 = Integer.parseInt(str);
        System.out.println("parseInt(\"10\")            = " + num3);

        // ---------- Integer -> String with toString() ----------
        // A wrapper object can turn itself into its text form.
        Integer num4 = 10;
        String s2 = num4.toString();
        System.out.println("Integer(10).toString()    = " + s2);

        // ---------- String -> Integer with Integer.valueOf ----------
        // valueOf() returns a wrapper OBJECT (Integer), not a primitive.
        // (parseInt -> primitive int, valueOf -> Integer object.)
        String str3 = "10";
        Integer num5 = Integer.valueOf(str3);
        System.out.println("valueOf(\"10\")             = " + num5);
    }
}

/*
 * Output:
 * num  (primitive int)      = 7
 * num2 (auto-unboxed)       = 7
 * parseInt("10")            = 10
 * Integer(10).toString()    = 10
 * valueOf("10")             = 10
 *
 * Explanation:
 * - Integer num1 = 7;       autoboxing wraps the int into an Integer object.
 * - int num2 = num1;        auto-unboxing unwraps the Integer back into an int.
 * - Integer.parseInt(str)   parses a String into a primitive int.
 * - num4.toString()         converts an Integer object into its String form.
 * - Integer.valueOf(str)    parses a String into an Integer OBJECT (boxed).
 *
 * Rule of thumb:
 * - Use parseInt when you want a primitive int.
 * - Use valueOf  when you want an Integer object.
 * - Avoid `new Integer(...)`; it is deprecated -- rely on autoboxing/valueOf.
 */
