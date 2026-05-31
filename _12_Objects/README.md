# Chapter 12: Objects and the Common `Object` Methods (`toString`, `equals`, `hashCode`)

## Table of Contents

1. [Introduction](#introduction)
2. [What is an Object](#what-is-an-object)
3. [The `Object` Class and Its Methods](#the-object-class-and-its-methods)
4. [The `toString()` Method](#the-tostring-method)
5. [Object Equality: `==` vs `equals()`](#object-equality--vs-equals)
6. [The `hashCode()` Method](#the-hashcode-method)
7. [Understanding the Code](#understanding-the-code)
8. [Line-by-Line Explanation](#line-by-line-explanation)
9. [Step-by-Step Execution Walkthrough](#step-by-step-execution-walkthrough)
10. [The `equals()` / `hashCode()` Contract](#the-equals--hashcode-contract)
11. [How to Compile and Run](#how-to-compile-and-run)
12. [Common Mistakes](#common-mistakes)
13. [Interview Questions](#interview-questions)

---

## Introduction

In Java, every class implicitly extends the `Object` class, inheriting a set of methods that every object has -- including `toString()`, `equals()`, and `hashCode()`. By default these methods work in terms of an object's identity (its memory address), which is rarely what we want. This chapter shows how to **override all three** so objects have a meaningful text form and are compared by their data instead of their identity.

This program defines a `Laptop` class that overrides `toString()`, `equals()`, and `hashCode()`, then demonstrates how each behaves and how they relate to the `==` operator.

---

## What is an Object

An object is an instance of a class -- a concrete "thing" created from a class blueprint, holding its own copy of the fields defined by that class.

```java
Laptop obj1 = new Laptop();  // 'obj1' is a reference to a Laptop object
obj1.brand = "Dell";
obj1.price = 50000;
```

- `new Laptop()` creates the object in memory.
- `obj1` is a **reference** -- it stores the address of that object, not the object itself.
- Two references can point to the same object, or to two different objects that happen to hold the same data. Telling these cases apart is what `==`, `equals()`, and `hashCode()` are about.

---

## The `Object` Class and Its Methods

`Object` (in `java.lang`) is the root of every class hierarchy. The three methods we override most often are:

| Method        | Default Behavior                                  | Why Override It                                  |
|---------------|---------------------------------------------------|--------------------------------------------------|
| `toString()`  | Returns `ClassName@hexHashCode`                   | To print a readable description of the object    |
| `equals()`    | Compares references (same as `==`)                | To compare objects by their data (field values)  |
| `hashCode()`  | A number based on the object's identity           | To keep equal objects consistent in hash collections |

These three are commonly overridden together because they describe what an object *is* and when two objects are *the same*.

---

## The `toString()` Method

The default `toString()` returns something like `Laptop@1b6d3586` (class name + `@` + hash code in hex). By overriding it we control what gets printed:

```java
@Override
public String toString() {
    return "Laptop{brand='" + brand + "', price=" + price + "}";
}
```

`toString()` is called **automatically** whenever an object is used where a `String` is expected:

```java
System.out.println("obj1.toString() : " + obj1);  // calls obj1.toString()
```

---

## Object Equality: `==` vs `equals()`

There are two very different questions you can ask about two objects:

| Comparison         | Question Asked                          | Compares       |
|--------------------|-----------------------------------------|----------------|
| `obj1 == obj2`     | Are these the *same object* in memory?  | References     |
| `obj1.equals(obj2)`| Do these objects *hold the same data*?  | Field values (when overridden) |

```java
obj1 == obj2;        // false: two separate objects
obj1.equals(obj2);   // true:  same brand and price
```

A correct `equals()` override examines the fields:

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;                   // same object
    if (o == null || getClass() != o.getClass())  // null or different type
        return false;
    Laptop other = (Laptop) o;                    // safe cast
    return price == other.price && Objects.equals(brand, other.brand);
}
```

> Note the parameter type is `Object`, not `Laptop`. This is what makes it a genuine override of `Object.equals`. Using `equals(Laptop)` would create an *overload* that collections never call.

---

## The `hashCode()` Method

A hash code is an `int` derived from an object's data. Hash-based collections (`HashMap`, `HashSet`) use it to decide which "bucket" an object goes into. The rule: **equal objects must have equal hash codes**. So whenever you override `equals()`, you must also override `hashCode()`:

```java
@Override
public int hashCode() {
    return Objects.hash(brand, price);
}
```

`Objects.hash(...)` builds a combined hash from the listed fields -- the same fields used in `equals()`.

---

## Understanding the Code

```java
package _12_Objects;

import java.util.Objects;

class Laptop {
    String brand;
    int price;

    @Override
    public String toString() {
        return "Laptop{brand='" + brand + "', price=" + price + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Laptop other = (Laptop) o;
        return price == other.price && Objects.equals(brand, other.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, price);
    }
}

public class _12_Objects {
    public static void main(String[] args) {
        Laptop obj1 = new Laptop();
        obj1.brand = "Dell";
        obj1.price = 50000;

        Laptop obj2 = new Laptop();
        obj2.brand = "Dell";
        obj2.price = 50000;

        Laptop obj3 = new Laptop();
        obj3.brand = "HP";
        obj3.price = 60000;

        System.out.println("obj1.toString() : " + obj1);
        System.out.println("obj3.toString() : " + obj3);

        System.out.println("obj1 == obj2      : " + (obj1 == obj2));
        System.out.println("obj1.equals(obj2) : " + obj1.equals(obj2));
        System.out.println("obj1.equals(obj3) : " + obj1.equals(obj3));

        System.out.println("obj1.hashCode()   : " + obj1.hashCode());
        System.out.println("obj2.hashCode()   : " + obj2.hashCode());
        System.out.println("obj3.hashCode()   : " + obj3.hashCode());
    }
}
```

---

## Line-by-Line Explanation

### `import java.util.Objects;`

- Imports the `Objects` utility class, which provides the null-safe helpers `Objects.equals(a, b)` and `Objects.hash(...)` used inside our overrides.

### The `Laptop` fields

```java
String brand;
int price;
```

- The two fields that define a laptop -- and that we use to decide equality and to build the hash code.

### `toString()`

```java
@Override
public String toString() {
    return "Laptop{brand='" + brand + "', price=" + price + "}";
}
```

- Replaces the default `Laptop@hashcode` output with a readable description.

### `equals(Object o)`

```java
if (this == o) return true;
if (o == null || getClass() != o.getClass()) return false;
Laptop other = (Laptop) o;
return price == other.price && Objects.equals(brand, other.brand);
```

- **Step 1:** if both references are the same object, they are equal.
- **Step 2:** if the other object is `null` or a different class, they are not equal.
- **Step 3:** cast to `Laptop` and compare the defining fields. `price` is a primitive, compared with `==`; `brand` is a `String`, compared null-safely with `Objects.equals`.

### `hashCode()`

```java
@Override
public int hashCode() {
    return Objects.hash(brand, price);
}
```

- Produces a hash from the same fields used in `equals()`, so equal laptops always get the same hash code.

### `main` -- creating objects

- `obj1` and `obj2` hold identical data (`Dell`, `50000`); `obj3` is different (`HP`, `60000`).

### `main` -- the comparisons

- `obj1 == obj2` checks references -> `false` (different objects).
- `obj1.equals(obj2)` checks data -> `true`; `obj1.equals(obj3)` -> `false`.
- `hashCode()` is identical for the equal pair `obj1`/`obj2`, and different for `obj3`.

---

## Step-by-Step Execution Walkthrough

| Statement                | What Happens                                      | Output                                 |
|--------------------------|---------------------------------------------------|----------------------------------------|
| `"... " + obj1`          | Calls `obj1.toString()`                            | Laptop{brand='Dell', price=50000}      |
| `"... " + obj3`          | Calls `obj3.toString()`                            | Laptop{brand='HP', price=60000}        |
| `obj1 == obj2`           | Reference comparison of two distinct objects       | false                                  |
| `obj1.equals(obj2)`      | Field comparison; same brand and price             | true                                   |
| `obj1.equals(obj3)`      | Field comparison; different brand and price        | false                                  |
| `obj1.hashCode()`        | Hash from ("Dell", 50000)                          | 65966416                               |
| `obj2.hashCode()`        | Same data as obj1 -> same hash                     | 65966416                               |
| `obj3.hashCode()`        | Hash from ("HP", 60000)                            | 132633                                 |

**Key Insight:** `obj1` and `obj2` are different objects (`==` is false) yet are *equal* by data (`equals()` is true) and therefore share the **same hash code** -- exactly what hash-based collections require.

---

## The `equals()` / `hashCode()` Contract

- If `a.equals(b)` is `true`, then `a.hashCode()` must equal `b.hashCode()`.
- The reverse is **not** required: two unequal objects *may* share a hash code (a "collision"); that is allowed and handled by the collection.
- Therefore, **whenever you override `equals()`, you must also override `hashCode()`** using the same fields. Overriding only one breaks `HashMap`/`HashSet`.

A correct `equals()` must also be:

- **Reflexive:** `a.equals(a)` is true.
- **Symmetric:** `a.equals(b)` implies `b.equals(a)`.
- **Transitive:** `a.equals(b)` and `b.equals(c)` imply `a.equals(c)`.
- **Consistent:** repeated calls return the same result.
- `a.equals(null)` is always false.

> Most IDEs can generate correct `equals()`, `hashCode()`, and `toString()` for you via "Source Action" -- the same menu used to generate getters and setters.

---

## How to Compile and Run

The file declares `package _12_Objects;`, so compile and run from the parent directory using the package path:

```bash
javac _12_Objects/_12_Objects.java
java _12_Objects._12_Objects
```

### Expected Output

```
obj1.toString() : Laptop{brand='Dell', price=50000}
obj3.toString() : Laptop{brand='HP', price=60000}
obj1 == obj2      : false
obj1.equals(obj2) : true
obj1.equals(obj3) : false
obj1.hashCode()   : 65966416
obj2.hashCode()   : 65966416
obj3.hashCode()   : 132633
```

> The exact hash code numbers come from `Objects.hash(brand, price)` and depend only on the field values, so they are reproducible for the same data.

---

## Common Mistakes

### Mistake 1: Using `==` to compare object contents

```java
if (obj1 == obj2) { ... }  // Compares references, almost never what you want
```

`==` is `true` only when both references point to the exact same object. To compare data, use `equals()`.

### Mistake 2: Overriding `equals()` but not `hashCode()`

This breaks `HashSet` and `HashMap`: two "equal" objects can land in different buckets, so lookups and de-duplication fail. Always override both, using the same fields.

### Mistake 3: Writing `equals(Laptop)` instead of `equals(Object)`

```java
public boolean equals(Laptop other) { ... }  // OVERLOAD, not an override
```

The real `Object.equals` takes an `Object`. With the wrong parameter type you create a new overloaded method, and collections still call the inherited `equals(Object)`. Use `@Override` so the compiler catches this.

### Mistake 4: Using `==` to compare Strings inside `equals()`

```java
return brand == other.brand;       // compares references -- can be false
return Objects.equals(brand, other.brand);  // correct, null-safe content compare
```

### Mistake 5: Forgetting `@Override`

Without it, a small signature mistake silently becomes a new method instead of an override, and the bug is hard to spot. The annotation makes the compiler verify the override.

---

## Interview Questions

### Q1: What is the `Object` class in Java?

**Answer:** `Object` (in `java.lang`) is the root of the class hierarchy. Every class directly or indirectly extends it, inheriting methods such as `toString()`, `equals()`, `hashCode()`, `getClass()`, `clone()`, `wait()`, and `notify()`.

### Q2: What does the default `toString()` return, and why override it?

**Answer:** It returns the class name, `@`, and the object's hash code in hex (e.g. `Laptop@1b6d3586`). We override it to provide a meaningful, human-readable description of the object's state.

### Q3: What is the difference between `==` and `equals()`?

**Answer:** `==` compares references -- whether two variables point to the same object (for primitives, it compares values). `equals()` is a method that, when overridden, compares the logical content of two objects. By default (not overridden), `equals()` behaves like `==`.

### Q4: Why must you override `hashCode()` when you override `equals()`?

**Answer:** Because of the contract: equal objects must have equal hash codes. Hash-based collections use `hashCode()` to pick a bucket and `equals()` to confirm a match. If equal objects had different hash codes, they could be placed in different buckets and never be recognized as equal.

### Q5: Can two unequal objects have the same hash code?

**Answer:** Yes. That is called a hash collision and is perfectly legal. The contract only requires equal objects to share a hash code, not that unequal objects have different ones. Collections resolve collisions by also calling `equals()`.

### Q6: What is the `java.util.Objects` class?

**Answer:** A utility class of static helper methods for working with objects safely, including `Objects.equals(a, b)` (null-safe equality), `Objects.hash(...)` (combined hash code), `Objects.isNull`, `Objects.nonNull`, `Objects.requireNonNull`, and `Objects.toString`.

### Q7: Why must the parameter of `equals()` be `Object`?

**Answer:** Because `Object.equals(Object)` is the method being overridden. If you declare `equals(Laptop)`, you create an overload, not an override, and Java's collections -- which call `equals(Object)` -- will ignore it. `@Override` makes the compiler enforce the correct signature.

### Q8: What does the `@Override` annotation do?

**Answer:** It tells the compiler that a method is intended to override a superclass method. If it does not actually override anything (e.g. due to a wrong signature), the compiler reports an error, preventing subtle bugs.

### Q9: What is the full contract of a correct `equals()` method?

**Answer:** It must be reflexive (`a.equals(a)` is true), symmetric (`a.equals(b)` implies `b.equals(a)`), transitive (if `a.equals(b)` and `b.equals(c)` then `a.equals(c)`), consistent (repeated calls give the same result), and `a.equals(null)` must be false.

### Q10: How do you compare two Strings correctly?

**Answer:** Use `.equals()` (or `.equalsIgnoreCase()` for case-insensitive comparison), or `Objects.equals()` for a null-safe version. Avoid `==`, which compares references and may return `false` even for identical text when the strings are separate objects.

---

*This is part of a Java learning series. It builds on earlier chapters on Classes and Inheritance, and pairs naturally with collections, where `equals()` and `hashCode()` are essential.*
