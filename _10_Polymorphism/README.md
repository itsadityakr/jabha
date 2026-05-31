# Chapter 10: Polymorphism in Java

## Table of Contents

1. [Introduction](#introduction)
2. [What is Polymorphism](#what-is-polymorphism)
3. [Why Do We Need Polymorphism](#why-do-we-need-polymorphism)
4. [Types of Polymorphism in Java](#types-of-polymorphism-in-java)
5. [Understanding the Code](#understanding-the-code)
6. [Line-by-Line Explanation](#line-by-line-explanation)
7. [Step-by-Step Execution Walkthrough](#step-by-step-execution-walkthrough)
8. [Upcasting and Dynamic Method Dispatch](#upcasting-and-dynamic-method-dispatch)
9. [Compile-Time Polymorphism (Method Overloading)](#compile-time-polymorphism-method-overloading)
10. [Overloading vs Overriding](#overloading-vs-overriding)
11. [How to Compile and Run](#how-to-compile-and-run)
12. [Common Mistakes](#common-mistakes)
13. [Interview Questions](#interview-questions)

---

## Introduction

Polymorphism is one of the four pillars of Object-Oriented Programming (OOP). The word "polymorphism" comes from Greek and means "many forms". In Java, polymorphism allows a single action to behave differently depending on the object that performs it.

This program demonstrates runtime polymorphism, where a parent class reference (`Animal`) points to objects of different child classes (`Dog`, `Fish`, `Bird`), and the overridden `move()` method that runs is decided at runtime based on the actual object type.

---

## What is Polymorphism

Polymorphism is the ability of an object, method, or reference to take on many forms. The same method call can produce different behavior depending on the actual object it is invoked on.

Think of the word "draw". An artist draws a painting, a gunslinger draws a pistol, and a gambler draws a card. The same word means different actions depending on who performs it. Similarly, calling `move()` on different animals produces different movement.

**Syntax:**

```java
class Parent {
    public void action() {
        System.out.println("Parent action");
    }
}

class Child extends Parent {
    @Override
    public void action() {
        System.out.println("Child action");
    }
}

// A parent reference pointing to a child object
Parent p = new Child();
p.action();  // Runs Child's version (runtime polymorphism)
```

---

## Why Do We Need Polymorphism

### Without Polymorphism (Rigid Code)

```java
void makeItMove(Dog dog)  { dog.move(); }
void makeItMove(Fish fish) { fish.move(); }
void makeItMove(Bird bird) { bird.move(); }
// A separate method for every type -- hard to maintain
```

### With Polymorphism (Flexible Code)

```java
void makeItMove(Animal animal) {
    animal.move();  // Works for Dog, Fish, Bird, or any future Animal
}
```

### Benefits

| Benefit            | Description                                                       |
|--------------------|-------------------------------------------------------------------|
| Code Reusability   | One method can handle objects of many related types               |
| Extensibility      | Add new subclasses without changing existing code                 |
| Maintainability    | Logic lives in one place instead of being duplicated per type     |
| Flexibility        | Program to the parent type, decide the actual behavior at runtime  |

---

## Types of Polymorphism in Java

### 1. Compile-Time Polymorphism (Static)

Resolved by the compiler. Achieved through **method overloading** (same method name, different parameter lists).

```
Same method name -> Different parameters -> Decided at compile time
```

### 2. Runtime Polymorphism (Dynamic)

Resolved during execution (the focus of this chapter's code). Achieved through **method overriding** combined with upcasting.

```
Parent reference -> Child object -> Overridden method -> Decided at runtime
```

---

## Understanding the Code

```java
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
```

### Class Hierarchy

```
            Animal  (defines move())
           /   |    \
        Dog   Fish   Bird   (each overrides move())
```

---

## Line-by-Line Explanation

### The Parent Class: Animal

```java
class Animal {
    public void move() {
        System.out.println("Animal moves");
    }
}
```

- `Animal` is the base class with a `move()` method that prints `"Animal moves"`.
- This is the method that the child classes will override.

### The Child Classes: Dog, Fish, Bird

```java
class Dog extends Animal {
    public void move() {
        System.out.println("Dog runs");
    }
}
```

- `Dog`, `Fish`, and `Bird` each extend `Animal`.
- Each provides its own version of `move()`, replacing the parent's behavior. This is **method overriding**.
- `Dog` prints `"Dog runs"`, `Fish` prints `"Fish swims"`, and `Bird` prints `"Bird flies"`.

### Direct Object References

```java
Animal animal = new Animal();
Dog dog = new Dog();
Fish fish = new Fish();
Bird bird = new Bird();
```

- Each variable's reference type matches its object type.
- Calling `move()` on each runs the method belonging to that exact class.

### Parent References to Child Objects (Upcasting)

```java
Animal a1 = new Animal();
Animal a2 = new Dog();
Animal a3 = new Fish();
Animal a4 = new Bird();
```

- All four variables are of type `Animal`, but `a2`, `a3`, and `a4` point to child objects.
- This is called **upcasting** -- treating a subclass object as its superclass type.
- Even though the reference type is `Animal`, the actual object determines which `move()` runs.

---

## Step-by-Step Execution Walkthrough

When `main` runs, the following output is produced:

| Statement      | Reference Type | Actual Object | Method Called  | Output         |
|----------------|----------------|---------------|----------------|----------------|
| `animal.move()`| `Animal`       | `Animal`      | `Animal.move()`| Animal moves   |
| `dog.move()`   | `Dog`          | `Dog`         | `Dog.move()`   | Dog runs       |
| `fish.move()`  | `Fish`         | `Fish`        | `Fish.move()`  | Fish swims     |
| `bird.move()`  | `Bird`         | `Bird`        | `Bird.move()`  | Bird flies     |
| `a1.move()`    | `Animal`       | `Animal`      | `Animal.move()`| Animal moves   |
| `a2.move()`    | `Animal`       | `Dog`         | `Dog.move()`   | Dog runs       |
| `a3.move()`    | `Animal`       | `Fish`        | `Fish.move()`  | Fish swims     |
| `a4.move()`    | `Animal`       | `Bird`        | `Bird.move()`  | Bird flies     |

**Key Insight:** `a2`, `a3`, and `a4` all have the reference type `Animal`, yet they print different results. The method that runs is chosen by the *actual object*, not the *reference type*. This is the essence of runtime polymorphism.

---

## Upcasting and Dynamic Method Dispatch

### Upcasting

Upcasting is assigning a child object to a parent reference:

```java
Animal a2 = new Dog();  // Dog object, Animal reference
```

- This is always safe and happens automatically.
- The reference type (`Animal`) decides which methods are *accessible*.
- The object type (`Dog`) decides which version actually *runs*.

### Dynamic Method Dispatch

Dynamic method dispatch is the mechanism by which a call to an overridden method is resolved at runtime rather than compile time.

```java
Animal a2 = new Dog();
a2.move();  // JVM looks at the actual object (Dog) and calls Dog.move()
```

- At compile time, Java only checks that `Animal` has a `move()` method.
- At runtime, the JVM looks at the real object and dispatches to its overridden method.

---

## Compile-Time Polymorphism (Method Overloading)

While this chapter's code demonstrates runtime polymorphism, Java also supports compile-time polymorphism through **method overloading** -- multiple methods with the same name but different parameter lists in the same class.

```java
class Calculator {
    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}
```

### Rules for Method Overloading

- The method name must be the same.
- The parameter list must differ in number, type, or order.
- The return type alone is NOT enough to overload a method.
- The compiler decides which version to call based on the arguments.

---

## Overloading vs Overriding

| Feature           | Overloading                     | Overriding                          |
|-------------------|---------------------------------|-------------------------------------|
| Polymorphism Type | Compile-time (static)           | Runtime (dynamic)                   |
| Parameters        | Must be different               | Must be the same                    |
| Inheritance       | Not required                    | Required (parent-child relationship)|
| Return Type       | Can be different                | Must be the same or covariant       |
| Resolved At       | Compile time                    | Runtime                             |
| Scope             | Same class                      | Across parent and child classes     |

---

## How to Compile and Run

```bash
javac _10_Polymorphism_Runtime.java
java _10_Polymorphism_Runtime
```

> Note: The file declares `package _10_Polymorphism;`. If you compile from the parent directory, use:
>
> ```bash
> javac _10_Polymorphism/_10_Polymorphism_Runtime.java
> java _10_Polymorphism._10_Polymorphism_Runtime
> ```

### Expected Output

```
Animal moves
Dog runs
Fish swims
Bird flies
Animal moves
Dog runs
Fish swims
Bird flies
```

---

## Common Mistakes

### Mistake 1: Confusing overloading and overriding

Overloading is "same name, different parameters" in the same class (compile-time). Overriding is "same name, same parameters" across parent and child classes (runtime). They are different mechanisms.

### Mistake 2: Thinking the reference type decides the method

```java
Animal a2 = new Dog();
a2.move();  // Runs Dog.move(), NOT Animal.move()
```

The actual object type decides which overridden method runs, not the reference type.

### Mistake 3: Trying to call child-specific methods through a parent reference

```java
class Dog extends Animal {
    public void bark() { System.out.println("Bark"); }
}

Animal a2 = new Dog();
// a2.bark();  // ERROR: Animal reference does not know about bark()
```

The reference type (`Animal`) limits which methods are accessible at compile time. To call `bark()`, you must downcast: `((Dog) a2).bark();`.

### Mistake 4: Forgetting the @Override annotation

While optional, `@Override` lets the compiler verify that you are actually overriding a parent method. Without it, a typo in the method name silently creates a new method instead of overriding.

---

## Interview Questions

### Q1: What is polymorphism in Java?

**Answer:** Polymorphism means "many forms". It is the ability of a single method call or reference to behave differently based on the actual object involved. Java supports compile-time polymorphism (method overloading) and runtime polymorphism (method overriding with dynamic method dispatch).

### Q2: What are the two types of polymorphism in Java?

**Answer:** Compile-time polymorphism (also called static polymorphism), achieved through method overloading and resolved by the compiler; and runtime polymorphism (also called dynamic polymorphism), achieved through method overriding and resolved by the JVM at runtime.

### Q3: What is the difference between method overloading and method overriding?

**Answer:**

| Feature       | Overloading                    | Overriding                          |
|---------------|--------------------------------|-------------------------------------|
| Definition    | Same name, different parameters| Same name, same parameters          |
| Occurs in     | Same class                     | Parent and child classes            |
| Binding       | Compile-time (static binding)  | Runtime (dynamic binding)           |
| Return type   | Can be different               | Must be same or covariant           |
| Inheritance   | Not required                   | Required                            |

### Q4: What is dynamic method dispatch?

**Answer:** Dynamic method dispatch is the mechanism by which a call to an overridden method is resolved at runtime rather than at compile time. When a parent reference points to a child object and an overridden method is called, the JVM determines the actual object type and invokes that object's version of the method.

### Q5: What is upcasting?

**Answer:** Upcasting is assigning a subclass object to a superclass reference, such as `Animal a = new Dog();`. It is done implicitly and safely. The reference type determines which methods are accessible at compile time, while the actual object type determines which overridden method runs at runtime.

### Q6: Can return type alone be used to overload a method?

**Answer:** No. Two methods that differ only in return type but have identical names and parameter lists will cause a compilation error. Overloading requires the parameter lists to differ in number, type, or order.

### Q7: Why does a parent reference to a child object call the child's method?

**Answer:** Because of dynamic method dispatch. Java uses late binding for overridden instance methods -- the method to call is chosen based on the actual object type at runtime, not the declared reference type. So `Animal a = new Dog(); a.move();` calls `Dog`'s `move()`.

### Q8: Can static methods be overridden?

**Answer:** No. Static methods belong to the class, not to instances, so they are not subject to dynamic dispatch. If a subclass declares a static method with the same signature, it is *method hiding*, not overriding. The version called depends on the reference type, not the object type.

### Q9: How is polymorphism related to inheritance?

**Answer:** Runtime polymorphism depends on inheritance. A child class must inherit from a parent and override its method for dynamic dispatch to occur. Inheritance establishes the "is-a" relationship that allows a parent reference to hold a child object.

### Q10: What is the benefit of programming to the parent type?

**Answer:** Programming to the parent type (for example, accepting an `Animal` parameter instead of `Dog`, `Fish`, and `Bird` separately) makes code reusable and extensible. New subclasses can be added without modifying existing methods, because the correct behavior is selected automatically at runtime.

---

*This is part of a Java learning series. It builds on Chapter 8: Inheritance and Chapter 9: Super and This Keywords.*
