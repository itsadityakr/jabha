# Chapter 7: Loops in Java

## Table of Contents

1. [Introduction](#introduction)
2. [What is a Loop](#what-is-a-loop)
3. [The `while` Loop](#the-while-loop)
4. [The `do-while` Loop](#the-do-while-loop)
5. [The `for` Loop](#the-for-loop)
6. [The Enhanced `for-each` Loop](#the-enhanced-for-each-loop)
7. [`break` and `continue`](#break-and-continue)
8. [Nested Loops](#nested-loops)
9. [Which Loop Should I Use](#which-loop-should-i-use)
10. [How to Compile and Run](#how-to-compile-and-run)
11. [Common Mistakes](#common-mistakes)
12. [Interview Questions](#interview-questions)

---

## Introduction

A **loop** repeats a block of code as long as a condition stays true. Instead of writing the same line five times, you write it once inside a loop and let it run five times. Loops are how programs process lists, repeat actions, and count.

This chapter uses one program, `_7_While_DoWhile_For_Loops_Code.java`, that demonstrates all three loop types plus `for-each`, `break`, `continue`, and nesting.

---

## What is a Loop

Every loop has three logical parts, no matter the syntax:

1. **Initialisation** – a starting point (e.g. `i = 1`).
2. **Condition** – a boolean checked each round; the loop continues while it is true.
3. **Update** – a change that moves the loop toward ending (e.g. `i++`).

> If the update never makes the condition false, you get an **infinite loop**. Always make sure the loop can end.

---

## The `while` Loop

Checks the condition **before** each iteration. The body may run **zero** times if the condition starts false.

```java
int i = 1;
while (i <= 5) {
    System.out.print(i + " ");
    i++;            // update
}
// prints: 1 2 3 4 5
```

Use `while` when you do not know in advance how many times to loop (e.g. "keep reading until the input ends").

---

## The `do-while` Loop

Runs the body **first**, then checks the condition. So it always runs **at least once**, even if the condition is false.

```java
int j = 1;
do {
    System.out.print(j + " ");
    j++;
} while (j <= 5);
// prints: 1 2 3 4 5
```

Use `do-while` when the body must execute at least once (e.g. show a menu, then ask whether to repeat).

> Note the **semicolon** after the `while(...)` in a do-while.

---

## The `for` Loop

Packs initialisation, condition, and update onto one line. Ideal when you know how many times to repeat.

```java
for (int k = 1; k <= 5; k++) {
    System.out.print(k + " ");
}
// prints: 1 2 3 4 5
```

```
for ( init ; condition ; update )
       |         |          |
   runs once  checked    runs after
   at start   each round  each round
```

---

## The Enhanced `for-each` Loop

Iterates over **every element** of an array or collection without a counter or index. It is cleaner and avoids index errors when you simply need each value.

```java
int[] nums = {10, 20, 30};
for (int n : nums) {
    System.out.print(n + " ");
}
// prints: 10 20 30
```

Read `for (int n : nums)` as "for each int `n` in `nums`". You cannot use it to change array elements by index or to know the current index — use a normal `for` loop for that.

---

## `break` and `continue`

| Keyword    | Effect                                                        |
|------------|--------------------------------------------------------------|
| `break`    | Exits the **entire** loop immediately.                       |
| `continue` | Skips the rest of the **current** iteration and moves on.    |

```java
for (int b = 1; b <= 10; b++) {
    if (b == 4) break;        // stop completely at 4
    System.out.print(b + " ");
}
// prints: 1 2 3

for (int c = 1; c <= 6; c++) {
    if (c % 2 == 0) continue; // skip even numbers
    System.out.print(c + " ");
}
// prints: 1 3 5
```

---

## Nested Loops

A loop placed inside another loop. For **each** step of the outer loop, the inner loop runs fully. Common for grids, tables, and patterns.

```java
for (int row = 1; row <= 3; row++) {
    for (int col = 1; col <= 3; col++) {
        System.out.print("* ");
    }
    System.out.println();
}
// prints a 3x3 grid of stars
```

---

## Which Loop Should I Use

| Situation                                   | Best loop      |
|---------------------------------------------|----------------|
| You know the exact number of repetitions    | `for`          |
| You loop until some condition changes       | `while`        |
| The body must run at least once             | `do-while`     |
| You just need each element of an array       | `for-each`     |

---

## How to Compile and Run

```bash
javac _7_Loops/_7_While_DoWhile_For_Loops_Code.java
java _7_Loops._7_While_DoWhile_For_Loops_Code
```

### Expected Output

```
while  : 1 2 3 4 5
do-while: 1 2 3 4 5
for    : 1 2 3 4 5
for-each: 10 20 30
break  : 1 2 3
continue: 1 3 5
nested :
* * *
* * *
* * *
```

---

## Common Mistakes

### Mistake 1: Forgetting the update (infinite loop)

```java
int i = 1;
while (i <= 5) {
    System.out.println(i);
    // i++ is missing -> i stays 1 forever
}
```

### Mistake 2: An off-by-one error

```java
for (int i = 0; i <= 5; i++)  // runs 6 times (0..5)
for (int i = 0; i <  5; i++)  // runs 5 times (0..4)
```

Decide carefully between `<` and `<=`.

### Mistake 3: A stray semicolon after `for`/`while`

```java
for (int i = 0; i < 5; i++);   // the ; is the (empty) body!
{
    System.out.println(i);     // runs once, not five times
}
```

### Mistake 4: Forgetting the semicolon in `do-while`

```java
do {
    ...
} while (i < 5)   // ERROR: needs a trailing semicolon
```

---

## Interview Questions

### Q1: What is the difference between `while` and `do-while`?

**Answer:** A `while` loop checks the condition before the first iteration, so its body may run zero times. A `do-while` loop checks the condition after the body, so the body always runs at least once.

### Q2: When would you use a `for` loop instead of a `while` loop?

**Answer:** Use a `for` loop when the number of iterations is known or counter-based, because initialisation, condition, and update are grouped together. Use a `while` loop when you loop until some condition changes and the count is not known in advance.

### Q3: What is the difference between `break` and `continue`?

**Answer:** `break` exits the entire loop immediately. `continue` skips the remaining statements in the current iteration and proceeds to the next iteration.

### Q4: What is an infinite loop and how does it happen?

**Answer:** An infinite loop never ends because its condition never becomes false — usually because the update step is missing or wrong (e.g. forgetting `i++`). 

### Q5: What is the enhanced for-each loop, and what are its limits?

**Answer:** It iterates over every element of an array or collection without an index: `for (int n : nums)`. It is concise but does not expose the index and cannot be used to replace elements by position or to iterate backward.

### Q6: In a nested loop, how many times does the inner loop run?

**Answer:** The inner loop runs fully for each iteration of the outer loop. If the outer loop runs `m` times and the inner runs `n` times, the inner body executes `m × n` times in total.

---

*This is part of a Java learning series. Proceed to Chapter 8: Classes and Objects to continue learning.*
