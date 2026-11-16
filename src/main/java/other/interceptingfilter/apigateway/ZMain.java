package other.interceptingfilter.apigateway;

/**
 * Intercepting Filter Pattern demo.
 *
 * Every request passes through a chain of filters (logging, authentication)
 * before reaching the target. The target only contains business logic.
 */
public class ZMain {

    public static void main(String[] args) {
        FilterManager gateway = new FilterManager(new Target());
        gateway.addFilter(new LoggingFilter());
        gateway.addFilter(new AuthenticationFilter());

        System.out.println("--- Request with valid token ---");
        System.out.println("Client got: " + gateway.execute(new Request("/orders", "secret")));

        System.out.println("\n--- Request with wrong token ---");
        System.out.println("Client got: " + gateway.execute(new Request("/orders", "hacker")));
    }
}
