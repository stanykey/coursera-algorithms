public class HelloGoodbye {
    public static void main(String[] args) {
        final String first = args[0];
        final String second = args[1];

        System.out.printf("Hello %s and %s.%n", first, second);
        System.out.printf("Goodbye %s and %s.%n", second, first);
    }
}
