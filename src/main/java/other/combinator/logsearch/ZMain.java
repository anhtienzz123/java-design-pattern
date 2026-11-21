package other.combinator.logsearch;

/**
 * Combinator Pattern demo.
 *
 * One primitive Finder (contains) is combined with and / or / except
 * into more specific log searches, without writing any new search loop.
 */
public class ZMain {

    private static final String LOG = """
            10:00 INFO  user alice logged in
            10:01 ERROR payment failed for alice: card declined
            10:02 WARN  disk usage at 85%
            10:03 ERROR database timeout on orders table
            10:04 INFO  user bob logged in
            10:05 ERROR payment failed for bob: timeout
            10:06 WARN  payment retry scheduled for bob
            """;

    public static void main(String[] args) {
        print("contains(\"error\")",
                Finder.contains("error"));

        print("contains(\"error\").and(contains(\"payment\"))",
                Finder.contains("error").and(Finder.contains("payment")));

        print("contains(\"error\").or(contains(\"warn\"))",
                Finder.contains("error").or(Finder.contains("warn")));

        print("contains(\"error\").except(contains(\"timeout\"))",
                Finder.contains("error").except(Finder.contains("timeout")));

        print("containsAll(\"payment\", \"bob\")",
                Finder.containsAll("payment", "bob"));

        print("containsAny(\"alice\", \"disk\")",
                Finder.containsAny("alice", "disk"));
    }

    private static void print(String description, Finder finder) {
        System.out.println("--- " + description + " ---");
        finder.find(LOG).forEach(line -> System.out.println("  " + line));
        System.out.println();
    }
}
