# Chapter 26: Annotations in Java

## Table of Contents

1. [Introduction](#introduction)
2. [What Is an Annotation](#what-is-an-annotation)
3. [Who Reads Annotations](#who-reads-annotations)
4. [The 5 Sections in This Chapter](#the-5-sections-in-this-chapter)
5. [Section 1: `@Override`](#section-1-override)
6. [Section 2: The Other Built-in Annotations](#section-2-the-other-built-in-annotations)
7. [Section 3: Meta-Annotations](#section-3-meta-annotations)
8. [Section 4: Writing Your Own Annotation](#section-4-writing-your-own-annotation)
9. [Section 5: Reading Annotations by Reflection](#section-5-reading-annotations-by-reflection)
10. [How to Compile and Run](#how-to-compile-and-run)
11. [Quick Reference Tables](#quick-reference-tables)
12. [Common Mistakes](#common-mistakes)
13. [Interview Questions](#interview-questions)

---

## Introduction

An **annotation** is **extra information you attach to your code** using the `@` symbol. The most important idea to understand on day one:

> An annotation by itself **does nothing**. It is just a label. Its power comes from **someone reading that label and reacting to it**.

Think of an annotation like a **sticky note** on a folder, or a **luggage tag** on a suitcase. The note does not move the suitcase — but the airport staff who *read* the tag know where to send it.

This whole chapter lives in **one runnable file**: [`_26_Annotations_Code.java`](_26_Annotations_Code.java). Run it once and you will watch all five topics print their results in order.

---

## What Is an Annotation

You attach an annotation by writing `@Name` directly above a class, method, field, or parameter:

```java
@Override
public void shows() { ... }     // annotation on a method

@Deprecated
public void payOld() { ... }    // annotation saying "don't use me"
```

Some annotations can also carry **values** inside parentheses:

```java
@Author(name = "Aditya", date = "2026-06-03")
class TaskRunner { }
```

That is all the syntax there is. The interesting part is *who reads it*.

---

## Who Reads Annotations

There are exactly **three** audiences that read annotations. Knowing which audience an annotation is for explains everything about how it behaves.

| # | Reader | Example annotations | When it acts |
|---|--------|---------------------|--------------|
| 1 | **The compiler** | `@Override`, `@Deprecated`, `@SuppressWarnings` | While you compile |
| 2 | **Tools & frameworks** | JUnit `@Test`, Spring `@Autowired`, JPA `@Entity` | When the tool runs |
| 3 | **Your own code** (via Reflection) | any custom annotation you write | While the program runs |

This chapter shows all three: the compiler ones (Sections 1–2), how to *build* annotations for the framework/runtime audience (Sections 3–4), and how to *read them yourself* at runtime (Section 5).

---

## Section 1: `@Override`

`@Override` is the annotation you will use the most. It tells the compiler:

> "I am **on purpose** replacing a method from my parent class. Please **check** that a matching method really exists up there."

### The bug it catches

Our code has a parent `A` with a method named `shows()` (note the **'s'**):

```java
class A {
    public void shows() {            // the real name is shows()
        System.out.println("In A.shows()");
    }
}

class B extends A {
    @Override
    public void shows() {            // correctly overrides A.shows()
        System.out.println("In B.shows() (correctly overrides A.shows())");
    }
}
```

Now imagine you **mistype** the name in `B` as `show()` (missing the 's'):

| Without `@Override` | With `@Override` |
|---------------------|------------------|
| Compiler stays silent. It thinks `show()` is a brand-new method. `A`'s old `shows()` keeps running. **Silent bug** that you only notice much later. | Compiler **immediately errors**: *"method does not override a method from its superclass"*. You fix the typo in seconds. |

That is the whole point: **`@Override` turns a silent runtime bug into a loud compile-time error.**

> 💡 `@Override` is a *compile-time-only* check. It is thrown away by the compiler and never reaches the running program (this is called `SOURCE` retention — see Section 3).

---

## Section 2: The Other Built-in Annotations

Java ships four more everyday annotations in the `java.lang` package. All four are read by the **compiler**.

### `@Deprecated` — "old, don't use me"

Marks something as outdated so the compiler warns anyone who uses it.

```java
@Deprecated(since = "2.0", forRemoval = true)
public void payOld() { ... }       // IDE shows this ~struck through~

public void pay() { ... }          // the new method to use instead
```

- `since` — the version it became outdated.
- `forRemoval = true` — it is scheduled to be **deleted** in a future version.

> ⚠️ **Advanced nuance (real Java behaviour):** when `forRemoval = true`, the warning belongs to the **`"removal"`** category, **not** `"deprecation"`. So to silence it you suppress `"removal"`, not `"deprecation"`. The code comments call this out.

### `@SuppressWarnings` — "mute these warnings here"

Tells the compiler to hide specific warning types for one element.

```java
@SuppressWarnings({ "removal", "unused" })
public static void main(String[] args) { ... }
```

Common values: `"deprecation"`, `"removal"`, `"unchecked"`, `"unused"`, `"all"`.
Use it **sparingly** — a hidden warning is a warning you chose to take responsibility for.

### `@SafeVarargs` — "my generic varargs is safe"

A generic varargs parameter `(T...)` makes Java nervous (it warns about "unchecked / heap pollution"). If you know your method only **reads** the values, this annotation promises that and silences the warning.

```java
@SafeVarargs
private static <T> List<T> listOf(T... items) {
    List<T> list = new ArrayList<>();
    for (T item : items) list.add(item);   // only reading — safe
    return list;
}
```

It is only allowed on methods that **can't be overridden** (`static`, `final`, or `private`), so the promise can't be broken by a subclass.

### `@FunctionalInterface` — "exactly one abstract method"

Declares that an interface is meant to have **a single abstract method** so it can be used with a lambda. The compiler then **enforces** that rule. (Full treatment in [Chapter 27, file `_27_2`](../_27_Interfaces_Types/_27_2_Functional_OR_SAM_Interface_Code.java).)

```java
@FunctionalInterface
interface Calculator {
    int operate(int a, int b);     // the ONE abstract method
    default void banner() { ... }  // default methods don't count
}

Calculator add = (x, y) -> x + y;  // lambda works because there's one method
```

---

## Section 3: Meta-Annotations

A **meta-annotation** is **an annotation that you put on *another annotation*** to configure how it behaves. Think of them as the **settings** for the custom labels you create. There are five.

### `@Retention` — how long the annotation lives

This is the most important meta-annotation. It has three settings:

| Policy | Kept in `.class` file? | Visible at runtime? | Example / use |
|--------|------------------------|---------------------|---------------|
| `SOURCE`  | ❌ no  | ❌ no | `@Override` — compiler check only, then discarded |
| `CLASS`   | ✅ yes | ❌ no | the **default** if you don't specify |
| `RUNTIME` | ✅ yes | ✅ **yes** | needed so **frameworks/reflection can read it** |

> 🔑 **Rule of thumb:** if you want to read an annotation with reflection (Section 5), it **must** be `@Retention(RUNTIME)`. This single fact explains 90% of "why is my annotation not being found?" problems.

### `@Target` — where the annotation may be used

Restricts an annotation to certain places. If you use it elsewhere, it's a compile error.

```java
@Target({ ElementType.TYPE, ElementType.METHOD })  // only on classes & methods
@interface Author { ... }
```

Common targets: `TYPE` (class/interface/enum), `METHOD`, `FIELD`, `PARAMETER`, `CONSTRUCTOR`.

### `@Documented` — show it in Javadoc

Asks the Javadoc tool to display the annotation in the generated API documentation.

### `@Inherited` — let subclasses inherit it

Normally a subclass does **not** see an annotation placed on its parent. `@Inherited` flips that:

```java
@Inherited
@interface Framework { String value(); }

@Framework("Spring")
class BaseEntity { }

class ChildEntity extends BaseEntity { }   // has NO @Framework of its own...
```

Yet asking `ChildEntity` for `@Framework` returns **"Spring"**, because the parent's annotation is `@Inherited`. (Works for class annotations only.)

### `@Repeatable` — apply the same annotation many times

By default you can use an annotation on something only once. `@Repeatable` allows repeats, but you must also create a **container** annotation to hold them:

```java
@Repeatable(Schedules.class)
@interface Schedule { String day(); }

@interface Schedules { Schedule[] value(); }   // the container (an array)

@Schedule(day = "Monday")
@Schedule(day = "Friday")                      // same annotation twice — allowed
class TaskRunner { }
```

You read all repeats at once with `getAnnotationsByType(Schedule.class)`.

---

## Section 4: Writing Your Own Annotation

You declare an annotation with **`@interface`** (the `@` is part of it). Inside, each "method" is an **element** — a named attribute you can set.

```java
@Retention(RetentionPolicy.RUNTIME)   // so reflection can read it (Section 5)
@Target(ElementType.METHOD)
@interface TestCase {
    String description();                        // REQUIRED (no default)
    Priority priority() default Priority.MEDIUM; // optional (enum default)
    int timeoutMillis() default 1000;            // optional (primitive default)
    String[] tags() default {};                  // optional (array, default empty)
}
```

### Element rules (the things people forget)

- An element's type must be a **primitive, `String`, `Class`, an enum, another annotation, or an array** of those.
- Elements have **no parameters and no body**.
- `default <value>` makes an element **optional**. With no default, it is **required**.
- An element named exactly **`value`** can be set without writing `value=`:

```java
@interface Role { String value(); }

@Role("ADMIN")              // shorthand for @Role(value = "ADMIN")
```

### Using them

```java
@TestCase(
    description = "login succeeds with valid credentials",
    priority = Priority.HIGH,
    tags = { "auth", "smoke" })
public void testLogin() { }

@TestCase(description = "cart total adds up")  // the rest use their defaults
public void testCart() { }
```

---

## Section 5: Reading Annotations by Reflection

This is **why annotations exist**. Frameworks like JUnit, Spring, and Jackson read *your* annotations at runtime using **Reflection** (the ability of a running program to inspect its own classes, methods, and fields) and then act on them.

In the file we build **two tiny frameworks ourselves** to prove the idea.

### Mini-framework 1: a JSON serializer driven by `@JsonField`

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface JsonField { String name() default ""; }

class Account {
    @JsonField(name = "account_id") int id;
    @JsonField                      String owner;     // key = "owner"
    @JsonField(name = "balance")    int balance;
    String internalNote;            // NO @JsonField -> skipped in output
}
```

The serializer walks every field, and **only fields tagged with `@JsonField`** appear in the JSON:

```text
{"account_id": 1, "owner": "Ada", "balance": 500}
```

Notice `internalNote` is **missing** — proving the output is driven by the annotation, not by the raw field list.

### Mini-framework 2: a validator driven by `@Range`

```java
@interface Range { int min(); int max(); }

@Range(min = 0, max = 1_000_000)
int balance;
```

The validator reads each `@Range` and reports any value outside the bounds:

```text
   OK     : balance = 500
   INVALID: balance = -50 (allowed 0..1000000)
```

### The key reflection calls

| Call | What it does |
|------|--------------|
| `SomeClass.class.getAnnotation(X.class)` | get one annotation on a class (or `null`) |
| `method.getAnnotation(X.class)` | get an annotation on a method |
| `field.getAnnotation(X.class)` | get an annotation on a field |
| `SomeClass.class.getAnnotationsByType(X.class)` | get **all** repeats of a `@Repeatable` annotation |
| `field.setAccessible(true)` then `field.get(obj)` | read the field's value |

> 🔑 Remember: **none** of this works unless the annotation is `@Retention(RUNTIME)`.

---

## How to Compile and Run

From the repository root (`d:\Repos\java`):

```bash
javac _26_Annotations/_26_Annotations_Code.java
java _26_Annotations._26_Annotations_Code
```

### Expected Output

```text
===== SECTION 1 : @Override =====
In B.shows() (correctly overrides A.shows())
In B.shows() (correctly overrides A.shows())

===== SECTION 2 : other built-in annotations =====
Old payment flow (DEPRECATED - will be removed)
New payment flow
listOf(...) = [Ada, Linus, Grace]
[Calculator] combines two ints into one
add(2, 3) = 5
mul(2, 3) = 6

===== SECTION 3 : meta-annotations =====
@Author name = Aditya, date = 2026-06-03
BaseEntity  @Framework = Spring
ChildEntity @Framework = Spring   <-- inherited via @Inherited
TaskRunner runs on 2 day(s):
   - Monday
   - Friday

===== SECTION 4 : custom annotations =====
Class-level @Role value = ADMIN
Scanning methods for @TestCase ...
  testLogin() -> desc='login succeeds with valid credentials', priority=HIGH, timeout=1000, tags=[auth, smoke]
  testCart() -> desc='cart total adds up', priority=MEDIUM, timeout=1000, tags=[]

===== SECTION 5 : reflection mini-framework =====
JSON (driven by @JsonField):
  {"account_id": 1, "owner": "Ada", "balance": 500}
  {"account_id": 2, "owner": "Linus", "balance": -50}
Validation (driven by @Range):
 good account:
   OK     : balance = 500
 bad account:
   INVALID: balance = -50 (allowed 0..1000000)
```

> The order of `testLogin` / `testCart` in Section 4 may swap — `getDeclaredMethods()` does not guarantee an order.

---

## Quick Reference Tables

### All annotations used in this chapter

| Annotation | Kind | One-line meaning |
|-----------|------|------------------|
| `@Override` | built-in | "I really override a parent method" (compiler checks) |
| `@Deprecated` | built-in | "old, don't use me" |
| `@SuppressWarnings` | built-in | "hide these warnings here" |
| `@SafeVarargs` | built-in | "my generic varargs is type-safe" |
| `@FunctionalInterface` | built-in | "this interface has exactly one abstract method" |
| `@Retention` | meta | how long the annotation lives |
| `@Target` | meta | where the annotation may be used |
| `@Documented` | meta | show it in Javadoc |
| `@Inherited` | meta | subclasses inherit it |
| `@Repeatable` | meta | may be applied more than once |
| `@Author`, `@TestCase`, `@Role`, `@JsonField`, `@Range` | custom | annotations we built ourselves |

### `@Retention` cheat-sheet

| Policy | Survives to runtime? | Use when |
|--------|----------------------|----------|
| `SOURCE` | No | Pure compiler checks (`@Override`) |
| `CLASS` (default) | No | You rarely need this explicitly |
| `RUNTIME` | **Yes** | **Anything read by reflection / frameworks** |

---

## Common Mistakes

### Mistake 1: Expecting an annotation to "do" something on its own

```java
@Range(min = 0, max = 100)
int balance = -50;     // -50 is NOT rejected automatically!
```

An annotation is just a label. **Something must read it** (our `validate()` method) for it to have any effect.

### Mistake 2: Forgetting `@Retention(RUNTIME)`

```java
@interface JsonField { }            // defaults to CLASS retention
field.getAnnotation(JsonField.class); // returns null at runtime — silent!
```

If reflection should see it, you **must** write `@Retention(RetentionPolicy.RUNTIME)`.

### Mistake 3: Suppressing the wrong warning category

```java
@Deprecated(forRemoval = true)            // warning category is "removal"
@SuppressWarnings("deprecation")          // does NOT silence it!
@SuppressWarnings("removal")              // correct
```

### Mistake 4: Giving a required element no value

```java
@interface TestCase { String description(); }  // required (no default)

@TestCase                                       // ERROR: description is missing
@TestCase(description = "ok")                    // correct
```

### Mistake 5: Using an annotation where its `@Target` forbids it

```java
@Target(ElementType.METHOD)
@interface TestCase { ... }

@TestCase                       // ERROR if placed on a field/class
int x;
```

---

## Interview Questions

### Q1: What is an annotation in Java?

**Answer:** It is metadata attached to code using `@`. By itself it changes nothing; it is read by the compiler, by tools/frameworks, or by your own code via reflection, which then act on it.

### Q2: What does `@Override` do, and why is it useful?

**Answer:** It tells the compiler that a method is intended to override a superclass method, and the compiler verifies a matching method exists. This catches typos and signature mismatches at compile time instead of letting them become silent runtime bugs.

### Q3: What is the difference between `@Deprecated` and `@SuppressWarnings`?

**Answer:** `@Deprecated` marks an element as outdated so the compiler *produces* a warning at every use. `@SuppressWarnings` does the opposite — it *hides* named categories of warnings for the element it is placed on.

### Q4: What is a meta-annotation? Name them.

**Answer:** A meta-annotation is an annotation applied to another annotation to configure it. The five built-in ones are `@Retention`, `@Target`, `@Documented`, `@Inherited`, and `@Repeatable`.

### Q5: Explain the three retention policies.

**Answer:** `SOURCE` is discarded by the compiler (e.g. `@Override`). `CLASS` is stored in the `.class` file but not loaded into the JVM (the default). `RUNTIME` is stored and loaded, so it is readable via reflection — required for frameworks.

### Q6: How do you read an annotation at runtime?

**Answer:** The annotation must use `@Retention(RUNTIME)`. Then you use reflection — e.g. `clazz.getAnnotation(MyAnno.class)`, `method.getAnnotation(...)`, `field.getAnnotation(...)`, or `getAnnotationsByType(...)` for repeatable ones — and read its elements.

### Q7: How do you create a custom annotation, and what types can its elements have?

**Answer:** Declare it with `@interface`. Each element looks like a method with no body; its type must be a primitive, `String`, `Class`, enum, another annotation, or an array of those. `default` makes an element optional, and an element named `value` can be set without naming it.

### Q8: What is `@Repeatable` and what does it require?

**Answer:** It lets the same annotation be applied multiple times on one element. It requires a **container** annotation whose single `value` element is an array of the repeatable type, and you read the repeats with `getAnnotationsByType(...)`.

---

*This is part of a Java learning series. Proceed to [Chapter 27: Types of Interfaces](../_27_Interfaces_Types/README.md) to continue learning.*
