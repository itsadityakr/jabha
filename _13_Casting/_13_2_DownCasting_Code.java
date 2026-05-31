package _13_Casting;

// Downcasting (for OBJECTS / reference types) is the opposite of upcasting:
// taking a superclass reference and converting it back to the subclass type so
// the subclass-specific members become available again:
//
//      Superclass ref = new Subclass();   // upcast first
//      Subclass s = (Subclass) ref;       // downcast back (EXPLICIT)
//
// Downcasting is NOT automatic -- you must write the (Subclass) cast, because it
// is only safe if the object REALLY is that subclass. If it is not, Java throws
// a ClassCastException at runtime. The safe way is to check with 'instanceof'
// before casting.
public class _13_2_DownCasting_Code {

    // The same A/B hierarchy as the upcasting demo, declared as static nested
    // classes so this file is self-contained and does not clash with the A/B in
    // _13_2_UpCasting.java (both files live in the same package, _13_Casting).
    static class A {
        public void showA() {
            System.out.println("In Class A");
        }
    }

    static class B extends A {
        // B-specific method, not present in A.
        public void showB() {
            System.out.println("In Class B");
        }
    }

    public static void main(String[] args) {
        // Step 1: upcast -- an A reference pointing to a real B object.
        A ref = new B();
        ref.showA(); // In Class A

        // ref.showB(); // COMPILE ERROR: showB() is not visible through A.

        // Step 2: downcast -- but only after confirming the object really is a
        // B. 'instanceof' guards against a ClassCastException.
        if (ref instanceof B) {
            B b = (B) ref; // explicit downcast
            b.showB();     // now the B-specific method is accessible
        }

        // ----- Why the instanceof check matters -----
        // A plain A is NOT a B, so downcasting it would fail:
        A onlyA = new A();
        if (onlyA instanceof B) {
            B b2 = (B) onlyA;
            b2.showB();
        } else {
            System.out.println("onlyA is not a B - skipping downcast");
        }

        // The following line (without a guard) would compile but CRASH at
        // runtime with java.lang.ClassCastException, because 'onlyA' is only an
        // A, not a B:
        //
        //     B bad = (B) onlyA; // ClassCastException
    }
}

/*
 * Output:
 * In Class A
 * In Class B
 * onlyA is not a B - skipping downcast
 *
 * Explanation:
 * - 'A ref = new B();' is an upcast; reference is A, the real object is B.
 * - To call showB() (a B-only method) we must downcast the A reference back to
 *   B using the explicit (B) cast.
 * - 'instanceof' confirms the object's real type first, so the downcast is safe.
 * - 'onlyA' is a plain A, so the instanceof check is false and we skip the cast.
 *   Casting it anyway would throw a ClassCastException at runtime.
 */
