# Chapter 27: Types of Interfaces in Java

## Table of Contents

1. [Introduction](#introduction)
2. [Quick Recap: What Is an Interface](#quick-recap-what-is-an-interface)
3. [The Three Types at a Glance](#the-three-types-at-a-glance)
4. [File Map](#file-map)
5. [Type 1: Normal (Regular) Interface](#type-1-normal-regular-interface)
6. [Type 2: Functional (SAM) Interface](#type-2-functional-sam-interface)
7. [Type 3: Marker (Tagging) Interface](#type-3-marker-tagging-interface)
8. [How to Compile and Run](#how-to-compile-and-run)
9. [Side-by-Side Comparison](#side-by-side-comparison)
10. [Common Mistakes](#common-mistakes)
11. [Interview Questions](#interview-questions)

---

## Introduction

In [Chapter 24](../_24_Interface/README.md) you learned **what** an interface is. This chapter is about the **three flavours** an interface can take, named by **how many abstract methods they contain**:

| Abstract methods | Name of the interface |
|------------------|-----------------------|
| **2 or more** | **Normal** (regular) interface |
| **Exactly 1** | **Functional** (SAM) interface |
| **Zero** | **Marker** (tagging) interface |

That single number — how many abstract methods — is the entire idea behind this chapter. Everything else follows from it.

---

## Quick Recap: What Is an Interface

An interface is a **contract**: a list of methods a class promises to provide. A class signs the contract with `implements` and must supply a body for every abstract method.

```java
interface Animal { void sound(); }     // the contract

class Dog implements Animal {          // signs it
    public void sound() { System.out.println("Woof"); }
}
```

- Interface methods are `public abstract` **by default**.
- Interface fields are `public static final` **by default** (constants).
- You cannot create an interface with `new`; you create a class that implements it.

---

## The Three Types at a Glance

```text
                 ┌──────────────────────────────────────────────┐
                 │              INTERFACE TYPES                  │
                 └──────────────────────────────────────────────┘
                                     │
        ┌────────────────────────────┼────────────────────────────┐
        ▼                            ▼                             ▼
  NORMAL (2+ methods)        FUNCTIONAL (1 method)         MARKER (0 methods)
  a full contract            replaceable by a lambda       just a type "tag"
  e.g. RemoteControl         e.g. Calculator               e.g. Serializable
```

---

## File Map

| File | Type | Topic |
|------|------|-------|
| [`_27_1_Normal_Interface_Code.java`](_27_1_Normal_Interface_Code.java) | Normal | many methods + constant, `default`, `static`, `private` members |
| [`_27_2_Functional_OR_SAM_Interface_Code.java`](_27_2_Functional_OR_SAM_Interface_Code.java) | Functional | one method, lambdas, method references, built-ins |
| [`_27_3_Marker_Interface_Code.java`](_27_3_Marker_Interface_Code.java) | Marker | empty interface used as a tag with `instanceof` |

---

## Type 1: Normal (Regular) Interface

A **normal** interface has **two or more abstract methods**. It is the everyday contract from Chapter 24. Since Java 8 and 9, an interface can hold a lot more than abstract methods, and this file shows **all five** kinds of members in one place.

### The five member kinds

| Member | Has a body? | Who provides it | Since |
|--------|-------------|-----------------|-------|
| **constant** (`int MAX_VOLUME = 100;`) | n/a | the interface (a shared `public static final` value) | always |
| **abstract method** | ❌ no | the **implementing class must** | always |
| **`default` method** | ✅ yes | the interface (class may override) | Java 8 |
| **`static` method** | ✅ yes | the interface (called on the interface) | Java 8 |
| **`private` method** | ✅ yes | the interface (hidden helper) | Java 9 |

```java
interface RemoteControl {

    int MAX_VOLUME = 100;                       // 1) CONSTANT

    void turnOn();                              // 2) ABSTRACT (more than one =>
    void turnOff();                             //    this is a "normal" interface)
    void setVolume(int level);

    default void mute() {                       // 3) DEFAULT (ready-made body)
        setVolume(0);
        System.out.println("Muted (via default method)");
    }

    static boolean isValidVolume(int level) {   // 4) STATIC (utility on the interface)
        return level >= 0 && level <= MAX_VOLUME;
    }

    private void log(String action) {           // 5) PRIVATE (shared helper, Java 9)
        System.out.println("[RemoteControl] " + action);
    }

    default void reset() {                      // a default method reusing the private one
        log("reset requested");
        turnOff();
        turnOn();
    }
}
```

### Why `default` methods exist

Before Java 8, adding a method to an interface **broke every class** that implemented it (they suddenly had an unimplemented method). `default` methods solve this: the interface ships a ready-made body, so old classes keep working without changes — and any class is still free to **override** it. In the code, `AcRemote` overrides `mute()` to refuse muting:

```java
class AcRemote implements RemoteControl {
    @Override
    public void mute() {
        System.out.println("AC cannot be muted (overrode the default mute())");
    }
    // ... abstract methods implemented too
}
```

### Why `static` and `private` methods exist

- **`static`** methods are utilities that belong to the interface itself, called as `RemoteControl.isValidVolume(80)`. They are **not** inherited by implementing classes.
- **`private`** methods (Java 9+) let two `default` methods share code **without** exposing that helper as part of the public contract.

---

## Type 2: Functional (SAM) Interface

A **functional** interface has **exactly one abstract method**. **SAM = Single Abstract Method.** Because there is only one method to fill in, the compiler lets you replace a whole class with a tiny **lambda**.

```java
@FunctionalInterface          // optional, but makes the compiler ENFORCE "one method"
interface A {
    void show(int i);         // THE single abstract method
}
```

> `@FunctionalInterface` (from [Chapter 26](../_26_Annotations/README.md)) is optional, but adding it means the compiler will **error** if anyone later adds a second abstract method — protecting every lambda that depends on this interface.

### From verbose class to one-line lambda

The file shows the same logic getting shorter and shorter:

```java
// STEP 1 — anonymous inner class (the old, verbose way, Chapter 23)
A oldWay = new A() {
    @Override public void show(int i) { System.out.println("Anonymous class show: " + i); }
};

// STEP 2 — a lambda: parameters -> body
A lambda   = (int i) -> System.out.println("Lambda show: " + i);

// STEP 3 — drop the type (it is inferred)
A inferred = (i)     -> System.out.println("Inferred-type show: " + i);

// STEP 4 — one parameter? drop the parentheses too
A shortest = i       -> System.out.println("Shortest show: " + i);

// STEP 5 — method reference: point at an existing method with ::
A methodRef = _27_2_Functional_OR_SAM_Interface_Code::printLoudly;
```

All five do the **same thing** — they just get progressively shorter.

### Lambdas can return values too

```java
@FunctionalInterface
interface Calculator { int operate(int a, int b); }

Calculator add = (a, b) -> a + b;
Calculator max = (a, b) -> a > b ? a : b;
add.operate(7, 8);   // 15
```

### You rarely need to write your own

The `java.util.function` package already provides ready-made functional interfaces:

| Interface | Shape | Meaning |
|-----------|-------|---------|
| `Runnable` | `() -> void` | do something, return nothing |
| `Supplier<T>` | `() -> T` | supply a value |
| `Consumer<T>` | `(T) -> void` | consume a value |
| `Function<T,R>` | `(T) -> R` | transform T into R |
| `Predicate<T>` | `(T) -> boolean` | test a condition |
| `BiFunction<T,U,R>` | `(T,U) -> R` | combine two inputs into one output |

```java
Predicate<Integer> isEven = n -> n % 2 == 0;
isEven.test(10);   // true
```

---

## Type 3: Marker (Tagging) Interface

A **marker** interface is **completely empty** — no methods, no constants. It does not add behaviour; it adds a **type tag**. Code can then ask *"is this object an instance of the marker?"* with `instanceof` and decide what to do.

```java
interface Deletable { }       // empty on purpose — it is only a tag
interface Downloadable { }     // another tag

class Document implements Deletable, Downloadable { ... }  // carries BOTH tags
class Movie    implements Downloadable { ... }              // NOT deletable
```

### How the tag is used

A service checks the tag before acting. Forget the tag, and the object simply cannot be deleted:

```java
static void delete(Object item) {
    if (item instanceof Deletable) {           // the marker check
        System.out.println("Deleting: " + item.getClass().getSimpleName());
    } else {
        System.out.println("REFUSED to delete " + item.getClass().getSimpleName()
                + " - it is not marked Deletable");
    }
}
```

### Why an *empty* interface is useful

- It attaches metadata at the **type level**, checkable by both the compiler and the runtime, with no fields or methods.
- The JDK uses several markers you already rely on:
  - `java.io.Serializable` → "this object may be turned into bytes"
  - `java.lang.Cloneable` → "`Object.clone()` is allowed here"
  - `java.util.RandomAccess` → "indexed `get(i)` is fast on this list"

```java
"hello" instanceof java.io.Serializable   // true  (String is Serializable)
movie   instanceof java.io.Serializable   // false (our Movie is not)
```

> **Marker vs annotation:** Today, annotations (Chapter 26) often replace markers. But a marker is a real **type**, so it gives **compile-time** checks an annotation can't — for example, a method can demand a `Deletable` parameter and the compiler will reject anything untagged.

---

## How to Compile and Run

From the repository root (`d:\Repos\java`):

```bash
javac _27_Interfaces_Types/_27_1_Normal_Interface_Code.java
javac _27_Interfaces_Types/_27_2_Functional_OR_SAM_Interface_Code.java
javac _27_Interfaces_Types/_27_3_Marker_Interface_Code.java

java _27_Interfaces_Types._27_1_Normal_Interface_Code
java _27_Interfaces_Types._27_2_Functional_OR_SAM_Interface_Code
java _27_Interfaces_Types._27_3_Marker_Interface_Code
```

### Expected Output — Normal interface (`_27_1`)

```text
TV: ON
TV volume set to 45
Rejected volume 250 (must be 0..100)
TV volume set to 0
Muted (via default method)
[RemoteControl] reset requested
TV: OFF
TV: ON
TV: OFF

AC: ON
AC cannot be muted (overrode the default mute())
AC: OFF

Is 80 a valid volume?  true
Is 120 a valid volume? false
MAX_VOLUME = 100
```

### Expected Output — Functional interface (`_27_2`)

```text
Anonymous class show: 1
Lambda show: 2
Inferred-type show: 3
Shortest show: 4
>>> 5 <<<

[Calculator] I combine two ints into one
add(7, 8) = 15
max(7, 8) = 8

isEven(10) = true
isEven(7)  = false
```

### Expected Output — Marker interface (`_27_3`)

```text
=== delete() respects the Deletable marker ===
Deleting: Document
REFUSED to delete Movie - it is not marked Deletable

=== download() respects the Downloadable marker ===
Downloading: Document
Downloading: Movie

=== a built-in marker: java.io.Serializable ===
Is "hello" Serializable? true
Is a Movie Serializable?  false
```

---

## Side-by-Side Comparison

| Aspect | Normal | Functional (SAM) | Marker |
|--------|--------|------------------|--------|
| Abstract methods | 2 or more | exactly 1 | 0 |
| Usable as a lambda? | No | **Yes** | No |
| Typical use | a full capability contract | a single action passed as a value | a type tag checked with `instanceof` |
| Special annotation | — | `@FunctionalInterface` | — |
| JDK example | `List`, `Map` | `Runnable`, `Comparator` | `Serializable`, `Cloneable` |

---

## Common Mistakes

### Mistake 1: Putting two abstract methods in a `@FunctionalInterface`

```java
@FunctionalInterface
interface A {
    void show(int i);
    void hide();        // ERROR: a functional interface allows only ONE
}
```

### Mistake 2: Thinking `default`/`static` methods break "functional"

They don't. Only **abstract** methods count toward the "exactly one" rule. A functional interface may have many `default`/`static`/`private` methods.

### Mistake 3: Forgetting to implement every abstract method of a normal interface

```java
class TvRemote implements RemoteControl {
    public void turnOn() { ... }
    // missing turnOff() and setVolume() -> compile error
}
```

### Mistake 4: Reducing visibility when implementing

```java
class TvRemote implements RemoteControl {
    void turnOn() { ... }    // ERROR: must be public (interface methods are public)
}
```

### Mistake 5: Putting methods inside a marker interface

A marker interface must stay **empty**. The moment you add a method, it stops being a marker and becomes a normal/functional interface.

---

## Interview Questions

### Q1: What are the three types of interfaces in Java?

**Answer:** Normal (two or more abstract methods), Functional/SAM (exactly one abstract method), and Marker (zero methods). They are distinguished by how many abstract methods they declare.

### Q2: What is a functional interface and why does it matter?

**Answer:** An interface with exactly one abstract method (a SAM). It matters because it can be implemented with a lambda expression or method reference, making code much shorter. `@FunctionalInterface` makes the compiler enforce the single-method rule.

### Q3: Do `default` and `static` methods stop an interface from being functional?

**Answer:** No. Only abstract methods are counted. A functional interface can have any number of `default`, `static`, or `private` methods and still qualify.

### Q4: Why were `default` methods added in Java 8?

**Answer:** To let new methods be added to existing interfaces without breaking the classes that already implement them. The interface supplies a body, so old implementers keep compiling, and they may override it if needed.

### Q5: What is a marker interface? Give examples.

**Answer:** An empty interface used purely as a type tag, checked with `instanceof`. Examples include `java.io.Serializable`, `java.lang.Cloneable`, and `java.util.RandomAccess`.

### Q6: Marker interface vs annotation — when would you choose each?

**Answer:** Both attach metadata. A marker interface is a real type, so it enables compile-time checks (a method can require the marker type as a parameter). An annotation is more flexible and can carry data (elements) and target many places, but is usually checked at runtime via reflection.

### Q7: Can a class implement more than one interface?

**Answer:** Yes. A class can implement many interfaces (e.g. `class Document implements Deletable, Downloadable`), which is how Java provides multiple inheritance of type. It can still only `extend` one class.

---

*This is part of a Java learning series. You have completed the interfaces track — revisit [Chapter 24: Interfaces](../_24_Interface/README.md) and [Chapter 26: Annotations](../_26_Annotations/README.md) to connect the ideas.*
