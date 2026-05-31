package _16_InnerClass;

// An INNER CLASS is a class declared INSIDE another class. Java has a few flavours;
// this file shows the two member kinds that live directly inside a class body:
//
//   1. Non-static inner class (here: B) -- tied to an INSTANCE of the outer class.
//      It can use the outer object's fields/methods, and to create one you first
//      need an outer object:   outer.new Inner();
//
//   2. Static nested class (here: C) -- marked `static`, NOT tied to any instance.
//      You create it directly:  new Outer.Inner();
//
// Note: the OUTER (top-level) class itself can never be `static` -- only nested
// classes can carry the `static` keyword.
class A { // parent (outer) class -- a top-level class cannot be static
    public void show() {
        System.out.println("Showing in class A");
    }

    // Non-static inner class: bound to an instance of A.
    class B {
        public void show() {
            System.out.println("Showing in class B");
        }

        public void Display() {
            System.out.println("Displaying in class B");
        }
    }

    // Static nested class: independent of any A instance.
    static class C {
        public void show() {
            System.out.println("Showing in class C");
        }
    }
}

public class _16_1_InnerClass_Code {
    public static void main(String[] args) {
        // Use the outer class normally.
        A obj = new A();
        obj.show();

        // B is non-static, so it needs an enclosing A instance. The syntax is
        // `outerObject.new Inner()` -- here `obj.new B()`. The type is written
        // A.B because B lives inside A.
        A.B obj2 = obj.new B();
        obj2.show();
        obj2.Display();

        // C is static, so no A instance is required -- create it directly with
        // `new A.C()`.
        A.C obj3 = new A.C();
        obj3.show();
    }
}

/*
 * Output:
 * Showing in class A
 * Showing in class B
 * Displaying in class B
 * Showing in class C
 *
 * Explanation:
 * - A is the outer class; B (non-static) and C (static) are nested inside it.
 * - obj.new B()  : a non-static inner class needs an outer-class OBJECT first.
 * - new A.C()    : a static nested class is created directly, no A object needed.
 * - The type names are A.B and A.C because both are members of A.
 */
