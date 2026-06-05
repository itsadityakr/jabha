// package _28_Exceptions;

// =============================================================================
// CHAPTER 28 - EXCEPTIONS (PART 1) | FILE 1 of 3 : THE THREE KINDS OF ERRORS
// =============================================================================
//
// Java programs can fail in three different ways, and telling them apart is the
// whole point of this chapter:
//
//   1) COMPILE-TIME error - the COMPILER (javac) refuses to build the program.
//                           No .class file is produced, so the program never
//                           even starts. These are usually SYNTAX or TYPE
//                           mistakes (a missing ';', a wrong type, an unknown
//                           variable). You must fix them before anything runs.
//
//   2) LOGICAL error      - the program compiles AND runs, but produces the
//                           WRONG answer. Java cannot detect these for you,
//                           because the code is legal - only your intent is off.
//                           (See FILE 2.)
//
//   3) RUNTIME error       - the program compiles fine, but blows up WHILE it is
//                           running and throws an exception. (See FILE 3.)
//
// COMMON MISCONCEPTION (worth knowing): you might expect the line below,
//   int result = 1 / 0;
// to be a COMPILE-TIME error. It is NOT. javac happily compiles it, produces a
// .class file, and the failure only happens WHEN THE PROGRAM RUNS:
//
//   Exception in thread "main" java.lang.ArithmeticException: / by zero
//
// So integer "divide by zero" is actually a RUNTIME error (an exception), the
// same family we learn to handle with try/catch in FILE 3 - not a compile-time
// error at all. It is kept here on purpose so you can see the difference for
// yourself: compile it (no error), then run it (it crashes).
//
// What a REAL compile-time error looks like (each line below WOULD stop javac
// if you uncommented it - they are left commented so this file still builds):
//
//   int x = "hello";        // type mismatch: String cannot become int
//   int y = 5               // missing ';' at the end of the statement
//   System.out.println(z);  // 'z' was never declared
//   notAMethod();           // calling a method that does not exist
//
// =============================================================================

public class _28_1_Compile_Time_Error_Code {
    public static void main(String[] args) {

        int result = 1 / 0;
        // Looks like a compile-time error, but it is NOT.
        // - javac COMPILES this line successfully (a .class file IS produced).
        // - The crash happens at RUNTIME:
        //       Exception in thread "main" java.lang.ArithmeticException: / by zero
        // The program stops here, so the println below never runs.
        System.out.println(result);
    }
}
