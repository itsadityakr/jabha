# Chapter 16: Inner Classes (Member, Static Nested & Anonymous)

## Table of Contents

1. [Introduction](#introduction)
2. [Kinds of Inner Class](#kinds-of-inner-class)
3. [Member (Non-static) Inner Class](#member-non-static-inner-class)
4. [Static Nested Class](#static-nested-class)
5. [Anonymous Inner Class](#anonymous-inner-class)
6. [Anonymous Subclass of an Abstract Class](#anonymous-subclass-of-an-abstract-class)
7. [Understanding the Code](#understanding-the-code)
8. [Line-by-Line Explanation](#line-by-line-explanation)
9. [How to Compile and Run](#how-to-compile-and-run)
10. [Common Mistakes](#common-mistakes)
11. [Interview Questions](#interview-questions)

---

## Introduction

An **inner class** is a class declared **inside another class**. Java uses them to
group a helper type with the class it belongs to, to access the enclosing object's
members, and to create small one-off types without cluttering the package with
named files.

This chapter covers the everyday flavours across three programs:

| File | Demonstrates |
|------|--------------|
| `_16_1_InnerClass_Code.java` | A **member (non-static) inner class** `B` and a **static nested class** `C` inside an outer class `A` |
| `_16_2_AnonymousInnerClass_Code.java` | An **anonymous inner class** -- a one-off, unnamed subclass of `A` -- contrasted with a normal named subclass `B` |
| `_16_3_AbstractAnonymousInnerClass_Code.java` | An **anonymous inner class implementing an abstract class** `A` |

> **Important:** all three files declare a top-level `class A` in the **same
> package** (`_16_InnerClass`). That means they **cannot be compiled together**
> (`javac _16_InnerClass/*.java` fails with *"duplicate class: A"*). Compile and
> run them **one file at a time** -- see [How to Compile and Run](#how-to-compile-and-run).

---

## Kinds of Inner Class

| Kind | Declared | Needs an outer instance? | Created with |
|------|----------|--------------------------|--------------|
| Member (non-static) inner | inside the class body | **yes** | `outer.new Inner()` |
| Static nested | inside the body, marked `static` | no | `new Outer.Inner()` |
| Anonymous | inline, no name | depends on context | `new Type() { ... }` |

> The **outer (top-level) class itself can never be `static`** -- only a *nested*
> class may carry the `static` keyword.

---

## Member (Non-static) Inner Class

A member inner class is tied to an **instance** of the outer class, so it can use
that object's fields and methods. To create one you need an outer object first:

```java
A obj = new A();
A.B obj2 = obj.new B();   // note: outerObject.new Inner()
obj2.show();
```

The type is written `A.B` because `B` is a member of `A`.

---

## Static Nested Class

A static nested class is marked `static` and is **not** tied to any instance, so
you create it directly:

```java
A.C obj3 = new A.C();     // no A object required
obj3.show();
```

Use a static nested class when the helper type does not need access to a specific
outer object.

---

## Anonymous Inner Class

An **anonymous inner class** defines and instantiates an unnamed subclass in a
single expression. It is the shortcut for "I need a one-off subclass right here
and will never reuse it."

```java
A obj1 = new A() {              // unnamed subclass of A
    public void show() {        // override, just for this object
        System.out.println("Showing in class A (Anonymous)");
    }
};
obj1.show();
```

Writing a whole separate named subclass (`class B extends A { ... }`) is the
right choice when the behaviour is reused. When it is needed **only once**, an
anonymous class avoids the overhead of naming and saving an extra class.

---

## Anonymous Subclass of an Abstract Class

You cannot instantiate an abstract class with `new A()`. But `new A() { ... }`
does **not** create an `A`; it creates an instance of an **anonymous subclass**
that extends `A` and supplies the missing implementation:

```java
abstract class A {
    abstract public void show();
}

A obj1 = new A() {              // anonymous subclass implements show()
    public void show() {
        System.out.println("Showing in class A (Anonymous)");
    }
};
obj1.show();
```

This is the most common everyday use of anonymous classes -- providing a one-off
implementation of an abstract type or interface.

---

## Understanding the Code

### `_16_1_InnerClass_Code.java`

```java
class A {
    public void show() { System.out.println("Showing in class A"); }
    class B {                       // non-static inner class
        public void show()    { System.out.println("Showing in class B"); }
        public void Display() { System.out.println("Displaying in class B"); }
    }
    static class C {                // static nested class
        public void show() { System.out.println("Showing in class C"); }
    }
}

public class _16_1_InnerClass_Code {
    public static void main(String[] args) {
        A obj = new A();
        obj.show();

        A.B obj2 = obj.new B();     // needs an A instance
        obj2.show();
        obj2.Display();

        A.C obj3 = new A.C();       // static -> created directly
        obj3.show();
    }
}
```

### `_16_2_AnonymousInnerClass_Code.java`

```java
class A { public void show() { System.out.println("Showing in class A"); } }
class B extends A { public void show() { System.out.println("Showing in class B"); } }

public class _16_2_AnonymousInnerClass_Code {
    public static void main(String[] args) {
        A obj = new B();            // named subclass
        obj.show();

        A obj1 = new A() {          // anonymous subclass
            public void show() { System.out.println("Showing in class A (Anonymous)"); }
        };
        obj1.show();
    }
}
```

### `_16_3_AbstractAnonymousInnerClass_Code.java`

```java
abstract class A { abstract public void show(); }

public class _16_3_AbstractAnonymousInnerClass_Code {
    public static void main(String[] args) {
        A obj1 = new A() {          // anonymous subclass of an abstract class
            public void show() { System.out.println("Showing in class A (Anonymous)"); }
        };
        obj1.show();
    }
}
```

---

## Line-by-Line Explanation

### File 1 -- member & static nested

- `class B` inside `A` -- a non-static inner class, bound to an `A` instance.
- `static class C` -- a static nested class, independent of any `A` instance.
- `A obj = new A();` -- ordinary outer object.
- `obj.new B();` -- creates the inner `B`; requires the outer object `obj`.
- `new A.C();` -- creates the static `C` directly, no outer object needed.

### File 2 -- anonymous vs named

- `class B extends A` -- the traditional named subclass overriding `show()`.
- `A obj = new B();` -- uses that named subclass.
- `new A() { public void show() {...} }` -- an anonymous subclass created inline;
  `obj1` is typed `A` but refers to this one-off subclass.

### File 3 -- anonymous + abstract

- `abstract class A` with `abstract public void show();` -- cannot be instantiated.
- `new A() { ... }` -- builds an anonymous subclass that implements `show()` and
  instantiates **that** subclass (not `A` itself).

---

## How to Compile and Run

Each file declares `package _16_InnerClass;`. Because **all three files define a
top-level `class A`**, they conflict if compiled together -- compile and run them
**one at a time**:

```bash
# Member & static nested inner classes
javac _16_InnerClass/_16_1_InnerClass_Code.java
java  _16_InnerClass._16_1_InnerClass_Code

# Anonymous inner class
javac _16_InnerClass/_16_2_AnonymousInnerClass_Code.java
java  _16_InnerClass._16_2_AnonymousInnerClass_Code

# Anonymous subclass of an abstract class
javac _16_InnerClass/_16_3_AbstractAnonymousInnerClass_Code.java
java  _16_InnerClass._16_3_AbstractAnonymousInnerClass_Code
```

> Do **not** use `javac _16_InnerClass/*.java` here -- it fails with
> *"duplicate class: \_16\_InnerClass.A"* because three files each declare `class A`.

### Expected Output

`_16_1_InnerClass_Code`:

```
Showing in class A
Showing in class B
Displaying in class B
Showing in class C
```

`_16_2_AnonymousInnerClass_Code`:

```
Showing in class B
Showing in class A (Anonymous)
```

`_16_3_AbstractAnonymousInnerClass_Code`:

```
Showing in class A (Anonymous)
```

---

## Common Mistakes

### Mistake 1: Creating a non-static inner class without an outer object

```java
A.B b = new A.B();   // COMPILE ERROR
A a = new A();
A.B b2 = a.new B();  // correct
```

A non-static inner class needs an enclosing instance; use `outerObject.new Inner()`.

### Mistake 2: Adding `static` to the outer class

```java
static class A { ... } // ERROR at top level
```

Only *nested* classes can be `static`; a top-level class cannot.

### Mistake 3: Forgetting the semicolon after an anonymous class

```java
A obj1 = new A() {
    public void show() { ... }
}      // <-- missing ';'
```

`new Type() { ... }` is part of a statement, so it must end with `;`.

### Mistake 4: Thinking `new A() { ... }` instantiates the abstract class

It does not. It creates an **anonymous subclass** of `A` and instantiates that.
The abstract `A` is never directly instantiated.

### Mistake 5: Compiling all files in this chapter together

```bash
javac _16_InnerClass/*.java   # fails: duplicate class A
```

Compile each file individually, as shown above.

---

## Interview Questions

### Q1: What is an inner class?

**Answer:** A class declared inside another class. It groups a helper type with
its enclosing class and (for non-static inner classes) can access the outer
object's members.

### Q2: What is the difference between a static nested class and a non-static inner class?

**Answer:** A non-static (member) inner class is tied to an instance of the outer
class and is created with `outerObject.new Inner()`. A static nested class is not
tied to any instance and is created directly with `new Outer.Inner()`.

### Q3: How do you instantiate a non-static inner class?

**Answer:** Through an outer-class object: `Outer o = new Outer(); Outer.Inner i =
o.new Inner();`.

### Q4: Can a top-level class be declared `static`?

**Answer:** No. Only nested classes may be `static`; the `static` keyword is not
allowed on a top-level class.

### Q5: What is an anonymous inner class?

**Answer:** A class with no name that is defined and instantiated in one
expression (`new Type() { ... }`). It creates a one-off subclass of a class (or
implementation of an interface), typically for a single use.

### Q6: When would you use an anonymous inner class instead of a named subclass?

**Answer:** When the subclass/implementation is needed only once. Writing a
separate named class to use it a single time is unnecessary overhead; an anonymous
class keeps the one-off behaviour inline.

### Q7: Can you create an anonymous inner class from an abstract class or interface?

**Answer:** Yes. `new AbstractType() { ... }` creates an anonymous subclass that
implements the abstract methods and instantiates that subclass -- the abstract
type itself is never directly instantiated.

### Q8: Does `new A() { ... }` create an object of `A` when `A` is abstract?

**Answer:** No. It creates an instance of an anonymous subclass of `A` that
provides the missing implementation. `A` itself cannot be instantiated.

---

*This is part of a Java learning series. It builds directly on Inheritance
(Chapter 8), Polymorphism (Chapter 10), and the `abstract` keyword (Chapter 15),
and anonymous inner classes are the stepping stone to interfaces and lambda
expressions.*
