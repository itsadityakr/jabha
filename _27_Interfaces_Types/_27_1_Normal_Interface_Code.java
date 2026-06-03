package _27_Interfaces_Types;

// =============================================================================
// CHAPTER 27 - TYPES OF INTERFACES  |  FILE 1 of 3 : NORMAL (REGULAR) INTERFACE
// =============================================================================
//
// A NORMAL interface (also called a "regular" interface) is one that declares
// TWO OR MORE abstract methods. It is the everyday interface from Chapter 24 -
// a contract that an implementing class promises to fulfil.
//
// Since Java 8 and 9 an interface can hold much more than abstract methods:
//
//   constant        -> public static final field (a shared constant)
//   abstract method  -> no body; the implementer MUST provide one
//   default method   -> HAS a body; inherited, and overridable        (Java 8+)
//   static method    -> belongs to the interface itself                (Java 8+)
//   private method   -> hidden helper shared by default methods        (Java 9+)
//
// This file demonstrates ALL of them in one place using a RemoteControl example.
// =============================================================================

interface RemoteControl {

    // 1) CONSTANT - implicitly public static final (Chapter 24). MUST be given
    //    a value, because it is final.
    int MAX_VOLUME = 100;

    // 2) ABSTRACT METHODS - the core contract. Having MORE THAN ONE is exactly
    //    what makes this a "normal" interface (contrast with the single-method
    //    functional interface in file _27_2).
    void turnOn();
    void turnOff();
    void setVolume(int level);

    // 3) DEFAULT METHOD (Java 8+) - ships a ready-made body. Adding it to the
    //    interface does NOT break existing implementers, because they inherit
    //    this body for free. They MAY override it if they want different behaviour.
    default void mute() {
        setVolume(0); // a default method can call the abstract methods
        System.out.println("Muted (via default method)");
    }

    // 4) STATIC METHOD (Java 8+) - a utility tied to the interface itself,
    //    called as RemoteControl.isValidVolume(...). It is NOT inherited by
    //    implementing classes and cannot be overridden.
    static boolean isValidVolume(int level) {
        return level >= 0 && level <= MAX_VOLUME;
    }

    // 5) PRIVATE METHOD (Java 9+) - a hidden helper used to SHARE code between
    //    default methods without exposing it in the public contract.
    private void log(String action) {
        System.out.println("[RemoteControl] " + action);
    }

    // A second default method that reuses the private helper above.
    default void reset() {
        log("reset requested"); // calls the private helper
        turnOff();
        turnOn();
    }
}

// A concrete class must implement EVERY abstract method (turnOn, turnOff,
// setVolume). default / static / private methods are NOT its responsibility.
class TvRemote implements RemoteControl {

    private int volume = 10;

    @Override
    public void turnOn() {
        System.out.println("TV: ON");
    }

    @Override
    public void turnOff() {
        System.out.println("TV: OFF");
    }

    @Override
    public void setVolume(int level) {
        // Reuse the interface's STATIC method to validate the input.
        if (RemoteControl.isValidVolume(level)) {
            this.volume = level;
            System.out.println("TV volume set to " + volume);
        } else {
            System.out.println("Rejected volume " + level + " (must be 0.." + MAX_VOLUME + ")");
        }
    }
}

// Another implementer that OVERRIDES the default mute() to behave differently,
// showing that default methods are just defaults, not final behaviour.
class AcRemote implements RemoteControl {

    @Override
    public void turnOn() {
        System.out.println("AC: ON");
    }

    @Override
    public void turnOff() {
        System.out.println("AC: OFF");
    }

    @Override
    public void setVolume(int level) {
        System.out.println("AC has no volume control; ignoring " + level);
    }

    @Override
    public void mute() { // OVERRIDES the inherited default method
        System.out.println("AC cannot be muted (overrode the default mute())");
    }
}

public class _27_1_Normal_Interface_Code {
    public static void main(String[] args) {

        // Program to the INTERFACE type, not the concrete class (Chapter 24).
        RemoteControl tv = new TvRemote();
        tv.turnOn();
        tv.setVolume(45);
        tv.setVolume(250); // rejected by the static validator (> MAX_VOLUME)
        tv.mute();         // uses the inherited DEFAULT method
        tv.reset();        // default method that calls a PRIVATE helper
        tv.turnOff();

        System.out.println();

        RemoteControl ac = new AcRemote();
        ac.turnOn();
        ac.mute(); // runs AC's OVERRIDDEN version of the default method
        ac.turnOff();

        System.out.println();

        // STATIC interface method - called on the INTERFACE, not on an object.
        System.out.println("Is 80 a valid volume?  " + RemoteControl.isValidVolume(80));
        System.out.println("Is 120 a valid volume? " + RemoteControl.isValidVolume(120));

        // CONSTANT - accessed through the interface name.
        System.out.println("MAX_VOLUME = " + RemoteControl.MAX_VOLUME);
    }
}
