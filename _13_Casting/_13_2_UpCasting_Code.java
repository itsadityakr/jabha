package _13_Casting;

// Upcasting (for OBJECTS / reference types) means treating a subclass object
// through a superclass reference:
//
//      Superclass ref = new Subclass();
//
// It is ALWAYS safe and happens IMPLICITLY (no cast needed), because every B
// "is an" A. The trade-off: through the A reference you can only call members
// declared in A -- the B-specific ones are hidden.
public class _13_2_UpCasting_Code {

    // The hierarchy used by this demo: superclass A and subclass B.
    // They are declared as static nested classes so this file is fully
    // self-contained and does not clash with the A/B in _13_2_DownCasting.java
    // (both files live in the same package, _13_Casting).
    static class A {
        public void showA() {
            System.out.println("In Class A");
        }
    }

    static class B extends A {
        // B-specific method that does NOT exist in A.
        public void showB() {
            System.out.println("In Class B");
        }
    }

    public static void main(String[] args) {
        B b = new B();

        // ----- Upcasting: B reference -> A reference (implicit) -----
        // No cast is required; a B "is an" A.
        A ref = b;

        // Through an A reference, only A's members are visible.
        ref.showA(); // In Class A

        // ref.showB(); // COMPILE ERROR: showB() is not declared in A.
        //              // The reference type (A) limits what is visible.

        // Through the original B reference, everything is available.
        b.showA(); // In Class A
        b.showB(); // In Class B
    }
}

/*
 * Output:
 * In Class A
 * In Class A
 * In Class B
 *
 * Explanation:
 * - 'A ref = b;' is upcasting: it is implicit and always safe.
 * - ref.showA() works because showA() is declared in A.
 * - ref.showB() would not compile: the A reference type only exposes members
 *   declared in A. To use showB() you need a B reference (or a downcast -- see
 *   _13_2_DownCasting).
 */
