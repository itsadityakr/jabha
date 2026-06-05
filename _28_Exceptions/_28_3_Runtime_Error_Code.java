// package _28_Exceptions;

// =============================================================================
// CHAPTER 28 - EXCEPTIONS (PART 1) | FILE 3 of 3 : A RUNTIME ERROR
// =============================================================================
//
// A RUNTIME error happens WHILE the program is running: the code compiled fine,
// it started executing, and then something illegal occurred and an EXCEPTION was
// thrown (for example, dividing an int by zero). If nothing handles it, the JVM
// prints a stack trace and the program stops at that exact spot.
//
// EXCEPTION HANDLING is how we deal with runtime errors gracefully. We wrap the
// risky code in a try{} block; if it throws, control jumps to a matching
// catch{} block instead of crashing the whole program. This lets the program
// recover and keep running.
//
// Think of statements as two kinds:
//   - NORMAL statements  : a failure can be caught and the program continues.
//   - CRITICAL statements: the failing line itself is abandoned - the rest of
//                          the try{} block after the throw is skipped, and
//                          execution resumes in the catch{} block.
// In short: an exception stops the BLOCK where the error occurs, not (when it
// is caught) the whole program.
//
// =============================================================================

public class _28_3_Runtime_Error_Code {
    public static void main(String[] args) {

        int i = 9;
        int j = 0;
        int result = 0;
        // result = i / j; // Left uncaught, this line would throw at runtime and
        //                  // crash the program (ArithmeticException: / by zero).

        // Using a try-catch block so the program does NOT crash:
        try {
            result = i / j; // 9 / 0 -> throws ArithmeticException here
        } catch (Exception e) { // the ArithmeticException class could be used here too
            System.out.println("Exception: " + e.getMessage());
        }

        // Maintain the hierarchy (most specific first, most general last) when
        // using several catch blocks, like this:
        //  catch (ArithmeticException e) {              // most specific
        //     System.out.println("Exception: " + e.getMessage());
        // }
        //  catch (ArrayIndexOutOfBoundsException e) {   // another specific type
        //     System.out.println("Exception: " + e.getMessage());
        // }
        //  catch (Exception e) {                        // most general - catches the rest
        //     System.out.println("Exception: " + e.getMessage());
        // }

        // Exception is the PARENT class of all exception classes, so a single
        // catch (Exception e) can catch all of them. Because it is the most
        // general, it must always come LAST - a more specific catch after it
        // would be unreachable and cause a compile error.

        System.out.println("Result is : " + result); // prints: Result is : 0 (result was never reassigned)
        // IMPORTANT: because the exception above was CAUGHT, the program did NOT
        // crash - execution simply continued after the try-catch. So this line
        // AND the line below both run and print normally.
        // (If there were NO try-catch, the throw would crash the program and
        //  neither of these two println lines would ever run.)
        System.out.println("Worked normally"); // prints: Worked normally
    }
}

// =============================================================================
// THE EXCEPTION / ERROR HIERARCHY (everything descends from Throwable)
// =============================================================================
//
// Object
//  └─ Throwable                         (the root of everything you can throw/catch)
//      ├─ Error                          serious problems you normally do NOT catch
//      │   ├─ ThreadDeath                (deprecated)
//      │   ├─ IOError
//      │   └─ VirtualMachineError
//      │       ├─ OutOfMemoryError
//      │       └─ StackOverflowError
//      │
//      └─ Exception                      problems your program CAN and SHOULD handle
//          ├─ RuntimeException           (UNCHECKED - not forced by the compiler)
//          │   ├─ ArithmeticException
//          │   ├─ ArrayIndexOutOfBoundsException
//          │   ├─ NumberFormatException
//          │   ├─ StringIndexOutOfBoundsException
//          │   └─ NullPointerException
//          │
//          ├─ IOException                (CHECKED - compiler forces you to handle it)
//          │   └─ FileNotFoundException
//          └─ SQLException               (CHECKED - e.g. a database/connection failure)
//
// Quick rule:
//   - Error            -> something went badly wrong in the JVM; let it crash.
//   - RuntimeException -> usually a bug in your code; UNCHECKED (no throws needed).
//   - Other Exceptions -> external/expected failures; CHECKED (must be handled
//                         with try-catch or declared with "throws").
// =============================================================================
