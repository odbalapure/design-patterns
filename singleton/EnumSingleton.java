package singleton;

public enum EnumSingleton {
    // Java ensures only instance of an enum value is created, even in multithreaded
    // environment. This is the most robust and concise way to implement singleton.
    INSTNACE;

    // NOTE: This is not always suitable especially if we need to extend a class or
    // if lazy initialization is a strict requirement.
    public void goSomething() {
        System.out.println("Enum singleton");
    }
}
