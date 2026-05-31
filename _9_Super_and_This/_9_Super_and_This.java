// By default, every constructor has a super() method call in it.

/**
 * Base class to demonstrate this() and super() keywords.
 */
class A {
    public A() {
        super(); // calls Object's default Constructor
        System.out.println("A : Default Constructor");
    }

    public A(int num) {
        // super();
        this(); // calls the default constructor of the same class
        System.out.println("A : Parameterized Constructor");
    }
}

/**
 * Derived class to demonstrate calling superclass constructors.
 */
class B extends A {
    public B() {
        super(6); // calls the parameterized constructor of class A
        System.out.println("B : Default Constructor");
    }

    public B(int num) {
        super(); // calls the default constructor of class A
        System.out.println("B : Parameterized Constructor");
    }
}

/**
 * Main class to test constructor chaining using this and super.
 */
public class _9_Super_and_This {
    public static void main(String[] args) {
        B obj = new B();
    }
}

/*
 * Output:
 * A : Default Constructor
 * A : Parameterized Constructor
 * B : Default Constructor
 *
 * Explanation: new B() calls super(6) -> A(int), which calls this() -> A(),
 * which calls super() -> Object(). The stack then unwinds, printing A's
 * default constructor, then A's parameterized constructor, then B's.
 */
