# Chapter 14: Wrapper Classes (Boxing, Unboxing & Conversions)

## Table of Contents

1. [Introduction](#introduction)
2. [The Eight Wrapper Classes](#the-eight-wrapper-classes)
3. [Why Wrapper Classes Exist](#why-wrapper-classes-exist)
4. [Boxing and Autoboxing](#boxing-and-autoboxing)
5. [Unboxing and Auto-unboxing](#unboxing-and-auto-unboxing)
6. [Converting Between Strings and Numbers](#converting-between-strings-and-numbers)
   - [`parseInt` vs `valueOf`](#parseint-vs-valueof)
   - [`toString`](#tostring)
7. [Understanding the Code](#understanding-the-code)
8. [Line-by-Line Explanation](#line-by-line-explanation)
9. [How to Compile and Run](#how-to-compile-and-run)
10. [Common Mistakes](#common-mistakes)
11. [Interview Questions](#interview-questions)

---

## Introduction

A **wrapper class** wraps a primitive value (`int`, `double`, `char`, ...) inside
an **object**. Each of Java's eight primitive types has a matching wrapper class
in the `java.lang` package.

The wrapper lets a primitive behave like an object: it can be stored in
collections, can be `null`, and comes with a set of handy static helper methods.

This chapter covers boxing/unboxing and the most common conversions in a single
program:

| File | Demonstrates |
|------|--------------|
| `_14_WrapperClasses_Code.java` | Autoboxing, auto-unboxing, `parseInt`, `valueOf`, and `toString` |

---

## The Eight Wrapper Classes

| Primitive | Wrapper class |
|-----------|---------------|
| `byte`    | `Byte`        |
| `short`   | `Short`       |
| `int`     | `Integer`     |
| `long`    | `Long`        |
| `float`   | `Float`       |
| `double`  | `Double`      |
| `char`    | `Character`   |
| `boolean` | `Boolean`     |

> Note the two that don't simply capitalize the primitive name: `int` -> **`Integer`**
> and `char` -> **`Character`**.

---

## Why Wrapper Classes Exist

- **Collections store objects, not primitives.** `ArrayList<Integer>`,
  `HashMap<String, Double>`, etc. can only hold objects, so numbers must be
  wrapped.
- **Nullability.** A primitive `int` is always a number; an `Integer` can be
  `null` to represent "no value".
- **Utility methods and constants.** Wrappers provide static helpers such as
  `Integer.parseInt`, `Integer.valueOf`, `Integer.MAX_VALUE`, and
  `Integer.MIN_VALUE`.

---

## Boxing and Autoboxing

**Boxing** is wrapping a primitive into its wrapper object. **Autoboxing** is Java
doing that automatically (since Java 5):

```java
Integer num1 = 7;        // autoboxing: int 7 -> Integer object
// Integer old = new Integer(8); // manual boxing -- DEPRECATED, avoid
```

You should **never** use `new Integer(...)`; it is deprecated. Let autoboxing (or
`Integer.valueOf`) do the work.

---

## Unboxing and Auto-unboxing

**Unboxing** is extracting the primitive back out of the wrapper. **Auto-unboxing**
is Java doing it for you:

```java
Integer num1 = 7;
int num2 = num1;             // auto-unboxing: Integer -> int
// int manual = num1.intValue(); // the explicit, manual form
```

---

## Converting Between Strings and Numbers

A very common task is turning text (e.g. user input `"10"`) into a number, or a
number back into text.

### `parseInt` vs `valueOf`

Both read a numeric `String`, but they return **different types**:

```java
int num3      = Integer.parseInt("10");  // returns a primitive int
Integer num5  = Integer.valueOf("10");   // returns an Integer object
```

| Method | Returns | Use when you want... |
|--------|---------|----------------------|
| `Integer.parseInt(s)` | primitive `int` | a plain number |
| `Integer.valueOf(s)`  | `Integer` object | a wrapper object (e.g. for a collection) |

### `toString`

Going the other way, a wrapper object can produce its text form:

```java
Integer num4 = 10;
String s2 = num4.toString();   // "10"
```

---

## Understanding the Code

```java
public class _14_WrapperClasses_Code {
    public static void main(String[] args) {
        int num = 7;
        Integer num1 = 7;            // autoboxing
        int num2 = num1;             // auto-unboxing

        String str = "10";
        int num3 = Integer.parseInt(str);   // String -> int

        Integer num4 = 10;
        String s2 = num4.toString();         // Integer -> String

        String str3 = "10";
        Integer num5 = Integer.valueOf(str3); // String -> Integer
    }
}
```

---

## Line-by-Line Explanation

- `int num = 7;` -- an ordinary primitive `int`.
- `Integer num1 = 7;` -- **autoboxing**; the `int` is wrapped into an `Integer`.
- `int num2 = num1;` -- **auto-unboxing**; the `Integer` is unwrapped back to `int`.
- `Integer.parseInt(str)` -- parses the `String` `"10"` into a **primitive** `int`.
- `num4.toString()` -- converts the `Integer` object into the `String` `"10"`.
- `Integer.valueOf(str3)` -- parses the `String` `"10"` into an **`Integer` object**.

---

## How to Compile and Run

The file declares `package _14_WrapperClasses;`. Compile and run from the parent
directory using the package path:

```bash
# Compile
javac _14_WrapperClasses/_14_WrapperClasses_Code.java

# Run
java _14_WrapperClasses._14_WrapperClasses_Code
```

### Expected Output

```
num  (primitive int)      = 7
num2 (auto-unboxed)       = 7
parseInt("10")            = 10
Integer(10).toString()    = 10
valueOf("10")             = 10
```

---

## Common Mistakes

### Mistake 1: Using `new Integer(...)`

```java
Integer x = new Integer(8); // DEPRECATED
Integer y = 8;              // correct -- autoboxing
Integer z = Integer.valueOf(8); // also fine
```

The constructors were deprecated because they always create a new object and
waste memory; `valueOf`/autoboxing can reuse cached objects.

### Mistake 2: Confusing `parseInt` with `valueOf`

```java
int a      = Integer.parseInt("10"); // primitive int
Integer b  = Integer.valueOf("10");  // Integer object
```

`parseInt` returns a primitive; `valueOf` returns a wrapper object. Picking the
wrong one leads to needless boxing/unboxing.

### Mistake 3: Parsing a non-numeric String

```java
int n = Integer.parseInt("abc"); // throws NumberFormatException
```

`parseInt`/`valueOf` throw `NumberFormatException` if the text is not a valid
number. Validate or wrap the call in a `try/catch`.

### Mistake 4: Unboxing a `null`

```java
Integer maybe = null;
int n = maybe; // throws NullPointerException at runtime
```

A wrapper can be `null`; auto-unboxing `null` crashes. Check for `null` first.

### Mistake 5: Comparing wrappers with `==`

```java
Integer a = 1000, b = 1000;
System.out.println(a == b);       // false -- compares references!
System.out.println(a.equals(b));  // true  -- compares values
```

Use `.equals()` (or unbox to primitives) to compare wrapper **values**. `==`
compares object identity, which is unreliable outside the small cached range
(-128..127).

---

## Interview Questions

### Q1: What is a wrapper class?

**Answer:** A class that wraps a primitive value inside an object. Each primitive
has a corresponding wrapper in `java.lang` (`int` -> `Integer`, `char` ->
`Character`, etc.).

### Q2: Why do we need wrapper classes?

**Answer:** Because collections and generics work with objects, not primitives;
because a wrapper can be `null`; and because wrappers provide useful static
helpers and constants.

### Q3: What is autoboxing and auto-unboxing?

**Answer:** Autoboxing is the automatic conversion of a primitive into its
wrapper (`int` -> `Integer`); auto-unboxing is the reverse (`Integer` -> `int`).
Java has done this automatically since Java 5.

### Q4: What is the difference between `parseInt` and `valueOf`?

**Answer:** `Integer.parseInt(s)` returns a primitive `int`; `Integer.valueOf(s)`
returns an `Integer` object. `valueOf` may also reuse cached objects for small
values.

### Q5: Why is `new Integer(7)` discouraged?

**Answer:** The wrapper constructors are deprecated. They always allocate a new
object, whereas autoboxing or `valueOf` can reuse cached instances and is clearer.

### Q6: What happens if you unbox a `null` wrapper?

**Answer:** It throws a `NullPointerException` at runtime, because there is no
primitive value to extract.

### Q7: Why can comparing two `Integer` objects with `==` give surprising results?

**Answer:** `==` compares object references, not values. Java caches `Integer`
objects for values -128..127, so small equal values may share a reference and
compare `true`, while larger ones don't. Always use `.equals()` for value
comparison.

### Q8: What exception does `parseInt` throw on bad input?

**Answer:** `NumberFormatException`, thrown when the string is not a valid number.

---

*This is part of a Java learning series. Wrapper classes underpin the collections
framework and generics, where every "number" you store is really a boxed object.*
