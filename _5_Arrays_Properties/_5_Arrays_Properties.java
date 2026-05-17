package _5_Arrays_Properties;

/**
 * Represents a Student entity with properties like rollno, name, and marks.
 */
class Student {

    int rollno;
    String name;
    float marks;

    /**
     * Prints the details of the student.
     */
    public void printDetails() {
        System.out.println(rollno + ", " + name + ", " + marks);
    }
}

/**
 * Demonstrates the creation of an array of objects (though an array is not strictly used here, objects are instantiated).
 */
public class _5_Arrays_Properties {

    public static void main(String[] args) {
        // Creating and initializing first student
        Student s1 = new Student();
        s1.rollno = 1;
        s1.name = "Navin";
        s1.marks = 77;
        s1.printDetails();

        // Creating and initializing second student
        Student s2 = new Student();
        s2.rollno = 2;
        s2.name = "Aditya";
        s2.marks = 65;

        // Creating and initializing third student
        Student s3 = new Student();
        s3.rollno = 3;
        s3.name = "Tappu";
        s3.marks = 98;
    }
}
