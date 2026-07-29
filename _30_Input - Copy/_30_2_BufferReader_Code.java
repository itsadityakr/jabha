package _30_Input;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class _30_2_BufferReader_Code {
    public static void main(String[] args) throws Exception {

        System.out.println("Enter a number");

        InputStreamReader S_Reader = new InputStreamReader(System.in); // used to read the input from the user

        BufferedReader BR = new BufferedReader(S_Reader); // used to read data from the user

        int n = Integer.parseInt(BR.readLine()); // Read the input from the user

        System.out.println(n);
    }
}