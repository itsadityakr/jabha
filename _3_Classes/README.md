# Chapter 3: Classes and Objects in Java

## Table of Contents

1. [Introduction](#introduction)
2. [What is a Class](#what-is-a-class)
3. [What is an Object](#what-is-an-object)
4. [Difference Between Class and Object](#difference-between-class-and-object)
5. [Understanding the Code](#understanding-the-code)
6. [Line-by-Line Explanation](#line-by-line-explanation)
7. [How Object Creation Works in Memory](#how-object-creation-works-in-memory)
8. [Methods in Java](#methods-in-java)
9. [How to Compile and Run](#how-to-compile-and-run)
10. [Common Mistakes](#common-mistakes)
11. [Interview Questions](#interview-questions)

---

## Introduction

Classes and objects are the two most fundamental building blocks of Object-Oriented Programming (OOP). Java is an object-oriented language, which means almost everything in Java revolves around classes and objects. If you do not understand classes and objects, you cannot progress further in Java.

This program demonstrates how to create a simple `Calculator` class with a method, create an object of that class, and call the method using that object.

---

## What is a Class

A class is a blueprint or template that defines the structure and behavior of objects. It describes what properties (variables) and actions (methods) an object will have, but it does not occupy any memory by itself.

Think of a class like an architectural blueprint for a house. The blueprint describes the number of rooms, the size of windows, and the layout, but the blueprint itself is not a house. You build actual houses (objects) based on the blueprint (class).

**Syntax:**

```java
class ClassName {
    // Properties (variables)
    // Behaviors (methods)
}
```

**Key Points:**

- A class is declared using the `class` keyword.
- A class name should start with an uppercase letter (convention).
- A class can contain variables (also called fields or attributes) and methods.
- A class does not consume memory until an object is created from it.

---

## What is an Object

An object is an instance of a class. When you create an object, Java allocates memory for it and initializes its properties. Each object has its own copy of the instance variables defined in the class.

Using the house analogy: if the class is the blueprint, then each house built from that blueprint is an object. You can build multiple houses from the same blueprint, and each house can have different paint colors, furniture, and residents.

**Syntax to create an object:**

```java
ClassName objectName = new ClassName();
```

**Breaking it down:**

| Part                | Meaning                                                            |
|---------------------|--------------------------------------------------------------------|
| `ClassName`         | The type of the object (which class it belongs to)                |
| `objectName`        | The reference variable that points to the object in memory        |
| `new`               | A keyword that allocates memory for the object on the heap        |
| `ClassName()`       | The constructor call that initializes the object                  |

---

## Difference Between Class and Object

| Aspect            | Class                                    | Object                                    |
|-------------------|------------------------------------------|-------------------------------------------|
| Definition        | A blueprint or template                  | An instance of a class                    |
| Memory            | Does not occupy memory                   | Occupies memory when created              |
| Creation          | Defined using the `class` keyword        | Created using the `new` keyword           |
| Quantity          | Defined once                             | Can be created multiple times             |
| Contains          | Variable declarations and methods        | Actual values for those variables         |
| Real-world analogy| Recipe for a cake                        | The actual cake made from the recipe      |

---

## Understanding the Code

```java
class Calculator {

    int a;

    public int add(int num1, int num2) {
        return num1 + num2;
    }
}

public class _3_Classes {

    public static void main(String[] args) {
        int n1 = 2,
            n2 = 4;

        Calculator calc = new Calculator();

        int add = calc.add(n1, n2);

        System.out.println(add);
    }
}
```

---

## Line-by-Line Explanation

### `class Calculator {`

- Declares a class named `Calculator`.
- This class is not `public`, which means it can only be accessed within the same file or package.
- This is the blueprint for creating Calculator objects.

### `int a;`

- This is an instance variable (also called a field or attribute).
- Every object of `Calculator` will have its own copy of `a`.
- Since it is an `int`, its default value is `0` if not explicitly assigned.
- In this program, `a` is declared but not used. It is there to show that a class can have properties.

### `public int add(int num1, int num2) {`

- **`public`**: This method can be accessed from outside the class.
- **`int`**: The return type. This method will return an integer value.
- **`add`**: The name of the method.
- **`int num1, int num2`**: These are parameters. When you call this method, you must pass two integer values.

### `return num1 + num2;`

- Adds `num1` and `num2` together and returns the result to the caller.
- The `return` keyword sends the value back and exits the method.

### `Calculator calc = new Calculator();`

- **`Calculator calc`**: Declares a reference variable `calc` of type `Calculator`.
- **`new Calculator()`**: Creates a new object of the `Calculator` class in heap memory.
- The reference variable `calc` now points to this newly created object.

### `int add = calc.add(n1, n2);`

- Calls the `add` method on the `calc` object, passing `n1` (2) and `n2` (4) as arguments.
- The method returns `6`, which is stored in the variable `add`.
- The dot operator (`.`) is used to access methods and properties of an object.

### `System.out.println(add);`

- Prints the value `6` to the console.

---

## How Object Creation Works in Memory

When you write `Calculator calc = new Calculator();`, the following happens internally:

1. **Stack Memory**: The reference variable `calc` is created on the stack. It holds the memory address (reference) of the actual object.

2. **Heap Memory**: The `new` keyword allocates memory on the heap for the `Calculator` object. The instance variable `a` is initialized to its default value (`0` for `int`).

3. **Reference Assignment**: The address of the newly created object on the heap is stored in the `calc` variable on the stack.

```
Stack Memory              Heap Memory
+----------+              +-------------------+
| calc  ---|------------->| Calculator Object |
+----------+              | a = 0             |
                           | add() method ref  |
                           +-------------------+
```

---

## Methods in Java

A method is a block of code that performs a specific task. Methods allow you to write reusable code.

**Types of Methods:**

| Type                     | Description                                                    | Example                           |
|--------------------------|----------------------------------------------------------------|-----------------------------------|
| Instance method          | Belongs to an object, requires object to call                  | `calc.add(2, 4)`                 |
| Static method            | Belongs to the class, does not require an object               | `Math.max(2, 4)`                 |
| Method with return value | Returns a value using the `return` keyword                     | `public int add(int a, int b)`   |
| Void method              | Does not return any value                                      | `public void printHello()`       |

**Key Terminology:**

- **Parameters**: Variables declared in the method definition. Example: `int num1, int num2` in `add(int num1, int num2)`.
- **Arguments**: Actual values passed when calling the method. Example: `n1, n2` in `calc.add(n1, n2)`.
- **Return Type**: The data type of the value the method sends back. If it returns nothing, use `void`.

---

## How to Compile and Run

```bash
javac _3_Classes.java
java _3_Classes
```

### Expected Output

```
6
```

---

## Common Mistakes

### Mistake 1: Trying to call an instance method without creating an object

```java
Calculator.add(2, 4);  // ERROR: add is not a static method
```

You must create an object first and then call the method using that object.

### Mistake 2: Forgetting the `new` keyword

```java
Calculator calc;
calc.add(2, 4);  // ERROR: calc is null, NullPointerException at runtime
```

Declaring a reference variable does not create an object. You must use `new` to allocate memory.

### Mistake 3: Mismatching return types

```java
public int add(int a, int b) {
    return;  // ERROR: must return an int value
}
```

If the return type is `int`, the method must return an integer value.

---

## Interview Questions

### Q1: What is the difference between a class and an object?

**Answer:** A class is a blueprint that defines properties and methods. An object is an instance of a class created at runtime. A class does not occupy memory, but an object does. You can create multiple objects from a single class, each with its own set of property values.

### Q2: What is the `new` keyword used for in Java?

**Answer:** The `new` keyword is used to create a new object. It allocates memory on the heap for the object, initializes the instance variables to their default values, calls the constructor, and returns a reference to the newly created object.

### Q3: What are default values of instance variables in Java?

**Answer:**

| Data Type | Default Value |
|-----------|---------------|
| int       | 0             |
| float     | 0.0f          |
| double    | 0.0           |
| boolean   | false         |
| char      | '\u0000'      |
| String    | null          |
| Object    | null          |

### Q4: What is the difference between a parameter and an argument?

**Answer:** A parameter is the variable declared in the method definition. An argument is the actual value passed to the method when it is called. For example, in `public int add(int num1, int num2)`, `num1` and `num2` are parameters. In `calc.add(2, 4)`, `2` and `4` are arguments.

### Q5: Can a class have multiple methods with the same name?

**Answer:** Yes, this is called method overloading. Multiple methods can have the same name as long as they have different parameter lists (different number of parameters, different types, or different order of types).

### Q6: What is the difference between stack and heap memory?

**Answer:** Stack memory stores local variables and reference variables. It follows LIFO (Last In, First Out) order and is faster. Heap memory stores objects created with the `new` keyword. It is larger but slower. When a method finishes executing, its stack frame is removed, but objects on the heap persist until garbage collected.

### Q7: What happens if you do not initialize an instance variable?

**Answer:** Java automatically assigns default values to instance variables. For `int` it is `0`, for `boolean` it is `false`, and for object references it is `null`. However, local variables (declared inside a method) are NOT given default values and must be initialized before use.

### Q8: Can you have two public classes in the same Java file?

**Answer:** No. A Java source file can have only one public class, and the file name must match the name of that public class. You can have multiple non-public classes in the same file.

---

*This is part of a Java learning series. Proceed to Chapter 4: Arrays to continue learning.*
