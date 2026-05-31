package _16_InnerClass;

// This file combines two ideas from earlier chapters:
//   - the ABSTRACT keyword (Chapter 15): a class you cannot instantiate, and
//   - the ANONYMOUS INNER CLASS (file _16_2): a one-off, unnamed subclass.
//
// You cannot write `new A()` for an abstract class. But you CAN write
// `new A() { ... }` -- this does NOT create an object of the abstract class
// itself; it creates an object of an anonymous subclass that extends A and
// supplies the missing implementation. This is the most common everyday use of
// anonymous inner classes (think interfaces, listeners, Runnable, etc.).
abstract class A {
    abstract public void show(); // abstract method: no body, must be implemented
}

public class _16_3_AbstractAnonymousInnerClass_Code {
    public static void main(String[] args) {

        // A obj = new A(); // ERROR: cannot instantiate the abstract class A
        // obj.show();

        // `new A() { ... }` builds an anonymous subclass of the abstract A and
        // implements its abstract show() method right here. The object is an
        // instance of that unnamed subclass -- not of A itself -- which is how an
        // abstract type can be used without writing a separate named subclass.
        A obj1 = new A() {
            public void show() { // provide the required implementation
                System.out.println("Showing in class A (Anonymous)");
            }
        };
        obj1.show();
    }
}

/*
 * Output:
 * Showing in class A (Anonymous)
 *
 * Explanation:
 * - A is abstract, so `new A()` is illegal on its own.
 * - new A() { public void show() {...} } creates an anonymous subclass that
 *   implements the abstract show(), and instantiates THAT subclass.
 * - This is the standard way to provide a one-off implementation of an abstract
 *   type (or interface) without declaring a separate named class.
 */
