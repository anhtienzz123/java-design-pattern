package other.interceptingfilter.apigateway;

/** A simple request: which path is called and the token sent by the client. */
public record Request(String path, String token) {
}
