package _12_Objects;

// java.util.Objects is a utility class with null-safe helpers used inside
// equals() and hashCode() (Objects.equals(...) and Objects.hash(...)).
import java.util.Objects;

// A simple model class that overrides the three most common Object methods:
//   toString()  -> a readable text form of the object
//   equals()    -> compares two objects by their DATA (not memory address)
//   hashCode()  -> a number derived from the data, required by HashMap/HashSet
//
// Every class in Java inherits these from the Object class. By default they
// work on the object's identity (memory address); we override them so that two
// laptops with the same brand and price are treated as equal.
class Laptop {
    String brand;
    int price;

    // toString() is called automatically when an object is printed or joined
    // with a String. Without it, printing shows something like "Laptop@1b6d35".
    @Override
    public String toString() {
        return "Laptop{brand='" + brand + "', price=" + price + "}";
    }

    // equals() defines DATA equality. The parameter MUST be of type Object to
    // genuinely override Object.equals (using Laptop here would only overload).
    @Override
    public boolean equals(Object o) {
        // 1. Same reference -> definitely equal.
        if (this == o)
            return true;

        // 2. Null or a different class -> not equal.
        if (o == null || getClass() != o.getClass())
            return false;

        // 3. Safe to cast now; compare the fields that define equality.
        Laptop other = (Laptop) o;
        return price == other.price && Objects.equals(brand, other.brand);
    }

    // hashCode() MUST be overridden whenever equals() is, so that equal objects
    // produce the same hash code. HashMap and HashSet rely on this contract.
    @Override
    public int hashCode() {
        return Objects.hash(brand, price);
    }
}

public class _12_Objects_Code {
    public static void main(String[] args) {
        // obj1 and obj2 hold identical data; obj3 is different.
        Laptop obj1 = new Laptop();
        obj1.brand = "Dell";
        obj1.price = 50000;

        Laptop obj2 = new Laptop();
        obj2.brand = "Dell";
        obj2.price = 50000;

        Laptop obj3 = new Laptop();
        obj3.brand = "HP";
        obj3.price = 60000;

        // --- toString() ---
        // Printing an object calls toString() automatically.
        System.out.println("obj1.toString() : " + obj1);
        System.out.println("obj3.toString() : " + obj3);

        // --- == (reference comparison) ---
        // '==' is true only when both variables point to the SAME object.
        System.out.println("obj1 == obj2      : " + (obj1 == obj2)); // false

        // --- equals() (data comparison) ---
        // Our overridden equals() compares brand and price.
        System.out.println("obj1.equals(obj2) : " + obj1.equals(obj2)); // true
        System.out.println("obj1.equals(obj3) : " + obj1.equals(obj3)); // false

        // --- hashCode() ---
        // Equal objects (obj1, obj2) produce the SAME hash code.
        System.out.println("obj1.hashCode()   : " + obj1.hashCode());
        System.out.println("obj2.hashCode()   : " + obj2.hashCode());
        System.out.println("obj3.hashCode()   : " + obj3.hashCode());
    }
}

/*
 * Output:
 * obj1.toString() : Laptop{brand='Dell', price=50000}
 * obj3.toString() : Laptop{brand='HP', price=60000}
 * obj1 == obj2      : false
 * obj1.equals(obj2) : true
 * obj1.equals(obj3) : false
 * obj1.hashCode()   : 65966416
 * obj2.hashCode()   : 65966416
 * obj3.hashCode()   : 132633
 *
 * Explanation:
 * - toString() gives a readable form instead of "Laptop@<hash>".
 * - '==' is false: obj1 and obj2 are two separate objects in memory.
 * - equals() is true for obj1/obj2 (same brand + price) and false for obj1/obj3.
 * - hashCode() is IDENTICAL for the equal objects obj1 and obj2, as the
 *   equals()/hashCode() contract requires; obj3 differs, so its hash differs.
 *   (Exact hash numbers depend on the field values via Objects.hash.)
 */
