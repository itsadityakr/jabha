package _26_Annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// =============================================================================
// CHAPTER 26 - ANNOTATIONS  (one complete, runnable file)
// =============================================================================
//
// WHAT IS AN ANNOTATION?
// ----------------------
// An annotation is METADATA about code - a label that starts with '@' and is
// attached to a class, method, field, parameter, etc. By itself it does NOT
// change what the program does; its value comes from someone READING it. Three
// audiences read annotations:
//
//      1. the COMPILER         -> @Override, @Deprecated, @SuppressWarnings ...
//      2. tools / frameworks    -> JUnit @Test, Spring @Autowired, JPA @Entity ...
//      3. your own code at RUNTIME via Reflection (SECTION 5 below)
//
// Think of an annotation as a "sticky note" on your code: the note does nothing
// on its own; its power is that a reader reacts to it.
//
// THIS FILE IS ORGANISED INTO 5 SECTIONS (run main() to see each in action):
//
//   SECTION 1 : @Override                       (the built-in you use most)
//   SECTION 2 : @Deprecated, @SuppressWarnings,  (the other built-ins)
//               @SafeVarargs, @FunctionalInterface
//   SECTION 3 : META-ANNOTATIONS                 (@Retention, @Target,
//               @Documented, @Inherited, @Repeatable)
//   SECTION 4 : WRITING YOUR OWN ANNOTATION      (elements, defaults, 'value')
//   SECTION 5 : READING ANNOTATIONS BY REFLECTION (a mini framework you build)
//
// The supporting types for every section are declared first (top-level types),
// then the single public class with main() runs all five demos in order.
// =============================================================================

// #############################################################################
// SECTION 1 SUPPORT : @Override
// #############################################################################
//
// THE STORY: class B wants to OVERRIDE a method of class A. That method is
// called shows() - note the trailing 's'. It is dangerously easy to mistype the
// name as show(). WITHOUT @Override the compiler would accept the typo as a
// brand-new, unrelated method, leaving A's version in place - a SILENT BUG.
// @Override turns that silent bug into a loud COMPILE ERROR.
//
// (@Override has @Retention(SOURCE): it is a pure compile-time check and is
//  thrown away by the compiler - it never reaches the .class file or the JVM.)

class A {
    public void shows() { // the REAL method name is shows() - with an 's'
        System.out.println("In A.shows()");
    }
}

class B extends A {

    // If we had mistyped this as show() (missing 's'), @Override would FAIL to
    // compile with "method does not override a method from its superclass".
    // Without @Override the typo would silently create a new method and A's
    // version would keep running - a bug that hides until runtime.
    @Override // compiler guarantee: "this MUST really override a superclass method"
    public void shows() {
        System.out.println("In B.shows() (correctly overrides A.shows())");
    }
}

// #############################################################################
// SECTION 2 SUPPORT : the other BUILT-IN annotations
// #############################################################################

// @Deprecated marks an element as outdated. The compiler warns (and IDEs draw a
// ~strikethrough~) everywhere it is used. Since Java 9, 'since' and 'forRemoval'
// document the details. forRemoval = true means "scheduled for deletion" and -
// an advanced nuance - its warning lives in the "removal" lint category, NOT
// "deprecation" (see @SuppressWarnings on main()).
class LegacyPaymentApi {

    @Deprecated(since = "2.0", forRemoval = true)
    public void payOld() {
        System.out.println("Old payment flow (DEPRECATED - will be removed)");
    }

    public void pay() { // the modern replacement callers should migrate to
        System.out.println("New payment flow");
    }
}

// @FunctionalInterface declares an interface that must have EXACTLY ONE abstract
// method (a SAM - Single Abstract Method). The compiler ENFORCES that rule, so
// every lambda that depends on the single method stays safe. (Full treatment in
// Chapter 27, file _27_2.) default/static methods do NOT count against the rule.
@FunctionalInterface
interface Calculator {
    int operate(int a, int b); // the single abstract method

    default void banner() {
        System.out.println("[Calculator] combines two ints into one");
    }
}

// #############################################################################
// SECTION 3 SUPPORT : META-ANNOTATIONS (annotations that annotate annotations)
// #############################################################################
//
//   @Retention  -> lifespan: SOURCE (discarded) / CLASS (in .class, default) /
//                  RUNTIME (kept and readable by reflection - what frameworks need)
//   @Target     -> where it may be used (TYPE, METHOD, FIELD, PARAMETER, ...)
//   @Documented -> include it in generated Javadoc
//   @Inherited  -> subclasses inherit it from their superclass (classes only)
//   @Repeatable -> the same annotation may be applied more than once

// @Author shows @Documented + @Retention(RUNTIME) + @Target. RUNTIME is what
// lets SECTION 3 of main() read it back via reflection.
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@interface Author {
    String name();                   // required element (no default)
    String date() default "unknown"; // optional element (has a default)
}

// @Inherited demo: a subclass with no annotation of its own still REPORTS the
// superclass's @Framework when asked via reflection.
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Framework {
    String value(); // single 'value' element -> usable as @Framework("Spring")
}

@Framework("Spring")
class BaseEntity {
}

class ChildEntity extends BaseEntity { // declares NO @Framework, yet inherits it
}

// @Repeatable demo: @Schedule may be applied many times. It must point to a
// CONTAINER annotation (@Schedules) whose 'value' is an array of @Schedule.
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(Schedules.class)
@interface Schedule {
    String day();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Schedules { // the mandatory container that holds the repeats
    Schedule[] value();
}

@Author(name = "Aditya", date = "2026-06-03")
@Schedule(day = "Monday")
@Schedule(day = "Friday") // same annotation twice - allowed by @Repeatable
class TaskRunner {
}

// #############################################################################
// SECTION 4 SUPPORT : WRITING YOUR OWN ANNOTATION
// #############################################################################
//
// Declared with '@interface'. Each "method" inside is an ELEMENT (an attribute).
// Element rules:
//   * return type: primitive, String, Class, enum, another annotation, or an
//     array of those.
//   * no parameters, no body.
//   * 'default <value>' makes the element optional; otherwise it is REQUIRED.
//   * an element named exactly 'value' may be set without writing 'value=' :
//     @Role("ADMIN") is shorthand for @Role(value = "ADMIN").

enum Priority {
    LOW, MEDIUM, HIGH
}

// @TestCase imitates a real test-framework annotation and shows every element
// kind: a required String, an enum default, a primitive default, an array default.
@Retention(RetentionPolicy.RUNTIME) // RUNTIME so SECTION 4 of main() can read it
@Target(ElementType.METHOD)
@interface TestCase {
    String description();                        // required
    Priority priority() default Priority.MEDIUM; // optional enum element
    int timeoutMillis() default 1000;            // optional primitive element
    String[] tags() default {};                  // optional array element
}

// @Role uses the special single element name 'value' for the @Role("ADMIN") form.
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@interface Role {
    String value();
}

// #############################################################################
// SECTION 5 SUPPORT : annotations meant to be READ BY REFLECTION
// #############################################################################

// @JsonField marks a field for JSON output; optional 'name' renames the key.
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface JsonField {
    String name() default ""; // "" means: use the real field name
}

// @Range declares an inclusive [min, max] bound that the validator enforces.
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Range {
    int min();
    int max();
}

// A plain data class decorated with the SECTION 5 annotations.
class Account {

    @JsonField(name = "account_id")
    int id;

    @JsonField // no name override -> JSON key will be "owner"
    String owner;

    @JsonField(name = "balance")
    @Range(min = 0, max = 1_000_000)
    int balance;

    // No @JsonField -> the serializer deliberately SKIPS this field, proving the
    // serializer is driven by the annotation, not by the raw field list.
    String internalNote;

    Account(int id, String owner, int balance, String internalNote) {
        this.id = id;
        this.owner = owner;
        this.balance = balance;
        this.internalNote = internalNote;
    }
}

// #############################################################################
// THE PUBLIC CLASS : runs all five sections from main()
// #############################################################################

@Role("ADMIN") // class-level custom annotation (read in SECTION 4)
public class _26_Annotations_Code {

    // --- sample methods carrying @TestCase, scanned by reflection in SECTION 4 -
    @TestCase(
            description = "login succeeds with valid credentials",
            priority = Priority.HIGH,
            tags = { "auth", "smoke" })
    public void testLogin() {
        // a real test body would go here
    }

    @TestCase(description = "cart total adds up") // other elements use defaults
    public void testCart() {
    }

    // --- SECTION 2 helper : @SafeVarargs --------------------------------------
    // A generic varargs (T...) becomes an array of a generic type, which Java
    // cannot fully prove type-safe, so it warns ("unchecked / heap pollution").
    // @SafeVarargs is YOUR promise that the method only READS the values. It is
    // allowed only on methods that cannot be overridden (static/final/private).
    @SafeVarargs
    private static <T> List<T> listOf(T... items) {
        List<T> list = new ArrayList<>();
        for (T item : items) {
            list.add(item); // only reading from the varargs array - safe
        }
        return list;
    }

    // --- SECTION 5 helper #1 : serialize an object to JSON via @JsonField -----
    static String toJson(Object obj) throws IllegalAccessException {
        StringBuilder json = new StringBuilder("{");
        boolean first = true;

        for (Field field : obj.getClass().getDeclaredFields()) {
            JsonField jf = field.getAnnotation(JsonField.class);
            if (jf == null) {
                continue; // field not marked for JSON -> skip it
            }
            field.setAccessible(true); // allow reading non-public fields
            String key = jf.name().isEmpty() ? field.getName() : jf.name();
            Object value = field.get(obj);

            if (!first) {
                json.append(", ");
            }
            first = false;

            if (value instanceof String) {
                json.append('"').append(key).append("\": \"").append(value).append('"');
            } else {
                json.append('"').append(key).append("\": ").append(value);
            }
        }
        return json.append("}").toString();
    }

    // --- SECTION 5 helper #2 : validate @Range int fields ---------------------
    static void validate(Object obj) throws IllegalAccessException {
        for (Field field : obj.getClass().getDeclaredFields()) {
            Range range = field.getAnnotation(Range.class);
            if (range == null) {
                continue;
            }
            field.setAccessible(true);
            int value = field.getInt(obj);
            if (value < range.min() || value > range.max()) {
                System.out.println("   INVALID: " + field.getName() + " = " + value
                        + " (allowed " + range.min() + ".." + range.max() + ")");
            } else {
                System.out.println("   OK     : " + field.getName() + " = " + value);
            }
        }
    }

    // @SuppressWarnings hides named warning categories for this method. We hide:
    //   "removal" -> the forRemoval call to payOld() (NOT "deprecation", because
    //                forRemoval = true puts the warning in the "removal" category)
    //   "unused"  -> the demo variable 'neverUsed' below
    // Use suppression sparingly: a hidden warning is one you take responsibility for.
    @SuppressWarnings({ "removal", "unused" })
    public static void main(String[] args) throws IllegalAccessException {

        // ===================================================================
        // SECTION 1 : @Override
        // ===================================================================
        System.out.println("===== SECTION 1 : @Override =====");
        B b = new B();
        b.shows();          // real override -> "In B.shows() ..."
        A upcast = new B(); // dynamic dispatch still runs B's overridden version
        upcast.shows();

        // ===================================================================
        // SECTION 2 : @Deprecated / @SuppressWarnings / @SafeVarargs /
        //             @FunctionalInterface
        // ===================================================================
        System.out.println("\n===== SECTION 2 : other built-in annotations =====");

        LegacyPaymentApi api = new LegacyPaymentApi();
        api.payOld(); // @Deprecated(forRemoval) warning suppressed via "removal"
        api.pay();

        int neverUsed = 42; // @SuppressWarnings("unused") hides the warning here

        List<String> names = listOf("Ada", "Linus", "Grace"); // @SafeVarargs
        System.out.println("listOf(...) = " + names);

        Calculator add = (x, y) -> x + y; // @FunctionalInterface => lambda fits
        Calculator mul = (x, y) -> x * y;
        add.banner();
        System.out.println("add(2, 3) = " + add.operate(2, 3));
        System.out.println("mul(2, 3) = " + mul.operate(2, 3));

        // ===================================================================
        // SECTION 3 : META-ANNOTATIONS (read back via reflection)
        // ===================================================================
        System.out.println("\n===== SECTION 3 : meta-annotations =====");

        // @Retention(RUNTIME) + @Author on TaskRunner
        Author author = TaskRunner.class.getAnnotation(Author.class);
        System.out.println("@Author name = " + author.name() + ", date = " + author.date());

        // @Inherited : ChildEntity has no @Framework of its own, yet inherits it
        Framework base = BaseEntity.class.getAnnotation(Framework.class);
        Framework child = ChildEntity.class.getAnnotation(Framework.class);
        System.out.println("BaseEntity  @Framework = " + (base != null ? base.value() : "none"));
        System.out.println("ChildEntity @Framework = " + (child != null ? child.value() : "none")
                + "   <-- inherited via @Inherited");

        // @Repeatable : getAnnotationsByType returns every @Schedule repeat
        Schedule[] schedules = TaskRunner.class.getAnnotationsByType(Schedule.class);
        System.out.println("TaskRunner runs on " + schedules.length + " day(s):");
        for (Schedule s : schedules) {
            System.out.println("   - " + s.day());
        }

        // ===================================================================
        // SECTION 4 : custom annotations (@Role, @TestCase) via reflection
        // ===================================================================
        System.out.println("\n===== SECTION 4 : custom annotations =====");

        Role role = _26_Annotations_Code.class.getAnnotation(Role.class);
        System.out.println("Class-level @Role value = " + role.value());

        System.out.println("Scanning methods for @TestCase ...");
        for (var method : _26_Annotations_Code.class.getDeclaredMethods()) {
            TestCase tc = method.getAnnotation(TestCase.class);
            if (tc == null) {
                continue; // method not annotated -> skip
            }
            System.out.println("  " + method.getName() + "() -> "
                    + "desc='" + tc.description() + "'"
                    + ", priority=" + tc.priority()
                    + ", timeout=" + tc.timeoutMillis()
                    + ", tags=" + Arrays.toString(tc.tags()));
        }

        // ===================================================================
        // SECTION 5 : a mini FRAMEWORK driven entirely by annotations
        // ===================================================================
        System.out.println("\n===== SECTION 5 : reflection mini-framework =====");

        Account good = new Account(1, "Ada", 500, "secret memo");
        Account bad = new Account(2, "Linus", -50, "another memo");

        System.out.println("JSON (driven by @JsonField):");
        System.out.println("  " + toJson(good));
        System.out.println("  " + toJson(bad)); // 'internalNote' never appears

        System.out.println("Validation (driven by @Range):");
        System.out.println(" good account:");
        validate(good);
        System.out.println(" bad account:");
        validate(bad); // balance = -50 < min 0 -> reported INVALID
    }
}
