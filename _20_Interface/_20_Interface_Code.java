package _20_Interface;

// class -> class => extends
// class -> interface => implements
// interface -> class => implements
// interface -> interface => extends

// abstract class A {
//     public abstract void show();
//     public abstract void config();
// }

interface A { // interface = public abstract method by default

    // int age; // in interfaces fields are final static by default
    String area = "In Interfaces we need to initialize the fields by default"; // if we dont initialize it then it will throw error

    void show(); // removing public abstract as interface does not require it
    void config();
}

interface X{
    void run();
}

class B implements A,X { // in abstract classes we use extends and in interface we use implements
    // A,X works in A and X simultaneously
    public void show(){
        System.out.println("Show");
    }

    public void config(){
        System.out.println("Config");
    }

    public void run(){ // we need to define every method we implement from the interface, else it will throw error
        System.out.println("Run");
    }
}

// why is it final and static by default? 
// because interface is just blueprint
// and if multiple objects are created for that interface then it should not have any instance variables
// that can lead to ambiguity (if obj1.age = 20 and obj2.age = 30, then what is the value of age? -> ambiguity!)

// why is it final -> if it is not final then it can be changed
// if it can be changed then it is not a blueprint
// if it is not a blueprint then it is not an interface
// that's why it is final!

// why is it static
// because if it is not static then it is not a blueprint
// if it is not a blueprint then it is not an interface
// that's why it is static!

public class _20_Interface_Code {
    public static void main(String[] args) {
        A obj;
        // obj = new A(); // A is abstract; cannot be instantiated
        obj = new B(); // B is implementing A

        obj.show();
        obj.config();
    }
}
