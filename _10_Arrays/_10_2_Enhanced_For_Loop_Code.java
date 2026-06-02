package _10_Arrays;

/**
 * Demonstrates the ENHANCED FOR LOOP (for-each) over a 1D array.
 *
 * The enhanced for loop reads each element of an array in order, without an
 * index variable. Syntax:  for (type variable : array) { ... }
 */
public class _10_2_Enhanced_For_Loop_Code {

    public static void main(String[] args) {

        int[] marks = { 85, 90, 75, 60, 95 };

        // Read every element with the enhanced for loop.
        int total = 0;
        System.out.print("marks: ");
        for (int mark : marks) {   // "for each int mark in marks"
            System.out.print(mark + " ");
            total += mark;
        }
        System.out.println();
        System.out.println("total: " + total);
        System.out.println("average: " + (total / marks.length));

        // It also works on arrays of objects, like String[].
        String[] fruits = { "Apple", "Banana", "Cherry" };
        System.out.print("fruits: ");
        for (String fruit : fruits) {
            System.out.print(fruit + " ");
        }
        System.out.println();

        // NOTE: the loop variable holds a COPY of each value, so assigning to it
        // would not change the array. To modify elements by position, use a
        // normal indexed for loop instead (see _10_1_Arrays_Code style).
    }
}

/*
 * Output:
 * marks: 85 90 75 60 95
 * total: 405
 * average: 81
 * fruits: Apple Banana Cherry
 *
 * Explanation: The enhanced for loop is ideal for reading every element. The
 * loop variable holds a copy of each value, so it cannot be used to modify the
 * array elements by index -- use an indexed for loop for that.
 */
