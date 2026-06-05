# Chapter 28: Exceptions in Java

## Table of Contents

1. [Introduction](#introduction)
2. [The Three Kinds of Errors](#the-three-kinds-of-errors)
3. [File Map](#file-map)
4. [File 1: Compile-Time Error (and a Famous Misconception)](#file-1-compile-time-error-and-a-famous-misconception)
5. [File 2: Logical Error](#file-2-logical-error)
6. [File 3: Runtime Error and `try`/`catch`](#file-3-runtime-error-and-trycatch)
7. [The Exception Hierarchy](#the-exception-hierarchy)
8. [Checked vs Unchecked Exceptions](#checked-vs-unchecked-exceptions)
9. [How to Compile and Run](#how-to-compile-and-run)
10. [Side-by-Side Comparison](#side-by-side-comparison)
11. [Common Mistakes](#common-mistakes)
12. [Interview Questions](#interview-questions)

---

## Introduction

An **exception** is an event that disrupts the normal flow of a program **while it is running**. Java represents every such event as an **object** — and gives you a structured way to **catch** it and recover, instead of letting the whole program crash.

But before we talk about handling, we need to be precise about **what can go wrong** in a Java program. Not every "error" is an exception, and confusing the categories is the single most common beginner mistake. This chapter sorts them out with three tiny, runnable files.

> The big idea: there are **three kinds of failure** — *compile-time*, *logical*, and *runtime* — and **only the third kind** (runtime) involves exceptions and `try`/`catch`.

---

## The Three Kinds of Errors

| Kind | When it happens | Does it compile? | Does it run? | Who detects it |
|------|-----------------|------------------|--------------|----------------|
| **Compile-time** | Before the program starts | ❌ **No** `.class` produced | never starts | the **compiler** (`javac`) |
| **Logical** | While running | ✅ yes | ✅ yes, to the end | **a human** (Java can't) |
| **Runtime** | While running | ✅ yes | 💥 crashes partway | the **JVM**, by throwing an exception |

```text
            ┌──────────────────────────────────────────────────────────┐
            │                   SOMETHING IS WRONG                       │
            └──────────────────────────────────────────────────────────┘
                                       │
        ┌──────────────────────────────┼──────────────────────────────┐
        ▼                              ▼                              ▼
  COMPILE-TIME                     LOGICAL                        RUNTIME
  won't even build           builds & runs, wrong answer     builds, then throws
  e.g. missing ';'           e.g. 1 + 0 when you meant 10     e.g. 1 / 0 at runtime
  fix: the compiler tells    fix: read/test your logic       fix: try/catch or
       you exactly where                                          fix the cause
```

---

## File Map

| File | Demonstrates | Key idea |
|------|--------------|----------|
| [`_28_1_Compile_Time_Error_Code.java`](_28_1_Compile_Time_Error_Code.java) | Compile-time errors (and the `1/0` myth) | the compiler refuses to build; `1/0` is **not** one of these |
| [`_28_2_Logical_Error_Code.java`](_28_2_Logical_Error_Code.java) | A logical error | compiles and runs, but the answer is wrong |
| [`_28_3_Runtime_Error_Code.java`](_28_3_Runtime_Error_Code.java) | A runtime error + `try`/`catch` | catch the exception so the program keeps running |

---

## File 1: Compile-Time Error (and a Famous Misconception)

A **compile-time error** is one the compiler (`javac`) catches **before the program ever runs**. No `.class` file is produced, so there is nothing to launch. These are almost always **syntax** or **type** mistakes:

```java
int x = "hello";        // type mismatch: a String cannot become an int
int y = 5               // missing ';'
System.out.println(z);  // 'z' was never declared
notAMethod();           // calling a method that does not exist
```

Uncomment any of those and `javac` will stop with an error pointing at the exact line. (They are kept commented in the file so it still builds.)

### The famous misconception: `1 / 0`

It is tempting to think this line is a compile-time error:

```java
int result = 1 / 0;
```

**It is not.** This file proves it:

1. `javac _28_1_Compile_Time_Error_Code.java` — **compiles successfully**, a `.class` file appears.
2. `java _28_1_Compile_Time_Error_Code` — **crashes at runtime**:

```text
Exception in thread "main" java.lang.ArithmeticException: / by zero
```

So integer **divide-by-zero is a *runtime* error** (an `ArithmeticException`) — exactly the family we learn to handle in File 3 — **not** a compile-time error. The file keeps this line on purpose so you can compile it (no error) and then run it (it throws) and see the difference with your own eyes.

> 💡 **Why does this trip people up?** Some IDEs underline `1/0` with a *warning*, which looks like a compile error. But the **language** only forbids it at runtime, because in general a divisor's value isn't known until the program runs.

---

## File 2: Logical Error

A **logical error** is the sneakiest bug of all, because **nothing complains**:

- The code is legal, so it **compiles**.
- It **runs** all the way through with no crash.
- But the **result is wrong**, because your logic didn't match your intent.

```java
int result = 1 + 0;     // programmer MEANT 10, but this legally evaluates to 1
System.out.println(result); // prints: 1
```

There is no error message anywhere — Java cannot read your mind. The only way to catch a logical error is to **test the program and notice the output is wrong**. This is exactly why we write unit tests.

> The compiler checks **grammar and types**, never **correctness of intent**. `1 + 0` is grammatically perfect; it's just not what you wanted.

---

## File 3: Runtime Error and `try`/`catch`

A **runtime error** happens **while the program is running**: it compiled, it started, and then an illegal operation threw an **exception**. If nobody handles it, the JVM prints a stack trace and stops at that line.

**Exception handling** is how we deal with this gracefully. We wrap risky code in a `try` block; if it throws, control jumps to a matching `catch` block instead of crashing:

```java
int i = 9, j = 0, result = 0;

try {
    result = i / j;                 // 9 / 0 -> throws ArithmeticException here
} catch (Exception e) {             // control jumps here instead of crashing
    System.out.println("Exception: " + e.getMessage());
}

System.out.println("Result is : " + result);  // still runs!
System.out.println("Worked normally");          // still runs!
```

### The key insight: a *caught* exception does NOT stop the program

Running the file prints:

```text
Exception: / by zero
Result is : 0
Worked normally
```

Notice that **`Worked normally` prints**. Because the exception was **caught**, the program did **not** crash — it simply continued after the `try`/`catch`. Contrast this with the situation if there were **no** `try`/`catch`: the throw would crash the program at `result = i / j`, and **neither** of the final two lines would ever run.

| | With `try`/`catch` | Without `try`/`catch` |
|---|---|---|
| At `i / j` | exception caught | exception thrown, **uncaught** |
| Lines after | **run normally** | **never run** (program dies) |
| Exit | normal | stack trace, exit code 1 |

### `e.getMessage()` vs printing the whole exception

```java
System.out.println("Exception: " + e);              // ArithmeticException: / by zero  (type + message)
System.out.println("Exception: " + e.getMessage()); // / by zero                       (message only)
```

### Ordering multiple `catch` blocks (most specific first)

When you catch several types, you must list them **most specific first, most general last**:

```java
try {
    // ...
} catch (ArithmeticException e) {            // specific
    // ...
} catch (ArrayIndexOutOfBoundsException e) { // another specific
    // ...
} catch (Exception e) {                      // most general - MUST be last
    // ...
}
```

`Exception` is the parent of all the others, so a single `catch (Exception e)` can catch everything. Because it is the most general, placing it **before** a more specific catch makes that specific block **unreachable** — which is a **compile error**.

---

## The Exception Hierarchy

Everything you can throw or catch descends from **`Throwable`**:

```text
Object
 └─ Throwable                         (root of everything throwable/catchable)
     ├─ Error                          serious JVM problems - normally NOT caught
     │   ├─ ThreadDeath                (deprecated)
     │   ├─ IOError
     │   └─ VirtualMachineError
     │       ├─ OutOfMemoryError
     │       └─ StackOverflowError
     │
     └─ Exception                      problems your code CAN and SHOULD handle
         ├─ RuntimeException           (UNCHECKED - compiler does not force handling)
         │   ├─ ArithmeticException
         │   ├─ ArrayIndexOutOfBoundsException
         │   ├─ NumberFormatException
         │   ├─ StringIndexOutOfBoundsException
         │   └─ NullPointerException
         │
         ├─ IOException                (CHECKED - compiler forces handling)
         │   └─ FileNotFoundException
         └─ SQLException               (CHECKED - e.g. a database failure)
```

| Branch | What it means | Should you catch it? |
|--------|---------------|----------------------|
| **`Error`** | Something went badly wrong inside the JVM (out of memory, stack overflow). | **No** — usually unrecoverable; let it crash. |
| **`RuntimeException`** | Usually a **bug in your code** (bad index, null, divide by zero). | Fix the bug; catch only when sensible. |
| **Other `Exception`s** | External/expected failures (file missing, DB down). | **Yes** — must be handled or declared. |

---

## Checked vs Unchecked Exceptions

This single distinction explains *why the compiler forces `try`/`catch` in some cases but not others*:

| | **Unchecked** | **Checked** |
|---|---|---|
| Base type | `RuntimeException` (and `Error`) | `Exception` (but **not** `RuntimeException`) |
| Compiler forces handling? | ❌ No | ✅ **Yes** — `try/catch` **or** `throws` |
| Typical cause | a **bug** in your logic | an **external** condition you can't control |
| Examples | `ArithmeticException`, `NullPointerException`, `ArrayIndexOutOfBoundsException` | `IOException`, `FileNotFoundException`, `SQLException` |

> `ArithmeticException` from `1/0` is **unchecked**, which is why File 1 compiled without any `try`/`catch` — the compiler never demanded it. You only found out at runtime.

---

## How to Compile and Run

From the repository root (`d:\Repos\java`):

```bash
javac _28_Exceptions/_28_1_Compile_Time_Error_Code.java
javac _28_Exceptions/_28_2_Logical_Error_Code.java
javac _28_Exceptions/_28_3_Runtime_Error_Code.java

java -cp _28_Exceptions _28_1_Compile_Time_Error_Code
java -cp _28_Exceptions _28_2_Logical_Error_Code
java -cp _28_Exceptions _28_3_Runtime_Error_Code
```

### Expected Output — File 1 (`_28_1`)

It **compiles** with no error, but **running** it throws:

```text
Exception in thread "main" java.lang.ArithmeticException: / by zero
	at _28_1_Compile_Time_Error_Code.main(_28_1_Compile_Time_Error_Code.java:49)
```

### Expected Output — File 2 (`_28_2`)

```text
1
```

(No error — that's the whole point of a *logical* error. You only know `1` is "wrong" if you expected `10`.)

### Expected Output — File 3 (`_28_3`)

```text
Exception: / by zero
Result is : 0
Worked normally
```

---

## Side-by-Side Comparison

| Aspect | Compile-time | Logical | Runtime |
|--------|--------------|---------|---------|
| Detected by | `javac` | a human / a test | the JVM (throws an exception) |
| Program builds? | No | Yes | Yes |
| Program runs? | Never starts | Runs fully | Crashes (unless caught) |
| Example in this chapter | a missing `;` (commented) | `1 + 0` | `1 / 0`, `i / j` |
| How you fix it | follow the compiler message | check your logic | `try`/`catch`, or fix the cause |

---

## Common Mistakes

### Mistake 1: Thinking `1 / 0` is a compile-time error

It compiles fine and throws `ArithmeticException` **at runtime**. Divide-by-zero is an unchecked runtime exception, not a syntax error.

### Mistake 2: Expecting Java to catch logical errors

```java
int total = price - tax;   // meant price + tax — compiles & runs, silently wrong
```

Only **testing** reveals this. The compiler and JVM see nothing wrong.

### Mistake 3: Ordering `catch` blocks general-first

```java
} catch (Exception e) { ... }            // catches everything...
} catch (ArithmeticException e) { ... }  // ERROR: unreachable code
```

Put the **specific** types first, `Exception` **last**.

### Mistake 4: Assuming a caught exception stops the program

A caught exception lets the program **continue** after the `try`/`catch`. Only an **uncaught** exception terminates it.

### Mistake 5: Catching `Error` to "be safe"

```java
catch (Throwable t) { }   // also swallows OutOfMemoryError, StackOverflowError...
```

`Error`s signal an unrecoverable JVM state. Catch `Exception`, not `Throwable`/`Error`.

---

## Interview Questions

### Q1: What is the difference between a compile-time error, a logical error, and a runtime error?

**Answer:** A compile-time error is caught by the compiler before the program runs (e.g. a syntax or type mistake) and produces no `.class` file. A logical error compiles and runs to completion but yields the wrong result because the logic is incorrect — Java cannot detect it. A runtime error occurs while running, when an illegal operation throws an exception; uncaught, it crashes the program.

### Q2: Is `int x = 1 / 0;` a compile-time or runtime error?

**Answer:** Runtime. It compiles successfully and throws `java.lang.ArithmeticException: / by zero` when executed, because it is an unchecked runtime exception, not a syntax error.

### Q3: What is an exception?

**Answer:** An object representing an event that disrupts normal program flow at runtime. Every exception descends from `Throwable`. You handle exceptions with `try`/`catch`/`finally` or declare them with `throws`.

### Q4: Explain the exception hierarchy.

**Answer:** `Throwable` is the root, with two branches: `Error` (serious JVM problems like `OutOfMemoryError`, normally not caught) and `Exception` (recoverable problems). Under `Exception`, `RuntimeException` and its subclasses are unchecked; all other `Exception` subclasses (like `IOException`, `SQLException`) are checked.

### Q5: What is the difference between checked and unchecked exceptions?

**Answer:** Checked exceptions (subclasses of `Exception` but not `RuntimeException`) must be handled with `try`/`catch` or declared with `throws`, or the code won't compile. Unchecked exceptions (`RuntimeException` and `Error` subclasses) are not enforced by the compiler — they usually indicate bugs.

### Q6: Why must specific `catch` blocks come before general ones?

**Answer:** Because `catch` blocks are checked top-to-bottom and `Exception` is the parent of all the others. A general `catch (Exception e)` placed first would catch everything, making any specific `catch` after it unreachable — which is a compile error.

### Q7: Does catching an exception stop the program?

**Answer:** No. A caught exception transfers control to the `catch` block and the program continues afterward. Only an uncaught exception propagates up and terminates the program with a stack trace.

---

*This is part of a Java learning series. You arrived from [Chapter 27: Types of Interfaces](../_27_Interfaces_Types/README.md). Continue to [Chapter 29: The `throw` Keyword & Custom Exceptions](../_29_throw_Keyword/README.md) to learn how to raise exceptions yourself.*
