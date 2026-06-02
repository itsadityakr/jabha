package _9_Methods;

/**
 * Demonstrates METHOD OVERRIDING in Java.
 *
 * Overriding means a CHILD class provides its OWN version of a method that is
 * already defined in its PARENT class, using the SAME name and SAME parameter
 * list. Which version runs is decided at RUNTIME based on the actual object,
 * which is why overriding is called runtime (dynamic) polymorphism.
 *
 * (Inheritance is covered fully in Chapter 15; here we use a small parent/child
 * pair just to show overriding.)
 */

class Animal {
    void sound() {
        System.out.println("Some generic animal sound");
    }
}

class Dog extends Animal {
    @Override // optional but recommended: the compiler checks it really overrides
    void sound() {
        System.out.println("Woof");
    }
}

class Cat extends Animal {
    @Override
    void sound() {
        System.out.println("Meow");
    }
}

public class _9_3_Method_Overriding_Code {
    public static void main(String[] args) {

        Animal a1 = new Animal();
        Animal a2 = new Dog(); // a parent reference holding a child object
        Animal a3 = new Cat();

        // The OBJECT's type decides which sound() runs, not the reference type.
        a1.sound(); // Animal's version
        a2.sound(); // Dog's version    (overridden)
        a3.sound(); // Cat's version    (overridden)
    }
}

/*
 * Output:
 * Some generic animal sound
 * Woof
 * Meow
 *
 * Explanation: a2 and a3 are declared as Animal but actually hold a Dog and a
 * Cat. At runtime Java calls the overriding method of the real object, so they
 * print "Woof" and "Meow". This run-time selection is dynamic dispatch.
 *
 * Overloading vs Overriding:
 *  - Overloading: same name, DIFFERENT parameters, same class, COMPILE time.
 *  - Overriding : same name, SAME parameters, parent vs child, RUN time.
 */
