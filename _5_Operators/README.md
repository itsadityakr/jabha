# Chapter 5: Operators in Java

## Table of Contents

1. [Introduction](#introduction)
2. [What is an Operator](#what-is-an-operator)
3. [Categories of Operators](#categories-of-operators)
4. [Arithmetic Operators](#arithmetic-operators)
5. [Assignment Operators](#assignment-operators)
6. [Relational Operators](#relational-operators)
7. [Logical Operators](#logical-operators)
8. [Increment and Decrement](#increment-and-decrement)
9. [Operator Precedence](#operator-precedence)
10. [How to Compile and Run](#how-to-compile-and-run)
11. [Common Mistakes](#common-mistakes)
12. [Interview Questions](#interview-questions)

---

## Introduction

An **operator** is a symbol that performs an operation on one or more values. For example, in `a + b`, the `+` is an operator and `a` and `b` are operands. Operators are how you actually *do* something with the data you stored in Chapter 4.

This chapter is split into three small programs:

| File | Topic |
|------|-------|
| `_5_1_Arithmetic_Operators_Code.java`  | `+ - * / %` and `++ --` |
| `_5_2_Assignment_Operators_Code.java`  | `=` and `+= -= *= /= %=` |
| `_5_3_Logical_Operators_Code.java`     | `&& || !` and relational operators |

---

## What is an Operator

An operator performs an action on **operands** (the values it works on).

```java
int sum = a + b;
//        ^ ^ ^
//        | | operand
//        | operator
//        operand
```

Operators are grouped by how many operands they take:

- **Unary** – one operand (e.g. `-x`, `!flag`, `count++`)
- **Binary** – two operands (e.g. `a + b`, `a > b`)
- **Ternary** – three operands (the `? :` operator, covered in Chapter 6)

---

## Categories of Operators

| Category    | Operators                          | Purpose                              |
|-------------|------------------------------------|--------------------------------------|
| Arithmetic  | `+  -  *  /  %`                    | Math calculations                    |
| Assignment  | `=  +=  -=  *=  /=  %=`            | Store / update values                |
| Relational  | `==  !=  >  <  >=  <=`             | Compare values, produce a boolean    |
| Logical     | `&&  ||  !`                        | Combine boolean conditions           |
| Unary       | `++  --  -  !`                    | Act on a single operand              |

---

## Arithmetic Operators

| Operator | Name           | Example  | Result (for `a=10`, `b=3`) |
|----------|----------------|----------|----------------------------|
| `+`      | Addition       | `a + b`  | `13`                       |
| `-`      | Subtraction    | `a - b`  | `7`                        |
| `*`      | Multiplication | `a * b`  | `30`                       |
| `/`      | Division       | `a / b`  | `3` (integer division)     |
| `%`      | Modulus        | `a % b`  | `1` (remainder)            |

> **Integer division drops the decimal part.** `10 / 3` is `3`, not `3.33`. If you need the decimals, at least one operand must be a floating-point type: `10.0 / 3` gives `3.333...`.

The **modulus** operator `%` returns the remainder of a division. It is commonly used to test whether a number is even (`n % 2 == 0`) or to wrap values around a range.

---

## Assignment Operators

The `=` operator stores a value in a variable. **Compound** assignment operators combine arithmetic with assignment.

| Operator | Example   | Equivalent to | 
|----------|-----------|---------------|
| `=`      | `x = 5`   | store 5 in x  |
| `+=`     | `x += 5`  | `x = x + 5`   |
| `-=`     | `x -= 5`  | `x = x - 5`   |
| `*=`     | `x *= 5`  | `x = x * 5`   |
| `/=`     | `x /= 5`  | `x = x / 5`   |
| `%=`     | `x %= 5`  | `x = x % 5`   |

`+=` also works on Strings: `text += " more"` appends to `text`.

---

## Relational Operators

Relational (comparison) operators compare two values and always produce a `boolean` (`true` or `false`).

| Operator | Meaning                  | Example   | Result |
|----------|--------------------------|-----------|--------|
| `==`     | equal to                 | `5 == 5`  | `true` |
| `!=`     | not equal to             | `5 != 3`  | `true` |
| `>`      | greater than             | `5 > 3`   | `true` |
| `<`      | less than                | `5 < 3`   | `false`|
| `>=`     | greater than or equal to | `5 >= 5`  | `true` |
| `<=`     | less than or equal to    | `5 <= 4`  | `false`|

> For objects (like `String`), use `.equals()` to compare contents. `==` on objects compares references, not values.

---

## Logical Operators

Logical operators combine boolean values.

| Operator | Name | Result is `true` when…             |
|----------|------|------------------------------------|
| `&&`     | AND  | **both** sides are true            |
| `||`     | OR   | **at least one** side is true      |
| `!`      | NOT  | the single operand is false        |

**Truth table:**

| A     | B     | `A && B` | `A || B` |
|-------|-------|----------|----------|
| true  | true  | true     | true     |
| true  | false | false    | true     |
| false | true  | false    | true     |
| false | false | false    | false    |

**Short-circuit evaluation:** `&&` stops as soon as the left side is `false` (the result cannot be `true`), and `||` stops as soon as the left side is `true`. This is useful for safety:

```java
if (x != 0 && 10 / x > 1) { ... }  // 10 / x is skipped when x == 0
```

---

## Increment and Decrement

| Operator | Name      | Effect              |
|----------|-----------|---------------------|
| `++`     | increment | adds 1              |
| `--`     | decrement | subtracts 1         |

Position matters:

| Form  | Name           | Behaviour                                  |
|-------|----------------|--------------------------------------------|
| `x++` | post-increment | use the current value, **then** add 1      |
| `++x` | pre-increment  | add 1 **first**, then use the new value    |

```java
int p = 5;
System.out.println(p++); // prints 5, p becomes 6
System.out.println(++p); // p becomes 7, prints 7
```

---

## Operator Precedence

When several operators appear in one expression, Java evaluates them in a fixed order (highest first):

1. `()` parentheses
2. `++ -- !` (unary)
3. `* / %`
4. `+ -`
5. `< <= > >=`
6. `== !=`
7. `&&`
8. `||`
9. `= += -= ...` (assignment)

```java
int result = 2 + 3 * 4;   // 14, because * runs before +
int result2 = (2 + 3) * 4; // 20, parentheses force addition first
```

> When in doubt, **use parentheses** to make the order explicit and the code readable.

---

## How to Compile and Run

```bash
javac _5_Operators/_5_1_Arithmetic_Operators_Code.java
java _5_Operators._5_1_Arithmetic_Operators_Code

javac _5_Operators/_5_2_Assignment_Operators_Code.java
java _5_Operators._5_2_Assignment_Operators_Code

javac _5_Operators/_5_3_Logical_Operators_Code.java
java _5_Operators._5_3_Logical_Operators_Code
```

---

## Common Mistakes

### Mistake 1: Expecting decimals from integer division

```java
double half = 1 / 2;   // 0.0, NOT 0.5  (1/2 is integer division -> 0)
double half2 = 1.0 / 2; // 0.5
```

### Mistake 2: Using `=` instead of `==`

```java
if (x = 5) { ... }   // ERROR: = assigns, it does not compare
if (x == 5) { ... }  // correct
```

### Mistake 3: Comparing Strings with `==`

```java
if (name == "Java") { ... }       // compares references (unreliable)
if (name.equals("Java")) { ... }  // compares text (correct)
```

### Mistake 4: Confusing `p++` and `++p`

```java
int p = 5;
int a = p++; // a is 5, p is 6
int b = ++p; // p is 7, b is 7
```

---

## Interview Questions

### Q1: What is the difference between `/` and `%`?

**Answer:** `/` returns the quotient of a division and `%` returns the remainder. For example, `17 / 5` is `3` and `17 % 5` is `2`.

### Q2: Why does `5 / 2` give `2` instead of `2.5`?

**Answer:** Both operands are `int`, so Java performs integer division and discards the decimal part. To get `2.5`, make at least one operand a floating-point value: `5.0 / 2`.

### Q3: What is the difference between `x++` and `++x`?

**Answer:** `x++` (post-increment) returns the current value of `x` and then increases it. `++x` (pre-increment) increases `x` first and then returns the new value.

### Q4: What is short-circuit evaluation?

**Answer:** With `&&`, if the left operand is `false`, the right operand is never evaluated because the result is already `false`. With `||`, if the left operand is `true`, the right operand is skipped. This avoids unnecessary work and can prevent errors such as division by zero.

### Q5: What is the difference between `==` and `.equals()`?

**Answer:** For primitives, `==` compares values. For objects, `==` compares references (whether they are the same object), while `.equals()` compares the actual contents. Use `.equals()` to compare String text.

### Q6: What does the `%` (modulus) operator do, and give a use case.

**Answer:** It returns the remainder of a division. A common use is checking whether a number is even or odd: `n % 2 == 0` is true for even numbers.

### Q7: What is operator precedence?

**Answer:** It is the order in which operators are evaluated in an expression. For example, `*` and `/` are evaluated before `+` and `-`. Parentheses have the highest precedence and can be used to override the default order.

---

*This is part of a Java learning series. Proceed to Chapter 6: Conditional Statements to continue learning.*
