package zdemo.combinator.logsearchdemo;

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
		Finder errorFinder = Finder.contains("ERROR").and(Finder.contains("payment"));
		errorFinder.find(LOG).forEach(l -> System.out.println(l));
	}
}
