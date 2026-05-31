package _6_Encapsulation;

/**
 * Demonstrates encapsulation by keeping fields private and providing public getters and setters.
 */
class Human {

    private int age;

    /**
     * Gets the age of the human.
     * @return The age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the human.
     * @param age The new age
     * @param obj The Human object instance
     */
    public void setAge(int age, Human obj) {
        Human obj1 = obj;
        obj1.age = age;
        // age = age;
    }
}

/**
 * Main class to demonstrate the Human class and encapsulation.
 */
public class _6_Encapsulation {

    public static void main(String[] args) {

        Human obj = new Human();
        // Setting the age via the method
        obj.setAge(4, obj);
        // Printing the age using the getter
        System.out.println(obj.getAge());
    }
}

/*
 * Output:
 * 4
 *
 * Explanation: setAge(4, obj) sets the private age field to 4 via the object
 * reference, and getAge() reads it back.
 */
