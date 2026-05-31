package _16_InnerClass;

// An ANONYMOUS INNER CLASS is a class with NO name that you define and instantiate
// in a single expression. It is the shortcut for "I need a one-off subclass of A
// (or implementation of an interface) right here, and I'll never reuse it."
//
// The normal way is to write a whole separate subclass (see class B below). But
// if you only need that override ONCE, writing a named class is overkill --
// declaring, naming, and saving a class just to use it a single time is wasteful.
// The anonymous inner class lets you override the behaviour inline instead.
class A {
    public void show() {
        System.out.println("Showing in class A");
    }
}

// The traditional approach: a separate, named subclass that overrides show().
// Useful when the new behaviour is reused; heavyweight if it is needed only once.
class B extends A {
    public void show() {
        System.out.println("Showing in class B");
    }
}

public class _16_2_AnonymousInnerClass_Code {
    public static void main(String[] args) {
        // Using the named subclass B (the ordinary route).
        A obj = new B();
        obj.show();

        // Anonymous inner class: `new A() { ... }` creates an UNNAMED subclass of
        // A on the spot and overrides show(). `obj1` is declared as type A, but the
        // object it points to is this one-off subclass. The trailing `;` is
        // required -- the whole thing is one statement.
        A obj1 = new A() {
            public void show() { // override A's behaviour just for this object
                System.out.println("Showing in class A (Anonymous)");
            }
        };
        obj1.show();
    }
}

/*
 * Output:
 * Showing in class B
 * Showing in class A (Anonymous)
 *
 * Explanation:
 * - class B extends A and overrides show() -- the classic named-subclass way.
 * - new A() { ... } builds an anonymous subclass of A inline and overrides show()
 *   without ever giving the class a name.
 * - Use it for a one-time override where a full named class would be overkill.
 */
