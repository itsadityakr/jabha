package _2_JVM_JDK_JRE_Memory;

/**
 * A small, runnable demonstration of how Java manages memory:
 *   - primitives live on the STACK and are copied by value,
 *   - objects live on the HEAP and are accessed through references,
 *   - String literals are shared in the STRING POOL.
 *
 * The architecture concepts (JVM, JDK, JRE) are explained in the README;
 * this file shows the memory behaviour you can actually observe.
 */
public class _2_Memory_Demo_Code {

    public static void main(String[] args) {

        // ---- Stack: primitives are copied by value ----
        int a = 10;
        int b = a;   // b gets a COPY of a's value
        b = 20;      // changing b does NOT affect a
        System.out.println("a = " + a + ", b = " + b); // a still 10

        // ---- Heap: objects are shared through references ----
        int[] x = { 1, 2, 3 };
        int[] y = x; // y points to the SAME array object as x
        y[0] = 99;   // change through y is visible through x
        System.out.println("x[0] = " + x[0]); // 99 (same object)

        // ---- String Pool: literals are shared ----
        String s1 = "Java";
        String s2 = "Java";          // reuses the SAME pooled object
        String s3 = new String("Java"); // 'new' forces a separate heap object

        System.out.println("s1 == s2      : " + (s1 == s2));       // true  (same pool object)
        System.out.println("s1 == s3      : " + (s1 == s3));       // false (different objects)
        System.out.println("s1.equals(s3) : " + s1.equals(s3));    // true  (same text)
        System.out.println("s1 == s3.intern(): " + (s1 == s3.intern())); // true (intern returns pooled)
    }
}

/*
 * Output:
 * a = 10, b = 20
 * x[0] = 99
 * s1 == s2      : true
 * s1 == s3      : false
 * s1.equals(s3) : true
 * s1 == s3.intern(): true
 *
 * Explanation:
 *  - 'b = a' copies the value, so the two primitives are independent (stack).
 *  - 'y = x' copies the reference, so both point to one array (heap).
 *  - "Java" literals share one object in the String Pool, so s1 == s2 is true.
 *  - 'new String("Java")' makes a distinct heap object, so s1 == s3 is false,
 *    but the text is equal, and intern() returns the shared pooled instance.
 */
