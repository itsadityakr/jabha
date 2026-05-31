# Chapter 1: Hello World - Your First Java Program

## Table of Contents

1. [Introduction](#introduction)
2. [Prerequisites](#prerequisites)
3. [What is Java](#what-is-java)
4. [Understanding the Code](#understanding-the-code)
5. [Line-by-Line Explanation](#line-by-line-explanation)
6. [How to Compile and Run](#how-to-compile-and-run)
7. [What Happens Behind the Scenes](#what-happens-behind-the-scenes)
8. [Common Errors Beginners Face](#common-errors-beginners-face)
9. [Interview Questions](#interview-questions)

---

## Introduction

This is the very first program that every Java developer writes. The "Hello World" program is a tradition in programming that dates back to the 1970s. The purpose of this program is to verify that your development environment is set up correctly and to introduce you to the basic structure of a Java program.

In this example, the program simply prints a greeting message to the console.

---

## Prerequisites

Before running this program, make sure you have the following installed on your system:

- **JDK (Java Development Kit)**: Version 8 or above. You can download it from the official Oracle website or use OpenJDK.
- **A Text Editor or IDE**: Notepad, VS Code, IntelliJ IDEA, or Eclipse.
- **Command Line Access**: Terminal (macOS/Linux) or Command Prompt/PowerShell (Windows).

To verify your installation, open a terminal and run:

```bash
java -version
javac -version
```

Both commands should display a version number without any errors.

---

## What is Java

Java is a high-level, object-oriented, platform-independent programming language developed by Sun Microsystems (now owned by Oracle) in 1995. Java follows the principle of "Write Once, Run Anywhere" (WORA), meaning code compiled on one platform can run on any other platform that has a Java Virtual Machine (JVM).

Key characteristics of Java:

- **Object-Oriented**: Everything in Java revolves around classes and objects.
- **Platform-Independent**: Java code is compiled into bytecode, which runs on the JVM regardless of the operating system.
- **Strongly Typed**: Every variable must have a declared data type.
- **Compiled and Interpreted**: Java source code is first compiled into bytecode (.class file), and then interpreted by the JVM at runtime.

---

## Understanding the Code

```java
class Hello {

    public static void main(String args[]) {
        System.out.println("Hi this is Aditya");
    }
}
```

### Structure Overview

Every Java program must have at least one class, and the execution starts from the `main` method. Think of a class as a container that holds your code, and the `main` method as the entry door through which the program begins execution.

---

## Line-by-Line Explanation

### Line 1: `class Hello {`

- **`class`** is a keyword in Java used to declare a class.
- **`Hello`** is the name of the class. In Java, class names should start with an uppercase letter by convention.
- **`{`** marks the beginning of the class body.

### Line 2: `public static void main(String args[]) {`

This is the most important line for any beginner. Let us break down every word:

| Keyword    | Meaning                                                                                          |
|------------|--------------------------------------------------------------------------------------------------|
| `public`   | An access modifier. It means this method can be accessed from anywhere, including the JVM.       |
| `static`   | This means the method belongs to the class itself, not to any specific object of the class.      |
| `void`     | The return type. `void` means this method does not return any value.                             |
| `main`     | The name of the method. The JVM looks specifically for a method named `main` to start execution. |
| `String args[]` | An array of Strings that can accept command-line arguments when the program is run.         |

If even one of these keywords is missing or misspelled, the program will not run.

### Line 3: `System.out.println("Hi this is Aditya");`

- **`System`** is a built-in class in the `java.lang` package.
- **`out`** is a static member of the `System` class. It is an object of type `PrintStream`.
- **`println`** is a method of `PrintStream` that prints the given text followed by a new line.
- **`"Hi this is Aditya"`** is a String literal, which is the text that will be displayed on the console.
- The semicolon **`;`** at the end marks the end of the statement. Every statement in Java must end with a semicolon.

### Line 4 and 5: Closing braces `}`

- The first `}` closes the `main` method.
- The second `}` closes the `Hello` class.

---

## How to Compile and Run

### Step 1: Save the file

Save the file as `_1_Hello.java` inside the `_1_Hello` folder. The file declares `package _1_Hello;`, and the class inside it is named `Hello`. Note that the file name does not need to match the class name unless the class is declared as `public`.

### Step 2: Compile

From the parent directory, compile using the package path:

```bash
javac _1_Hello/_1_Hello.java
```

This command invokes the Java Compiler (`javac`), which converts the `.java` source file into a `.class` bytecode file.

### Step 3: Run

Run using the fully qualified name (`package.ClassName`):

```bash
java _1_Hello.Hello
```

This command invokes the Java Virtual Machine (JVM), which reads the `Hello.class` file and executes the `main` method.

### Expected Output

```
Hi this is Aditya
```

---

## What Happens Behind the Scenes

1. You write the source code in a `.java` file (human-readable).
2. The Java Compiler (`javac`) converts it into bytecode stored in a `.class` file.
3. The JVM loads the `.class` file and interprets the bytecode.
4. The JVM finds the `main` method and begins executing the instructions inside it.
5. `System.out.println()` sends the text to the standard output stream, which is your console/terminal.

```
Source Code (.java)  -->  Compiler (javac)  -->  Bytecode (.class)  -->  JVM  -->  Output
```

---

## Common Errors Beginners Face

### Error 1: Class name does not match file name (when class is public)

If you declare the class as `public class Hello`, the file name MUST be `Hello.java`. Otherwise, you will get a compilation error.

### Error 2: Missing semicolon

```
error: ';' expected
```

Every statement in Java must end with a semicolon.

### Error 3: `main` method signature is wrong

If you write `public static void main(String args)` (without the square brackets), the JVM will not recognize it as the entry point.

### Error 4: Case sensitivity

Java is case-sensitive. `System` is not the same as `system`. `String` is not the same as `string`.

---

## Interview Questions

### Q1: Why is the `main` method declared as `public static void`?

**Answer:** The `main` method is `public` so that the JVM can access it from outside the class. It is `static` because the JVM calls it without creating an object of the class. It is `void` because it does not return any value to the JVM.

### Q2: Can we run a Java program without the `main` method?

**Answer:** Starting from Java 7, the answer is no. In Java 6 and earlier, you could use a static block to execute code without a `main` method, but this is no longer supported in modern versions of Java.

### Q3: What is the difference between `System.out.println()` and `System.out.print()`?

**Answer:** `println()` prints the text and then moves the cursor to the next line. `print()` prints the text but keeps the cursor on the same line.

### Q4: What is bytecode?

**Answer:** Bytecode is the intermediate code generated by the Java compiler. It is not machine code specific to any operating system. Instead, it is designed to be executed by the JVM, which makes Java platform-independent.

### Q5: Can we have multiple `main` methods in a Java program?

**Answer:** Yes, you can have multiple `main` methods in different classes within the same program. However, the JVM will only execute the `main` method of the class you specify when running the program.

### Q6: What happens if we write `static public void main` instead of `public static void main`?

**Answer:** The program will still compile and run correctly. The order of `public` and `static` does not matter in Java. However, the conventional and recommended order is `public static void main`.

---

*This is part of a Java learning series. Proceed to Chapter 2: Type Casting to continue learning.*
