# Chapter 13: Casting (Type Casting, Upcasting, and Downcasting)

## Table of Contents

1. [Introduction](#introduction)
2. [Two Different Worlds of Casting](#two-different-worlds-of-casting)
3. [Primitive Type Casting](#primitive-type-casting)
   - [Widening (Implicit)](#widening-implicit)
   - [Narrowing (Explicit)](#narrowing-explicit)
4. [Reference Casting: Upcasting](#reference-casting-upcasting)
5. [Reference Casting: Downcasting](#reference-casting-downcasting)
6. [The `instanceof` Operator](#the-instanceof-operator)
7. [Understanding the Code](#understanding-the-code)
8. [Line-by-Line Explanation](#line-by-line-explanation)
9. [How to Compile and Run](#how-to-compile-and-run)
10. [Common Mistakes](#common-mistakes)
11. [Interview Questions](#interview-questions)

---

## Introduction

**Casting** means converting a value from one type to another. Java uses the word
"casting" for two related but quite different ideas:

1. **Primitive type casting** -- converting between number types like `int`,
   `double`, `byte`, and `char`.
2. **Reference (object) casting** -- viewing an object through a different type
   in its inheritance hierarchy (**upcasting** and **downcasting**).

This chapter covers all three across three programs:

| File | Demonstrates |
|------|--------------|
| `_13_1_TypeCasting.java` | Widening and narrowing of **primitive** types |
| `_13_2_UpCasting.java`   | **Upcasting** a subclass object (`B`) to a superclass reference (`A`) |
| `_13_2_DownCasting.java` | **Downcasting** a superclass reference (`A`) back to the subclass (`B`) |

Both object-casting examples use the same simple hierarchy: a superclass `A` with
a method `showA()`, and a subclass `B extends A` that adds `showB()`. In each file
`A` and `B` are declared as **static nested classes** so that both files can keep
the same `A`/`B` names and still compile together in one package without clashing.

---

## Two Different Worlds of Casting

| | Primitive casting | Reference casting |
|---|---|---|
| Works on | `int`, `double`, `byte`, `char`, ... | objects (class instances) |
| Changes | the actual value/representation | only the *view* (the reference type) |
| Risk | data loss (truncation, overflow) | `ClassCastException` at runtime |
| Directions | widening / narrowing | upcasting / downcasting |

The key mental model: **primitive casting changes the data; reference casting
never changes the object -- it only changes which members you are allowed to
see.**

---

## Primitive Type Casting

Java's numeric primitives can be ordered by size/capacity:

```
byte -> short -> int -> long -> float -> double
                char -> int
```

### Widening (Implicit)

Moving **up** this chain (small type into a bigger one) is always safe, so Java
does it automatically -- no cast needed, no data lost.

```java
int a = 10;
long b = a;     // int  -> long   (automatic)
double c = b;   // long -> double (automatic)  => 10.0
```

### Narrowing (Explicit)

Moving **down** the chain (big type into a smaller one) may lose data, so Java
forces you to write an explicit `(type)` cast to show you accept the risk.

```java
double d = 9.99;
int e = (int) d;        // 9   -- the fraction is DROPPED, not rounded

int big = 257;
byte sb = (byte) big;   // 1   -- 257 overflows the byte range (-128..127)

int code = 65;
char ch = (char) code;  // 'A' -- 65 is the character code for 'A'
```

> Narrowing does **not** round. `(int) 9.99` is `9`, and `(int) 9.0001` is also `9`.

---

## Reference Casting: Upcasting

**Upcasting** treats a subclass object through a superclass reference. It is
**implicit** (no cast needed) and **always safe**, because a subclass object
genuinely *is* a kind of its superclass.

```java
B b = new B();
A ref = b;        // upcasting -- implicit, always safe
ref.showA();      // OK: showA() is declared in A
// ref.showB();   // COMPILE ERROR: showB() is not declared in A
```

The rule at play: **the reference type decides what is visible.** Through an `A`
reference you can only call methods declared in `A`, even though the object is
really a `B`.

---

## Reference Casting: Downcasting

**Downcasting** converts a superclass reference back to the subclass type, so the
subclass-specific members become available again. It must be written
**explicitly**, and it is only safe if the object *really is* that subclass --
otherwise Java throws a `ClassCastException` at runtime.

```java
A ref = new B();              // upcast first
ref.showA();                  // In Class A
// ref.showB();               // not visible through A

if (ref instanceof B) {       // check before casting
    B b = (B) ref;            // explicit downcast -- safe here
    b.showB();                // now accessible
}
```

If you downcast an object that is **not** actually the target type, it compiles
but crashes at runtime:

```java
A onlyA = new A();
B bad = (B) onlyA; // throws ClassCastException
```

---

## The `instanceof` Operator

`instanceof` tests whether an object is an instance of a given type (or subtype).
It returns a `boolean` and is the standard guard before a downcast:

```java
if (ref instanceof B) {
    B b = (B) ref; // guaranteed safe inside this block
    b.showB();
}
```

This prevents `ClassCastException` by confirming the real type first.

> **Modern shortcut (Java 16+):** *pattern matching for `instanceof`* lets you
> test and cast in one step, binding the result to a new variable:
>
> ```java
> if (ref instanceof B b) { // checks the type AND casts into 'b'
>     b.showB();
> }
> ```
>
> This chapter keeps the explicit `(B) ref` cast on purpose, so the downcast step
> is visible while you are learning what casting actually does. The pattern form
> is the idiomatic choice in real code. (Your IDE may show an informational hint
> suggesting it -- that is a style tip, not an error.)

---

## Understanding the Code

### `_13_1_TypeCasting.java`

```java
// Widening (implicit)
int a = 10;
long b = a;        // int  -> long
double c = b;      // long -> double  => 10.0

// Narrowing (explicit)
double d = 9.99;
int e = (int) d;        // 9   (fraction dropped)
int big = 257;
byte sb = (byte) big;   // 1   (overflow)
int code = 65;
char ch = (char) code;  // 'A' (code -> character)
```

### `_13_2_UpCasting.java`

```java
public class _13_2_UpCasting {
    static class A {
        public void showA() { System.out.println("In Class A"); }
    }
    static class B extends A {
        public void showB() { System.out.println("In Class B"); }
    }

    public static void main(String[] args) {
        B b = new B();
        A ref = b;      // upcast (implicit)
        ref.showA();    // In Class A
    }
}
```

### `_13_2_DownCasting.java`

```java
public class _13_2_DownCasting {
    static class A {
        public void showA() { System.out.println("In Class A"); }
    }
    static class B extends A {
        public void showB() { System.out.println("In Class B"); }
    }

    public static void main(String[] args) {
        A ref = new B();             // upcast
        if (ref instanceof B) {
            B b = (B) ref;           // downcast
            b.showB();               // In Class B
        }
    }
}
```

---

## Line-by-Line Explanation

### Widening

- `long b = a;` -- an `int` always fits in a `long`, so Java converts it for you.
- `double c = b;` -- a `long` fits in a `double`; the value gains a `.0`.

### Narrowing

- `(int) d` -- discards everything after the decimal point (`9.99 -> 9`).
- `(byte) big` -- a `byte` holds only `-128..127`; `257` wraps around to `1`.
- `(char) code` -- interprets the integer `65` as a Unicode/ASCII code -> `'A'`.

### Upcasting

- `A ref = b;` -- implicit upcast; no `(A)` needed.
- `ref.showA();` -- works because `showA()` is declared in `A`.
- `ref.showB();` -- would not compile: `showB()` is not part of `A`.

### Downcasting

- `A ref = new B();` -- upcast; reference is `A`, object is `B`.
- `ref instanceof B` -- confirms the real type before casting.
- `(B) ref` -- explicit downcast; now `showB()` is reachable.
- Casting a plain `A` to `B` would throw `ClassCastException`.

---

## How to Compile and Run

All three files declare `package _13_Casting;`. Because each file keeps its `A`
and `B` as **static nested classes**, the files are fully self-contained -- you
can compile them individually or all at once. Compile and run from the parent
directory using the package path:

```bash
# Compile everything in the chapter at once...
javac _13_Casting/*.java

# ...or one file at a time, e.g.:
# javac _13_Casting/_13_1_TypeCasting.java

# Then run each program:
java _13_Casting._13_1_TypeCasting
java _13_Casting._13_2_UpCasting
java _13_Casting._13_2_DownCasting
```

### Expected Output

`_13_1_TypeCasting`:

```
Widening:
int a    = 10
long b   = 10
double c = 10.0

Narrowing (double -> int):
double d = 9.99
int e    = 9

Narrowing (int -> byte, overflow):
int big = 257
byte sb = 1

Narrowing (int -> char):
int code = 65
char ch  = A
```

`_13_2_UpCasting`:

```
In Class A
In Class A
In Class B
```

`_13_2_DownCasting`:

```
In Class A
In Class B
onlyA is not a B - skipping downcast
```

---

## Common Mistakes

### Mistake 1: Expecting narrowing to round

```java
int e = (int) 9.99; // 9, NOT 10 -- the fraction is truncated
```

Casting a floating-point value to an integer drops the decimal part. Use
`Math.round()` if you want rounding.

### Mistake 2: Forgetting the explicit cast when narrowing

```java
int big = 257;
byte sb = big; // COMPILE ERROR: possible lossy conversion
byte ok = (byte) big; // correct -- you acknowledge the risk
```

### Mistake 3: Calling subclass methods through a superclass reference

```java
A ref = new B();
ref.showB(); // COMPILE ERROR: showB() not visible via A
```

The reference type limits visibility. Downcast to `B` first (after an
`instanceof` check).

### Mistake 4: Downcasting without checking the real type

```java
A onlyA = new A();
B b = (B) onlyA; // ClassCastException at runtime
```

Always guard a downcast with `instanceof` unless you are certain of the type.

### Mistake 5: Confusing the reference type with the object type

Upcasting/downcasting never changes the object -- only the *view* of it. A `B`
seen through an `A` reference is still a `B`, which is why a later downcast back
to `B` succeeds.

---

## Interview Questions

### Q1: What is type casting in Java?

**Answer:** Converting a value or reference from one type to another. For
primitives it changes the numeric representation (widening/narrowing); for
objects it changes the reference type used to view an object (upcasting/downcasting).

### Q2: What is the difference between widening and narrowing?

**Answer:** Widening converts a smaller primitive type to a larger one
(e.g. `int` -> `double`); it is implicit and lossless. Narrowing converts a
larger type to a smaller one (e.g. `double` -> `int`); it requires an explicit
cast and may lose data through truncation or overflow.

### Q3: Does casting a `double` to an `int` round the value?

**Answer:** No. It truncates -- the fractional part is discarded. `(int) 9.99`
is `9`. Use `Math.round()` for rounding.

### Q4: What is upcasting? Is a cast required?

**Answer:** Upcasting is referring to a subclass object through a superclass
reference (e.g. `A ref = new B();`). No explicit cast is required because it is
always safe -- a subclass object *is a* superclass.

### Q5: What is downcasting and why is it risky?

**Answer:** Downcasting converts a superclass reference back to a subclass type
to access subclass-specific members. It is risky because if the object is not
actually that subclass, Java throws a `ClassCastException` at runtime. It must be
written explicitly and is usually guarded with `instanceof`.

### Q6: Can you access subclass-specific methods through a superclass reference?

**Answer:** No. The reference type determines visibility, so only members
declared in the superclass are accessible. To use subclass-specific methods you
must downcast to the subclass type first.

### Q7: What does the `instanceof` operator do?

**Answer:** It returns `true` if an object is an instance of the specified class
or one of its subclasses (and is not `null`). It is the standard way to verify a
type before performing a downcast safely.

### Q8: What happens if you downcast incorrectly?

**Answer:** The code compiles (the types are related), but at runtime the JVM
throws a `java.lang.ClassCastException` because the object is not really an
instance of the target subclass.

### Q9: Why is upcasting implicit but downcasting explicit?

**Answer:** Upcasting is always safe -- every subclass object is also an instance
of its superclass -- so Java does it automatically. Downcasting may be invalid
(the object might not be the target subclass), so Java requires an explicit cast
to make you take responsibility for the risk.

### Q10: Does casting an object change the object itself?

**Answer:** No. Reference casting only changes how the object is *viewed* (which
members are accessible); the underlying object and its actual type are unchanged.

---

*This is part of a Java learning series. It builds on the chapters on Inheritance
and Objects, and underpins polymorphism, interfaces, and the collections
framework, where upcasting and `instanceof` are used constantly.*
