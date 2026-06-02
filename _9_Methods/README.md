# Chapter 9: Methods in Java

## Table of Contents

1. [Introduction](#introduction)
2. [What is a Method](#what-is-a-method)
3. [Anatomy of a Method](#anatomy-of-a-method)
4. [Parameters vs Arguments](#parameters-vs-arguments)
5. [Return Values and `void`](#return-values-and-void)
6. [Method Overloading](#method-overloading)
7. [Method Overriding](#method-overriding)
8. [Overloading vs Overriding](#overloading-vs-overriding)
9. [How to Compile and Run](#how-to-compile-and-run)
10. [Common Mistakes](#common-mistakes)
11. [Interview Questions](#interview-questions)

---

## Introduction

A **method** is a named block of code that performs a specific task. You define it once and call it whenever you need it. Methods make programs shorter, easier to read, and easier to fix, because the logic lives in one place.

This chapter has three programs:

| File | Topic |
|------|-------|
| `_9_1_Methods_Basics_Code.java`    | Declaring, calling, returning, `void` |
| `_9_2_Method_Overloading_Code.java`| Same name, different parameters (compile-time) |
| `_9_3_Method_Overriding_Code.java` | Child redefines a parent method (run-time) |

You already saw a method in Chapter 8 (`Calculator.add`). This chapter goes deeper.

---

## What is a Method

Instead of repeating the same code, you wrap it in a method and call it by name:

```java
static int add(int a, int b) {
    return a + b;
}

int sum = add(5, 3); // calls the method, sum becomes 8
```

Benefits: **reuse** (write once, call many times), **readability** (a good name explains intent), and **maintainability** (fix a bug in one place).

---

## Anatomy of a Method

```java
public static int add(int num1, int num2) {
//  |       |     |    |        |
//  |       |     |    |        second parameter
//  |       |     |    first parameter
//  |       |     return type (the kind of value sent back)
//  |       modifier (static = belongs to the class)
//  access modifier (who can call it)
    return num1 + num2; // method body
}
```

| Part            | Meaning                                                        |
|-----------------|----------------------------------------------------------------|
| Access modifier | Who can call it (`public`, `private`, …)                       |
| `static`        | Belongs to the class; callable without creating an object      |
| Return type     | The type of value returned, or `void` for nothing             |
| Method name     | What you call it (convention: starts lowercase, a verb)        |
| Parameters      | Inputs the method needs, in parentheses                        |
| Body            | The code that runs, in braces                                  |

---

## Parameters vs Arguments

- **Parameters** are the variables in the method *declaration*: `add(int num1, int num2)`.
- **Arguments** are the actual values you *pass* when calling: `add(5, 3)` — here `5` and `3` are arguments.

---

## Return Values and `void`

A method either returns a value or returns nothing:

```java
static int square(int n) {  // returns an int
    return n * n;
}

static void greet(String name) { // returns nothing
    System.out.println("Hello, " + name);
}
```

- If the return type is not `void`, the method **must** `return` a value of that type.
- `return` also immediately exits the method.
- A `void` method can still use a bare `return;` to exit early.

---

## Method Overloading

**Overloading** = multiple methods with the **same name** but **different parameter lists** (different number, types, or order). The compiler picks the right one based on the arguments. This is decided at **compile time** (also called *static* or *compile-time polymorphism*).

```java
static int    add(int a, int b)        { return a + b; }
static int    add(int a, int b, int c) { return a + b + c; } // different count
static double add(double a, double b)  { return a + b; }     // different type
static String add(int a, String b)     { return a + b; }     // different types/order
```

```java
add(2, 3);        // -> 5     (calls the two-int version)
add(2, 3, 4);     // -> 9     (calls the three-int version)
add(2.5, 3.5);    // -> 6.0   (calls the double version)
add(5, "th");     // -> "5th" (calls the int+String version)
```

> **The return type alone does not count.** Two methods that differ *only* by return type are a compile error. The **parameter lists** must differ.

---

## Method Overriding

**Overriding** = a **child class** provides its **own version** of a method already defined in its **parent**, with the **same name and same parameters**. Which version runs is decided at **run time** by the actual object — this is *runtime* (dynamic) polymorphism.

```java
class Animal {
    void sound() { System.out.println("Some generic animal sound"); }
}

class Dog extends Animal {
    @Override
    void sound() { System.out.println("Woof"); }
}
```

```java
Animal a = new Dog(); // parent reference, child object
a.sound();            // prints "Woof" — the object's version runs
```

The `@Override` annotation is optional but recommended: the compiler will error if the method does not actually override anything (e.g. a typo in the name), catching bugs early.

> Overriding builds on **inheritance**, covered fully in Chapter 15, and underpins **polymorphism** in Chapter 17.

---

## Overloading vs Overriding

| Aspect            | Overloading                          | Overriding                          |
|-------------------|--------------------------------------|-------------------------------------|
| Method name       | Same                                 | Same                                |
| Parameter list    | **Different**                        | **Same**                            |
| Where             | Same class                           | Parent class and child class        |
| Inheritance       | Not required                         | Required (`extends`)                |
| Decided at        | Compile time (static)                | Run time (dynamic)                  |
| Also called       | Compile-time polymorphism            | Runtime polymorphism                |

---

## How to Compile and Run

```bash
javac _9_Methods/_9_1_Methods_Basics_Code.java
java _9_Methods._9_1_Methods_Basics_Code

javac _9_Methods/_9_2_Method_Overloading_Code.java
java _9_Methods._9_2_Method_Overloading_Code

javac _9_Methods/_9_3_Method_Overriding_Code.java
java _9_Methods._9_3_Method_Overriding_Code
```

---

## Common Mistakes

### Mistake 1: Overloading by return type only

```java
int  value() { return 1; }
double value() { return 1.0; } // ERROR: same name and parameters
```

The parameter lists must differ, not just the return type.

### Mistake 2: A non-void method that does not return on every path

```java
static int max(int a, int b) {
    if (a > b) return a;
    // ERROR: missing return when a <= b
}
```

### Mistake 3: Thinking the reference type chooses the overridden method

```java
Animal a = new Dog();
a.sound(); // runs Dog's sound(), NOT Animal's — the object decides
```

### Mistake 4: Changing the parameter list when you meant to override

```java
class Dog extends Animal {
    void sound(String s) { ... } // this OVERLOADS, it does not OVERRIDE
}
```

Use `@Override` to catch this — the compiler will complain.

---

## Interview Questions

### Q1: What is a method in Java?

**Answer:** A method is a named block of code that performs a task. It can take parameters as input and may return a value. Methods enable code reuse, readability, and easier maintenance.

### Q2: What is the difference between a parameter and an argument?

**Answer:** A parameter is the variable declared in the method definition; an argument is the actual value passed when the method is called. In `add(int a, int b)`, `a` and `b` are parameters; in `add(5, 3)`, `5` and `3` are arguments.

### Q3: What is method overloading?

**Answer:** Defining multiple methods with the same name but different parameter lists (number, type, or order). The compiler chooses which to call based on the arguments. It is resolved at compile time.

### Q4: What is method overriding?

**Answer:** A subclass provides its own implementation of a method already defined in its superclass, with the same name and parameter list. The version that runs is chosen at run time based on the actual object type.

### Q5: Can you overload a method by changing only the return type?

**Answer:** No. Overloaded methods must differ in their parameter lists. Two methods with the same name and parameters but different return types cause a compile error.

### Q6: What is the difference between overloading and overriding?

**Answer:** Overloading uses the same name with different parameters in the same class and is resolved at compile time (compile-time polymorphism). Overriding uses the same name and parameters across a parent and child class, requires inheritance, and is resolved at run time (runtime polymorphism).

### Q7: What does the `@Override` annotation do?

**Answer:** It tells the compiler the method is intended to override a superclass method. If it does not actually override one (for example due to a wrong name or parameter list), the compiler reports an error, which prevents subtle bugs.

---

*This is part of a Java learning series. Proceed to Chapter 10: Arrays to continue learning.*
