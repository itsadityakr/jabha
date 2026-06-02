package _4_Operators;

/**
 * Demonstrates the logical operators in Java.
 *
 * Logical operators work on boolean values and return a boolean:
 *   &&  logical AND  (true only if BOTH sides are true)
 *   ||  logical OR   (true if AT LEAST ONE side is true)
 *   !   logical NOT  (reverses a boolean)
 *
 * Relational operators (>, <, >=, <=, ==, !=) produce the booleans that
 * logical operators combine.
 */
public class _4_3_Logical_Operators_Code {

    public static void main(String[] args) {

        int age = 25;
        boolean hasLicense = true;

        // Relational operators produce true/false.
        System.out.println("age > 18      : " + (age > 18));
        System.out.println("age == 25     : " + (age == 25));
        System.out.println("age != 30     : " + (age != 30));

        // AND: both conditions must be true.
        boolean canDrive = (age >= 18) && hasLicense;
        System.out.println("canDrive (AND): " + canDrive);

        // OR: at least one condition must be true.
        boolean weekend = false;
        boolean holiday = true;
        boolean dayOff = weekend || holiday;
        System.out.println("dayOff (OR)   : " + dayOff);

        // NOT: reverses the value.
        System.out.println("!hasLicense   : " + (!hasLicense));

        // Short-circuit behaviour: with &&, if the left side is false, the
        // right side is never evaluated (and with ||, if the left is true).
        int x = 0;
        boolean safe = (x != 0) && (10 / x > 1); // left is false -> right skipped
        System.out.println("safe (short)  : " + safe);
    }
}

/*
 * Output:
 * age > 18      : true
 * age == 25     : true
 * age != 30     : true
 * canDrive (AND): true
 * dayOff (OR)   : true
 * !hasLicense   : false
 * safe (short)  : false
 *
 * Explanation: && needs both sides true; || needs at least one true; ! flips
 * the value. Short-circuiting means (x != 0) is false, so 10 / x is never run,
 * which safely avoids a divide-by-zero.
 */
