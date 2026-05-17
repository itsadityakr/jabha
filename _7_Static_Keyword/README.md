# Chapter 7: Static Keyword in Java

## Table of Contents

1. [Introduction](#introduction)
2. [What is the Static Keyword](#what-is-the-static-keyword)
3. [Static Variables vs Instance Variables](#static-variables-vs-instance-variables)
4. [Understanding the Code](#understanding-the-code)
5. [Line-by-Line Explanation](#line-by-line-explanation)
6. [Step-by-Step Execution Walkthrough](#step-by-step-execution-walkthrough)
7. [How Static Variables Work in Memory](#how-static-variables-work-in-memory)
8. [Static Methods](#static-methods)
9. [Static Block](#static-block)
10. [When to Use Static](#when-to-use-static)
11. [How to Compile and Run](#how-to-compile-and-run)
12. [Common Mistakes](#common-mistakes)
13. [Interview Questions](#interview-questions)

---

## Introduction

The `static` keyword is one of the most important and frequently misunderstood concepts in Java. It fundamentally changes the behavior of a variable, method, or block by associating it with the class itself rather than with any specific object of that class.

This program demonstrates how a static variable behaves differently from an instance variable. Specifically, it shows that changing a static variable affects all objects of the class because there is only one copy of a static variable shared among all instances.

---

## What is the Static Keyword

The `static` keyword in Java indicates that a member (variable, method, or block) belongs to the class itself, not to any specific instance (object) of the class. This means:

- A static variable has only one copy in memory, shared by all objects of the class.
- A static method can be called without creating an object of the class.
- A static block is executed once when the class is first loaded into memory.

Think of it like the Wi-Fi password in a hostel. Every room (object) has its own room number and furniture (instance variables), but the Wi-Fi password (static variable) is the same for every room. If the hostel changes the Wi-Fi password, it changes for everyone.

---

## Static Variables vs Instance Variables

| Feature                  | Static Variable                         | Instance Variable                      |
|--------------------------|----------------------------------------|----------------------------------------|
| Belongs to               | The class                              | The object                             |
| Number of copies         | One (shared by all objects)            | One per object                         |
| Memory allocation        | When class is loaded                   | When object is created                 |
| Accessed via             | ClassName.variableName (preferred)     | objectName.variableName                |
| Default value            | Same as instance variables (0, null, etc.) | Same (0, null, etc.)              |
| Also known as            | Class variable                         | Non-static variable                    |

---

## Understanding the Code

```java
class Phone{
    static String name;
    int price;

    public void show(){
        System.out.println(name + " : " + price);
    }
}

public class _7_Static_Keyword {
    public static void main(String[] args) {
        Phone obj1 = new Phone();
        Phone.name = "Samsung";
        obj1.price = 1_45_000;

        Phone obj2 = new Phone();
        Phone.name = "Apple";
        obj2.price = 90_000;

        Phone.name = "Phone";

        obj1.show();
        obj2.show();
    }
}
```

---

## Line-by-Line Explanation

### `static String name;`

- Declares `name` as a static variable.
- There is only ONE copy of `name` in memory, regardless of how many `Phone` objects you create.
- It is accessed using `Phone.name`, not `obj.name` (though `obj.name` would also work, it is discouraged).

### `int price;`

- Declares `price` as an instance variable.
- Each `Phone` object gets its own separate copy of `price`.
- `obj1.price` and `obj2.price` are independent of each other.

### `Phone.name = "Samsung";`

- Sets the static variable `name` to `"Samsung"`.
- Since `name` is static, this change is visible through all objects.
- Accessed using the class name `Phone.name`, which is the correct way to access static members.

### `obj1.price = 1_45_000;`

- Sets the `price` of `obj1` to 145000.
- Note: The underscores (`_`) in `1_45_000` are numeric literals separators introduced in Java 7. They make large numbers easier to read but have no effect on the value.
- `1_45_000` is the same as `145000`.

### `Phone.name = "Apple";`

- Changes the static variable `name` to `"Apple"`.
- This overwrites the previous value `"Samsung"`.
- Since `name` is static, `obj1`'s `name` is also now `"Apple"` because there is only one shared copy.

### `Phone.name = "Phone";`

- Changes the static variable `name` again to `"Phone"`.
- Both `obj1` and `obj2` will now see `name` as `"Phone"`.

### `obj1.show();`

- Prints: `Phone : 145000`
- `name` is `"Phone"` (the latest static value), and `obj1.price` is `145000`.

### `obj2.show();`

- Prints: `Phone : 90000`
- `name` is still `"Phone"` (same static variable), and `obj2.price` is `90000`.

---

## Step-by-Step Execution Walkthrough

| Step | Code                          | Static `name` | `obj1.price` | `obj2.price` |
|------|-------------------------------|:-------------:|:------------:|:------------:|
| 1    | `new Phone()` (obj1)          | null          | 0            | -            |
| 2    | `Phone.name = "Samsung"`      | "Samsung"     | 0            | -            |
| 3    | `obj1.price = 1_45_000`       | "Samsung"     | 145000       | -            |
| 4    | `new Phone()` (obj2)          | "Samsung"     | 145000       | 0            |
| 5    | `Phone.name = "Apple"`        | "Apple"       | 145000       | 0            |
| 6    | `obj2.price = 90_000`         | "Apple"       | 145000       | 90000        |
| 7    | `Phone.name = "Phone"`        | "Phone"       | 145000       | 90000        |
| 8    | `obj1.show()`                 | Output: `Phone : 145000`                      |
| 9    | `obj2.show()`                 | Output: `Phone : 90000`                       |

Notice that both objects print `"Phone"` as the name, even though `obj1` was created when `name` was `"Samsung"` and `obj2` was created when `name` was `"Apple"`. This is because `name` is static and there is only one copy.

---

## How Static Variables Work in Memory

```
Method Area (Class Data)
+----------------------------+
| Phone Class                |
| static name = "Phone"      |
+----------------------------+

Stack Memory              Heap Memory
+----------+              +-------------------+
| obj1  ---|------------->| Phone Object      |
+----------+              | price = 145000    |
                           +-------------------+
+----------+
| obj2  ---|------------->+-------------------+
+----------+              | Phone Object      |
                           | price = 90000     |
                           +-------------------+
```

- The static variable `name` is stored in the Method Area (or Class Data area), NOT inside any object.
- Each object only stores its own instance variables (`price`).
- When any code reads `name`, it goes to the one shared copy in the Method Area.

---

## Static Methods

A static method belongs to the class and can be called without creating an object.

```java
class MathHelper {
    public static int square(int n) {
        return n * n;
    }
}

// No need to create an object
int result = MathHelper.square(5);  // result is 25
```

### Rules for Static Methods

1. A static method can access static variables and other static methods directly.
2. A static method CANNOT access instance variables or instance methods directly (because there is no object context).
3. A static method CANNOT use the `this` or `super` keyword.

```java
class Example {
    static int x = 10;
    int y = 20;

    public static void staticMethod() {
        System.out.println(x);     // OK: accessing static variable
        // System.out.println(y);  // ERROR: cannot access instance variable
        // System.out.println(this.x); // ERROR: cannot use 'this' in static context
    }
}
```

---

## Static Block

A static block is a block of code that runs once when the class is loaded into memory, before any objects are created and before the `main` method executes.

```java
class Demo {
    static {
        System.out.println("Static block executed");
    }

    Demo() {
        System.out.println("Constructor executed");
    }
}
```

If you create two objects of `Demo`, the output will be:

```
Static block executed
Constructor executed
Constructor executed
```

The static block runs only once, while the constructor runs for every new object.

---

## When to Use Static

| Use Case                            | Example                              |
|--------------------------------------|--------------------------------------|
| Constants shared across all objects   | `static final double PI = 3.14159`  |
| Utility methods                       | `Math.sqrt()`, `Arrays.sort()`      |
| Counters or shared data              | Counting number of objects created   |
| Factory methods                       | `Integer.parseInt("123")`           |
| Main method                          | `public static void main(String[])` |

---

## How to Compile and Run

```bash
javac _7_Static_Keyword.java
java _7_Static_Keyword
```

### Expected Output

```
Phone : 145000
Phone : 90000
```

---

## Common Mistakes

### Mistake 1: Accessing instance variables from a static method

```java
public static void main(String[] args) {
    System.out.println(price);  // ERROR: non-static variable cannot be referenced from a static context
}
```

### Mistake 2: Using `this` in a static method

```java
public static void display() {
    System.out.println(this.name);  // ERROR: 'this' cannot be used in a static context
}
```

### Mistake 3: Thinking static means constant

`static` means class-level. It does NOT mean the value cannot change. For a constant, you need `static final`:

```java
static final double PI = 3.14159;  // This is a constant
static int count = 0;              // This is a static variable (can change)
```

---

## Interview Questions

### Q1: What is the static keyword in Java?

**Answer:** The `static` keyword is used to create class-level members. A static variable or method belongs to the class rather than to any specific object. Static variables have one shared copy across all instances. Static methods can be called without creating an object.

### Q2: What is the difference between a static variable and an instance variable?

**Answer:** A static variable belongs to the class and has only one copy shared by all objects. An instance variable belongs to individual objects, and each object has its own copy. Static variables are stored in the Method Area, while instance variables are stored on the heap as part of the object.

### Q3: Can a static method access non-static (instance) variables?

**Answer:** No. A static method cannot directly access instance variables or instance methods because there is no specific object context. To access instance members from a static method, you must first create an object and then use that object to access them.

### Q4: Why is the `main` method static in Java?

**Answer:** The `main` method is static because the JVM needs to call it without creating an object of the class. When the program starts, no objects exist yet. If `main` were not static, the JVM would need to create an object first, but it would not know which constructor to call or what arguments to pass.

### Q5: What is a static block and when is it executed?

**Answer:** A static block is a block of code that runs automatically when the class is first loaded into memory. It runs before the `main` method and before any constructors. It is used for static initialization, such as setting up complex static variables or loading configuration.

### Q6: Can we access static members using an object reference?

**Answer:** Yes, technically you can access static members using an object reference (e.g., `obj1.name`), but it is strongly discouraged. It creates confusion because it looks like an instance member access. The correct and recommended way is to use the class name (e.g., `Phone.name`). Most IDEs will warn you if you access static members through an object.

### Q7: Can you override a static method in Java?

**Answer:** No. Static methods cannot be overridden because they belong to the class, not to objects. If a subclass defines a static method with the same signature, it is called method hiding, not overriding. The method that gets called is determined by the reference type at compile time, not the object type at runtime.

### Q8: What are the numeric literal separators in Java?

**Answer:** Since Java 7, you can use underscores (`_`) as separators in numeric literals to improve readability. For example, `1_00_000` is the same as `100000`, and `3.14_15_92` is the same as `3.141592`. The underscores are ignored by the compiler and do not affect the value.

---

*This is part of a Java learning series. Proceed to Chapter 8: Inheritance to continue learning.*
