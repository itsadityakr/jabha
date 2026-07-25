# Java Learning Notes

A numbered, run-in-order collection of Java study notes and worked examples, starting at "Hello World" and building up to interfaces, annotations and exception handling.

Each numbered folder is one self-contained topic. It holds a chapter-style `README.md` with explanations, common mistakes and interview questions, alongside one or more heavily commented `.java` programs that demonstrate the same material in code. Nothing in a later topic is needed to understand an earlier one, so the intended way to use this repo is to work through the folders in numeric order: read the topic `README.md`, then compile and run the example files and compare the output against the notes.

There is no build tool, module system or external dependency here. Every example is plain Java compiled directly with `javac`.

## Topics

| #  | Topic                    | #  | Topic               |
| -- | ------------------------ | -- | ------------------- |
| 01 | Hello                    | 16 | Super and This      |
| 02 | JVM / JDK / JRE / Memory | 17 | Polymorphism        |
| 03 | Type Casting             | 18 | `final` Keyword     |
| 04 | Data Types               | 19 | Objects             |
| 05 | Operators                | 20 | Casting (Up / Down) |
| 06 | Conditional Statements   | 21 | Wrapper Classes     |
| 07 | Loops                    | 22 | `abstract` Keyword  |
| 08 | Classes                  | 23 | Inner Classes       |
| 09 | Methods                  | 24 | Interface           |
| 10 | Arrays                   | 25 | Enums               |
| 11 | Array Properties         | 26 | Annotations         |
| 12 | String and StringBuilder | 27 | Interface Types     |
| 13 | Encapsulation            | 28 | Exceptions          |
| 14 | `static` Keyword         | 29 | `throw` / `throws`  |
| 15 | Inheritance              |    |                     |

## Repository layout

Topic folders are named `_N_TopicName`, where `N` is the chapter number and `TopicName` describes the subject, for example `_10_Arrays` or `_22_Abstract_Keyword`. The leading underscore is required: a bare `10_Arrays` is not a legal Java identifier, so it could not be used as a package name.

Every one of the 29 topic folders contains its own `README.md` with the written notes for that chapter.

Source files inside a folder are named `_N_Description_Code.java` when the topic has a single example, and `_N_M_Description_Code.java` when a topic is split across several files, where `M` is the file's order within the chapter.

```
_1_Hello/
  README.md
  _1_Hello_Code.java
_27_Interfaces_Types/
  README.md
  _27_1_Normal_Interface_Code.java
  _27_2_Functional_OR_SAM_Interface_Code.java
  _27_3_Marker_Interface_Code.java
```

Most files begin with a `package` declaration matching their folder name, such as `package _27_Interfaces_Types;`. The exceptions are the files in `_28_Exceptions` and `_29_throw_Keyword`, where the `package` line is present but commented out, placing those classes in the default package. This affects how they are run; see below.

## Requirements

A JDK and nothing else. **JDK 11 or newer** is recommended.

Almost all of the code compiles on JDK 8. The only version-sensitive construct in the repo is the local variable type inference keyword `var`, used in `_26_Annotations/_26_Annotations_Code.java`, which requires JDK 10 or newer. JDK 11 is suggested because it also enables the single-file source launcher shown below. The examples have been verified against JDK 26.

Check what you have with:

```
javac -version
```

## Compiling and running

Run all commands from the repository root. The commands below are identical on Windows (PowerShell or `cmd`) and on a POSIX shell such as bash or zsh; forward slashes in the `javac` argument work on Windows as well.

### Standard case: a file with a package declaration

`javac` writes the `.class` file next to the source, so the compiled class ends up inside the topic folder, which is exactly where its package name expects it. You can then launch it from the root using its fully qualified name.

```
javac _10_Arrays/_10_1_Arrays_Code.java
java _10_Arrays._10_1_Arrays_Code
```

Note that the class holding `main` does not always share the file's name. In `_1_Hello/_1_Hello_Code.java` the class is called `Hello`, so the run command uses that instead:

```
javac _1_Hello/_1_Hello_Code.java
java _1_Hello.Hello
```

If in doubt, open the file and look for the class declaring `public static void main`.

### Chapters 28 and 29: default package

Because the `package` line is commented out in these files, the compiled class has no package. Compile as usual, then point the classpath at the topic folder:

```
javac _29_throw_Keyword/_29_1_throw_Code.java
java -cp _29_throw_Keyword _29_1_throw_Code
```

### Quickest option: single-file source launcher

On JDK 11 and newer a single file can be compiled and executed in one step, leaving no `.class` file behind. This works regardless of the package situation:

```
java _1_Hello/_1_Hello_Code.java
```

## How to study this

The numbering is already the recommended reading order. The chapters group naturally into five stages.

**Language foundations (01-07)** — the toolchain and memory model, primitive and reference data types, type casting, operators, conditionals and loops. Start here even if you have written Java before; chapter 02 explains what the JVM, JDK and JRE each do, which the later chapters assume.

**Structuring code (08-12)** — classes, methods and overloading, then arrays, array properties and the `String` / `StringBuilder` pair. This is the first point at which the examples stop being single-method programs.

**Object-oriented core (13-22)** — encapsulation, `static`, inheritance, `super` and `this`, polymorphism, `final`, objects, upcasting and downcasting, wrapper classes and `abstract`. This is the largest block and the most interdependent one; read it straight through rather than dipping in.

**Abstraction and metadata (23-27)** — inner and anonymous classes, interfaces, enums, annotations, and the normal / functional (SAM) / marker interface taxonomy. Chapter 27 builds directly on 23, 24 and 26, so do not skip ahead to it.

**Error handling (28-29)** — the difference between compile-time, logical and runtime errors, then throwing, declaring and propagating exceptions with `throw` and `throws`, including custom exception types.

A practical approach for each chapter: read the folder's `README.md` first, predict the output of each example before running it, then run it and reconcile any difference. Most source files also end with a commented block showing the expected output.

## License

No `LICENSE` file is present in this repository, so no license has been formally declared. The material is personal learning notes by [Aditya Kumar](https://github.com/itsadityakr).
