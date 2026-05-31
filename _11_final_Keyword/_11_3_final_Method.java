package _11_final_Keyword;

class A {
    public final void show() {
        System.out.println("A show");
    }
}

class B extends A {
    // public void show() {
    //     System.out.println("B show");
    // }
}

public class _11_3_final_Method {
    public static void main(String[] args) {
        B b = new B();
        b.show();
    }
}

/*
 * Output:
 * A show
 *
 * Explanation: A.show() is declared final, so B is NOT allowed to override it.
 * B's show() override is commented out, so b.show() runs the inherited final
 * method from A, printing "A show".
 *
 * If you uncomment B's show(), the file will fail to compile with:
 *   error: show() in B cannot override show() in A; overriding method is final
 */
