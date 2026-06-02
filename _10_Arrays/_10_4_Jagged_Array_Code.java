package _10_Arrays;

/**
 * Demonstrates a JAGGED ARRAY in Java.
 *
 * A jagged array is a 2D array where each row can have a DIFFERENT number of
 * columns. You create the rows first (leaving the column size empty), then give
 * each row its own length.
 */
public class _10_4_Jagged_Array_Code {

    public static void main(String[] args) {

        // Declare 3 rows but do NOT fix the number of columns yet.
        int[][] jagged = new int[3][];

        // Give each row a different length.
        jagged[0] = new int[2]; // row 0 has 2 columns
        jagged[1] = new int[4]; // row 1 has 4 columns
        jagged[2] = new int[1]; // row 2 has 1 column

        // Fill each row with some values.
        for (int row = 0; row < jagged.length; row++) {
            for (int col = 0; col < jagged[row].length; col++) {
                jagged[row][col] = (row + 1) * (col + 1);
            }
        }

        // Print the jagged structure. Note each row's length differs.
        System.out.println("jagged array:");
        for (int row = 0; row < jagged.length; row++) {
            System.out.print("row " + row + " (len " + jagged[row].length + "): ");
            for (int value : jagged[row]) {
                System.out.print(value + " ");
            }
            System.out.println();
        }

        // You can also create a jagged array directly with values.
        int[][] direct = {
            { 1 },
            { 1, 2, 3 },
            { 1, 2 }
        };
        System.out.println("direct row 1 length: " + direct[1].length);
    }
}

/*
 * Output:
 * jagged array:
 * row 0 (len 2): 1 2
 * row 1 (len 4): 2 4 6 8
 * row 2 (len 1): 3
 * direct row 1 length: 3
 *
 * Explanation: Unlike a rectangular 2D array, each row of a jagged array has its
 * own length. We set new int[2], new int[4], new int[1] for the three rows, so
 * jagged[row].length differs per row.
 */
