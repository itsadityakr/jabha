// package _29_throw_Keyword;

// =============================================================================
// CHAPTER 29 - THE throw KEYWORD (+ CUSTOM EXCEPTIONS)
// =============================================================================
//
// In Chapter 28 we CAUGHT exceptions that Java threw for us (like dividing by
// zero). This chapter is the other side of the coin: how YOU throw an exception
// on purpose, and how to build your OWN exception type.
//
//   throw  -> the keyword that actually raises an exception object right now.
//             Syntax: throw new SomeException("message");
//             As soon as it runs, normal flow stops and Java starts looking for
//             a matching catch{} block.
//
// A CUSTOM EXCEPTION is just a class that EXTENDS an existing exception class.
// By extending Exception (a CHECKED exception) we get a brand-new exception type
// with our own name, which makes error messages and catch blocks clearer.
// =============================================================================

// Our own exception type. It "is-a" Exception because it extends Exception, so
// it can be thrown and caught exactly like the built-in ones.
class CustomException extends Exception {
    // Constructor: forward the message up to Exception's constructor with
    // super(message). That message is what e.getMessage() returns later.
    CustomException(String message) {
        super(message);
    }
}

public class _29_1_throw_Code {
    public static void main(String[] args) {
        int i = 20;
        int j = 0;

        try {
            j = 18 / i; // 18 / 20 -> 0 (INTEGER division truncates the .9 away)
            if (j == 0) {
                // Three different things we COULD throw when j is 0. The first two
                // (commented out) would land in the ArithmeticException catch
                // block below {Block 1}; the active one throws OUR custom type and
                // lands in {Block 2}.
                // throw new ArithmeticException();                                    // -> {Block 1}
                // throw new ArithmeticException("This is arithmetic exception where j = 0"); // -> {Block 1}
                throw new CustomException("This is arithmetic exception where j = 0"); // -> {Block 2}
            }
        } catch (CustomException e) { // {Block 2} - catches the exact type we threw
            j = 18 / 1; // recover: give j a sensible fallback value (18)
            // System.out.println("Exception: " + e); // would print type + message
            // e.getMessage() prints ONLY the message text we passed in:
            System.out.println("Exception: " + e.getMessage());
        } catch (ArithmeticException e) { // {Block 1} - would catch a thrown ArithmeticException
            j = 18 / 1; // recover with the same fallback value (18)
            // System.out.println("Exception: " + e); // would print type + message
            // e.getMessage() prints ONLY the message text:
            System.out.println("Exception: " + e.getMessage());
        } catch (Exception e) { // safety net: the parent type catches anything else
            System.out.println(e);
        }

        // Because the exception was caught and we set j = 18 inside the catch,
        // the program continues and prints the recovered value.
        System.out.println("Result is : " + j); // prints: Result is : 18
    }
}
