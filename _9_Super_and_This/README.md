# Chapter 9: Super and This Keywords in Java

## Table of Contents

1. [Introduction](#introduction)
2. [What is the `this` Keyword](#what-is-the-this-keyword)
3. [What is the `super` Keyword](#what-is-the-super-keyword)
4. [Difference Between `this` and `super`](#difference-between-this-and-super)
5. [Understanding the Code](#understanding-the-code)
6. [Line-by-Line Explanation](#line-by-line-explanation)
7. [Step-by-Step Execution Walkthrough](#step-by-step-execution-walkthrough)
8. [Uses of `this` Keyword](#uses-of-this-keyword)
9. [Uses of `super` Keyword](#uses-of-super-keyword)
10. [Rules and Restrictions](#rules-and-restrictions)
11. [How to Compile and Run](#how-to-compile-and-run)
12. [Common Mistakes](#common-mistakes)
13. [Interview Questions](#interview-questions)

---

## Introduction

The `this` and `super` keywords are two of the most important keywords in Java, especially when working with constructors and inheritance. They control how constructors call other constructors, how variables are resolved when there are naming conflicts, and how parent class members are accessed from a child class.

This program demonstrates constructor chaining using both `this()` and `super()`, showing the exact order in which constructors are called when an object is created.

---

## What is the `this` Keyword

The `this` keyword is a reference to the current object -- the object on which a method or constructor is being invoked. It always points to the instance of the class in which it is used.

### Simple Analogy

Think of `this` as the word "I" in English. When a person says "I am going to the store," "I" refers to the person speaking. Similarly, when an object uses `this`, it refers to itself.

### Basic Usage

```java
class Student {
    String name;

    Student(String name) {
        this.name = name;  // this.name = instance variable, name = parameter
    }
}
```

Without `this`, both `name` references on the left and right would refer to the parameter, and the instance variable would never be set.

---

## What is the `super` Keyword

The `super` keyword is a reference to the parent (superclass) object. It is used to access members of the parent class from the child class, especially when the child class has overridden or shadowed those members.

### Simple Analogy

Think of `super` as saying "my parent." When a child wants something from the parent's house, they say "let me get it from my parent." Similarly, when a child class wants to use a parent class member, it uses `super`.

### Basic Usage

```java
class Animal {
    String type = "Animal";
}

class Dog extends Animal {
    String type = "Dog";

    void printTypes() {
        System.out.println(this.type);   // "Dog" (current class)
        System.out.println(super.type);  // "Animal" (parent class)
    }
}
```

---

## Difference Between `this` and `super`

| Feature                   | `this`                                    | `super`                                    |
|---------------------------|-------------------------------------------|--------------------------------------------|
| Refers to                 | Current class object                      | Parent class object                        |
| Used for                  | Accessing current class members           | Accessing parent class members             |
| Constructor call          | `this()` calls another constructor of the same class | `super()` calls a constructor of the parent class |
| Can be used in            | Any non-static method or constructor      | Any non-static method or constructor       |
| Must be first statement   | Yes (when used as constructor call)       | Yes (when used as constructor call)        |
| Implicit behavior         | Not added automatically                   | `super()` is implicitly added to every constructor if no `this()` or `super()` is present |

---

## Understanding the Code

```java
// By default, every constructor has a super() method call in it.

class A {
    public A() {
        super(); // calls Object's default Constructor
        System.out.println("A : Default Constructor");
    }

    public A(int num) {
        this(); // calls the default constructor of the same class
        System.out.println("A : Parameterized Constructor");
    }
}

class B extends A {
    public B() {
        super(6); // calls the parameterized constructor of class A
        System.out.println("B : Default Constructor");
    }

    public B(int num) {
        super(); // calls the default constructor of class A
        System.out.println("B : Parameterized Constructor");
    }
}

public class _9_Super_and_This {
    public static void main(String[] args) {
        B obj = new B();
    }
}
```

### Class Hierarchy

```
    Object (root of all classes)
      |
      A (has two constructors: default and parameterized)
      |
      B (has two constructors: default and parameterized)
```

---

## Line-by-Line Explanation

### Class A - Default Constructor

```java
public A() {
    super(); // calls Object's default Constructor
    System.out.println("A : Default Constructor");
}
```

- `super()` calls the default constructor of the `Object` class (since `A` implicitly extends `Object`).
- Even if you do not write `super()`, the compiler adds it automatically.
- After the parent constructor completes, it prints `"A : Default Constructor"`.

### Class A - Parameterized Constructor

```java
public A(int num) {
    this(); // calls the default constructor of the same class
    System.out.println("A : Parameterized Constructor");
}
```

- `this()` calls the default constructor of class `A` (the one defined above).
- This is constructor chaining within the same class.
- After the default constructor completes (which first calls `Object()` then prints "A : Default Constructor"), this constructor then prints `"A : Parameterized Constructor"`.

### Class B - Default Constructor

```java
public B() {
    super(6); // calls the parameterized constructor of class A
    System.out.println("B : Default Constructor");
}
```

- `super(6)` explicitly calls the parameterized constructor `A(int num)` of the parent class, passing `6` as the argument.
- This means `A(int num)` will execute, which internally calls `this()` (A's default constructor), creating a chain.
- After the parent's constructor chain completes, it prints `"B : Default Constructor"`.

### Class B - Parameterized Constructor

```java
public B(int num) {
    super(); // calls the default constructor of class A
    System.out.println("B : Parameterized Constructor");
}
```

- `super()` calls the default constructor `A()` of the parent class.
- This constructor is not called in the current program because we only create `new B()` (the default constructor of B).

---

## Step-by-Step Execution Walkthrough

When `new B()` is executed, the following chain of constructor calls occurs:

| Step | Constructor Called         | Why                                       | Output                         |
|------|---------------------------|-------------------------------------------|--------------------------------|
| 1    | `B()`                     | We wrote `new B()`                       | (not yet, body runs after super) |
| 2    | `A(int num)` with num=6  | `B()` has `super(6)`                     | (not yet, body runs after this) |
| 3    | `A()`                     | `A(int num)` has `this()`                | (not yet, body runs after super) |
| 4    | `Object()`                | `A()` has `super()`                      | (nothing visible)              |
| 5    | `A()` body executes       | Back to step 3                           | A : Default Constructor        |
| 6    | `A(int num)` body executes| Back to step 2                           | A : Parameterized Constructor  |
| 7    | `B()` body executes       | Back to step 1                           | B : Default Constructor        |

### Visual Call Stack

```
new B()
  |
  +-- B() calls super(6)
        |
        +-- A(int num) calls this()
              |
              +-- A() calls super()
                    |
                    +-- Object() [completes]
                    |
              A() prints: "A : Default Constructor"
              |
        A(int num) prints: "A : Parameterized Constructor"
        |
  B() prints: "B : Default Constructor"
```

---

## Uses of `this` Keyword

### 1. Distinguishing instance variables from parameters

```java
class Student {
    String name;

    Student(String name) {
        this.name = name;  // Without 'this', both refer to the parameter
    }
}
```

### 2. Calling another constructor of the same class

```java
class Student {
    String name;
    int age;

    Student() {
        this("Unknown", 0);  // Calls the parameterized constructor
    }

    Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

### 3. Passing the current object as an argument

```java
class Printer {
    void print(Student s) {
        System.out.println(s.name);
    }
}

class Student {
    String name = "Aditya";

    void display(Printer p) {
        p.print(this);  // Passes the current Student object
    }
}
```

### 4. Returning the current object

```java
class Builder {
    int value;

    Builder setValue(int value) {
        this.value = value;
        return this;  // Returns the current object for method chaining
    }
}

// Usage: builder.setValue(10).setValue(20);
```

---

## Uses of `super` Keyword

### 1. Calling the parent class constructor

```java
class Animal {
    Animal(String type) {
        System.out.println("Animal: " + type);
    }
}

class Dog extends Animal {
    Dog() {
        super("Dog");  // Calls Animal(String type)
    }
}
```

### 2. Accessing parent class methods (when overridden)

```java
class Animal {
    void sound() {
        System.out.println("Some sound");
    }
}

class Dog extends Animal {
    void sound() {
        super.sound();  // Calls Animal's sound() method
        System.out.println("Bark");
    }
}
```

### 3. Accessing parent class variables (when shadowed)

```java
class Animal {
    String type = "Animal";
}

class Dog extends Animal {
    String type = "Dog";

    void print() {
        System.out.println(this.type);   // "Dog"
        System.out.println(super.type);  // "Animal"
    }
}
```

---

## Rules and Restrictions

### Rule 1: `this()` and `super()` must be the first statement in a constructor

```java
class B extends A {
    B() {
        System.out.println("Hello");  // ERROR if super() or this() follows
        super();  // MUST be the first statement
    }
}
```

### Rule 2: You cannot use both `this()` and `super()` in the same constructor

Since both must be the first statement, you can only use one of them.

```java
class B extends A {
    B() {
        super();
        this();  // ERROR: call to this must be first statement
    }
}
```

### Rule 3: `super()` is added implicitly if no `this()` or `super()` is present

If you do not write `this()` or `super()` in a constructor, the compiler automatically inserts `super()` (a call to the parent's no-argument constructor).

### Rule 4: `this` and `super` cannot be used in static methods

```java
public static void main(String[] args) {
    // System.out.println(this);   // ERROR: 'this' in static context
    // System.out.println(super.x); // ERROR: 'super' in static context
}
```

### Rule 5: Recursive constructor calls are not allowed

```java
class A {
    A() {
        this();  // ERROR: recursive constructor invocation
    }
}
```

---

## How to Compile and Run

The file declares `package _9_Super_and_This;`, so compile and run from the
parent directory using the package path:

```bash
javac _9_Super_and_This/_9_Super_and_This.java
java _9_Super_and_This._9_Super_and_This
```

### Expected Output

```
A : Default Constructor
A : Parameterized Constructor
B : Default Constructor
```

---

## Common Mistakes

### Mistake 1: Forgetting that `super()` is added automatically

```java
class A {
    A(int x) { }
}

class B extends A {
    B() {
        // Compiler inserts super() here, but A() does not exist
        // ERROR: no suitable constructor found for A()
    }
}
```

Solution: Explicitly call `super(someValue)` or add a default constructor to the parent class.

### Mistake 2: Using `this()` and `super()` in the same constructor

This is not allowed. You can only use one of them.

### Mistake 3: Creating infinite constructor recursion

```java
class A {
    A() {
        this(5);
    }
    A(int x) {
        this();  // ERROR: recursive constructor invocation
    }
}
```

Java detects direct circular constructor calls at compile time.

### Mistake 4: Confusing `this` (reference) with `this()` (constructor call)

- `this` (without parentheses) is a reference to the current object. It can be used anywhere in non-static methods.
- `this()` (with parentheses) is a constructor call. It can ONLY be used as the first statement inside a constructor.

---

## Interview Questions

### Q1: What is the `this` keyword in Java?

**Answer:** `this` is a reference variable that refers to the current object. It is used to access instance variables when they are shadowed by parameters, to call other constructors of the same class using `this()`, to pass the current object as an argument, and to return the current object from a method.

### Q2: What is the `super` keyword in Java?

**Answer:** `super` is a reference variable that refers to the immediate parent class object. It is used to call the parent class constructor using `super()`, to access parent class methods that have been overridden, and to access parent class variables that have been shadowed by child class variables.

### Q3: Why must `super()` or `this()` be the first statement in a constructor?

**Answer:** Because the parent class must be fully initialized before the child class can use any inherited members. If the parent constructor were called in the middle or at the end, the child class might try to use uninitialized inherited members, leading to unpredictable behavior. Java enforces this rule at the compiler level.

### Q4: What happens if you do not write `super()` in a constructor?

**Answer:** The compiler automatically inserts `super()` (a call to the parent's no-argument constructor) as the first statement. If the parent class does not have a no-argument constructor, the code will not compile, and you must explicitly call a parameterized parent constructor using `super(arguments)`.

### Q5: Can `this()` and `super()` be used together in the same constructor?

**Answer:** No. Both `this()` and `super()` must be the first statement in a constructor, and only one statement can be first. Therefore, you can use either `this()` or `super()`, but not both in the same constructor.

### Q6: What is constructor chaining?

**Answer:** Constructor chaining is the process of one constructor calling another constructor. It can happen within the same class using `this()` or across the inheritance hierarchy using `super()`. The purpose is to avoid code duplication and ensure proper initialization. Every object creation in Java ultimately chains up to the `Object` class constructor.

### Q7: Can we use `this` and `super` inside a static method?

**Answer:** No. `this` and `super` refer to object instances, but static methods belong to the class and do not have an object context. Using `this` or `super` in a static method will result in a compilation error: "non-static variable this/super cannot be referenced from a static context."

### Q8: What is the difference between `this()` and `this`?

**Answer:** `this` (without parentheses) is a reference to the current object and can be used in any non-static context. `this()` (with parentheses) is a constructor call that invokes another constructor of the same class and can only be used as the first statement inside a constructor.

### Q9: In the code example, why does `A : Default Constructor` appear before `A : Parameterized Constructor`?

**Answer:** Because the parameterized constructor `A(int num)` uses `this()` to call the default constructor `A()` first. The call stack is: `B()` calls `super(6)` which invokes `A(int)`, then `A(int)` calls `this()` which invokes `A()`, then `A()` calls `super()` which invokes `Object()`. The execution then unwinds: `A()` prints first, then `A(int)` prints, then `B()` prints. Constructors always execute from parent to child.

### Q10: What is the `Object` class and how is it related to `super()`?

**Answer:** The `Object` class is the root superclass of every class in Java. Even if a class does not explicitly extend any class, it implicitly extends `Object`. When `super()` is called in the topmost user-defined class, it calls `Object()`'s constructor, which initializes the base object infrastructure (hash code, class metadata, etc.).

---

*This is the final chapter in the current Java learning series. Review all chapters to build a strong foundation in Java.*
