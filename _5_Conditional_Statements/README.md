# Chapter 5: Conditional Statements in Java

## Table of Contents

1. [Introduction](#introduction)
2. [What is a Conditional Statement](#what-is-a-conditional-statement)
3. [The `if` Statement](#the-if-statement)
4. [The `if-else` Statement](#the-if-else-statement)
5. [The `if-else-if` Ladder](#the-if-else-if-ladder)
6. [Nested `if`](#nested-if)
7. [The Ternary Operator](#the-ternary-operator)
8. [The `switch` Statement](#the-switch-statement)
9. [`if-else` vs `switch`](#if-else-vs-switch)
10. [How to Compile and Run](#how-to-compile-and-run)
11. [Common Mistakes](#common-mistakes)
12. [Interview Questions](#interview-questions)

---

## Introduction

So far our programs have run every line from top to bottom. **Conditional statements** let a program make decisions — run some code only when a condition is true, and other code otherwise. This is what makes a program behave differently for different inputs.

This chapter is split into three small programs:

| File | Topic |
|------|-------|
| `_5_1_If_Else_Code.java`  | `if`, `if-else`, `if-else-if`, nested `if` |
| `_5_2_Ternary_Code.java`  | The `? :` ternary operator |
| `_5_3_Switch_Code.java`   | The `switch` statement |

---

## What is a Conditional Statement

A conditional statement evaluates a **boolean condition** (something that is `true` or `false`, built using the relational and logical operators from Chapter 4) and chooses which block of code to run.

```
            condition?
             /     \
          true     false
           |         |
        run block  skip / run else block
```

---

## The `if` Statement

Runs a block **only** when the condition is true.

```java
if (marks >= 33) {
    System.out.println("Pass");
}
```

If the condition is `false`, the block is skipped entirely.

---

## The `if-else` Statement

Provides an alternative block for when the condition is false. Exactly one of the two blocks always runs.

```java
if (number % 2 == 0) {
    System.out.println("even");
} else {
    System.out.println("odd");
}
```

---

## The `if-else-if` Ladder

Tests several conditions **in order**. The first one that is true runs, and all the rest are skipped. The final `else` is optional and acts as a catch-all.

```java
if (marks >= 90) {
    grade = 'A';
} else if (marks >= 75) {
    grade = 'B';
} else if (marks >= 60) {
    grade = 'C';
} else {
    grade = 'F';
}
```

> Order matters. Because the ladder stops at the first true condition, you usually arrange conditions from the most specific/most restrictive to the least.

---

## Nested `if`

An `if` placed inside another `if`. The inner condition is only checked when the outer one is true.

```java
if (age >= 18) {
    if (hasId) {
        System.out.println("Entry allowed");
    }
}
```

This is equivalent to `if (age >= 18 && hasId)`, but nesting is handy when each level needs its own `else`.

---

## The Ternary Operator

The ternary operator is a compact `if-else` that **produces a value**:

```java
result = condition ? valueIfTrue : valueIfFalse;
```

Example:

```java
int max = (a > b) ? a : b;
String type = (n % 2 == 0) ? "even" : "odd";
```

| Part            | Meaning                              |
|-----------------|--------------------------------------|
| `condition`     | a boolean expression                 |
| `valueIfTrue`   | used when the condition is true      |
| `valueIfFalse`  | used when the condition is false     |

Use it for short either/or choices. For anything complex, a normal `if-else` is more readable.

---

## The `switch` Statement

A `switch` compares one variable against several constant `case` labels and runs the matching block. It is cleaner than a long ladder when you test a single value against fixed options.

```java
switch (day) {
    case 1:
        dayName = "Monday";
        break;
    case 2:
        dayName = "Tuesday";
        break;
    default:
        dayName = "Weekend";
}
```

**Key parts:**

- **`case`** – a constant value to compare against.
- **`break`** – stops execution and exits the switch. Without it, execution "falls through" into the next case.
- **`default`** – runs when no case matches (like the final `else`). It is optional.

**Fall-through** can be used on purpose by stacking cases that share a block:

```java
switch (d) {
    case 1: case 2: case 3: case 4: case 5:
        System.out.println("Weekday");
        break;
    case 6: case 7:
        System.out.println("Weekend");
        break;
}
```

A `switch` works with `int` (and smaller integer types), `char`, `String`, and `enum`.

---

## `if-else` vs `switch`

| Aspect            | `if-else-if`                          | `switch`                                |
|-------------------|---------------------------------------|-----------------------------------------|
| Condition type    | Any boolean expression (ranges, etc.) | Equality against constant values only   |
| Best for          | Ranges, complex/combined conditions   | One variable vs many fixed values       |
| Readability       | Can get long                          | Cleaner for many discrete cases         |
| Fall-through      | Not applicable                        | Possible (and sometimes useful)         |

Use `if-else` when testing ranges or combined conditions (`marks >= 75`). Use `switch` when checking one value against a list of exact options (`day == 1`, `2`, `3`...).

---

## How to Compile and Run

```bash
javac _5_Conditional_Statements/_5_1_If_Else_Code.java
java _5_Conditional_Statements._5_1_If_Else_Code

javac _5_Conditional_Statements/_5_2_Ternary_Code.java
java _5_Conditional_Statements._5_2_Ternary_Code

javac _5_Conditional_Statements/_5_3_Switch_Code.java
java _5_Conditional_Statements._5_3_Switch_Code
```

---

## Common Mistakes

### Mistake 1: Using `=` instead of `==`

```java
if (x = 5) { ... }   // ERROR: = assigns; a condition needs ==
if (x == 5) { ... }  // correct
```

### Mistake 2: Forgetting `break` in a switch

```java
switch (n) {
    case 1:
        System.out.println("one");
        // no break -> falls through and also prints "two"
    case 2:
        System.out.println("two");
        break;
}
```

Add `break` unless you intentionally want fall-through.

### Mistake 3: A misplaced semicolon after `if`

```java
if (x > 0);   // the ; ends the if; the block below ALWAYS runs
{
    System.out.println("positive");
}
```

Do not put a semicolon directly after the `if (...)` condition.

### Mistake 4: Overusing nested ternaries

Deeply nested `? :` expressions are hard to read. Prefer an `if-else-if` ladder once there is more than one level.

---

## Interview Questions

### Q1: What is the difference between `if-else` and `switch`?

**Answer:** `if-else` can test any boolean condition, including ranges and combined conditions. `switch` only tests equality of a single variable against constant values, but it is cleaner and often faster when checking one value against many fixed options.

### Q2: What is the purpose of `break` in a `switch`?

**Answer:** `break` exits the switch after a case runs. Without it, execution "falls through" and continues into the following cases until a `break` or the end of the switch is reached.

### Q3: What is the `default` case?

**Answer:** It is the block that runs when no `case` matches the switch value, similar to the final `else` in an if-else-if ladder. It is optional.

### Q4: What is the ternary operator and when should you use it?

**Answer:** It is a three-operand conditional, `condition ? valueIfTrue : valueIfFalse`, that produces a value. Use it for short, simple either/or choices. For complex logic, a normal `if-else` is more readable.

### Q5: Which data types can a `switch` work with?

**Answer:** `byte`, `short`, `int`, `char`, their wrapper classes, `String`, and `enum` types. It cannot switch on `long`, `float`, `double`, or `boolean`.

### Q6: In an if-else-if ladder, what happens if two conditions are true?

**Answer:** Only the **first** true condition (from top to bottom) runs; the rest are skipped. This is why the order of conditions matters.

### Q7: Can you switch on a `String` in Java?

**Answer:** Yes, since Java 7 a `switch` can use `String` values. The comparison uses `String.equals()` internally and is case-sensitive.

---

*This is part of a Java learning series. Proceed to Chapter 6: Classes and Objects to continue learning.*
