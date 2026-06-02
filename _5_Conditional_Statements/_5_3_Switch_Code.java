package _5_Conditional_Statements;

/**
 * Demonstrates the switch statement in Java.
 *
 * A switch compares one value against several constant 'case' labels and runs
 * the matching block. It is a clean alternative to a long if-else-if ladder
 * when you are testing a single variable against fixed values.
 */
public class _5_3_Switch_Code {

    public static void main(String[] args) {

        int day = 3;
        String dayName;

        // Classic switch with break statements.
        switch (day) {
            case 1:
                dayName = "Monday";
                break;
            case 2:
                dayName = "Tuesday";
                break;
            case 3:
                dayName = "Wednesday";
                break;
            case 4:
                dayName = "Thursday";
                break;
            case 5:
                dayName = "Friday";
                break;
            default: // runs when no case matches
                dayName = "Weekend";
        }
        System.out.println("day " + day + " is " + dayName);

        // switch also works with String, char, and enum types.
        char grade = 'B';
        switch (grade) {
            case 'A':
                System.out.println("Excellent");
                break;
            case 'B':
                System.out.println("Good");
                break;
            case 'C':
                System.out.println("Average");
                break;
            default:
                System.out.println("Needs improvement");
        }

        // "Fall-through": cases without a break share the same block. Here all
        // three working-day cases lead to the same message.
        int d = 6;
        switch (d) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                System.out.println("Weekday");
                break;
            case 6:
            case 7:
                System.out.println("Weekend");
                break;
        }
    }
}

/*
 * Output:
 * day 3 is Wednesday
 * Good
 * Weekend
 *
 * Explanation: switch jumps to the matching case. The break statement stops
 * execution from "falling through" into the next case. Cases stacked together
 * (1..5) deliberately share one block, which is how 6 and 7 both print Weekend.
 */
