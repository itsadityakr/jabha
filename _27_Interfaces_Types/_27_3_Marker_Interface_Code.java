package _27_Interfaces_Types;

// =============================================================================
// CHAPTER 27 - TYPES OF INTERFACES  |  FILE 3 of 3 : MARKER (TAGGING) INTERFACE
// =============================================================================
//
// A MARKER interface is an EMPTY interface - no methods, no constants. It does
// not add behaviour; it adds a TYPE TAG. Other code can then ask "is this object
// an instance of the marker?" using 'instanceof' and decide what to do.
//
// WHY would an EMPTY interface be useful?
//   * It attaches metadata at the TYPE level, checkable by BOTH the compiler and
//     the runtime, without any fields or methods.
//   * The JDK itself uses several markers:
//        java.io.Serializable   -> "this object may be converted to bytes"
//        java.lang.Cloneable    -> "Object.clone() is permitted on this class"
//        java.util.RandomAccess -> "indexed get(i) is fast on this List"
//   * Modern code often replaces markers with ANNOTATIONS (Chapter 26), but a
//     marker still gives COMPILE-TIME type checking that an annotation cannot:
//     a method can require a Deletable parameter, refusing anything untagged.
// =============================================================================

// Two custom marker interfaces - note the deliberately EMPTY bodies.
interface Deletable {
    // intentionally empty: this interface is purely a tag
}

interface Downloadable {
    // intentionally empty: this interface is purely a tag
}

// A Document is BOTH deletable and downloadable. A class may carry many tags.
class Document implements Deletable, Downloadable {
    String name;

    Document(String name) {
        this.name = name;
    }
}

// A Movie can be downloaded but, in this system, must NOT be deletable, so it
// deliberately does NOT implement Deletable.
class Movie implements Downloadable {
    String title;

    Movie(String title) {
        this.title = title;
    }
}

public class _27_3_Marker_Interface_Code {

    // A "service" that deletes ONLY objects tagged as Deletable. The marker acts
    // as a safety gate: forget the tag and the object simply cannot be deleted.
    static void delete(Object item) {
        if (item instanceof Deletable) { // <-- the marker check
            System.out.println("Deleting: " + item.getClass().getSimpleName());
        } else {
            System.out.println("REFUSED to delete " + item.getClass().getSimpleName()
                    + " - it is not marked Deletable");
        }
    }

    static void download(Object item) {
        if (item instanceof Downloadable) {
            System.out.println("Downloading: " + item.getClass().getSimpleName());
        } else {
            System.out.println("Cannot download " + item.getClass().getSimpleName());
        }
    }

    public static void main(String[] args) {
        Document doc = new Document("resume.pdf");
        Movie movie = new Movie("Inception");

        System.out.println("=== delete() respects the Deletable marker ===");
        delete(doc);   // allowed - Document implements Deletable
        delete(movie); // refused - Movie is not Deletable

        System.out.println("\n=== download() respects the Downloadable marker ===");
        download(doc);   // allowed
        download(movie); // allowed

        System.out.println("\n=== a built-in marker: java.io.Serializable ===");
        // String implements Serializable; our Movie does not.
        System.out.println("Is \"hello\" Serializable? " + ("hello" instanceof java.io.Serializable));
        System.out.println("Is a Movie Serializable?  " + (movie instanceof java.io.Serializable));
    }
}
