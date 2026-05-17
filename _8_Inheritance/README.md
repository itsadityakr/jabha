# Chapter 8: Inheritance in Java

## Table of Contents

1. [Introduction](#introduction)
2. [What is Inheritance](#what-is-inheritance)
3. [Why Do We Need Inheritance](#why-do-we-need-inheritance)
4. [Types of Inheritance in Java](#types-of-inheritance-in-java)
5. [Understanding the Code](#understanding-the-code)
6. [Line-by-Line Explanation](#line-by-line-explanation)
7. [Step-by-Step Execution Walkthrough](#step-by-step-execution-walkthrough)
8. [Constructor Chaining in Inheritance](#constructor-chaining-in-inheritance)
9. [Method Overriding](#method-overriding)
10. [The `extends` Keyword](#the-extends-keyword)
11. [How to Compile and Run](#how-to-compile-and-run)
12. [Common Mistakes](#common-mistakes)
13. [Interview Questions](#interview-questions)

---

## Introduction

Inheritance is one of the four pillars of Object-Oriented Programming (OOP). It allows a class to inherit properties and methods from another class. The class that inherits is called the child class (or subclass, or derived class), and the class being inherited from is called the parent class (or superclass, or base class).

This program demonstrates multi-level inheritance (A -> B -> C), constructor chaining, and method overriding.

---

## What is Inheritance

Inheritance is a mechanism where a new class acquires the properties and behaviors of an existing class. The child class can use all the non-private members of the parent class without rewriting them.

Think of it like a family. A child inherits certain traits from their parents (eye color, height potential, etc.). The child has all the parent's traits plus potentially their own unique traits. In programming, the child class has all the parent's methods and variables, plus its own additions.

**Syntax:**

```java
class ParentClass {
    // parent properties and methods
}

class ChildClass extends ParentClass {
    // child inherits parent's properties and methods
    // child can also have its own properties and methods
}
```

---

## Why Do We Need Inheritance

### Without Inheritance (Code Duplication)

```java
class Car {
    String brand;
    int speed;
    public void start() { System.out.println("Starting..."); }
    public void stop() { System.out.println("Stopping..."); }
}

class ElectricCar {
    String brand;
    int speed;
    int batteryLevel;
    public void start() { System.out.println("Starting..."); }  // Duplicated
    public void stop() { System.out.println("Stopping..."); }   // Duplicated
    public void charge() { System.out.println("Charging..."); }
}
```

### With Inheritance (Code Reuse)

```java
class Car {
    String brand;
    int speed;
    public void start() { System.out.println("Starting..."); }
    public void stop() { System.out.println("Stopping..."); }
}

class ElectricCar extends Car {
    int batteryLevel;
    public void charge() { System.out.println("Charging..."); }
}
```

### Benefits

| Benefit            | Description                                                   |
|--------------------|---------------------------------------------------------------|
| Code Reusability   | Write once in the parent, use in all children                 |
| Maintainability    | Fix a bug in one place, it is fixed for all subclasses        |
| Extensibility      | Add new features by creating subclasses without modifying parent |
| Polymorphism       | Treat objects of different subclasses uniformly through the parent type |

---

## Types of Inheritance in Java

### 1. Single Inheritance

One child class inherits from one parent class.

```java
class A { }
class B extends A { }
```

### 2. Multi-Level Inheritance

A child class inherits from a parent, which itself inherits from another parent.

```java
class A { }
class B extends A { }
class C extends B { }
```

### 3. Hierarchical Inheritance

Multiple child classes inherit from the same parent class.

```java
class A { }
class B extends A { }
class C extends A { }
```

### 4. Multiple Inheritance (NOT supported with classes in Java)

A class inheriting from more than one parent class. Java does NOT support this to avoid the Diamond Problem. However, multiple inheritance is possible through interfaces.

```java
// NOT ALLOWED in Java:
class C extends A, B { }  // COMPILATION ERROR

// Allowed through interfaces:
interface A { }
interface B { }
class C implements A, B { }  // OK
```

---

## Understanding the Code

```java
class A {
    A() {
        System.out.println("A");
    }

    public void show() {
        System.out.println("Show Method : A");
    }
}

class C extends B {
    C() {
        System.out.println("C");
    }
}

class B extends A {
    B() {
        System.out.println("B");
    }

    public void show() {
        System.out.println("Show Method : B");
    }
}

public class _8_Inheritance {
    public static void main(String[] args) {
        C obj = new C();
        obj.show();
    }
}
```

### Inheritance Hierarchy

```
    A (Grandparent)
    |
    B (Parent)      -- inherits from A, overrides show()
    |
    C (Child)       -- inherits from B (and transitively from A)
```

---

## Line-by-Line Explanation

### Class A

```java
class A {
    A() {
        System.out.println("A");
    }

    public void show() {
        System.out.println("Show Method : A");
    }
}
```

- Class `A` is the base class (the topmost class in our hierarchy).
- It has a default constructor that prints `"A"`.
- It has a `show()` method that prints `"Show Method : A"`.

### Class B

```java
class B extends A {
    B() {
        System.out.println("B");
    }

    public void show() {
        System.out.println("Show Method : B");
    }
}
```

- Class `B` extends `A`, meaning `B` inherits everything from `A`.
- `B` has its own constructor that prints `"B"`.
- `B` overrides the `show()` method. When `show()` is called on a `B` object, it prints `"Show Method : B"` instead of `"Show Method : A"`.

### Class C

```java
class C extends B {
    C() {
        System.out.println("C");
    }
}
```

- Class `C` extends `B`, meaning `C` inherits from `B` (and transitively from `A`).
- `C` has its own constructor that prints `"C"`.
- `C` does NOT override the `show()` method. So when `show()` is called on a `C` object, it uses the version from `B` (the nearest parent that has the method).

### Main Method

```java
C obj = new C();
obj.show();
```

- `new C()` creates a new object of type `C`, which triggers constructor chaining.
- `obj.show()` calls the `show()` method. Since `C` does not have its own `show()`, Java looks up the hierarchy and finds it in `B`.

---

## Step-by-Step Execution Walkthrough

When `new C()` is executed:

| Step | What Happens                                           | Output |
|------|--------------------------------------------------------|--------|
| 1    | `C()` constructor is called                            | -      |
| 2    | Before executing `C()`'s body, Java calls `B()` (parent constructor) | - |
| 3    | Before executing `B()`'s body, Java calls `A()` (parent constructor) | - |
| 4    | Before executing `A()`'s body, Java calls `Object()` (every class implicitly extends Object) | - |
| 5    | `Object()` completes (does nothing visible)            | -      |
| 6    | `A()` body executes: `System.out.println("A")`         | A      |
| 7    | `B()` body executes: `System.out.println("B")`         | B      |
| 8    | `C()` body executes: `System.out.println("C")`         | C      |
| 9    | `obj.show()` -- `C` has no `show()`, looks at `B`      | Show Method : B |

**Key Insight:** Constructors are called from top to bottom (parent first, child last). This is called constructor chaining.

---

## Constructor Chaining in Inheritance

When you create an object of a subclass, Java automatically calls the constructors of all parent classes in order, starting from the topmost parent (Object) down to the current class.

This happens because every constructor implicitly has a `super()` call as its first statement (unless you explicitly call another constructor).

```java
class A {
    A() {
        // super();  <-- invisible call to Object()
        System.out.println("A");
    }
}

class B extends A {
    B() {
        // super();  <-- invisible call to A()
        System.out.println("B");
    }
}
```

---

## Method Overriding

Method overriding occurs when a subclass provides its own implementation of a method that is already defined in its parent class.

### Rules for Method Overriding

1. The method in the child class must have the exact same name, return type, and parameter list as the method in the parent class.
2. The access modifier of the overriding method must be the same or less restrictive.
3. The overriding method cannot throw broader checked exceptions.
4. Static methods cannot be overridden (they can only be hidden).
5. Constructors cannot be overridden.
6. The `@Override` annotation is recommended (though optional) to catch errors at compile time.

### How Java Decides Which Method to Call

When you call `obj.show()` on a `C` object:

1. Java first looks in class `C`. If `show()` exists in `C`, it calls that version.
2. If not found in `C`, Java looks in the parent class `B`. If `show()` exists in `B`, it calls that version.
3. If not found in `B`, Java looks in `A`, and so on up the hierarchy.

In our code, `C` does not have `show()`, so Java finds and calls `B`'s `show()`.

---

## The `extends` Keyword

The `extends` keyword is used to create a child class that inherits from a parent class.

```java
class Child extends Parent {
    // Child inherits all non-private members of Parent
}
```

What is inherited:

- All `public` and `protected` members (variables and methods)
- All `default` (package-private) members if the child is in the same package
- Constructors are NOT inherited, but they are called through constructor chaining

What is NOT inherited:

- `private` members (they exist in the parent but cannot be accessed directly from the child)
- Constructors

---

## How to Compile and Run

```bash
javac _8_Inheritance.java
java _8_Inheritance
```

### Expected Output

```
A
B
C
Show Method : B
```

---

## Common Mistakes

### Mistake 1: Thinking constructors are inherited

```java
class A {
    A(int x) { }
}

class B extends A {
    // B does NOT automatically have a constructor that takes int
    // B() will try to call A(), which does not exist --> ERROR
}
```

You must explicitly define constructors in the child class and call the appropriate parent constructor using `super()`.

### Mistake 2: Thinking private members are not inherited

Private members ARE inherited (they exist in the child object's memory), but they cannot be accessed directly from the child class. They can only be accessed through public methods (getters/setters) defined in the parent class.

### Mistake 3: Forgetting that constructor chaining calls parent constructors first

The parent constructor always executes before the child constructor. You cannot skip it.

---

## Interview Questions

### Q1: What is inheritance in Java?

**Answer:** Inheritance is an OOP mechanism where a child class acquires the properties and methods of a parent class using the `extends` keyword. It promotes code reusability, maintainability, and forms the basis for polymorphism.

### Q2: What types of inheritance does Java support?

**Answer:** Java supports single inheritance, multi-level inheritance, and hierarchical inheritance through classes. Java does NOT support multiple inheritance through classes (to avoid the Diamond Problem), but it supports multiple inheritance through interfaces.

### Q3: What is the Diamond Problem?

**Answer:** The Diamond Problem occurs in multiple inheritance when a class inherits from two classes that both inherit from a common base class. If both parent classes have a method with the same signature, the compiler cannot determine which version to use. Java avoids this by not allowing multiple inheritance with classes.

### Q4: What is method overriding?

**Answer:** Method overriding is when a subclass provides a specific implementation for a method that is already defined in its parent class. The overriding method must have the same name, return type, and parameter list. At runtime, Java calls the overridden version based on the actual object type, not the reference type.

### Q5: What is the difference between method overloading and method overriding?

**Answer:**

| Feature          | Overloading                        | Overriding                          |
|------------------|------------------------------------|-------------------------------------|
| Definition       | Same name, different parameters    | Same name, same parameters          |
| Occurs in        | Same class                         | Parent and child classes            |
| Binding          | Compile-time (static binding)      | Runtime (dynamic binding)           |
| Return type      | Can be different                   | Must be same or covariant           |
| Access modifier  | Can be different                   | Cannot be more restrictive          |

### Q6: What is constructor chaining?

**Answer:** Constructor chaining is the process where constructors call other constructors in a chain. In inheritance, when a child object is created, the parent class constructor is called first (via implicit or explicit `super()` call), then the child class constructor executes. This ensures the parent part of the object is properly initialized before the child part.

### Q7: Can a child class access private members of the parent class?

**Answer:** No, a child class cannot directly access private members. However, private members do exist in the child object. They can be accessed indirectly through public or protected getter/setter methods provided by the parent class.

### Q8: What is the `Object` class in Java?

**Answer:** The `Object` class (in `java.lang` package) is the root of the class hierarchy. Every class in Java directly or indirectly extends `Object`. It provides fundamental methods like `toString()`, `equals()`, `hashCode()`, `getClass()`, `clone()`, `wait()`, `notify()`, and `finalize()`.

### Q9: Can you extend multiple classes in Java?

**Answer:** No. A class can extend only one class in Java. This is a design decision to avoid the Diamond Problem. If you need to inherit behavior from multiple sources, use interfaces.

### Q10: What happens if the parent class does not have a default (no-argument) constructor?

**Answer:** If the parent class only has parameterized constructors (no default constructor), the child class must explicitly call one of those parameterized constructors using `super(arguments)` in its constructor. If the child does not do this, the compiler will report an error because the implicit `super()` call cannot find a matching no-argument constructor in the parent.

---

*This is part of a Java learning series. Proceed to Chapter 9: Super and This Keywords to continue learning.*
