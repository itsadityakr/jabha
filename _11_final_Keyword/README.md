# Chapter 11: The `final` Keyword in Java

## Table of Contents

1. [Introduction](#introduction)
2. [What is the `final` Keyword](#what-is-the-final-keyword)
3. [Three Uses of `final`](#three-uses-of-final)
4. [Part 1: `final` Variables](#part-1-final-variables)
5. [Part 2: `final` Classes](#part-2-final-classes)
6. [Part 3: `final` Methods](#part-3-final-methods)
7. [Summary Table](#summary-table)
8. [How to Compile and Run](#how-to-compile-and-run)
9. [Common Mistakes](#common-mistakes)
10. [Interview Questions](#interview-questions)

---

## Introduction

The `final` keyword in Java is a non-access modifier used to apply restrictions. It tells the compiler that something cannot be changed after it has been defined. Depending on where it is used, `final` restricts a variable, a method, or an entire class.

This chapter is split across three files, one for each use:

| File | Demonstrates |
|------|--------------|
| `_11_1_final_Variable_Code.java` | `final` variables (constants and final references) |
| `_11_2_final_Class_Code.java`    | `final` classes (cannot be extended)               |
| `_11_3_final_Method_Code.java`   | `final` methods (cannot be overridden)             |

---

## What is the `final` Keyword

`final` means "this cannot change". It is the keyword you use whenever you want to lock something down so that it cannot be reassigned, overridden, or extended.

- A `final` **variable** can be assigned only once -- it becomes a constant.
- A `final` **method** cannot be overridden by a subclass.
- A `final` **class** cannot be extended (subclassed) at all.

---

## Three Uses of `final`

```
            final
          /   |   \
   variable  method  class
   (constant) (no    (no
              override) subclass)
```

Each use applies the same idea -- "no further change" -- to a different building block of the language.

---

## Part 1: `final` Variables

### Code

```java
package _11_final_Keyword;

final class Base {
    public final void show() {
        System.out.println("Base show");
    }
}

/*
 * class Derived extends Base {
 * // ERROR: Cannot override final method
 * public void show() {
 * System.out.println("Derived show");
 * }
 * }
 */

public class _11_1_final_Variable_Code {
    final int MAX = 100; // Compile-time constant

    public static void main(String[] args) {
        final int AGE = 20; // Reusable constant

        // AGE = 30; // ERROR: final variable cannot be reassigned
        // MAX = 50; // ERROR: final variable cannot be reassigned

        final double PI = 3.14159;
        System.out.println("Value of PI: " + PI);

        // Final variables can be used in expressions
        double circumference = 2 * PI * 5;
        System.out.println("Circumference: " + circumference);

        // Final variables with object references
        final StringBuilder sb = new StringBuilder("Hello");
        sb.append(" World"); // OK: modifying content
        System.out.println(sb);

        // sb = new StringBuilder("Goodbye"); // ERROR: final reference cannot be reassigned
    }
}
```

### Explanation

- `final int MAX = 100;` is a final **instance variable** (a constant tied to each object). It cannot be reassigned.
- `final int AGE = 20;` and `final double PI = 3.14159;` are final **local variables**. Once set, they cannot change. Attempting `AGE = 30;` is a compile-time error.
- Final variables can be freely **read** and used in expressions -- for example, `2 * PI * 5`.
- `final StringBuilder sb = new StringBuilder("Hello");` makes the **reference** `sb` final, not the object it points to:
  - `sb.append(" World");` is allowed -- you are modifying the object's contents.
  - `sb = new StringBuilder("Goodbye");` is NOT allowed -- you cannot point `sb` at a different object.

### Key Insight

For reference types, `final` locks the reference, not the object it points to. The object's internal state can still change.

### Expected Output

```
Value of PI: 3.14159
Circumference: 31.4159
Hello World
```

---

## Part 2: `final` Classes

### Code

```java
package _11_final_Keyword;

public class _11_2_final_Class_Code {
    public static void main(String[] args) {
        FinalClass finalClass = new FinalClass();
        finalClass.show();
    }
}

final class FinalClass {
    public void show() {
        System.out.println("Final class method");
    }
}

// class A extends FinalClass {
//     public void show() {
//         System.out.println("Derived class method");
//     }
// }

/*
 * class Derived extends FinalClass {
 * // ERROR: Cannot extend final class
 * public void show() {
 * System.out.println("Derived class method");
 * }
 * }
 */
```

### Explanation

- `FinalClass` is declared `final`, so it cannot be extended. The commented-out `class A extends FinalClass` and `class Derived extends FinalClass` would both cause compile-time errors.
- You can still **create objects** of a final class and call its methods, as `main` does with `new FinalClass()` and `finalClass.show()`.
- This is exactly how Java's own `String` class is built -- it is `final` so no one can subclass and alter its behavior.

### Why Make a Class `final`?

- To prevent inheritance for security or design reasons.
- To guarantee that the class's behavior cannot be altered by a subclass.
- To make the class safely immutable.

### Expected Output

```
Final class method
```

---

## Part 3: `final` Methods

### Code

```java
package _11_final_Keyword;

class A {
    public final void show() {
        System.out.println("A show");
    }
}

class B extends A {
    // Uncommenting this override causes a COMPILE ERROR:
    // "show() in B cannot override show() in A; overriding method is final"
    // public void show() {
    //     System.out.println("B show");
    // }
}

public class _11_3_final_Method_Code {
    public static void main(String[] args) {
        B b = new B();
        b.show();   // calls the inherited final method from A
    }
}
```

### Explanation

- `A` declares `show()` as `final`. This means no subclass is allowed to override it.
- In `B`, the `show()` override is **commented out**, so `B` simply inherits `A`'s final `show()`. The program compiles and `b.show()` prints `A show`.
- If you **uncomment** `B`'s `show()`, the file fails to compile with: *"show() in B cannot override show() in A; overriding method is final"* -- which is exactly the rule this example teaches.
- A `final` method can still be **inherited** and **called** -- it just cannot be **redefined**.

### Key Insight

`final` on a method blocks overriding (changing behavior in a subclass), not inheritance.

### Why Make a Method `final`?

- To preserve critical behavior that subclasses must not change.
- To prevent accidental overriding of methods that the class relies on internally.

---

## Summary Table

| Applied To | Meaning                                   | What is Blocked            | Still Allowed                        |
|------------|-------------------------------------------|----------------------------|--------------------------------------|
| Variable   | Value/reference assigned only once        | Reassignment               | Reading; modifying referenced object |
| Method     | Cannot be redefined in a subclass         | Overriding                 | Inheriting and calling the method    |
| Class      | Cannot be subclassed                      | Extending (`extends`)      | Creating objects of the class        |

---

## How to Compile and Run

All three files declare `package _11_final_Keyword;`. Compile and run them from the parent directory using the package path:

```bash
# Final variable demo
javac _11_final_Keyword/_11_1_final_Variable_Code.java
java _11_final_Keyword._11_1_final_Variable_Code

# Final class demo
javac _11_final_Keyword/_11_2_final_Class_Code.java
java _11_final_Keyword._11_2_final_Class_Code

# Final method demo (override in B is commented out, so it prints "A show")
javac _11_final_Keyword/_11_3_final_Method_Code.java
java _11_final_Keyword._11_3_final_Method_Code
```

> In `_11_3_final_Method_Code.java`, `B`'s `show()` override is commented out, so the file compiles and prints `A show`. Uncommenting that override produces the compile-time error "overriding method is final" -- which is the lesson the example teaches.

---

## Common Mistakes

### Mistake 1: Thinking `final` on a reference makes the object immutable

```java
final StringBuilder sb = new StringBuilder("Hello");
sb.append(" World");           // Allowed -- the contents are NOT final
sb = new StringBuilder("Hi");  // Error -- the reference IS final
```

`final` locks the reference, not the contents of the object it points to.

### Mistake 2: Confusing `final` with `finally` and `finalize()`

- `final` -- a keyword that restricts variables, methods, and classes.
- `finally` -- a block in exception handling that always runs.
- `finalize()` -- a (deprecated) method called by the garbage collector before an object is destroyed.

These three are unrelated despite the similar names.

### Mistake 3: Trying to override a `final` method

A subclass cannot override a `final` method (as `_11_3_final_Method_Code.java` shows). The compiler rejects it. To allow overriding, remove `final` from the parent method.

### Mistake 4: Trying to extend a `final` class

A `final` class cannot appear after `extends`. Use composition (holding an instance of the class) instead of inheritance.

---

## Interview Questions

### Q1: What are the three uses of the `final` keyword in Java?

**Answer:** `final` can be applied to a variable (it becomes a constant that can be assigned only once), a method (it cannot be overridden by a subclass), and a class (it cannot be extended/subclassed).

### Q2: Does `final` make an object immutable?

**Answer:** No. When applied to a reference variable, `final` only prevents the reference from being reassigned. The object's internal state can still be changed. For example, a `final StringBuilder` can still have text appended to it, but the variable cannot be pointed at a new `StringBuilder`.

### Q3: Can a `final` method be inherited?

**Answer:** Yes. A `final` method is inherited by subclasses and can be called normally. `final` only prevents the method from being *overridden*, not from being inherited or used.

### Q4: Why is the `String` class declared `final`?

**Answer:** To guarantee immutability and security. Because `String` cannot be subclassed, no one can create a malicious subclass that changes string behavior, and the JVM can safely cache and share string literals.

### Q5: What is the difference between `final`, `finally`, and `finalize()`?

**Answer:** `final` is a modifier that restricts variables, methods, and classes. `finally` is a block used with try-catch that always executes regardless of whether an exception occurs. `finalize()` is a method historically called by the garbage collector before reclaiming an object (now deprecated).

### Q6: Can a `final` variable be initialized later (not at declaration)?

**Answer:** Yes, a `final` variable can be a "blank final" -- declared without a value and assigned exactly once, either in a constructor (for instance variables) or before first use (for local variables). After that single assignment, it cannot be changed.

### Q7: Can a constructor be declared `final`?

**Answer:** No. Constructors are never inherited and therefore cannot be overridden, so marking a constructor `final` is meaningless and is a compile-time error.

### Q8: What happens if you try to override a `final` method?

**Answer:** The code fails to compile with an error such as "overridden method is final". The subclass is not permitted to provide a new implementation. This is exactly what `_11_3_final_Method_Code.java` demonstrates.

### Q9: Can a `final` class have non-final methods?

**Answer:** Yes. A `final` class can contain ordinary (non-final) methods, as `FinalClass` does with `show()`. Since the class itself cannot be subclassed, those methods can never be overridden anyway, so marking them `final` is unnecessary.

### Q10: When should you use the `final` keyword?

**Answer:** Use `final` for constants (`final` variables) to express values that must not change, for methods whose behavior is critical and must not be altered by subclasses, and for classes that are designed to be complete and not extended (often for immutability or security).

---

*This is part of a Java learning series. It builds on Chapter 8: Inheritance and Chapter 10: Polymorphism, since `final` methods and classes directly control overriding and inheritance.*
