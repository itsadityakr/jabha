package _10_Arrays;

/**
 * Demonstrates a TWO-DIMENSIONAL (2D) ARRAY in Java.
 *
 * A 2D array is an "array of arrays" — a grid with rows and columns, useful for
 * matrices and tables. Element access uses two indices: matrix[row][col].
 */
public class _10_3_TwoD_Array_Code {

    public static void main(String[] args) {

        // A 3-row, 3-column grid, initialised with values.
        int[][] matrix = {
            { 1, 2, 3 },
            { 4, 5, 6 },
            { 7, 8, 9 }
        };

        // matrix.length      -> number of rows (3)
        // matrix[i].length   -> number of columns in row i (3)
        System.out.println("rows: " + matrix.length + ", cols: " + matrix[0].length);

        // Access a single element: row 1, column 2 (zero-based) -> 6
        System.out.println("matrix[1][2] = " + matrix[1][2]);

        // Print the whole grid with nested loops.
        System.out.println("grid:");
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println(); // new line after each row
        }

        // You can also create an empty 2D array and fill it.
        int[][] table = new int[2][3]; // 2 rows, 3 columns, all default 0
        table[0][0] = 10;
        table[1][2] = 99;
        System.out.println("table[0][0] = " + table[0][0] + ", table[1][2] = " + table[1][2]);
    }
}

/*
 * Output:
 * rows: 3, cols: 3
 * matrix[1][2] = 6
 * grid:
 * 1 2 3
 * 4 5 6
 * 7 8 9
 * table[0][0] = 10, table[1][2] = 99
 *
 * Explanation: A 2D array is indexed as [row][col]. matrix[1][2] is row index 1
 * (second row) and column index 2 (third value), which is 6. Nested loops walk
 * every row and column to print the grid.
 */
