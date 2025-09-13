package singleton;

public class Main {
    public static void main(String[] args) {
        // Enum singleton
        EnumSingleton enumSingleton = EnumSingleton.INSTNACE;
        enumSingleton.goSomething();
    }
}
