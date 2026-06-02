package _7_Loops;

/**
 * Demonstrates the three looping constructs in Java:
 *   - while loop      (check condition, then run)
 *   - do-while loop   (run once, then check condition)
 *   - for loop        (counter-controlled loop)
 * plus the enhanced for-each loop and the break / continue keywords.
 *
 * A loop repeats a block of code while a condition stays true. This avoids
 * writing the same statement over and over.
 */
public class _7_While_DoWhile_For_Loops_Code {

    public static void main(String[] args) {

        // ---- 1. while loop ----
        // The condition is checked BEFORE each iteration. If it is false at the
        // start, the body may run zero times.
        System.out.print("while  : ");
        int i = 1;
        while (i <= 5) {
            System.out.print(i + " ");
            i++; // must change the variable, or the loop never ends
        }
        System.out.println();

        // ---- 2. do-while loop ----
        // The body runs FIRST, then the condition is checked. So it always runs
        // at least once, even if the condition is false.
        System.out.print("do-while: ");
        int j = 1;
        do {
            System.out.print(j + " ");
            j++;
        } while (j <= 5);
        System.out.println();

        // ---- 3. for loop ----
        // All three parts live on one line: initialise; condition; update.
        System.out.print("for    : ");
        for (int k = 1; k <= 5; k++) {
            System.out.print(k + " ");
        }
        System.out.println();

        // ---- 4. enhanced for-each loop ----
        // Iterates over every element of an array/collection, no counter needed.
        System.out.print("for-each: ");
        int[] nums = { 10, 20, 30 };
        for (int n : nums) {
            System.out.print(n + " ");
        }
        System.out.println();

        // ---- 5. break: exit the loop early ----
        System.out.print("break  : ");
        for (int b = 1; b <= 10; b++) {
            if (b == 4) {
                break; // stop the loop completely when b reaches 4
            }
            System.out.print(b + " ");
        }
        System.out.println();

        // ---- 6. continue: skip the rest of THIS iteration ----
        System.out.print("continue: ");
        for (int c = 1; c <= 6; c++) {
            if (c % 2 == 0) {
                continue; // skip even numbers
            }
            System.out.print(c + " ");
        }
        System.out.println();

        // ---- 7. nested loop: a loop inside a loop ----
        System.out.println("nested :");
        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 3; col++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }
}

/*
 * Output:
 * while  : 1 2 3 4 5
 * do-while: 1 2 3 4 5
 * for    : 1 2 3 4 5
 * for-each: 10 20 30
 * break  : 1 2 3
 * continue: 1 3 5
 * nested :
 * * * *
 * * * *
 * * * *
 *
 * Explanation: while/for check the condition before running; do-while runs once
 * before checking. break leaves the loop entirely; continue skips to the next
 * iteration. A nested loop runs the inner loop fully for each outer step.
 */
