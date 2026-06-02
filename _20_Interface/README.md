# Chapter 20: Interfaces in Java

## Table of Contents

1. [Introduction](#introduction)
2. [What is an Interface](#what-is-an-interface)
3. [`extends` vs `implements`](#extends-vs-implements)
4. [Understanding the Code](#understanding-the-code)
5. [Line-by-Line Explanation](#line-by-line-explanation)
6. [Fields in an Interface](#fields-in-an-interface)
7. [Why Interface Fields Are `public static final`](#why-interface-fields-are-public-static-final)
8. [Multiple Inheritance with Interfaces](#multiple-inheritance-with-interfaces)
9. [Interface vs Abstract Class](#interface-vs-abstract-class)
10. [How to Compile and Run](#how-to-compile-and-run)
11. [Common Mistakes](#common-mistakes)
12. [Interview Questions](#interview-questions)

---

## Introduction

An **interface** is a fully abstract blueprint. It declares *what* a class can do (a set of method signatures) without saying *how* it does it. Any class that "signs the contract" by implementing the interface must provide the body for every method.

Interfaces are how Java achieves **100% abstraction** and a safe form of **multiple inheritance**. This builds directly on the `abstract` keyword from Chapter 18.

---

## What is an Interface

An interface is declared with the `interface` keyword instead of `class`:

```java
interface A {
    void show();
    void config();
}
```

Key facts:

- Every method in an interface is **`public` and `abstract` by default** — you do not need to write those keywords.
- An interface cannot be instantiated with `new` (it has no method bodies to run).
- A class connects to an interface using the `implements` keyword and must override **every** method.
- Since Java 8, interfaces can also have `default` and `static` methods (with bodies), but the classic role is a pure contract.

---

## `extends` vs `implements`

The relationship keyword depends on what is connecting to what:

| From      | To        | Keyword      |
|-----------|-----------|--------------|
| class     | class     | `extends`    |
| class     | interface | `implements` |
| interface | class     | (not allowed) |
| interface | interface | `extends`    |

A class can `implement` many interfaces at once, but can only `extend` one class.

---

## Understanding the Code

```java
interface A { // methods are public + abstract by default

    // fields are public static final by default and MUST be initialised
    String area = "In Interfaces we need to initialize the fields by default";

    void show();   // no body, no 'public abstract' needed
    void config();
}

interface X {
    void run();
}

class B implements A, X { // implement multiple interfaces, separated by commas
    public void show() {
        System.out.println("Show");
    }

    public void config() {
        System.out.println("Config");
    }

    public void run() {
        System.out.println("Run");
    }
}

public class _20_Interface_Code {
    public static void main(String[] args) {
        A obj;
        // obj = new A(); // ERROR: an interface cannot be instantiated
        obj = new B();    // a reference of type A can hold a B object

        obj.show();
        obj.config();
    }
}
```

---

## Line-by-Line Explanation

### `interface A { ... }`

- Declares an interface named `A`. It is a pure contract.
- `void show();` and `void config();` are abstract methods — only signatures, no bodies.

### `class B implements A, X { ... }`

- `B` promises to provide bodies for **all** methods of both `A` and `X`.
- The overriding methods must be declared `public`, because interface methods are `public` by default and an override cannot reduce visibility.

### `A obj; obj = new B();`

- `obj` is a reference of the **interface type** `A`.
- It points to an object of the implementing class `B`. This is upcasting (Chapter 16): you program to the interface, not the concrete class.
- `obj.show()` runs `B`'s version of `show()` at runtime (dynamic dispatch, Chapter 13).

---

## Fields in an Interface

```java
interface A {
    String area = "...";   // implicitly public static final
}
```

- Every field in an interface is automatically **`public static final`** — a constant.
- Because it is `final`, it **must be initialised** at declaration. Leaving it uninitialised (`int age;`) is a compile error.

---

## Why Interface Fields Are `public static final`

An interface is just a **blueprint**, so its fields behave like shared constants, not per-object state:

- **Why `static`?** A blueprint has no objects of its own, so the field belongs to the interface itself, shared by all implementers — not duplicated per object.
- **Why `final`?** If implementers could change the value, the blueprint would no longer be a fixed contract. A constant keeps the contract reliable.
- **Why this matters:** if every object had its own changeable copy (say `obj1.age = 20`, `obj2.age = 30`), the "shared contract" value would be ambiguous. Making fields `static final` removes that ambiguity.

---

## Multiple Inheritance with Interfaces

Java does **not** allow a class to extend more than one class (to avoid the "diamond problem" of conflicting inherited code). But a class **can implement many interfaces**:

```java
class B implements A, X { ... }
```

Because interfaces only declare method signatures (no conflicting bodies), combining several of them is safe. This is how Java gives you multiple inheritance of *type*.

---

## Interface vs Abstract Class

| Aspect                | Interface                                  | Abstract Class                          |
|-----------------------|--------------------------------------------|-----------------------------------------|
| Keyword to connect    | `implements`                               | `extends`                               |
| Multiple inheritance  | Yes (implement many)                       | No (extend only one)                    |
| Methods               | Abstract by default (+ `default`/`static`) | Mix of abstract and concrete            |
| Fields                | `public static final` constants only       | Any kind of field, including state      |
| Constructor           | No                                         | Yes                                     |
| When to use           | A capability/contract many unrelated types share | A common base with shared code/state |

---

## How to Compile and Run

```bash
javac _20_Interface/_20_Interface_Code.java
java _20_Interface._20_Interface_Code
```

### Expected Output

```
Show
Config
```

---

## Common Mistakes

### Mistake 1: Trying to instantiate an interface

```java
A obj = new A();  // ERROR: A is abstract; cannot be instantiated
A obj = new B();  // correct: use an implementing class
```

### Mistake 2: Not overriding every method

```java
class B implements A, X {
    public void show() { ... }
    // missing config() and run() -> compile error
}
```

A non-abstract class must implement **all** inherited interface methods.

### Mistake 3: Reducing visibility of an overridden method

```java
class B implements A {
    void show() { ... }  // ERROR: must be public (interface methods are public)
}
```

### Mistake 4: Forgetting to initialise an interface field

```java
interface A {
    int age;        // ERROR: must be initialised (it is final)
    int age = 0;    // correct
}
```

---

## Interview Questions

### Q1: What is an interface in Java?

**Answer:** An interface is a reference type that declares a set of abstract methods (a contract). Classes implement the interface and provide the method bodies. By default its methods are `public abstract` and its fields are `public static final`.

### Q2: What is the difference between an interface and an abstract class?

**Answer:** A class can implement multiple interfaces but extend only one abstract class. Interfaces traditionally contain only abstract methods and constants and have no constructor, while abstract classes can have constructors, instance fields, and a mix of abstract and concrete methods. Use an interface for a capability shared by unrelated types; use an abstract class for a common base with shared code/state.

### Q3: Why are interface methods `public` and `abstract` by default?

**Answer:** An interface is a contract meant to be used by other code, so its methods are `public`. They are `abstract` because the interface only specifies *what* must be done, leaving *how* to the implementing class.

### Q4: Why are interface fields `public static final`?

**Answer:** An interface is a blueprint with no objects of its own, so fields are `static` (shared) and `final` (constant) to keep the contract fixed and unambiguous. Because they are `final`, they must be initialised when declared.

### Q5: How does Java achieve multiple inheritance through interfaces?

**Answer:** A class can implement several interfaces at once (`class B implements A, X`). Since interfaces declare only method signatures and no conflicting bodies, there is no diamond-problem ambiguity, so combining them is safe.

### Q6: Can an interface extend another interface?

**Answer:** Yes. An interface uses `extends` (not `implements`) to inherit from one or more other interfaces, combining their contracts.

### Q7: Can you create an object of an interface?

**Answer:** No. An interface cannot be instantiated because its methods have no bodies. You instantiate a class that implements it, and you may hold that object in a reference variable of the interface type.

---

*This is part of a Java learning series. Proceed to Chapter 21: Enums to continue learning.*
