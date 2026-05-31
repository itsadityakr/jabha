package _11_final_Keyword;

public class _11_2_final_Class_Code {
    public static void main(String[] args) {
        FinalClass finalClass = new FinalClass();
        finalClass.show();
    }
}

/*
 * Output:
 * Final class method
 *
 * Explanation: A final class cannot be extended, but objects of it can still
 * be created and its methods called normally.
 */

final class FinalClass {
    public void show() {
        System.out.println("Final class method");
    }   
}

// class A extends FinalClass {
//     public void show() {
//         System.out.println("Derived class method");
//     }
// }

/*
 * class Derived extends FinalClass {
 * // ERROR: Cannot extend final class
 * public void show() {
 * System.out.println("Derived class method");
 * }
 * }
 */