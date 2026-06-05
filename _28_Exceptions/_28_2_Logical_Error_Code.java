// package _28_Exceptions;

// =============================================================================
// CHAPTER 28 - EXCEPTIONS (PART 1) | FILE 2 of 3 : A LOGICAL ERROR
// =============================================================================
//
// A LOGICAL error is the sneakiest kind of bug:
//   - The code is perfectly LEGAL, so it COMPILES with no complaints.
//   - The program RUNS to the end with no crash and no exception.
//   - BUT the result is WRONG, because the logic does not match your intent.
//
// Java CANNOT catch this for you. The compiler only checks grammar and types,
// not whether your maths is what you actually meant. The mistake is only found
// when a human notices the output is wrong (or a test fails).
//
// In the example below the programmer MEANT to compute 10, but wrote "1 + 0",
// which legally evaluates to 1. No error is reported anywhere - the number is
// simply not the one that was wanted. That gap between "what I meant" and "what
// I wrote" is exactly what a logical error is.
//
// =============================================================================

public class _28_2_Logical_Error_Code {
    public static void main(String[] args) {

        int result = 1 + 0;
        // Suppose you expected this to be 10, but it computes to 1 - that is a
        // LOGICAL error. The program compiles and runs fine; only the value is
        // wrong, so nothing warns you about it.
        System.out.println(result); // prints: 1
    }
}
