# Chapter 21: Enums in Java

## Table of Contents

1. [Introduction](#introduction)
2. [What is an Enum](#what-is-an-enum)
3. [Why Use Enums](#why-use-enums)
4. [Example 1: A Basic Enum](#example-1-a-basic-enum)
5. [Useful Enum Methods](#useful-enum-methods)
6. [Comparing Enums](#comparing-enums)
7. [Enums in a `switch`](#enums-in-a-switch)
8. [Example 2: Enums with Fields and Constructors](#example-2-enums-with-fields-and-constructors)
9. [How Enums Work Internally](#how-enums-work-internally)
10. [How to Compile and Run](#how-to-compile-and-run)
11. [Common Mistakes](#common-mistakes)
12. [Interview Questions](#interview-questions)

---

## Introduction

An **enum** (short for *enumeration*) is a special type that represents a fixed set of named constants. When a variable can only take one value out of a small, known list — the status of a request, the days of the week, the suits in a deck of cards — an enum makes that list explicit and type-safe.

This chapter has two programs:

| File | Topic |
|------|-------|
| `_21_1_enums_Code.java` | A basic enum, its methods, comparison, and use in `switch` |
| `_21_2_enums_Code.java` | An enum with fields, constructors, and methods |

---

## What is an Enum

An enum is declared with the `enum` keyword. Each named value listed inside is a constant.

```java
enum Status {
    Running, Failed, Pending, Success;
}
```

Here `Status` behaves like a class, and `Running`, `Failed`, `Pending`, and `Success` are its only objects. You can never create another `Status` value beyond these four.

```java
Status s = Status.Running;
```

---

## Why Use Enums

Imagine a server that returns a status. Without enums you might use raw numbers or strings:

```java
int status = 1;           // what does 1 mean?
String status = "Runing"; // typo compiles fine, breaks at runtime
```

With an enum:

```java
Status status = Status.Running; // self-documenting and type-safe
```

Benefits:

- **Type safety** – only the defined values are allowed; a typo is a compile error.
- **Readability** – `Status.Running` is clearer than `1`.
- **Fixed set** – the compiler guarantees no other values exist.
- **Works with `switch`** – clean branching over the constants.

> Enums **cannot be extended** — you cannot add new constants by subclassing. The set is final by design.

---

## Example 1: A Basic Enum

```java
enum Status {
    Running, Failed, Pending, Success;
}

public class _21_1_enums_Code {
    public static void main(String[] args) {
        Status s = Status.Running;

        System.out.println(s);            // Running   (the name)
        System.out.println(s.ordinal());  // 0         (the position)

        Status[] all = Status.values();   // every constant
        for (Status status : all) {
            System.out.println(status);
        }

        if (s == Status.Running) {        // compare with ==
            System.out.println("Running");
        }

        switch (s) {                      // enum in a switch
            case Running: System.out.println("Running"); break;
            case Failed:  System.out.println("Failed");  break;
            case Pending: System.out.println("Pending"); break;
            case Success: System.out.println("Success"); break;
            default:      System.out.println("Unknown");
        }
    }
}
```

---

## Useful Enum Methods

Every enum automatically gets these methods:

| Method        | Returns                                              | Example                       |
|---------------|-----------------------------------------------------|-------------------------------|
| `name()`      | the exact name of the constant as a `String`        | `Status.Running.name()` -> `"Running"` |
| `ordinal()`   | the zero-based position in the declaration order    | `Status.Running.ordinal()` -> `0` |
| `values()`    | an array of all constants (static method)           | `Status.values()`            |
| `valueOf("x")`| the constant matching the given name                | `Status.valueOf("Failed")`   |

> Do **not** use `ordinal()` as a stored/business value. If you reorder the constants, every ordinal changes. Use it only for display or iteration.

---

## Comparing Enums

Enum constants are singletons (only one object per constant), so you can compare them with `==`:

```java
if (s == Status.Running) { ... }
```

`==` is safe here, is null-safe, and reads clearly. `.equals()` also works but `==` is the idiomatic choice for enums.

---

## Enums in a `switch`

A `switch` pairs naturally with an enum. Inside the `case` labels you write the constant name **without** the enum prefix:

```java
switch (s) {
    case Running:   // not Status.Running
        ...
        break;
}
```

The compiler also helps you spot a `switch` that forgets one of the constants.

---

## Example 2: Enums with Fields and Constructors

Enums are full classes, so each constant can carry data. Here each laptop constant stores a price.

```java
enum Laptop {
    Alienware(120000),
    Lenovo(100000),
    HP,                 // uses the no-argument constructor
    Dell(130000);

    private int price;

    Laptop() {          // called for HP
        this.price = 110000;
    }

    Laptop(int price) { // called for the others
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}

public class _21_2_enums_Code {
    public static void main(String[] args) {
        Laptop lap = Laptop.Alienware;
        System.out.println(lap + " : " + lap.getPrice()); // Alienware : 120000
        System.out.println(lap.ordinal());                // 0
        System.out.println(lap.name());                   // Alienware
    }
}
```

**What happens:**

- The value in parentheses after a constant (`Alienware(120000)`) is passed to the matching enum **constructor**.
- `HP` has no parentheses, so the **no-argument constructor** runs and assigns the default price `110000`.
- An enum constructor is always **private** in effect — you can never call `new Laptop()` yourself. The constants are created once, when the enum is loaded.

---

## How Enums Work Internally

Behind the scenes, an enum is compiled to a special `final` class that extends `java.lang.Enum`. Each constant becomes a `public static final` object of that class, created once. This is why:

- The set of values is fixed and an enum **cannot be subclassed**.
- Constants are singletons, so `==` comparison is correct.
- They can have fields, constructors, and methods like any other class.

---

## How to Compile and Run

```bash
javac _21_enums/_21_1_enums_Code.java
java _21_enums._21_1_enums_Code

javac _21_enums/_21_2_enums_Code.java
java _21_enums._21_2_enums_Code
```

### Expected Output (Example 2)

```
Alienware : 120000
0
Alienware
```

---

## Common Mistakes

### Mistake 1: Trying to instantiate an enum

```java
Status s = new Status();  // ERROR: enum constructors are not accessible
Status s = Status.Running; // correct
```

### Mistake 2: Prefixing the enum name inside a `switch` case

```java
switch (s) {
    case Status.Running:  // ERROR
    case Running:         // correct
}
```

### Mistake 3: Relying on `ordinal()` as a permanent value

```java
// If you reorder the constants, ordinal() values change.
// Store an explicit field instead if the number has meaning.
```

### Mistake 4: Forgetting the semicolon before members

```java
enum Laptop {
    HP, Dell   // ERROR: need a ';' here before the field/constructor
    private int price;
}
```

When an enum has fields or methods, the list of constants must end with a semicolon.

---

## Interview Questions

### Q1: What is an enum in Java?

**Answer:** An enum is a special type that defines a fixed set of named constants. Each constant is a singleton object of the enum type. Enums are type-safe and are used when a variable should only hold one value from a known list.

### Q2: Can an enum have a constructor?

**Answer:** Yes. An enum can have constructors, fields, and methods. The constructor is effectively private and is called once for each constant when the enum is loaded. You provide arguments in parentheses after the constant name.

### Q3: Why can't you create an enum object with `new`?

**Answer:** Enum constructors are not accessible from outside the enum. The fixed set of constants is created automatically when the enum class is loaded, so creating more instances would break the guarantee that the value set is fixed.

### Q4: What is the difference between `name()` and `ordinal()`?

**Answer:** `name()` returns the constant's name as a String; `ordinal()` returns its zero-based position in the declaration order. `ordinal()` should not be used as a stored business value because it changes if the constants are reordered.

### Q5: Can you use an enum in a `switch` statement?

**Answer:** Yes. Enums work cleanly in a `switch`, and the case labels use the constant name without the enum prefix (e.g. `case Running:`).

### Q6: Can an enum implement an interface or extend a class?

**Answer:** An enum can implement interfaces, but it cannot extend a class because it already implicitly extends `java.lang.Enum`. For the same reason, an enum cannot be subclassed.

### Q7: How do you compare two enum values, and why?

**Answer:** Use `==`. Because each enum constant is a singleton, `==` compares the correct identity, is null-safe, and is the idiomatic approach. `.equals()` works too but is not needed.

---

*This is the final chapter of the Java learning series. Congratulations on completing it — revisit any chapter to reinforce the fundamentals.*
