package _27_Interfaces_Types;

// =============================================================================
// CHAPTER 27 - TYPES OF INTERFACES  |  FILE 2 of 3 : FUNCTIONAL (SAM) INTERFACE
// =============================================================================
//
// A FUNCTIONAL interface has EXACTLY ONE abstract method.
// SAM = Single Abstract Method. Because there is only one method to implement,
// the compiler lets you replace a whole anonymous inner class (Chapter 23) with
// a short LAMBDA expression.
//
// @FunctionalInterface (a built-in annotation from Chapter 26) is OPTIONAL but
// recommended: it makes the compiler ENFORCE the "exactly one abstract method"
// rule. Add a second abstract method and compilation fails immediately - which
// protects every lambda that relies on the interface having a single method.
//
// (default, static and private methods do NOT count toward the limit - you may
//  have many of those and the interface is still "functional".)
// =============================================================================

@FunctionalInterface // if we add a 2nd abstract method here, this line errors
interface A {
    void show(int i); // THE single abstract method (takes an int, returns void)
}

// A second functional interface that RETURNS a value, to show lambdas are not
// limited to void. It also carries a default method (still allowed).
@FunctionalInterface
interface Calculator {
    int operate(int a, int b); // the single abstract method

    default void describe() {
        System.out.println("[Calculator] I combine two ints into one");
    }
}

public class _27_2_Functional_OR_SAM_Interface_Code {

    // A normal static method whose signature MATCHES A.show(int) -> void, so it
    // can be plugged in later as a METHOD REFERENCE.
    static void printLoudly(int i) {
        System.out.println(">>> " + i + " <<<");
    }

    public static void main(String[] args) {

        // -------------------------------------------------------------------
        // STEP 1 - the OLD way: an ANONYMOUS INNER CLASS (Chapter 23).
        // Lots of boilerplate just to supply one method body.
        // -------------------------------------------------------------------
        A oldWay = new A() {
            @Override
            public void show(int i) {
                System.out.println("Anonymous class show: " + i);
            }
        };
        oldWay.show(1);

        // -------------------------------------------------------------------
        // STEP 2 - a LAMBDA: the same thing with far less code. The parameter
        // list and body ARE the implementation of the single abstract method.
        //    (parameters) -> body
        // -------------------------------------------------------------------
        A lambda = (int i) -> System.out.println("Lambda show: " + i);
        lambda.show(2);

        // STEP 3 - the parameter TYPE is inferred, so you can drop 'int'.
        A inferred = (i) -> System.out.println("Inferred-type show: " + i);
        inferred.show(3);

        // STEP 4 - with exactly ONE parameter you may also drop the parentheses.
        A shortest = i -> System.out.println("Shortest show: " + i);
        shortest.show(4);

        // -------------------------------------------------------------------
        // STEP 5 - a METHOD REFERENCE: if a method already does what the lambda
        // would, point at it with '::' instead of rewriting the body.
        //    ClassName::methodName
        // -------------------------------------------------------------------
        A methodRef = _27_2_Functional_OR_SAM_Interface_Code::printLoudly;
        methodRef.show(5);

        System.out.println();

        // -------------------------------------------------------------------
        // A functional interface that RETURNS a value, implemented by lambdas.
        // -------------------------------------------------------------------
        Calculator add = (a, b) -> a + b;
        Calculator max = (a, b) -> a > b ? a : b;
        add.describe();                                  // default method works
        System.out.println("add(7, 8) = " + add.operate(7, 8));
        System.out.println("max(7, 8) = " + max.operate(7, 8));

        System.out.println();

        // -------------------------------------------------------------------
        // REAL WORLD: the java.util.function package already ships ready-made
        // functional interfaces, so you rarely need to write your own:
        //    Runnable          ()      -> void
        //    Supplier<T>       ()      -> T
        //    Consumer<T>       (T)     -> void
        //    Function<T,R>     (T)     -> R
        //    Predicate<T>      (T)     -> boolean
        //    BiFunction<T,U,R> (T,U)   -> R
        // -------------------------------------------------------------------
        java.util.function.Predicate<Integer> isEven = n -> n % 2 == 0;
        System.out.println("isEven(10) = " + isEven.test(10));
        System.out.println("isEven(7)  = " + isEven.test(7));
    }
}
