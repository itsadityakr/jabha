package _10_Polymorphism;

class Animal {
    public void move() {
        System.out.println("Animal moves");
    }
}

class Dog extends Animal {
    public void move() {
        System.out.println("Dog runs");
    }
}

class Fish extends Animal {
    public void move() {
        System.out.println("Fish swims");
    }
}

class Bird extends Animal {
    public void move() {
        System.out.println("Bird flies");
    }
}

public class _10_Polymorphism_Runtime {
    public static void main(String[] args) {
        Animal animal = new Animal();
        Dog dog = new Dog();
        Fish fish = new Fish();
        Bird bird = new Bird();

        Animal a1 = new Animal();
        Animal a2 = new Dog();
        Animal a3 = new Fish();
        Animal a4 = new Bird();

        animal.move();
        dog.move();
        fish.move();
        bird.move();

        a1.move();
        a2.move();
        a3.move();
        a4.move();
    }
}

/*
 * Output:
 * Animal moves
 * Dog runs
 * Fish swims
 * Bird flies
 * Animal moves
 * Dog runs
 * Fish swims
 * Bird flies
 *
 * Explanation: a2, a3, a4 are Animal references pointing to Dog, Fish, Bird
 * objects. Runtime polymorphism (dynamic method dispatch) means the actual
 * object's overridden move() runs, not the Animal version.
 */
