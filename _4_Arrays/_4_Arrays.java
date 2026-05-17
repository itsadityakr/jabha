package _4_Arrays;

/**
 * Demonstrates the declaration, initialization, and traversal of arrays in Java.
 */
public class _4_Arrays {

    public static void main(String[] args) {
        // Declaring and instantiating an integer array of size 5
        int nums[] = new int[5];

        // Using an enhanced for-loop to iterate over the array
        for (int i : nums) {
            System.out.println(i);
        }

        // Printing the object reference of the array
        System.out.println(nums + " Hi ");
    }
}
