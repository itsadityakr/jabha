package _4_Datatypes;

/**
 * Demonstrates the String type and its most common methods.
 *
 * A String is a sequence of characters. It is a class (a reference type), and
 * Strings are immutable: once created, their contents cannot be changed. Any
 * "modifying" method actually returns a brand new String.
 */
public class _4_4_Datatypes_Strings_Code {

    public static void main(String[] args) {

        String first = "Hello";
        String second = "World";

        // Concatenation joins two strings using the + operator.
        String message = first + " " + second;

        System.out.println("message        : " + message);
        System.out.println("length         : " + message.length());
        System.out.println("upper case     : " + message.toUpperCase());
        System.out.println("lower case     : " + message.toLowerCase());
        System.out.println("char at 0      : " + message.charAt(0));
        System.out.println("index of World : " + message.indexOf("World"));
        System.out.println("substring(0,5) : " + message.substring(0, 5));
        System.out.println("replace l->L   : " + message.replace('l', 'L'));
        System.out.println("contains 'World': " + message.contains("World"));

        // Immutability check: equals() compares contents, == compares references.
        String a = "Java";
        String b = "Java";
        System.out.println("a.equals(b)    : " + a.equals(b));
    }
}

/*
 * Output:
 * message        : Hello World
 * length         : 11
 * upper case     : HELLO WORLD
 * lower case     : hello world
 * char at 0      : H
 * index of World : 6
 * substring(0,5) : Hello
 * replace l->L   : HeLLo WorLd
 * contains 'World': true
 * a.equals(b)    : true
 *
 * Explanation: String methods never change the original string; they return a
 * new one. Use equals() to compare the text of two strings, not ==.
 */
