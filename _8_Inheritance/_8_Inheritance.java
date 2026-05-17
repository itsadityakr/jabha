package _8_Inheritance;

/**
 * Base class A demonstrating simple inheritance.
 */
class A {
    A() {
        System.out.println("A");
    }

    public void show() {
        System.out.println("Show Method : A");
    }
}

/**
 * Class C inheriting from B to show multi-level inheritance.
 */
class C extends B {
    C() {
        System.out.println("C");
    }

    // public void show() {
    // System.out.println("Show Method : C");
    // }
}

/**
 * Class B inheriting from A.
 */
class B extends A {
    B() {
        System.out.println("B");
    }

    public void show() {
        System.out.println("Show Method : B");
    }
}

/**
 * Main class demonstrating object creation and method overriding in inheritance.
 */
public class _8_Inheritance {
    public static void main(String[] args) {
        C obj = new C();
        obj.show();
    }
}
