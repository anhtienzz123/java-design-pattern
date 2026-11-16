package other.interceptingfilter.apigateway;

/** The real handler (business logic). It knows nothing about the filters. */
public class Target {

    public String handle(Request request) {
        return "200 OK - content of " + request.path();
    }
}
