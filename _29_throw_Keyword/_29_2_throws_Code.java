// package _29_throw_Keyword;

// =============================================================================
// CHAPTER 29 - THE throws KEYWORD (FILE 2 of 2) : DECLARING & PROPAGATING
// =============================================================================
//
// FILE 1 (_29_throw_Code) used `throw` to RAISE an exception and `try/catch` to
// HANDLE it on the spot. This file shows the OTHER strategy: instead of handling
// an exception, a method can DECLARE it with `throws` and pass the responsibility
// to whoever called it. The exception then travels UP the call stack until some
// method catches it - or, if nobody does, the JVM stops the program.
//
//   throw   -> an ACTION inside a body: "raise this exception now."   (FILE 1)
//   throws  -> a DECLARATION in the signature: "this method might      (THIS FILE)
//              throw these checked exceptions; caller, you deal with it."
//
// WHY IS `throws` NEEDED HERE?
//   Class.forName("Calc") looks up a class by name at runtime. If no such class
//   exists, it throws ClassNotFoundException - a CHECKED exception. Checked means
//   the compiler FORCES every method in the chain to either catch it or declare
//   `throws ClassNotFoundException`. Here we declare it all the way up so the
//   exception propagates: show()  ->  main()  ->  JVM.
//
// COMBINED METHODS (how propagation chains together):
//   1) A.show()  calls Class.forName("Calc")  and DECLARES `throws`, so it does
//      NOT catch the exception - it lets it escape.
//   2) main()    calls a.show()               and ALSO declares `throws`, so it
//      does not catch it either - it lets it escape to the JVM.
//   3) the JVM   receives the uncaught exception, prints a stack trace, and ends
//      the program.
//
// THE static {} BLOCK:
//   A `static {}` block runs ONCE, when the class is first loaded/initialized -
//   which happens just BEFORE main() starts. That is why "Class Loaded" prints
//   first, before any exception appears.
// =============================================================================

// A helper class whose method propagates (does NOT catch) a checked exception.
class A {
    // `throws ClassNotFoundException` = "I will not handle this; my caller must."
    public void show() throws ClassNotFoundException {
        // Class.forName looks up a class named "Calc" at runtime. There is no
        // such class, so this throws ClassNotFoundException. Because show()
        // declares `throws`, the exception is NOT caught here - it escapes to
        // whoever called show() (that is main()).
        Class.forName("Calc");

        // Unreachable: once forName throws above, control leaves this method
        // immediately, so this line never runs.
        System.out.println("show() finished normally");
    }
}

public class _29_2_throws_Code {

    // Runs once when this class is loaded, BEFORE main() executes.
    static {
        System.out.println("Class Loaded");
    }

    // main() ALSO declares `throws ClassNotFoundException` instead of using
    // try/catch, so the exception from show() keeps propagating - this time all
    // the way out to the JVM, which then ends the program with a stack trace.
    public static void main(String[] args) throws ClassNotFoundException {
        A a = new A();
        a.show(); // not wrapped in try/catch -> the exception propagates upward

        // Unreachable: show() throws, main() does not catch it, so execution
        // never reaches this line.
        System.out.println("main() finished normally");
    }
}

// -----------------------------------------------------------------------------
// throw  vs  throws  (the one-line reminder)
// -----------------------------------------------------------------------------
//   throw  new X("msg");                 // a STATEMENT - raises an exception now
//   void m() throws X { ... }            // a CLAUSE   - declares m() may throw X
//
// HANDLE (FILE 1) vs DECLARE (THIS FILE):
//   - try/catch  : stop the exception here and recover.
//   - throws      : do not stop it here; let the caller decide.
// -----------------------------------------------------------------------------
