package _3_Datatypes;

/**
 * Demonstrates non-primitive (reference) data types in Java.
 *
 * Non-primitive types do NOT store the value directly. Instead they store a
 * reference (the memory address) that points to an object on the heap.
 * Examples: String, arrays, classes, and interfaces.
 */
public class _3_2_Datatypes_Non_Primitive_Code {

    public static void main(String[] args) {

        // String -> a sequence of characters. It is a class, not a primitive.
        String name = "Java";

        // Array -> a container that holds multiple values of the same type.
        int[] numbers = { 10, 20, 30 };

        // A reference type can be null, meaning "points to no object".
        String empty = null;

        System.out.println("String        : " + name);
        System.out.println("Array length  : " + numbers.length);
        System.out.println("Array[0]      : " + numbers[0]);
        System.out.println("Null reference: " + empty);

        // Methods can be called on reference types because they are objects.
        System.out.println("Uppercase     : " + name.toUpperCase());
        System.out.println("Char count    : " + name.length());
    }
}

/*
 * Output:
 * String        : Java
 * Array length  : 3
 * Array[0]      : 10
 * Null reference: null
 * Uppercase     : JAVA
 * Char count    : 4
 *
 * Explanation: A non-primitive variable holds a reference to an object on the
 * heap. Because it is an object, it has methods (like toUpperCase) and can be
 * null. A primitive can never be null.
 */
