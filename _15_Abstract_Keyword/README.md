# Chapter 15: The `abstract` Keyword (Abstract Classes & Methods)

## Table of Contents

1. [Introduction](#introduction)
2. [Abstract Classes](#abstract-classes)
3. [Abstract Methods](#abstract-methods)
4. [The Rules of `abstract`](#the-rules-of-abstract)
5. [Concrete Classes](#concrete-classes)
6. [Using an Abstract Type](#using-an-abstract-type)
7. [Understanding the Code](#understanding-the-code)
8. [Line-by-Line Explanation](#line-by-line-explanation)
9. [How to Compile and Run](#how-to-compile-and-run)
10. [Abstract Class vs Interface](#abstract-class-vs-interface)
11. [Common Mistakes](#common-mistakes)
12. [Interview Questions](#interview-questions)

---

## Introduction

The `abstract` keyword marks something as **incomplete** -- a partial design that
must be finished by a subclass. It is the foundation of **abstraction**: hiding
implementation detail behind a contract.

There are two things you can declare `abstract`:

- an **abstract class** -- a blueprint that cannot be instantiated on its own, and
- an **abstract method** -- a method with a signature but no body, which subclasses
  must implement.

This chapter demonstrates both with a small `Car` hierarchy:

| File | Demonstrates |
|------|--------------|
| `_15_Abstract_Keyword_Code.java` | An abstract class `Car` with one abstract method (`drive`) and one concrete method (`playMusic`), implemented by the concrete class `WagonR` |

---

## Abstract Classes

An **abstract class** is declared with the `abstract` keyword and **cannot be
instantiated** directly:

```java
abstract class Car {
    public abstract void drive();      // abstract: no body
    public void playMusic() {          // concrete: has a body
        System.out.println("Playing Music");
    }
}

// Car c = new Car(); // COMPILE ERROR: Car is abstract
```

It exists to be **extended**. It can freely mix abstract methods (which subclasses
must implement) with concrete methods (which subclasses inherit ready-made).

---

## Abstract Methods

An **abstract method** declares only a signature and ends with a semicolon -- it
has **no body**:

```java
public abstract void drive();
```

Every **concrete** subclass is forced to **override** it and supply a real
implementation. This is the "contract": *any real Car must define how it drives.*

---

## The Rules of `abstract`

1. An abstract method **can only exist inside an abstract class**.
2. You **cannot create an object** of an abstract class (`new Car()` fails).
3. An abstract class **may have no abstract methods at all** -- it can still be
   abstract simply to prevent instantiation.
4. But an abstract method **must** live in an abstract class.
5. The first concrete subclass **must implement every** inherited abstract method
   (otherwise it, too, must be declared abstract).

---

## Concrete Classes

A **concrete class** is a normal, fully-implemented class. When it extends an
abstract class, it must implement all the inherited abstract methods, which makes
it "complete" and therefore instantiable:

```java
class WagonR extends Car {   // concrete class
    public void drive() {    // implements the abstract method
        System.out.println("Driving");
    }
}
```

---

## Using an Abstract Type

Although you can't instantiate an abstract class, you **can** use it as a
reference type and point it at a concrete subclass object (this is **upcasting**):

```java
Car obj = new WagonR();   // abstract reference -> concrete object
obj.drive();              // "Driving"     (WagonR's implementation)
obj.playMusic();          // "Playing Music" (inherited from Car)
```

This is how abstraction is used in practice: code is written against the abstract
type, while the actual behaviour comes from whichever concrete subclass is plugged
in.

---

## Understanding the Code

```java
abstract class Car {
    public abstract void drive();          // must be implemented by subclasses
    public void playMusic() {              // shared, ready-made behaviour
        System.out.println("Playing Music");
    }
}

class WagonR extends Car {                 // concrete
    public void drive() {
        System.out.println("Driving");
    }
}

public class _15_Abstract_Keyword_Code {
    public static void main(String[] args) {
        Car obj = new WagonR();            // upcast
        obj.drive();                       // Driving
        obj.playMusic();                   // Playing Music
    }
}
```

---

## Line-by-Line Explanation

- `abstract class Car` -- declares an incomplete class that cannot be instantiated.
- `public abstract void drive();` -- an abstract method: no body; subclasses must
  implement it.
- `public void playMusic() { ... }` -- a concrete method inherited as-is by every
  subclass.
- `class WagonR extends Car` -- a concrete subclass.
- `public void drive() { ... }` -- implements (overrides) the abstract method.
- `Car obj = new WagonR();` -- upcasts the `WagonR` object to a `Car` reference.
- `obj.drive();` -- calls `WagonR`'s overridden version -> "Driving".
- `obj.playMusic();` -- calls `Car`'s inherited concrete method -> "Playing Music".

---

## How to Compile and Run

All classes declare `package _15_Abstract_Keyword;`. Compile and run from the
parent directory using the package path:

```bash
# Compile
javac _15_Abstract_Keyword/_15_Abstract_Keyword_Code.java

# Run
java _15_Abstract_Keyword._15_Abstract_Keyword_Code
```

### Expected Output

```
Driving
Playing Music
```

---

## Abstract Class vs Interface

| | Abstract class | Interface |
|---|----------------|-----------|
| Methods | abstract **and** concrete | abstract (plus `default`/`static` since Java 8) |
| Fields | instance fields allowed | only `public static final` constants |
| Inheritance | a class extends **one** | a class implements **many** |
| Constructors | yes | no |
| Use when | classes share state/behaviour and are closely related | you only need a contract many unrelated classes can fulfil |

> A useful rule of thumb: an abstract class models an **"is-a"** relationship with
> shared code; an interface models a **"can-do"** capability.

---

## Common Mistakes

### Mistake 1: Trying to instantiate an abstract class

```java
Car c = new Car(); // COMPILE ERROR: Car is abstract; cannot be instantiated
```

Instantiate a concrete subclass instead: `Car c = new WagonR();`.

### Mistake 2: Forgetting to implement an abstract method

```java
class Sedan extends Car { } // COMPILE ERROR: must implement drive()
```

A concrete subclass must implement **every** inherited abstract method, or be
declared `abstract` itself.

### Mistake 3: Giving an abstract method a body

```java
public abstract void drive() { } // ERROR: abstract methods have NO body
```

An abstract method ends with a semicolon and has no `{ }` block.

### Mistake 4: Declaring an abstract method in a non-abstract class

```java
class Car {                       // not abstract
    public abstract void drive(); // COMPILE ERROR
}
```

If a class has even one abstract method, the class itself must be `abstract`.

---

## Interview Questions

### Q1: What is an abstract class?

**Answer:** A class declared with the `abstract` keyword that cannot be
instantiated. It is meant to be extended and can contain both abstract and
concrete methods.

### Q2: What is an abstract method?

**Answer:** A method declared without a body (ending in a semicolon). It defines a
contract that every concrete subclass must implement.

### Q3: Can an abstract class have a constructor?

**Answer:** Yes. The constructor is not used to create an abstract object
directly, but it runs (via `super(...)`) when a concrete subclass is instantiated,
to initialize inherited fields.

### Q4: Can an abstract class have no abstract methods?

**Answer:** Yes. A class can be abstract purely to prevent direct instantiation,
even if all its methods have bodies.

### Q5: Can an abstract method exist in a non-abstract class?

**Answer:** No. Any class containing an abstract method must itself be declared
`abstract`.

### Q6: What happens if a subclass doesn't implement all abstract methods?

**Answer:** It is still incomplete, so it must be declared `abstract` too;
otherwise the code won't compile.

### Q7: Can you create a reference of an abstract type?

**Answer:** Yes. You can't instantiate the abstract class, but you can use it as a
reference pointing to a concrete subclass object (`Car obj = new WagonR();`).

### Q8: What is the difference between an abstract class and an interface?

**Answer:** An abstract class can have instance fields, constructors, and concrete
methods, and a class can extend only one. An interface mainly declares a contract
(plus `default`/`static` methods since Java 8), holds only constants, and a class
can implement many. Use an abstract class for closely related classes that share
state/behaviour; use an interface for a capability many unrelated classes can
provide.

---

*This is part of a Java learning series. It builds on Inheritance and
Polymorphism, and leads into Interfaces -- together they form the basis of
abstraction in Java.*
