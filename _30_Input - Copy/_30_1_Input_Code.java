package _30_Input;

import java.io.IOException;

public class _30_1_Input_Code {
    public static void main(String[] args) throws IOException {
        
        System.out.println(); // print method of PrintStream class
        
        int n = System.in.read();

        System.out.println(n); // ASCII value

        System.out.println((char)n); // character
    }
}
