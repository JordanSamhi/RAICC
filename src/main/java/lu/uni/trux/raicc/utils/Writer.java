package lu.uni.trux.raicc.utils;

public class Writer {

    private final String end = "\u001B[0m";
    private final String red = "\u001B[31m";
    private final String green = "\u001B[32m";
    private final String blue = "\u001B[36m";
    private final String yellow = "\u001B[33m";

    private static Writer instance;

    public static Writer v() {
        if (instance == null) {
            instance = new Writer();
        }
        return instance;
    }

    private void pprint(String prefix, String message, String color) {
        if (!CommandLineOptions.v().hasRaw()) {
            System.out.printf("%s[%s] %s%s%n", color, prefix, message, end);
        }
    }

    public void perror(String message) {
        pprint("!", message, red);
    }

    public void psuccess(String message) {
        pprint("✓", message, green);
    }

    public void pinfo(String message) {
        pprint("*", message, blue);
    }

    public void pwarning(String message) {
        pprint("*", message, yellow);
    }
}

