package _3_Datatypes;

/**
 * Demonstrates the eight primitive data types in Java.
 *
 * Primitive types are the most basic data types built into the language.
 * They store simple values directly in memory (on the stack for local
 * variables) and are NOT objects.
 */
public class _3_1_Datatypes_Primitive_Code {

    public static void main(String[] args) {

        // 1. byte  -> 1 byte  (8 bits)  range: -128 to 127
        byte myByte = 100;

        // 2. short -> 2 bytes (16 bits) range: -32,768 to 32,767
        short myShort = 20000;

        // 3. int   -> 4 bytes (32 bits) range: about -2.1 billion to 2.1 billion
        int myInt = 100000;

        // 4. long  -> 8 bytes (64 bits) very large whole numbers
        //    The 'L' suffix tells Java this literal is a long, not an int.
        long myLong = 15000000000L;

        // 5. float -> 4 bytes, single precision decimal
        //    The 'f' suffix is required for float literals.
        float myFloat = 5.75f;

        // 6. double -> 8 bytes, double precision decimal (default for decimals)
        double myDouble = 19.99;

        // 7. char -> 2 bytes, a single 16-bit Unicode character (in single quotes)
        char myChar = 'A';

        // 8. boolean -> represents true or false only
        boolean myBoolean = true;

        System.out.println("byte    : " + myByte);
        System.out.println("short   : " + myShort);
        System.out.println("int     : " + myInt);
        System.out.println("long    : " + myLong);
        System.out.println("float   : " + myFloat);
        System.out.println("double  : " + myDouble);
        System.out.println("char    : " + myChar);
        System.out.println("boolean : " + myBoolean);
    }
}

/*
 * Output:
 * byte    : 100
 * short   : 20000
 * int     : 100000
 * long    : 15000000000
 * float   : 5.75
 * double  : 19.99
 * char    : A
 * boolean : true
 *
 * Explanation: Each primitive holds a single value of a fixed size. The size
 * of a type decides the range of values it can store.
 */
