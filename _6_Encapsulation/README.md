# Chapter 6: Encapsulation in Java

## Table of Contents

1. [Introduction](#introduction)
2. [What is Encapsulation](#what-is-encapsulation)
3. [Why Do We Need Encapsulation](#why-do-we-need-encapsulation)
4. [Access Modifiers in Java](#access-modifiers-in-java)
5. [Getters and Setters](#getters-and-setters)
6. [Understanding the Code](#understanding-the-code)
7. [Line-by-Line Explanation](#line-by-line-explanation)
8. [What Happens Without Encapsulation](#what-happens-without-encapsulation)
9. [Best Practices for Encapsulation](#best-practices-for-encapsulation)
10. [How to Compile and Run](#how-to-compile-and-run)
11. [Common Mistakes](#common-mistakes)
12. [Interview Questions](#interview-questions)

---

## Introduction

Encapsulation is one of the four fundamental principles of Object-Oriented Programming (OOP), along with Inheritance, Polymorphism, and Abstraction. It is the mechanism of bundling data (variables) and the methods that operate on that data into a single unit (class), while restricting direct access to the internal data from outside the class.

This program demonstrates how to make a variable private and provide controlled access to it through getter and setter methods.

---

## What is Encapsulation

Encapsulation means wrapping the data (variables) and code (methods) together as a single unit and controlling who can access what. The core idea is simple: hide the internal details and expose only what is necessary.

In Java, encapsulation is achieved by:

1. Declaring the instance variables of a class as `private`.
2. Providing `public` getter and setter methods to access and modify those variables.

Think of it like an ATM machine. You can withdraw money, check your balance, and deposit money (public methods). But you cannot directly access the vault, modify the database, or see the internal circuitry (private data). The ATM provides a controlled interface to interact with your account.

---

## Why Do We Need Encapsulation

### Problem Without Encapsulation

```java
class Human {
    int age;  // Anyone can access and modify this directly
}

Human person = new Human();
person.age = -500;  // This is allowed, but makes no sense
```

Without encapsulation, anyone can set `age` to an invalid value like `-500`. There is no validation, no control, and no way to enforce business rules.

### Solution With Encapsulation

```java
class Human {
    private int age;

    public void setAge(int age) {
        if (age > 0 && age < 150) {
            this.age = age;
        } else {
            System.out.println("Invalid age");
        }
    }

    public int getAge() {
        return age;
    }
}
```

Now the `age` variable is `private`, and the only way to set it is through the `setAge` method, which can include validation logic.

### Benefits of Encapsulation

| Benefit                | Description                                                      |
|------------------------|------------------------------------------------------------------|
| Data Hiding            | Internal data is hidden from outside classes                     |
| Data Validation        | Setter methods can validate data before assignment               |
| Flexibility            | Internal implementation can change without affecting external code|
| Read-Only/Write-Only   | You can create properties that are only readable or only writable|
| Maintainability        | Code is easier to maintain and debug                             |

---

## Access Modifiers in Java

Access modifiers control the visibility of classes, methods, and variables. Java has four access modifiers:

| Modifier    | Same Class | Same Package | Subclass (different package) | Everywhere |
|-------------|:----------:|:------------:|:----------------------------:|:----------:|
| `private`   | Yes        | No           | No                           | No         |
| (default)   | Yes        | Yes          | No                           | No         |
| `protected` | Yes        | Yes          | Yes                          | No         |
| `public`    | Yes        | Yes          | Yes                          | Yes        |

- **`private`**: Most restrictive. Accessible only within the same class. Used for encapsulation.
- **`default`** (no keyword): Accessible within the same package.
- **`protected`**: Accessible within the same package and by subclasses in other packages.
- **`public`**: Accessible from everywhere.

---

## Getters and Setters

### What is a Getter?

A getter (also called an accessor) is a public method that returns the value of a private variable. The naming convention is `getVariableName()`.

```java
public int getAge() {
    return age;
}
```

### What is a Setter?

A setter (also called a mutator) is a public method that sets or updates the value of a private variable. The naming convention is `setVariableName(value)`.

```java
public void setAge(int age) {
    this.age = age;
}
```

### Naming Convention

For a property named `propertyName`:

- Getter: `getPropertyName()` (for boolean: `isPropertyName()`)
- Setter: `setPropertyName(type value)`

This convention is called the JavaBeans naming convention and is used extensively in frameworks like Spring and Hibernate.

---

## Understanding the Code

```java
class Human {

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age, Human obj) {
        Human obj1 = obj;
        obj1.age = age;
    }
}

public class _6_Encapsulation {

    public static void main(String[] args) {

        Human obj = new Human();
        obj.setAge(4, obj);
        System.out.println(obj.getAge());
    }
}
```

Note: The `setAge` method in this code has an unconventional design for learning purposes. In real-world code, you would not pass the object as a parameter to its own setter.

---

## Line-by-Line Explanation

### `private int age;`

- Declares an instance variable `age` as `private`.
- This means `age` cannot be accessed directly from outside the `Human` class.
- Any attempt like `obj.age = 5;` from another class will result in a compilation error.

### `public int getAge() { return age; }`

- A public getter method that returns the value of the private `age` variable.
- This is the only way for outside code to read the value of `age`.
- The return type is `int` because `age` is an `int`.

### `public void setAge(int age, Human obj) {`

- A setter method that accepts two parameters: the new age value and a reference to a `Human` object.
- Note: This is an unusual setter design. The standard convention is `public void setAge(int age)`.

### `Human obj1 = obj;`

- Creates a new reference variable `obj1` that points to the same object as `obj`.
- No new object is created here. Both `obj` and `obj1` point to the same `Human` object in heap memory.

### `obj1.age = age;`

- Sets the `age` property of the object referenced by `obj1`.
- Since `obj1` and `obj` point to the same object, this also changes the `age` accessible through `obj`.
- Note: Even though `age` is `private`, it can be accessed from within the same class. The `private` restriction only applies to access from other classes.

### `Human obj = new Human();`

- Creates a new `Human` object with the default age value of `0`.

### `obj.setAge(4, obj);`

- Calls the setter method, passing `4` as the new age and `obj` itself as the `Human` reference.
- After this call, `obj.age` is `4`.

### `System.out.println(obj.getAge());`

- Calls the getter to retrieve the age and prints `4`.

---

## What Happens Without Encapsulation

If `age` were `public`:

```java
class Human {
    public int age;
}

Human obj = new Human();
obj.age = -100;       // No error, but logically invalid
obj.age = 999999;     // No error, but unrealistic
System.out.println(obj.age);  // Prints invalid data
```

There is no way to enforce rules like "age must be between 0 and 150." With encapsulation, you can add validation inside the setter method.

---

## Best Practices for Encapsulation

1. **Always declare instance variables as `private`.**
2. **Provide getters only if external code needs to read the value.**
3. **Provide setters only if external code needs to modify the value.** If a property should be set only once (for example, during object creation), do not provide a setter -- use a constructor instead.
4. **Add validation logic inside setters.**
5. **Follow the JavaBeans naming convention** for getters and setters.
6. **Use the `this` keyword** to differentiate between the parameter and the instance variable when they have the same name.

Standard setter example:

```java
public void setAge(int age) {
    if (age > 0 && age < 150) {
        this.age = age;
    }
}
```

---

## How to Compile and Run

```bash
javac _6_Encapsulation.java
java _6_Encapsulation
```

### Expected Output

```
4
```

---

## Common Mistakes

### Mistake 1: Shadowing without using `this`

```java
public void setAge(int age) {
    age = age;  // This does nothing! Both refer to the parameter
}
```

The correct way is:

```java
public void setAge(int age) {
    this.age = age;  // 'this.age' refers to the instance variable
}
```

### Mistake 2: Making getters and setters for every field blindly

Do not create getters and setters for every field just because it is a convention. Only expose what needs to be accessed or modified from outside. Creating unnecessary public methods defeats the purpose of encapsulation.

### Mistake 3: Trying to access private fields from outside the class

```java
Human obj = new Human();
obj.age = 5;  // COMPILATION ERROR: age has private access in Human
```

---

## Interview Questions

### Q1: What is encapsulation in Java?

**Answer:** Encapsulation is the OOP principle of bundling data (variables) and methods that operate on that data into a single unit (class), while hiding the internal data from outside access. It is achieved by making variables `private` and providing `public` getter and setter methods.

### Q2: What is the difference between encapsulation and abstraction?

**Answer:** Encapsulation is about hiding the internal data by making variables private and controlling access through methods. Abstraction is about hiding the implementation details and showing only the essential features. Encapsulation is about "how" data is protected. Abstraction is about "what" is exposed to the user.

### Q3: What are the advantages of encapsulation?

**Answer:** The advantages include: data hiding (internal state is protected from unauthorized access), data validation (setter methods can validate input before assignment), flexibility (internal implementation can change without affecting external code), read-only or write-only properties (by providing only a getter or only a setter), and improved maintainability.

### Q4: What is the `this` keyword in Java?

**Answer:** `this` is a reference variable that refers to the current object. It is commonly used to distinguish between instance variables and parameters when they have the same name. For example, in `this.age = age;`, `this.age` refers to the instance variable and `age` refers to the method parameter.

### Q5: Can a private variable be accessed from another object of the same class?

**Answer:** Yes. The `private` modifier restricts access at the class level, not the object level. Any method within the same class can access private members of any object of that class. This is why in the code, `obj1.age = age;` works inside the `Human` class even though `age` is private.

### Q6: What is data hiding?

**Answer:** Data hiding is a software development technique where the internal data of an object is hidden from outside access. In Java, it is implemented by declaring variables as `private`. Data hiding is a subset of encapsulation. Encapsulation includes both data hiding and providing controlled access through methods.

### Q7: What is the JavaBeans convention for getters and setters?

**Answer:** According to the JavaBeans specification, for a property named `xyz`, the getter should be named `getXyz()` (or `isXyz()` for boolean properties) and the setter should be named `setXyz(Type value)`. The first letter of the property name is capitalized. This convention is used by many Java frameworks for reflection-based access.

### Q8: Can you achieve encapsulation without getters and setters?

**Answer:** Partially. You can declare all variables as `private` and use constructors to set values and methods to operate on the data without explicitly providing getters and setters. However, getters and setters are the standard and most widely used approach to provide controlled access to private data.

---

*This is part of a Java learning series. Proceed to Chapter 7: Static Keyword to continue learning.*
