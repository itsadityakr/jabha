package _12_String;

/**
 * Demonstrates StringBuilder — a MUTABLE companion to String.
 *
 * Because String is immutable, building a String in a loop with + creates many
 * throwaway objects. StringBuilder can be changed in place, which is far more
 * efficient for repeated modification.
 */
public class _12_2_String_Builder_Code {

    public static void main(String[] args) {

        // Create a StringBuilder and modify it in place.
        StringBuilder sb = new StringBuilder("Hello");

        sb.append(" World");          // add to the end
        System.out.println("after append : " + sb);

        sb.insert(5, ",");            // insert "," at index 5
        System.out.println("after insert : " + sb);

        sb.replace(0, 5, "Hi");       // replace chars [0,5) with "Hi"
        System.out.println("after replace: " + sb);

        sb.deleteCharAt(2);           // remove the character at index 2
        System.out.println("after delete : " + sb);

        sb.reverse();                 // reverse the whole sequence
        System.out.println("after reverse: " + sb);

        sb.reverse();                 // reverse back for readability
        System.out.println("length       : " + sb.length());

        // Convert back to an ordinary String when finished building.
        String result = sb.toString();
        System.out.println("toString     : " + result);

        // Why StringBuilder: efficiently build a String in a loop.
        StringBuilder csv = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            csv.append(i);
            if (i < 5) {
                csv.append(",");
            }
        }
        System.out.println("built in loop: " + csv);
    }
}

/*
 * Output:
 * after append : Hello World
 * after insert : Hello, World
 * after replace: Hi, World
 * after delete : Hi World
 * after reverse: dlroW iH
 * length       : 8
 * toString     : Hi World
 * built in loop: 1,2,3,4,5
 *
 * Explanation: A StringBuilder is modified in place by append/insert/replace/
 * delete/reverse, so no new object is created for each change. This makes it
 * the right choice when assembling a String, especially inside a loop.
 */
