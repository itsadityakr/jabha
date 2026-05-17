# Chapter 2: Type Casting in Java

## Table of Contents

1. [Introduction](#introduction)
2. [What is Type Casting](#what-is-type-casting)
3. [Types of Type Casting](#types-of-type-casting)
4. [Understanding the Code](#understanding-the-code)
5. [Line-by-Line Explanation](#line-by-line-explanation)
6. [What Happens When You Convert Between Types](#what-happens-when-you-convert-between-types)
7. [Data Loss in Type Casting](#data-loss-in-type-casting)
8. [Type Promotion in Expressions](#type-promotion-in-expressions)
9. [How to Compile and Run](#how-to-compile-and-run)
10. [Common Mistakes](#common-mistakes)
11. [Interview Questions](#interview-questions)

---

## Introduction

Type casting is one of the fundamental concepts in Java that every developer must understand. When you work with different data types (such as `int`, `float`, `double`, `long`), there are situations where you need to convert a value from one type to another. This process is called type casting.

This program demonstrates explicit type casting, where a `float` value is manually converted to an `int` before performing a mathematical operation.

---

## What is Type Casting

Type casting is the process of converting a value from one data type to another. In Java, every variable has a specific data type, and the compiler enforces strict type rules. If you try to assign a value of one type to a variable of a different type, the compiler will either do it automatically (if it is safe) or ask you to do it manually (if there is a risk of data loss).

Think of it like pouring water from a large bucket into a small glass. If the glass is big enough, the water fits perfectly (automatic/widening casting). If the glass is too small, some water will spill (manual/narrowing casting with data loss).

---

## Types of Type Casting

### 1. Widening Casting (Implicit / Automatic)

This happens automatically when you convert a smaller data type to a larger data type. There is no risk of data loss.

```
byte --> short --> int --> long --> float --> double
```

**Example:**

```java
int a = 100;
double b = a;  // Automatic conversion from int to double
// b is now 100.0
```

The compiler does this on its own because a `double` can hold any `int` value without losing any information.

### 2. Narrowing Casting (Explicit / Manual)

This must be done manually by the programmer when you convert a larger data type to a smaller data type. There IS a risk of data loss.

```
double --> float --> long --> int --> short --> byte
```

**Example:**

```java
double a = 9.78;
int b = (int) a;  // Manual conversion from double to int
// b is now 9 (the decimal part .78 is lost)
```

You must explicitly write `(int)` to tell the compiler: "I know this might lose data, but I want to do it anyway."

---

## Understanding the Code

```java
public class _2_TypeCasting {

    public static void main(String[] args) {
        int a = 5;
        float b = 6.1f;

        int c = a * (int) b;

        System.out.println(c);
    }
}
```

---

## Line-by-Line Explanation

### `int a = 5;`

- Declares an integer variable `a` and assigns it the value `5`.
- `int` is a 32-bit data type that can store whole numbers from -2,147,483,648 to 2,147,483,647.

### `float b = 6.1f;`

- Declares a floating-point variable `b` and assigns it the value `6.1`.
- The `f` suffix is mandatory for `float` literals in Java. Without it, the compiler treats `6.1` as a `double` (which is 64-bit), and assigning a `double` to a `float` (which is 32-bit) would cause a compilation error.
- `float` is a 32-bit data type that stores decimal numbers with approximately 6-7 significant digits of precision.

### `int c = a * (int) b;`

This is the most important line in this program. Let us break it down step by step:

1. **`(int) b`** -- This is explicit type casting. The `float` value `6.1` is being converted to an `int`. When this happens, the decimal part (`.1`) is completely discarded (truncated, NOT rounded). So `(int) 6.1` becomes `6`.

2. **`a * (int) b`** -- This becomes `5 * 6`, which equals `30`.

3. **`int c = 30`** -- The result `30` is stored in the integer variable `c`.

### `System.out.println(c);`

- Prints the value of `c` to the console, which is `30`.

---

## What Happens When You Convert Between Types

### int to float (Widening - Safe)

```java
int x = 100;
float y = x;  // y becomes 100.0
```

- No data loss occurs for most values.
- However, for very large `int` values (above 16,777,216), there can be a loss of precision because `float` has only 23 bits for the significand.

### float to int (Narrowing - Data Loss)

```java
float x = 9.99f;
int y = (int) x;  // y becomes 9, NOT 10
```

- The decimal part is completely removed (truncated).
- Important: This is truncation, NOT rounding. `9.99` becomes `9`, not `10`.

### int to double (Widening - Safe)

```java
int x = 100;
double y = x;  // y becomes 100.0
```

- `double` is 64-bit and can hold any `int` value without any loss.

### double to int (Narrowing - Data Loss)

```java
double x = 99.99;
int y = (int) x;  // y becomes 99
```

- Same as float to int: the decimal part is truncated.

### int to long (Widening - Safe)

```java
int x = 100;
long y = x;  // y becomes 100
```

- `long` is 64-bit and can hold any `int` value.

### long to int (Narrowing - Possible Data Loss)

```java
long x = 3000000000L;
int y = (int) x;  // Unexpected result due to overflow
```

- If the `long` value exceeds the `int` range, the result will overflow and produce an incorrect value.

---

## Data Loss in Type Casting

Here is a detailed table showing what can go wrong with narrowing conversions:

| Conversion      | What Happens                                      | Example                    | Result   |
|-----------------|--------------------------------------------------|----------------------------|----------|
| float to int    | Decimal part is truncated                        | `(int) 6.9f`              | `6`      |
| double to int   | Decimal part is truncated                        | `(int) 99.99`             | `99`     |
| double to float | Precision may be reduced                         | `(float) 3.141592653589`  | `3.1415927` |
| long to int     | Higher bits are discarded, may cause overflow    | `(int) 3000000000L`       | `-1294967296` |
| int to byte     | Only the lowest 8 bits are kept                  | `(byte) 130`              | `-126`   |
| int to short    | Only the lowest 16 bits are kept                 | `(short) 40000`           | `-25536` |

---

## Type Promotion in Expressions

When different data types are used together in an expression, Java automatically promotes the smaller type to the larger type before performing the operation. This is called type promotion.

**Rules of Type Promotion:**

1. `byte`, `short`, and `char` are promoted to `int` in any expression.
2. If any operand is `long`, the entire expression is promoted to `long`.
3. If any operand is `float`, the entire expression is promoted to `float`.
4. If any operand is `double`, the entire expression is promoted to `double`.

**Example:**

```java
byte a = 10;
byte b = 20;
// byte c = a + b;  // ERROR: a + b is promoted to int
int c = a + b;      // Correct
```

---

## How to Compile and Run

```bash
javac _2_TypeCasting.java
java _2_TypeCasting
```

### Expected Output

```
30
```

---

## Common Mistakes

### Mistake 1: Forgetting the `f` suffix for float literals

```java
float b = 6.1;  // ERROR: 6.1 is treated as double
float b = 6.1f; // CORRECT
```

### Mistake 2: Assuming narrowing happens automatically

```java
float x = 6.1f;
int y = x;        // ERROR: cannot convert float to int automatically
int y = (int) x;  // CORRECT: explicit cast required
```

### Mistake 3: Expecting rounding instead of truncation

```java
int y = (int) 6.9f;  // y is 6, NOT 7
```

If you want rounding, use `Math.round()`:

```java
int y = Math.round(6.9f);  // y is 7
```

---

## Interview Questions

### Q1: What is type casting in Java?

**Answer:** Type casting is the process of converting a value from one data type to another. There are two types: widening (automatic, from smaller to larger type) and narrowing (manual, from larger to smaller type).

### Q2: What is the difference between implicit and explicit type casting?

**Answer:** Implicit casting (widening) is done automatically by the compiler when there is no risk of data loss, such as converting `int` to `double`. Explicit casting (narrowing) must be done manually by the programmer using the cast operator, such as converting `double` to `int`, because there is a risk of losing data.

### Q3: What happens when you cast a `float` to an `int`?

**Answer:** The decimal (fractional) part is completely discarded through truncation. For example, `(int) 6.9f` results in `6`, not `7`. The value is NOT rounded.

### Q4: Why do we need to add `f` at the end of a float literal?

**Answer:** By default, Java treats all decimal literals as `double`. Since `double` is 64-bit and `float` is 32-bit, assigning a `double` to a `float` would be a narrowing conversion. The `f` suffix explicitly tells the compiler that the literal is a `float`, not a `double`.

### Q5: What is type promotion in Java?

**Answer:** Type promotion is the automatic conversion of smaller data types to larger data types within an expression. For example, when you add a `byte` and a `short`, both are automatically promoted to `int` before the addition is performed. This prevents overflow during intermediate calculations.

### Q6: Can you cast a `boolean` to an `int` in Java?

**Answer:** No. In Java, `boolean` is not considered a numeric type. You cannot cast between `boolean` and any other data type. This is different from languages like C and C++, where `true` and `false` can be treated as `1` and `0`.

### Q7: What is the output of `(int) -9.7`?

**Answer:** The output is `-9`. The casting truncates toward zero, discarding the decimal part. It does not round to `-10`.

### Q8: What happens if you cast a large `long` value to `int`?

**Answer:** If the `long` value exceeds the range of `int` (-2,147,483,648 to 2,147,483,647), the higher-order bits are discarded, and the result wraps around due to overflow, producing an unexpected and incorrect value.

### Q9: What is the difference between `float` and `double`?

**Answer:**

| Feature       | float          | double          |
|---------------|----------------|-----------------|
| Size          | 32 bits        | 64 bits         |
| Precision     | ~6-7 digits    | ~15-16 digits   |
| Default       | No (needs `f`) | Yes             |
| Memory Usage  | Less           | More            |
| Use Case      | Memory-critical applications | General-purpose decimal calculations |

### Q10: Is type casting applicable to objects in Java?

**Answer:** Yes. In Java, you can also cast objects. Upcasting (child to parent) is implicit and always safe. Downcasting (parent to child) is explicit and can throw a `ClassCastException` at runtime if the object is not actually an instance of the target class.

---

*This is part of a Java learning series. Proceed to Chapter 3: Classes and Objects to continue learning.*
