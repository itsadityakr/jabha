package _25_enums;

// enums are named constants
// send req to server then server sends some status the errors like 404,503 etc
// these are the constant values, so we can use enum
// enums cant be extended

enum Status { // Status is the class and Running,Failed,Pending,Success are objects of Status
              // class
    Running, Failed, Pending, Success;
}

public class _25_1_enums_Code {
    public static void main(String[] args) {
        int i = 5;

        Status s = Status.Running;

        System.out.println(s); // prints the name of the enum value
        System.out.println(s.ordinal()); // returns the index number of the enum value

        Status[] ss = Status.values();
        for (Status status : ss) {
            System.out.println(status);
        }

        // how to compare enums
        if (s == Status.Running) {
            System.out.println("Running");
        }

        switch (s) {
            case Running:
                System.out.println("Running");
                break;
            case Failed:
                System.out.println("Failed");
                break;
            case Pending:
                System.out.println("Pending");
                break;
            case Success:
                System.out.println("Success");
                break;
            default:
                System.out.println("Unknown");
        }
    }
}
