# Chapter 2: JVM, JDK, JRE and Java Memory (Stack, Heap, String Pool)

## Table of Contents

1. [Introduction](#introduction)
2. [How Java Runs: Compile Once, Run Anywhere](#how-java-runs-compile-once-run-anywhere)
3. [JDK, JRE, and JVM](#jdk-jre-and-jvm)
4. [What the JVM Does](#what-the-jvm-does)
5. [Java Memory Areas](#java-memory-areas)
6. [Stack Memory](#stack-memory)
7. [Heap Memory](#heap-memory)
8. [The String Pool](#the-string-pool)
9. [Understanding the Code](#understanding-the-code)
10. [How to Compile and Run](#how-to-compile-and-run)
11. [Common Mistakes](#common-mistakes)
12. [Interview Questions](#interview-questions)

---

## Introduction

In Chapter 1 you compiled and ran a program with `javac` and `java`. But what *are* those tools, and where do your variables and objects actually live while the program runs? This chapter explains the three things people often confuse — **JDK, JRE, and JVM** — and the **memory model** (Stack, Heap, and the String Pool) that the rest of this course relies on.

This is a concepts chapter, with one small runnable demo (`_2_Memory_Demo_Code.java`) that lets you *see* the memory behaviour.

---

## How Java Runs: Compile Once, Run Anywhere

Java's famous promise is **"Write Once, Run Anywhere" (WORA)**. It works in two steps:

```
   YourProgram.java   --(javac, the compiler)-->   YourProgram.class (bytecode)
   YourProgram.class  --(java, the JVM)-->         runs on any operating system
```

1. The **compiler** (`javac`) turns your human-readable source code into **bytecode** (`.class` files). Bytecode is not machine code; it is an intermediate language the JVM understands.
2. The **JVM** reads that bytecode and executes it on the current machine.

Because every platform (Windows, macOS, Linux) has its own JVM, the *same* `.class` file runs everywhere without recompiling. The bytecode is portable; the JVM is platform-specific.

---

## JDK, JRE, and JVM

These three are nested, like boxes inside boxes:

```
+-------------------------------------------------------+
|  JDK  (Java Development Kit)                           |
|  Tools to DEVELOP: javac, jar, javadoc, debugger ...  |
|                                                       |
|   +-----------------------------------------------+   |
|   |  JRE  (Java Runtime Environment)              |   |
|   |  Everything needed to RUN Java apps           |   |
|   |  Core libraries + supporting files            |   |
|   |                                               |   |
|   |    +-------------------------------------+    |   |
|   |    |  JVM  (Java Virtual Machine)        |    |   |
|   |    |  Executes bytecode, manages memory  |    |   |
|   |    +-------------------------------------+    |   |
|   +-----------------------------------------------+   |
+-------------------------------------------------------+
```

| Term | Stands for | Contains | You need it to… |
|------|------------|----------|-----------------|
| **JDK** | Java Development Kit | JRE **+** developer tools (`javac`, `jar`, `javadoc`, debugger) | **write and compile** Java programs |
| **JRE** | Java Runtime Environment | JVM **+** core class libraries | **run** Java programs (no compiling) |
| **JVM** | Java Virtual Machine | The engine that executes bytecode | **execute** the `.class` bytecode |

**One-line summary:** JDK is for developers (it includes the JRE); the JRE is for running apps (it includes the JVM); the JVM is the engine that actually runs the bytecode.

> As a learner you install the **JDK**, because you need to both compile (`javac`) and run (`java`).

---

## What the JVM Does

The JVM is more than a simple interpreter. Its main jobs are:

- **Class Loading** – finds and loads `.class` files into memory.
- **Bytecode Verification** – checks the bytecode is safe and valid.
- **Execution** – interprets bytecode and, for "hot" code, uses the **JIT (Just-In-Time) compiler** to turn frequently used bytecode into native machine code for speed.
- **Memory Management** – allocates objects on the heap and reclaims unused ones via the **Garbage Collector (GC)**, so you rarely free memory manually.

---

## Java Memory Areas

When the JVM runs your program, it divides memory into areas. The two you must understand first are the **Stack** and the **Heap**.

```
        STACK (per thread)                 HEAP (shared)
   +------------------------+        +-------------------------+
   | main() frame           |        |   int[]{99,2,3}  <------|---+
   |   a = 10               |        |                         |   |
   |   b = 20               |        |   String "Java" (pool)  |   |
   |   x  --------------------------> (reference points here)  |   |
   |   y  ------------------------------------------------------+  |
   +------------------------+        +-------------------------+
   stores values & references         stores the actual objects
```

---

## Stack Memory

- Stores **local variables**, **method parameters**, and **references** to objects.
- Organised as **frames** — one frame per method call, removed when the method returns (LIFO: Last In, First Out).
- **Primitives are stored by value** here. Copying a primitive copies the value, so the copies are independent.
- Fast, automatically managed, and limited in size (a runaway recursion causes a `StackOverflowError`).

```java
int a = 10;
int b = a;  // b is a separate copy
b = 20;     // a is still 10
```

---

## Heap Memory

- Stores **all objects** created with `new` (and arrays, and Strings).
- **Shared** across the whole application; multiple references can point to the same object.
- Reclaimed automatically by the **Garbage Collector** when no references remain.
- Copying a **reference** does not copy the object — both references point to the *same* object.

```java
int[] x = {1, 2, 3};
int[] y = x;  // same array, not a copy
y[0] = 99;    // x[0] is now 99 too
```

---

## The String Pool

The **String Pool** (also called the String Constant Pool) is a special area the JVM uses to **reuse** String literals and save memory.

- When you write a literal like `"Java"`, the JVM checks the pool. If `"Java"` already exists there, it **reuses** the same object instead of creating a new one.
- Therefore two identical literals refer to the **same** object, and `==` is `true`.
- Using `new String("Java")` **forces** a brand-new object on the heap (outside the pool), so `==` is `false` even though the text is equal.
- `intern()` returns the pooled instance for any String.

```java
String s1 = "Java";
String s2 = "Java";            // reuses the pooled object
String s3 = new String("Java"); // forced new object

s1 == s2          // true  (same pooled object)
s1 == s3          // false (different objects)
s1.equals(s3)     // true  (same characters)
s1 == s3.intern() // true  (intern returns the pooled one)
```

> This is exactly why you compare String **text** with `.equals()`, not `==`.

---

## Understanding the Code

```java
int a = 10;
int b = a; b = 20;          // primitives: independent copies (stack)

int[] x = {1, 2, 3};
int[] y = x; y[0] = 99;     // objects: shared reference (heap)

String s1 = "Java";
String s2 = "Java";
String s3 = new String("Java");
```

Running the demo prints `a = 10, b = 20` (the copy is independent), `x[0] = 99` (both references share one array), and the String comparisons that prove pooling.

---

## How to Compile and Run

```bash
javac _2_JVM_JDK_JRE_Memory/_2_Memory_Demo_Code.java
java _2_JVM_JDK_JRE_Memory._2_Memory_Demo_Code
```

### Expected Output

```
a = 10, b = 20
x[0] = 99
s1 == s2      : true
s1 == s3      : false
s1.equals(s3) : true
s1 == s3.intern(): true
```

---

## Common Mistakes

### Mistake 1: Thinking the JVM compiles `.java` files

The **compiler** `javac` (part of the JDK) compiles `.java` to `.class`. The **JVM** only *runs* the resulting bytecode.

### Mistake 2: Installing only the JRE, then trying to compile

The JRE can run programs but has no `javac`. To develop, install the **JDK**.

### Mistake 3: Comparing Strings with `==`

`==` compares references. Two Strings with the same text can be different objects (especially with `new String(...)`). Use `.equals()` for text.

### Mistake 4: Assuming assigning an object copies it

```java
int[] y = x;   // copies the reference, NOT the array
```

Both variables now point to the same object; changes are visible through both.

---

## Interview Questions

### Q1: What is the difference between JDK, JRE, and JVM?

**Answer:** The JVM is the engine that executes Java bytecode and manages memory. The JRE is the JVM plus the core libraries needed to *run* Java applications. The JDK is the JRE plus development tools such as `javac`, so it is used to *write and compile* programs. They are nested: JDK ⊃ JRE ⊃ JVM.

### Q2: What is bytecode, and why does it make Java portable?

**Answer:** Bytecode is the platform-independent intermediate code produced by `javac` (the `.class` files). Because every platform has its own JVM that understands the same bytecode, one compiled file runs on any OS — "Write Once, Run Anywhere."

### Q3: What is the difference between stack and heap memory?

**Answer:** The stack stores local variables, parameters, and references in per-method frames that are removed when the method returns; primitives are stored by value. The heap stores all objects created with `new` and is shared across the program; objects are reclaimed by the garbage collector when unreferenced.

### Q4: What is the String Pool?

**Answer:** A special memory area where the JVM stores String literals and reuses them. Identical literals point to the same pooled object, which saves memory and is why `"Java" == "Java"` is `true`.

### Q5: Why is `new String("Java") == "Java"` false?

**Answer:** `new String(...)` always creates a separate object on the heap, distinct from the pooled literal `"Java"`. The references differ, so `==` is `false`, even though `.equals()` is `true` because the characters match.

### Q6: What is the Garbage Collector?

**Answer:** A part of the JVM that automatically frees heap memory occupied by objects that are no longer reachable by any reference, so Java programmers usually do not free memory manually.

### Q7: What does the JIT compiler do?

**Answer:** The Just-In-Time compiler is part of the JVM. It compiles frequently executed bytecode ("hot" code) into native machine code at runtime to make the program run faster than pure interpretation.

---

*This is part of a Java learning series. Proceed to Chapter 3: Type Casting to continue learning.*
