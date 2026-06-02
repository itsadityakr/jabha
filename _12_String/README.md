# Chapter 12: Strings in Java (String and StringBuilder)

## Table of Contents

1. [Introduction](#introduction)
2. [What is a String](#what-is-a-string)
3. [Creating Strings](#creating-strings)
4. [String Immutability](#string-immutability)
5. [Common String Methods](#common-string-methods)
6. [Comparing Strings](#comparing-strings)
7. [Why StringBuilder Exists](#why-stringbuilder-exists)
8. [StringBuilder Methods](#stringbuilder-methods)
9. [String vs StringBuilder vs StringBuffer](#string-vs-stringbuilder-vs-stringbuffer)
10. [How to Compile and Run](#how-to-compile-and-run)
11. [Common Mistakes](#common-mistakes)
12. [Interview Questions](#interview-questions)

---

## Introduction

Text is everywhere in programs — names, messages, file contents. In Java, text is handled by the **`String`** class. Strings are so important they get special syntax (double quotes) and their own memory area (the String Pool, from Chapter 2). This chapter covers Strings and their **mutable** companion, **`StringBuilder`**.

This chapter has two programs:

| File | Topic |
|------|-------|
| `_12_1_String_Code.java`         | The `String` class, methods, immutability |
| `_12_2_String_Builder_Code.java` | `StringBuilder` for efficient, mutable text |

---

## What is a String

A `String` is a sequence of characters. It is a **class** (a reference type), not a primitive, written between double quotes:

```java
String name = "Java";
```

Each character has an index starting at 0, just like an array:

```
 J   a   v   a
[0] [1] [2] [3]
```

---

## Creating Strings

```java
String a = "Hello";              // string literal -> stored in the String Pool
String b = new String("Hello");  // explicit new object on the heap
```

- A **literal** (`"Hello"`) is placed in the **String Pool** and reused if the same text already exists there.
- `new String("Hello")` **forces** a separate object on the heap, even if the same text is pooled. (See Chapter 2 for the String Pool.)

---

## String Immutability

**Strings are immutable** — once created, their characters can never be changed. Any method that appears to modify a String actually returns a **new** String, leaving the original untouched.

```java
String text = "Java Programming";
text.toUpperCase();          // returns a NEW string; result is discarded here
System.out.println(text);    // still "Java Programming"

String upper = text.toUpperCase(); // capture the new string
System.out.println(upper);         // "JAVA PROGRAMMING"
```

**Why immutable?** It makes Strings safe to share (including across threads), allows the String Pool to reuse them, and makes them reliable as keys in collections.

---

## Common String Methods

For `String text = "Java Programming";`

| Method                | Purpose                            | Result            |
|-----------------------|------------------------------------|-------------------|
| `length()`            | number of characters               | `16`              |
| `toUpperCase()`       | upper-case copy                    | `"JAVA PROGRAMMING"` |
| `toLowerCase()`       | lower-case copy                    | `"java programming"` |
| `charAt(5)`           | character at an index              | `'P'`             |
| `indexOf("Prog")`     | index of a substring (or -1)       | `5`               |
| `substring(0, 4)`     | part of the string `[start, end)`  | `"Java"`          |
| `replace('a', 'A')`   | replace characters                 | `"JAvA ProgrAmming"` |
| `contains("Java")`    | does it contain the text?          | `true`            |
| `trim()`              | remove leading/trailing spaces     | `"spaced"`        |

> Every one of these returns a **new** String. The original `text` is never changed.

---

## Comparing Strings

```java
String a = "Hello";
String b = new String("Hello");

a == b         // false -> compares references (different objects)
a.equals(b)    // true  -> compares characters
```

- Use **`.equals()`** to compare text.
- Use **`.equalsIgnoreCase()`** to ignore upper/lower case.
- Avoid **`==`** for text; it compares object identity, not content.

---

## Why StringBuilder Exists

Because String is immutable, building text with `+` in a loop creates a **new** String on every step, which is wasteful:

```java
String result = "";
for (int i = 0; i < 1000; i++) {
    result += i;   // creates a new String each time -> slow
}
```

`StringBuilder` is **mutable**: it changes its internal buffer in place, with no throwaway objects:

```java
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append(i);  // modifies the same object -> fast
}
String result = sb.toString();
```

---

## StringBuilder Methods

Starting from `new StringBuilder("Hello")`:

| Method                | Effect                                  | Result after call |
|-----------------------|-----------------------------------------|-------------------|
| `append(" World")`    | add to the end                          | `Hello World`     |
| `insert(5, ",")`      | insert at an index                      | `Hello, World`    |
| `replace(0, 5, "Hi")` | replace chars in range `[start, end)`   | `Hi, World`       |
| `deleteCharAt(2)`     | remove one character                    | `Hi World`        |
| `reverse()`           | reverse the whole sequence              | `dlroW iH`        |
| `length()`            | current length                          | `8`               |
| `toString()`          | convert back to an immutable `String`   | `"Hi World"`      |

(The table follows the exact sequence of calls in `_12_2_String_Builder_Code.java`.)

---

## String vs StringBuilder vs StringBuffer

| Feature        | `String`              | `StringBuilder`         | `StringBuffer`            |
|----------------|-----------------------|-------------------------|---------------------------|
| Mutable?       | No (immutable)        | Yes                     | Yes                       |
| Thread-safe?   | Yes (because immutable)| No                     | Yes (synchronised)        |
| Speed          | Slow for many edits   | **Fastest** for edits   | Slower than StringBuilder |
| Use when       | Text rarely changes   | Building text in one thread | Building text across threads |

> Rule of thumb: use `String` for fixed text, `StringBuilder` when you build or modify text repeatedly, and `StringBuffer` only when multiple threads share the buffer.

---

## How to Compile and Run

```bash
javac _12_String/_12_1_String_Code.java
java _12_String._12_1_String_Code

javac _12_String/_12_2_String_Builder_Code.java
java _12_String._12_2_String_Builder_Code
```

---

## Common Mistakes

### Mistake 1: Comparing text with `==`

```java
if (name == "Java") { ... }       // unreliable: compares references
if (name.equals("Java")) { ... }  // correct: compares characters
```

### Mistake 2: Expecting a String method to change the original

```java
String s = "hello";
s.toUpperCase();            // result discarded; s is unchanged
s = s.toUpperCase();        // correct: reassign to keep the result
```

### Mistake 3: Building large text with `+` in a loop

```java
String r = "";
for (...) r += x;           // slow: a new String every iteration
// Use StringBuilder instead.
```

### Mistake 4: `substring` end index confusion

```java
"Java".substring(0, 4);     // "Java" -> end index is EXCLUSIVE
"Java".substring(1);        // "ava"  -> from index 1 to the end
```

---

## Interview Questions

### Q1: Why are Strings immutable in Java?

**Answer:** Once created, a String's contents cannot change. Immutability allows safe sharing (including between threads), enables the String Pool to reuse literals, and makes Strings reliable as keys in hash-based collections. Methods that seem to modify a String return a new one instead.

### Q2: What is the difference between `==` and `.equals()` for Strings?

**Answer:** `==` compares references (whether two variables point to the same object), while `.equals()` compares the actual character contents. To compare text, always use `.equals()`.

### Q3: What is the difference between `String` and `StringBuilder`?

**Answer:** `String` is immutable, so each modification creates a new object. `StringBuilder` is mutable and modifies its internal buffer in place, which is far more efficient when building or repeatedly changing text.

### Q4: What is the difference between `StringBuilder` and `StringBuffer`?

**Answer:** Both are mutable. `StringBuffer` is synchronised (thread-safe) but slower; `StringBuilder` is not synchronised and is faster. Use `StringBuilder` for single-threaded code and `StringBuffer` only when a buffer is shared across threads.

### Q5: What happens with `String s = "Hi"; s = s + " there";`?

**Answer:** The original `"Hi"` is not changed. A new String `"Hi there"` is created and `s` is reassigned to point to it. The old object becomes eligible for garbage collection if nothing else references it.

### Q6: Why is concatenating Strings with `+` in a loop discouraged?

**Answer:** Because String is immutable, each `+=` creates a new String and copies the old contents, which is O(n²) work over a loop. A `StringBuilder` modifies one buffer in place, making it much faster.

### Q7: What does `substring(a, b)` return?

**Answer:** The portion of the String from index `a` (inclusive) to index `b` (exclusive). For example, `"Java".substring(0, 4)` returns `"Java"`, and `"Java".substring(1)` returns `"ava"`.

---

*This is part of a Java learning series. Proceed to Chapter 13: Encapsulation to continue learning.*
