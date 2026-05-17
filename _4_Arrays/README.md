# Chapter 4: Arrays in Java

## Table of Contents

1. [Introduction](#introduction)
2. [What is an Array](#what-is-an-array)
3. [Why Do We Need Arrays](#why-do-we-need-arrays)
4. [Types of Arrays](#types-of-arrays)
5. [Understanding the Code](#understanding-the-code)
6. [Line-by-Line Explanation](#line-by-line-explanation)
7. [How Arrays Work in Memory](#how-arrays-work-in-memory)
8. [Different Ways to Declare and Initialize Arrays](#different-ways-to-declare-and-initialize-arrays)
9. [Iterating Over Arrays](#iterating-over-arrays)
10. [How to Compile and Run](#how-to-compile-and-run)
11. [Common Mistakes](#common-mistakes)
12. [Interview Questions](#interview-questions)

---

## Introduction

Arrays are one of the most basic and widely used data structures in programming. In Java, an array is an object that stores a fixed number of values of the same data type in contiguous memory locations. Understanding arrays is essential because they form the foundation for more advanced data structures like ArrayLists, Stacks, and Queues.

This program demonstrates how to declare an array, instantiate it, and iterate over its elements using an enhanced for-loop.

---

## What is an Array

An array is a container object that holds a fixed number of values of a single type. The length of an array is established when the array is created, and after creation, its length is fixed and cannot be changed.

Think of an array as a row of numbered lockers in a school. Each locker (element) has a number (index) starting from 0, and each locker can hold exactly one item (value) of the same type.

```
Index:    [0]   [1]   [2]   [3]   [4]
Values:   [ 0 ] [ 0 ] [ 0 ] [ 0 ] [ 0 ]
```

**Key Characteristics:**

- Arrays in Java are zero-indexed (the first element is at index 0).
- Arrays have a fixed size that cannot change after creation.
- All elements in an array must be of the same data type.
- Arrays in Java are objects, meaning they are stored on the heap.
- The `length` property tells you how many elements the array can hold.

---

## Why Do We Need Arrays

Without arrays, if you wanted to store the marks of 100 students, you would need to declare 100 separate variables:

```java
int marks1 = 80;
int marks2 = 75;
int marks3 = 90;
// ... 97 more variables
```

This is impractical. With an array, you can store all 100 values in a single variable:

```java
int[] marks = new int[100];
```

Now you can access any student's marks using the index: `marks[0]`, `marks[1]`, `marks[99]`, and so on.

---

## Types of Arrays

### 1. Single-Dimensional Array

A simple list of elements.

```java
int[] numbers = {10, 20, 30, 40, 50};
```

### 2. Multi-Dimensional Array

An array of arrays, commonly used to represent matrices or tables.

```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};
```

### 3. Jagged Array

A multi-dimensional array where each row can have a different number of columns.

```java
int[][] jagged = new int[3][];
jagged[0] = new int[2];  // First row has 2 columns
jagged[1] = new int[4];  // Second row has 4 columns
jagged[2] = new int[1];  // Third row has 1 column
```

---

## Understanding the Code

```java
public class _4_Arrays {

    public static void main(String[] args) {
        int nums[] = new int[5];

        for (int i : nums) {
            System.out.println(i);
        }

        System.out.println(nums + " Hi ");
    }
}
```

---

## Line-by-Line Explanation

### `int nums[] = new int[5];`

This single line does two things:

1. **Declaration**: `int nums[]` declares a variable `nums` that can hold a reference to an integer array. Note: `int[] nums` and `int nums[]` are both valid syntaxes in Java, but `int[] nums` is the preferred Java style.

2. **Instantiation**: `new int[5]` allocates memory on the heap for an array of 5 integers. All 5 elements are automatically initialized to the default value for `int`, which is `0`.

After this line executes, the array looks like this:

```
nums --> [0, 0, 0, 0, 0]
          0  1  2  3  4   (indices)
```

### `for (int i : nums) {`

This is the **enhanced for-loop** (also called for-each loop), introduced in Java 5. It iterates over every element in the array without needing an index variable.

- `int i` is a temporary variable that takes the value of each element in the array, one at a time.
- `nums` is the array being iterated over.

In the first iteration, `i = nums[0]` which is `0`. In the second iteration, `i = nums[1]` which is `0`. And so on.

Important: In the enhanced for-loop, `i` is NOT the index. It is the VALUE of the element at each position.

### `System.out.println(i);`

Prints the current value of `i` on each iteration. Since all elements are `0`, this prints `0` five times.

### `System.out.println(nums + " Hi ");`

This line is very instructive for understanding how arrays work in Java:

- `nums` is a reference variable. When you try to print it directly, Java calls the `toString()` method inherited from the `Object` class.
- The default `toString()` method of an array returns a string in the format: `[TypeCode@HashCode]`. For example: `[I@1b6d3586`.
  - `[I` means it is an array of integers.
  - `@1b6d3586` is the hexadecimal representation of the object's hash code.
- `+ " Hi "` concatenates the string " Hi " to this representation.
- The output will be something like: `[I@1b6d3586 Hi`

If you want to print the actual contents of the array, use `Arrays.toString(nums)`:

```java
import java.util.Arrays;
System.out.println(Arrays.toString(nums));  // Output: [0, 0, 0, 0, 0]
```

---

## How Arrays Work in Memory

```
Stack Memory              Heap Memory
+----------+              +---+---+---+---+---+
| nums  ---|------------->| 0 | 0 | 0 | 0 | 0 |
+----------+              +---+---+---+---+---+
                           [0] [1] [2] [3] [4]
```

- The reference variable `nums` is stored on the stack.
- The actual array data is stored on the heap.
- `nums` holds the memory address of the array on the heap.
- This is why printing `nums` directly shows a hash code instead of the array contents.

---

## Different Ways to Declare and Initialize Arrays

```java
// Method 1: Declare, then instantiate
int[] arr;
arr = new int[5];

// Method 2: Declare and instantiate together
int[] arr = new int[5];

// Method 3: Declare, instantiate, and initialize
int[] arr = {10, 20, 30, 40, 50};

// Method 4: Using new keyword with values
int[] arr = new int[]{10, 20, 30, 40, 50};

// Method 5: Alternative syntax (C-style, less common in Java)
int arr[] = new int[5];
```

---

## Iterating Over Arrays

### Method 1: Traditional for-loop

```java
for (int i = 0; i < nums.length; i++) {
    System.out.println(nums[i]);
}
```

Use this when you need the index value.

### Method 2: Enhanced for-loop (for-each)

```java
for (int num : nums) {
    System.out.println(num);
}
```

Use this when you only need the values and do not care about the index.

### Method 3: While loop

```java
int i = 0;
while (i < nums.length) {
    System.out.println(nums[i]);
    i++;
}
```

### Comparison

| Feature              | Traditional for-loop | Enhanced for-loop |
|----------------------|---------------------|-------------------|
| Access to index      | Yes                 | No                |
| Can modify elements  | Yes                 | No (modifies local copy) |
| Readability          | Moderate            | High              |
| Risk of index errors | Yes                 | No                |

---

## How to Compile and Run

```bash
javac _4_Arrays.java
java _4_Arrays
```

### Expected Output

```
0
0
0
0
0
[I@<some_hashcode> Hi
```

---

## Common Mistakes

### Mistake 1: ArrayIndexOutOfBoundsException

```java
int[] arr = new int[5];
arr[5] = 10;  // ERROR: valid indices are 0 to 4
```

Arrays are zero-indexed, so an array of size 5 has indices 0 through 4.

### Mistake 2: Confusing array size with last index

```java
int[] arr = new int[5];
// arr.length is 5
// Last valid index is arr.length - 1 = 4
```

### Mistake 3: Trying to resize an array

```java
int[] arr = new int[5];
// You cannot make arr hold 10 elements later
// Arrays have a FIXED size
```

If you need a resizable collection, use `ArrayList` instead.

### Mistake 4: Comparing arrays with `==`

```java
int[] a = {1, 2, 3};
int[] b = {1, 2, 3};
System.out.println(a == b);  // false (compares references, not content)
System.out.println(Arrays.equals(a, b));  // true (compares content)
```

---

## Interview Questions

### Q1: What is an array in Java?

**Answer:** An array is a fixed-size, contiguous data structure that stores multiple values of the same data type. It is an object in Java, stored on the heap, and accessed via a reference variable on the stack. Elements are accessed using zero-based indices.

### Q2: What is the default value of elements in an integer array?

**Answer:** The default value is `0`. For `boolean` arrays, the default is `false`. For `double` and `float` arrays, the default is `0.0`. For object arrays (such as `String[]`), the default is `null`.

### Q3: What is the difference between `int[] arr` and `int arr[]`?

**Answer:** Both are valid syntaxes for declaring an array in Java. `int[] arr` is the preferred Java convention because it clearly indicates that `arr` is of type "integer array". `int arr[]` is the C/C++ style and is less commonly used in Java. Functionally, they are identical.

### Q4: Can the size of an array be changed after creation?

**Answer:** No. Once an array is created, its size is fixed and cannot be modified. If you need a dynamic-size collection, use `ArrayList` from the `java.util` package.

### Q5: What is the difference between an array and an ArrayList?

**Answer:**

| Feature        | Array                    | ArrayList                 |
|----------------|--------------------------|---------------------------|
| Size           | Fixed                    | Dynamic (grows/shrinks)   |
| Type           | Can hold primitives      | Can only hold objects     |
| Performance    | Faster                   | Slightly slower           |
| Methods        | Only `.length`           | Rich API (add, remove, etc.) |
| Syntax         | `int[] arr = new int[5]` | `ArrayList<Integer> list = new ArrayList<>()` |

### Q6: What happens when you print an array directly using `System.out.println(arr)`?

**Answer:** It prints the class name followed by the `@` symbol and the hexadecimal hash code of the array object, such as `[I@1b6d3586`. The `[I` indicates it is an integer array. To print the actual contents, use `Arrays.toString(arr)`.

### Q7: What is the enhanced for-loop and when should you use it?

**Answer:** The enhanced for-loop (for-each loop) is a simplified syntax introduced in Java 5 for iterating over arrays and collections. It should be used when you need to read all elements sequentially and do not need the index. It cannot be used to modify the original array elements or iterate in reverse.

### Q8: What exception is thrown if you access an invalid array index?

**Answer:** `ArrayIndexOutOfBoundsException`, which is a runtime exception. It is thrown when you try to access an index that is less than 0 or greater than or equal to the array's length.

---

*This is part of a Java learning series. Proceed to Chapter 5: Arrays with Object Properties to continue learning.*
