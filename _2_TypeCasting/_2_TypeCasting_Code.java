package _2_TypeCasting;

/**
 * Demonstrates type casting in Java, specifically explicit casting from float
 * to int.
 */
public class _2_TypeCasting_Code {

    /**
     * Main method to execute the type casting example.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        int a = 5;
        float b = 6.1f;

        // Explicitly casting float 'b' to int before multiplication
        int c = a * (int) b;

        System.out.println(c);
    }
}

/*
 * Output:
 * 30
 *
 * Explanation: (int) 6.1f becomes 6, so 5 * 6 = 30.
 */
