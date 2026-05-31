package _15_Abstract_Keyword;

// The `abstract` keyword marks something as INCOMPLETE -- a design that must be
// finished by a subclass.
//
// An ABSTRACT CLASS:
//   - cannot be instantiated directly (no `new Car()`),
//   - is meant to be extended by concrete subclasses,
//   - can mix abstract methods (no body) with normal methods (with a body).
//
// An ABSTRACT METHOD:
//   - declares a signature but has NO body (ends with a semicolon),
//   - must live inside an abstract class,
//   - forces every concrete subclass to OVERRIDE and implement it.
//
// Think of it as a contract: "any real Car must define how it drives, but they
// can all share the same playMusic() behaviour I provide here."
abstract class Car {
    // Abstract method: no body. Each concrete subclass MUST implement drive().
    public abstract void drive();
    // Key rules:
    //   - abstract methods can only exist in abstract classes
    //   - you can't create an object of an abstract class
    //   - an abstract class may have NO abstract methods at all
    //   - but an abstract method MUST be inside an abstract class

    // Concrete (non-abstract) method: it has a body, so subclasses inherit it
    // as-is and don't have to rewrite it.
    public void playMusic() {
        System.out.println("Playing Music");
    }
}

// A CONCRETE class: it extends the abstract Car and implements every abstract
// method, so it is "complete" and CAN be instantiated.
class WagonR extends Car { // concrete class
    // Implements (overrides) the inherited abstract method.
    public void drive() {
        System.out.println("Driving");
    }
}

public class _15_Abstract_Keyword_Code {
    public static void main(String[] args) {
        // Car obj = new Car(); // ERROR: cannot instantiate the abstract class Car

        // Upcasting: an abstract type can still be used as a reference; the actual
        // object is a concrete WagonR. This is the normal way to use abstraction.
        Car obj = new WagonR();

        obj.drive();      // runs WagonR's implementation -> "Driving"
        obj.playMusic();  // runs Car's inherited concrete method -> "Playing Music"
    }
}

/*
 * Output:
 * Driving
 * Playing Music
 *
 * Explanation:
 * - Car is abstract, so `new Car()` is illegal -- it is an incomplete blueprint.
 * - WagonR extends Car and implements drive(), so it is concrete and usable.
 * - `Car obj = new WagonR();` upcasts the WagonR object to a Car reference.
 * - obj.drive()     -> WagonR's overridden version (the abstract method made real).
 * - obj.playMusic() -> Car's concrete method, inherited unchanged.
 */
