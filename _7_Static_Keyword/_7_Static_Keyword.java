package _7_Static_Keyword;

/**
 * Demonstrates the use of the static keyword for class-level variables.
 */
class Phone{
    // Static variable shared across all instances
    static String name;
    // Instance variable specific to each object
    int price;

    /**
     * Displays the phone's details.
     */
    public void show(){
        System.out.println(name + " : " + price);
    }
}

/**
 * Main class to test the static variable behavior in the Phone class.
 */
public class _7_Static_Keyword {
    public static void main(String[] args) {
        Phone obj1 = new Phone();
        Phone.name = "Samsung";
        obj1.price = 1_45_000;

        Phone obj2 = new Phone();
        Phone.name = "Apple";
        obj2.price = 90_000;

        // Modifying the static variable affects all instances
        Phone.name = "Phone";

        obj1.show();
        obj2.show();
    }
}
