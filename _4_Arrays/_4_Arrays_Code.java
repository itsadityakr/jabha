package _4_Arrays;

/**
 * Demonstrates the declaration, initialization, and traversal of arrays in Java.
 */
public class _4_Arrays_Code {

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

/*
 * Output:
 * 0
 * 0
 * 0
 * 0
 * 0
 * [I@1b6d3586 Hi
 *
 * Explanation: A new int[5] is filled with default values (0). Printing the
 * array reference itself shows its type signature "[I" plus an @ and a hash
 * code (the hash varies on each run, so "1b6d3586" is just an example).
 */
