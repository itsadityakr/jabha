# Chapter 29: The `throw` / `throws` Keywords & Custom Exceptions

## Table of Contents

1. [Introduction](#introduction)
2. [`throw` vs `throws` (Don't Confuse Them)](#throw-vs-throws-dont-confuse-them)
3. [File Map](#file-map)

**File 1 — `throw` + custom exceptions (handle it here)**

4. [Building a Custom Exception: the `CustomException` Class](#building-a-custom-exception-the-customexception-class)
5. [Throwing It with `throw`](#throwing-it-with-throw)
6. [Catching It: Ordering the `catch` Blocks](#catching-it-ordering-the-catch-blocks)
7. [Walkthrough: What File 1 Does](#walkthrough-what-file-1-does)

**File 2 — `throws` + propagation (pass it up)**

8. [Declaring with `throws`: Propagating Up the Call Stack](#declaring-with-throws-propagating-up-the-call-stack)
9. [Combined Methods: How Propagation Chains Together](#combined-methods-how-propagation-chains-together)
10. [The `static` Block and Class Loading](#the-static-block-and-class-loading)
11. [Walkthrough: What File 2 Does](#walkthrough-what-file-2-does)

**Shared**

12. [`Exception` vs `RuntimeException` as a Base Class](#exception-vs-runtimeexception-as-a-base-class)
13. [How to Compile and Run](#how-to-compile-and-run)
14. [Common Mistakes](#common-mistakes)
15. [Interview Questions](#interview-questions)

---

## Introduction

In [Chapter 28](../_28_Exceptions/README.md) we **caught** exceptions that Java threw for us — like `ArithmeticException` from `1 / 0`. This chapter is the **other side of the coin**, and it teaches the two distinct verbs of exception *production* and the two ways to deal with one:

1. **File 1** — how **you** raise an exception on purpose with the **`throw`** keyword, using your **own** exception type (a custom exception class), and **handle** it on the spot with `try`/`catch`.
2. **File 2** — how a method **declares** an exception with the **`throws`** keyword and **propagates** it up the call stack instead of handling it.

Together these let you signal *your own* error conditions ("balance can't be negative", "age must be positive") and choose **where** in the program they get dealt with.

> **One-line summary:** `throw` *raises* an exception; `try`/`catch` *handles* it here (File 1); `throws` *declares* it and *passes it up* to the caller (File 2).

---

## `throw` vs `throws` (Don't Confuse Them)

These look almost identical but do **opposite jobs**. This is the most common confusion in the whole topic:

| | `throw` | `throws` |
|---|---------|----------|
| What it is | a **statement** that raises an exception **now** | a **clause** in a method signature |
| Where it goes | **inside** a method body | after the **parameter list**, before `{` |
| How many | one exception object at a time | a comma-separated **list of types** |
| Meaning | "raise this exception right here" | "this method *might* throw these; caller beware" |
| Example | `throw new CustomException("bad");` | `void load() throws IOException { ... }` |

```java
// 'throws' DECLARES the possibility; 'throw' DOES the throwing:
void readFile(String path) throws IOException {   // <- throws (a warning to callers)
    if (path == null)
        throw new IOException("path is null");    // <- throw (the actual action)
}
```

**File 1** uses **`throw`** (it catches everything inside the same method, so it needs no `throws`). **File 2** uses **`throws`** (it declares the exception and lets it travel up instead of catching it).

---

## File Map

| File | Keyword | Strategy | Demonstrates |
|------|---------|----------|--------------|
| [`_29_1_throw_Code.java`](_29_1_throw_Code.java) | `throw` | **handle here** | a custom exception class, throwing it with `throw`, and catching it among several `catch` blocks |
| [`_29_2_throws_Code.java`](_29_2_throws_Code.java) | `throws` | **pass it up** | declaring a checked exception with `throws`, propagating it through `show()` → `main()` → JVM, and a `static{}` block |

File 1 contains **two classes**:

- `CustomException` — our own exception type.
- `_29_1_throw_Code` — the `public` class with `main`, which throws and catches it.

File 2 also contains **two classes**:

- `A` — a helper whose `show()` method declares `throws` and does **not** catch.
- `_29_2_throws_Code` — the `public` class with `main` (and a `static{}` block) that also declares `throws`.

---

## Building a Custom Exception: the `CustomException` Class

A custom exception is just **a class that extends an existing exception class**. Here we extend `Exception`:

```java
class CustomException extends Exception {
    CustomException(String message) {
        super(message);          // hand the message up to Exception
    }
}
```

- **`extends Exception`** makes `CustomException` a real exception — it can be `throw`n and `catch`ed exactly like a built-in one ("is-a" relationship).
- The **constructor** takes a message and passes it to the parent with **`super(message)`**. That stored message is what **`e.getMessage()`** returns later.
- Giving it a meaningful name (`CustomException`, or better `InsufficientBalanceException`) makes both the **`catch` blocks** and the **error messages** self-documenting.

> Because it extends `Exception` (and not `RuntimeException`), `CustomException` is a **checked** exception — see [the section below](#exception-vs-runtimeexception-as-a-base-class) for what that implies.

---

## Throwing It with `throw`

The `throw` keyword raises an exception object immediately. As soon as it runs, normal execution stops and Java starts hunting for a matching `catch`:

```java
if (j == 0) {
    // throw new ArithmeticException();                       // -> {Block 1}
    // throw new ArithmeticException("...j = 0");             // -> {Block 1}
    throw new CustomException("This is arithmetic exception where j = 0"); // -> {Block 2}
}
```

The file shows **three things you *could* throw** (two commented out). Each one would be caught by a **different** `catch` block below, which is a neat way to see how the type you throw decides where control lands:

- `throw new ArithmeticException(...)` → caught by the `ArithmeticException` block (**{Block 1}**).
- `throw new CustomException(...)` → caught by the `CustomException` block (**{Block 2}**) — this is the active line.

> **Syntax:** `throw` needs an **object** (`throw new X(...)`), not a class. `throw CustomException;` is a compile error; it must be `throw new CustomException(...);`.

---

## Catching It: Ordering the `catch` Blocks

```java
} catch (CustomException e) {        // {Block 2} - the exact type we threw
    j = 18 / 1;                       // recover with a fallback value (18)
    System.out.println("Exception: " + e.getMessage());
} catch (ArithmeticException e) {    // {Block 1} - if an ArithmeticException were thrown
    j = 18 / 1;
    System.out.println("Exception: " + e.getMessage());
} catch (Exception e) {              // safety net - the parent catches anything else
    System.out.println(e);
}
```

Three rules at work here:

1. **The thrown type picks the block.** We threw a `CustomException`, so **{Block 2}** runs and the others are skipped.
2. **Most specific first, most general last.** `CustomException` and `ArithmeticException` are specific; `Exception` (their ancestor) comes **last** as a catch-all. Reverse the order and the specific blocks become **unreachable** — a compile error.
3. **Recovery.** Each catch sets `j = 18 / 1` (i.e. `18`), so after handling, the program has a sensible value to print instead of crashing.

`e.getMessage()` prints **only** the message text; `e` (or `System.out.println(e)`) prints the **type and** the message.

---

## Walkthrough: What File 1 Does

```java
int i = 20;
int j = 0;

try {
    j = 18 / i;            // 18 / 20 = 0   (INTEGER division throws away the .9)
    if (j == 0) {          // true, because j is 0
        throw new CustomException("This is arithmetic exception where j = 0");
    }
} catch (CustomException e) {
    j = 18 / 1;            // recover: j becomes 18
    System.out.println("Exception: " + e.getMessage());
}
// ...
System.out.println("Result is : " + j);
```

Step by step:

1. `j = 18 / i` → `18 / 20`. Because both are `int`, this is **integer division**, which truncates `0.9` to **`0`**.
2. `if (j == 0)` is **true**, so we `throw new CustomException(...)`.
3. The throw transfers control to the **`CustomException`** catch block (**{Block 2}**).
4. Inside, `j = 18 / 1` sets `j` to **`18`**, and we print the message.
5. Execution continues past the try/catch and prints the recovered value.

### Expected Output

```text
Exception: This is arithmetic exception where j = 0
Result is : 18
```

> Note the message text says *"arithmetic exception"*, but the object thrown is actually a **`CustomException`** — the wording is just the human message we chose; the **type** is what controls which `catch` runs.

---

## Declaring with `throws`: Propagating Up the Call Stack

File 1 **handled** the exception where it happened. File 2 ([`_29_2_throws_Code.java`](_29_2_throws_Code.java)) does the opposite: it **refuses to handle** the exception and instead **declares** it with `throws`, pushing the responsibility onto the caller.

```java
class A {
    public void show() throws ClassNotFoundException {   // "I won't catch this — caller must"
        Class.forName("Calc");                            // no class "Calc" -> throws ClassNotFoundException
    }
}
```

`Class.forName("Calc")` asks the JVM to load a class **by name at runtime**. There is no class called `Calc`, so it throws **`ClassNotFoundException`** — a **checked** exception. *Checked* means the compiler will **not** let the code build unless every method in the path either:

- **catches** it with `try`/`catch`, **or**
- **declares** it with `throws ClassNotFoundException`.

Here `show()` chooses to **declare**, so the exception escapes the method untouched.

> **Why pass it up instead of catching it?** Often the method where an error *occurs* doesn't know how to *respond* to it. A low-level `show()` can't decide whether to retry, log, or warn the user — so it reports the problem upward and lets a higher-level method (which has that context) decide. `throws` is how it reports upward.

---

## Combined Methods: How Propagation Chains Together

The power of `throws` is that it **chains**. Each method in the call stack that declares (rather than catches) the exception passes it one level higher, until something catches it — or the JVM ends the program:

```java
public static void main(String[] args) throws ClassNotFoundException {
    A a = new A();
    a.show();   // not wrapped in try/catch -> the exception keeps travelling up
}
```

```text
   Class.forName("Calc")        throws ClassNotFoundException
            │  (show() declares throws — does not catch)
            ▼
   A.show()                     re-throws it to its caller
            │  (main() declares throws — does not catch)
            ▼
   _29_2_throws_Code.main()     re-throws it to its caller
            │  (the caller of main IS the JVM)
            ▼
   JVM                          nobody caught it -> print stack trace, end program
```

The real stack trace from running the file proves this exact chain (read it **bottom-up** — that is the order the calls were made):

```text
	at A.show(_29_2_throws_Code.java:46)
	at _29_2_throws_Code.main(_29_2_throws_Code.java:66)
```

> This is what **"combined methods and class throw"** means: a chain of methods that each declare `throws`, so one exception travels through several methods before anything stops it.

---

## The `static` Block and Class Loading

File 2 also shows a **`static{}` block** — a chunk of code that runs **once, when the class is first loaded/initialized**, which is just **before `main()` starts**:

```java
public class _29_2_throws_Code {
    static {
        System.out.println("Class Loaded");   // runs once, BEFORE main()
    }

    public static void main(String[] args) throws ClassNotFoundException { ... }
}
```

That is why the program's output begins with `Class Loaded` **before** the exception appears: class initialization happens first, then `main` runs and triggers the failing `Class.forName`.

| Runs... | When |
|---------|------|
| `static {}` block | once, at class load — **before** `main` |
| instance initializer / constructor | every time an object is created with `new` |
| `main()` body | after the class is fully initialized |

---

## Walkthrough: What File 2 Does

```java
class A {
    public void show() throws ClassNotFoundException {
        Class.forName("Calc");   // throws ClassNotFoundException
    }
}

public class _29_2_throws_Code {
    static { System.out.println("Class Loaded"); }

    public static void main(String[] args) throws ClassNotFoundException {
        A a = new A();
        a.show();   // exception propagates: show() -> main() -> JVM
    }
}
```

Step by step:

1. The JVM loads `_29_2_throws_Code`; its **`static{}`** block runs and prints `Class Loaded`.
2. `main()` runs, creates an `A`, and calls `a.show()`.
3. Inside `show()`, `Class.forName("Calc")` finds no such class and **throws `ClassNotFoundException`**.
4. `show()` declared `throws`, so it does **not** catch — the exception goes back to `main()`.
5. `main()` also declared `throws`, so it does **not** catch — the exception goes to the **JVM**.
6. The JVM prints a stack trace and ends the program. The "finished normally" lines never run.

### Expected Output

```text
Class Loaded
Exception in thread "main" java.lang.ClassNotFoundException: Calc
	at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:580)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:502)
	at java.base/java.lang.Class.forName0(Native Method)
	at java.base/java.lang.Class.forName(Class.java:478)
	at java.base/java.lang.Class.forName(Class.java:468)
	at A.show(_29_2_throws_Code.java:46)
	at _29_2_throws_Code.main(_29_2_throws_Code.java:66)
```

> **File 1 vs File 2 in one line:** File 1 **caught** the exception, so it printed a tidy message and continued (`Result is : 18`). File 2 **declared** the exception and let it escape, so the program **crashed** with a stack trace. Same machinery, opposite strategy.

---

## `Exception` vs `RuntimeException` as a Base Class

When you create a custom exception, **what you extend decides whether it is checked or unchecked**:

| You extend... | Your exception is... | Compiler forces handling? |
|---------------|----------------------|---------------------------|
| `Exception` | **checked** | ✅ Yes — `try/catch` **or** `throws` required |
| `RuntimeException` | **unchecked** | ❌ No — handling is optional |

```java
class CheckedProblem   extends Exception        { }  // caller MUST handle or declare it
class UncheckedProblem extends RuntimeException  { }  // caller may ignore it
```

Our `CustomException extends Exception`, so it is **checked**. In the file this is invisible because the `throw` and the `catch` are in the **same method** — but if a *method* threw it and let it escape, that method would need a `throws CustomException` clause.

**Rule of thumb:** extend `Exception` for *recoverable, expected* conditions you want callers to deal with deliberately; extend `RuntimeException` for *programming bugs* that shouldn't normally happen.

---

## How to Compile and Run

From the repository root (`d:\Repos\java`):

```bash
# File 1 — throw + catch
javac _29_throw_Keyword/_29_1_throw_Code.java
java -cp _29_throw_Keyword _29_1_throw_Code

# File 2 — throws + propagation
javac _29_throw_Keyword/_29_2_throws_Code.java
java -cp _29_throw_Keyword _29_2_throws_Code
```

Each command compiles **two classes** into the folder, because each source file holds two (`CustomException` + `_29_1_throw_Code` for File 1; `A` + `_29_2_throws_Code` for File 2).

### Expected Output — File 1 (`_29_1_throw_Code`)

```text
Exception: This is arithmetic exception where j = 0
Result is : 18
```

### Expected Output — File 2 (`_29_2_throws_Code`)

```text
Class Loaded
Exception in thread "main" java.lang.ClassNotFoundException: Calc
	...
	at A.show(_29_2_throws_Code.java:46)
	at _29_2_throws_Code.main(_29_2_throws_Code.java:66)
```

File 2 **crashes on purpose** (exit code 1) — that is the point: nobody caught the propagated exception, so the JVM reports it.

### Try it yourself

**File 1:** swap the active `throw` for one of the commented ones to watch a **different** `catch` block fire:

```java
throw new ArithmeticException("now I land in Block 1");
```

Output becomes `Exception: now I land in Block 1` — handled by the `ArithmeticException` block instead.

**File 2:** stop the crash by *handling* the exception instead of declaring it — wrap the call in `main` and drop the `throws`:

```java
public static void main(String[] args) {        // no 'throws' now
    try {
        new A().show();
    } catch (ClassNotFoundException e) {
        System.out.println("Handled: " + e.getMessage());   // Handled: Calc
    }
}
```

Now the program prints `Class Loaded` then `Handled: Calc` and exits normally — propagation stopped because *you* caught it.

---

## Common Mistakes

### Mistake 1: Confusing `throw` and `throws`

`throw` **does** the throwing (a statement, inside the method). `throws` **declares** that a method may throw (a clause in the signature). See [the table above](#throw-vs-throws-dont-confuse-them).

### Mistake 2: Throwing a class instead of an object

```java
throw CustomException;            // ERROR
throw new CustomException("x");   // correct - throw an INSTANCE
```

### Mistake 3: Catching the parent before the child

```java
} catch (Exception e) { ... }          // catches everything first...
} catch (CustomException e) { ... }    // ERROR: unreachable code
```

List `CustomException` (specific) **before** `Exception` (general).

### Mistake 4: Forgetting `super(message)` in the constructor

```java
class CustomException extends Exception {
    CustomException(String message) { }   // message is lost! getMessage() returns null
}
```

Call `super(message)` so `getMessage()` can return it.

### Mistake 5: Extending `Exception` but never handling it

Because `Exception` is **checked**, a method that throws it and lets it escape **must** declare `throws CustomException`, or the code won't compile. (It works in File 1 only because the throw is caught in the same method.)

### Mistake 6: Calling a `throws` method without handling or re-declaring

```java
void caller() {
    new A().show();   // ERROR: unhandled ClassNotFoundException
}
```

If `show()` declares `throws ClassNotFoundException`, then `caller()` must **either** wrap it in `try`/`catch` **or** add `throws ClassNotFoundException` to its own signature (as File 2's `main` does). You cannot silently ignore a checked exception.

### Mistake 7: Thinking `throws` *handles* the exception

`throws` does **not** handle anything — it only **declares** and **passes the problem upward**. If every method (including `main`) just declares `throws`, the exception reaches the JVM and the program **still crashes**, as File 2 demonstrates. To actually stop it, some method must `catch` it.

---

## Interview Questions

### Q1: What is the difference between `throw` and `throws`?

**Answer:** `throw` is a statement used inside a method to actually raise an exception object (`throw new X(...)`). `throws` is a clause in a method's signature that declares the checked exceptions the method might propagate to its caller. One performs the action; the other documents the possibility.

### Q2: How do you create a custom exception?

**Answer:** Define a class that extends an existing exception type — `Exception` for a checked exception or `RuntimeException` for an unchecked one — and provide a constructor that passes a message to `super(message)` so `getMessage()` works.

### Q3: Why call `super(message)` in a custom exception's constructor?

**Answer:** The message is stored by the `Throwable` base class. Passing it up with `super(message)` lets `getMessage()` (and the printed stack trace) report it. Omitting it leaves the message `null`.

### Q4: Should a custom exception extend `Exception` or `RuntimeException`?

**Answer:** Extend `Exception` for checked exceptions — recoverable, expected conditions that the compiler forces callers to handle or declare. Extend `RuntimeException` for unchecked exceptions — typically programming bugs that callers shouldn't be forced to catch.

### Q5: Can you throw a built-in exception manually?

**Answer:** Yes. `throw new IllegalArgumentException("...")` or `throw new ArithmeticException("...")` is common for validating inputs. You are not limited to custom types.

### Q6: When you `throw` an exception, which `catch` block handles it?

**Answer:** The first `catch` whose declared type matches the thrown object's type (or a supertype of it), checked top to bottom. That's why specific types must be listed before general ones — otherwise the general block would catch it first and the specific block would be unreachable.

### Q7: What happens to code after a `throw` statement?

**Answer:** It is skipped. `throw` immediately transfers control to the nearest matching `catch` (or propagates out of the method if none matches), so any statements after it in the same block do not execute.

### Q8: What does it mean to *propagate* an exception, and how does `throws` do it?

**Answer:** Propagating means letting an exception travel up the call stack instead of catching it where it occurs. A method declares `throws SomeException` to say "I won't handle this — my caller must." If every method in the chain declares it (e.g. `show()` → `main()`), the exception reaches the JVM, which prints a stack trace and ends the program. Some method must `catch` it to stop the propagation.

### Q9: Why must `main` (or some caller) declare `throws` for a checked exception?

**Answer:** Checked exceptions are enforced by the compiler: any method that calls code which may throw a checked exception must either catch it or re-declare it with `throws`. If a method like `show()` declares `throws ClassNotFoundException`, then `main` must do the same (or use `try`/`catch`), or the program won't compile.

### Q10: When does a `static{}` block run, relative to `main`?

**Answer:** A `static{}` block runs **once**, when the class is first loaded and initialized — which happens **before** `main` executes. That is why File 2 prints `Class Loaded` before the exception. Static blocks are typically used for one-time setup of static state.

---

*This is part of a Java learning series. You arrived from [Chapter 28: Exceptions](../_28_Exceptions/README.md), where you learned to catch exceptions Java throws. Now you can both **raise** your own (`throw`, File 1) and **propagate** them up the call stack (`throws`, File 2).*
