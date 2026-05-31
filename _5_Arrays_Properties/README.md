# Chapter 5: Arrays with Object Properties

## Table of Contents

1. [Introduction](#introduction)
2. [What are Object Properties](#what-are-object-properties)
3. [Understanding the Code](#understanding-the-code)
4. [Line-by-Line Explanation](#line-by-line-explanation)
5. [How Multiple Objects Work in Memory](#how-multiple-objects-work-in-memory)
6. [Creating an Array of Objects](#creating-an-array-of-objects)
7. [Constructors vs Manual Assignment](#constructors-vs-manual-assignment)
8. [How to Compile and Run](#how-to-compile-and-run)
9. [Common Mistakes](#common-mistakes)
10. [Interview Questions](#interview-questions)

---

## Introduction

In the previous chapter, we worked with arrays that store primitive data types like `int`. But in real-world applications, we often need to store complex data -- for example, a student's roll number, name, and marks together as a single unit. This is where the combination of classes and arrays becomes powerful.

This program demonstrates how to create a custom `Student` class with properties and then create multiple objects of that class, each with different values.

---

## What are Object Properties

Object properties (also called fields, attributes, or instance variables) are variables that belong to an object. They define the state or data that an object holds.

For example, a `Student` object might have the following properties:

- `rollno` -- the student's roll number
- `name` -- the student's name
- `marks` -- the student's marks

Each student object has its own independent copy of these properties. Changing the name of one student does not affect the name of another student.

---

## Understanding the Code

```java
class Student {

    int rollno;
    String name;
    float marks;

    public void printDetails() {
        System.out.println(rollno + ", " + name + ", " + marks);
    }
}

public class _5_Arrays_Properties_Code {

    public static void main(String[] args) {
        Student s1 = new Student();
        s1.rollno = 1;
        s1.name = "Navin";
        s1.marks = 77;
        s1.printDetails();

        Student s2 = new Student();
        s2.rollno = 2;
        s2.name = "Aditya";
        s2.marks = 65;

        Student s3 = new Student();
        s3.rollno = 3;
        s3.name = "Tappu";
        s3.marks = 98;
    }
}
```

---

## Line-by-Line Explanation

### The Student Class

#### `class Student {`

Declares a class named `Student`. This serves as the blueprint for creating student objects.

#### `int rollno;`

An instance variable of type `int` to store the student's roll number. Default value is `0`.

#### `String name;`

An instance variable of type `String` to store the student's name. Default value is `null` because `String` is a reference type.

#### `float marks;`

An instance variable of type `float` to store the student's marks. Default value is `0.0f`.

#### `public void printDetails() {`

A method that prints the student's details. It is `void` because it does not return any value. It simply prints output to the console.

#### `System.out.println(rollno + ", " + name + ", " + marks);`

This line uses string concatenation. The `+` operator, when used with a `String`, converts the other operands to their string representations and joins them together. So for a student with rollno=1, name="Navin", and marks=77, the output will be: `1, Navin, 77.0`.

Note that `marks` prints as `77.0` (not `77`) because it is a `float`.

### The Main Class

#### `Student s1 = new Student();`

Creates a new `Student` object and stores its reference in `s1`. At this point, all properties have their default values: `rollno = 0`, `name = null`, `marks = 0.0`.

#### `s1.rollno = 1;`

Uses the dot operator (`.`) to access the `rollno` property of the `s1` object and assigns it the value `1`. This is called direct field access.

#### `s1.name = "Navin";`

Assigns the string `"Navin"` to the `name` property of `s1`.

#### `s1.marks = 77;`

Assigns `77` to the `marks` property. Even though `77` is an integer literal, Java automatically converts it to `77.0f` because `marks` is of type `float` (widening conversion).

#### `s1.printDetails();`

Calls the `printDetails()` method on the `s1` object, which prints: `1, Navin, 77.0`.

#### Creating s2 and s3

The same process is repeated for `s2` and `s3`, but with different values. Notice that `s2.printDetails()` and `s3.printDetails()` are not called in this code, so their details will not be printed.

---

## How Multiple Objects Work in Memory

```
Stack Memory              Heap Memory

+----------+              +-------------------+
| s1    ---|------------->| Student Object    |
+----------+              | rollno = 1        |
                           | name = "Navin"    |
+----------+              | marks = 77.0      |
| s2    ---|------+       +-------------------+
+----------+      |
                   +----->+-------------------+
+----------+              | Student Object    |
| s3    ---|------+       | rollno = 2        |
+----------+      |       | name = "Aditya"   |
                   |       | marks = 65.0      |
                   |       +-------------------+
                   |
                   +----->+-------------------+
                           | Student Object    |
                           | rollno = 3        |
                           | name = "Tappu"    |
                           | marks = 98.0      |
                           +-------------------+
```

Each object is completely independent. Changing `s1.name` has no effect on `s2.name` or `s3.name`.

---

## Creating an Array of Objects

Instead of creating separate variables `s1`, `s2`, `s3`, you can create an array to hold all the student objects:

```java
Student[] students = new Student[3];

students[0] = new Student();
students[0].rollno = 1;
students[0].name = "Navin";
students[0].marks = 77;

students[1] = new Student();
students[1].rollno = 2;
students[1].name = "Aditya";
students[1].marks = 65;

students[2] = new Student();
students[2].rollno = 3;
students[2].name = "Tappu";
students[2].marks = 98;
```

Important: When you create `new Student[3]`, it creates an array of 3 references, all initialized to `null`. You must separately create each `Student` object using `new Student()` and assign it to the array element.

```java
Student[] students = new Student[3];
// students[0] is null
// students[1] is null
// students[2] is null

students[0] = new Student();  // Now students[0] points to an actual object
```

---

## Constructors vs Manual Assignment

In this code, properties are set manually one by one after creating the object. This is not the recommended approach for production code. A better approach is to use a constructor:

```java
class Student {
    int rollno;
    String name;
    float marks;

    // Constructor
    Student(int rollno, String name, float marks) {
        this.rollno = rollno;
        this.name = name;
        this.marks = marks;
    }
}

// Usage:
Student s1 = new Student(1, "Navin", 77);
```

This is cleaner, less error-prone, and ensures that every student object is fully initialized upon creation.

---

## How to Compile and Run

```bash
javac _5_Arrays_Properties_Code.java
java _5_Arrays_Properties_Code
```

### Expected Output

```
1, Navin, 77.0
```

Only `s1.printDetails()` is called, so only the first student's details are printed.

---

## Common Mistakes

### Mistake 1: Accessing properties on a null reference

```java
Student s1;
s1.name = "Aditya";  // ERROR: NullPointerException (s1 is null)
```

You must create the object using `new` before accessing its properties.

### Mistake 2: Forgetting that object arrays contain null references

```java
Student[] students = new Student[3];
students[0].name = "Aditya";  // ERROR: NullPointerException
```

Creating the array only allocates space for references. You must create each object individually.

### Mistake 3: Confusing instance variables with local variables

Instance variables (declared in the class) have default values. Local variables (declared inside a method) do NOT have default values and must be initialized before use.

---

## Interview Questions

### Q1: What is the difference between instance variables and local variables?

**Answer:**

| Feature           | Instance Variables                     | Local Variables                       |
|-------------------|----------------------------------------|---------------------------------------|
| Declared in       | Class body, outside methods            | Inside a method, constructor, or block|
| Default value     | Yes (0, false, null, etc.)             | No (must be initialized)             |
| Scope             | Entire class                           | Only within the declaring block       |
| Stored in         | Heap (as part of the object)           | Stack                                 |
| Access modifiers  | Can have (public, private, etc.)       | Cannot have access modifiers          |

### Q2: What is NullPointerException and when does it occur?

**Answer:** `NullPointerException` is a runtime exception that occurs when you try to use a reference variable that points to `null` (no object). Common causes include calling a method on a null object, accessing a property of a null object, or trying to use an element of an object array that has not been initialized.

### Q3: What is the default value of a String instance variable?

**Answer:** The default value is `null`, not an empty string (`""`). `null` means the variable does not point to any `String` object in memory. An empty string is a valid `String` object that happens to have zero characters.

### Q4: Can you create an array of objects in Java?

**Answer:** Yes. You can create an array that holds references to objects. However, creating the array only creates space for references (initialized to `null`). You must separately create each object using the `new` keyword and assign it to the corresponding array index.

### Q5: What is the dot operator (`.`) used for in Java?

**Answer:** The dot operator is used to access members (properties and methods) of an object. For example, `s1.name` accesses the `name` property of the object referenced by `s1`, and `s1.printDetails()` calls the `printDetails()` method on that object.

### Q6: What is string concatenation and how does the `+` operator work with Strings?

**Answer:** String concatenation is the process of joining two or more strings together. When the `+` operator is used and at least one operand is a `String`, Java converts the other operand to its `String` representation and joins them. For example, `1 + ", " + "Navin"` produces `"1, Navin"`. Internally, Java uses `StringBuilder` for this conversion.

### Q7: Why does `marks` print as `77.0` instead of `77`?

**Answer:** Because `marks` is declared as `float`. When a `float` is converted to a `String` for printing, Java always includes the decimal point. Even though the value is a whole number, it is stored as a floating-point number (`77.0`), and that is how it is displayed.

---

*This is part of a Java learning series. Proceed to Chapter 6: Encapsulation to continue learning.*
