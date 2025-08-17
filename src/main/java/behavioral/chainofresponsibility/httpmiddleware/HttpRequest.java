package behavioral.chainofresponsibility.httpmiddleware;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String method;
    private final String path;
    private final Map<String, String> headers;
    private final Map<String, String> parameters;
    private final String body;
    private String clientIp;

    public HttpRequest(String method, String path) {
        this.method = method;
        this.path = path;
        this.headers = new HashMap<>();
        this.parameters = new HashMap<>();
        this.body = "";
        this.clientIp = "127.0.0.1";
    }

    public HttpRequest(String method, String path, Map<String, String> headers, 
                      Map<String, String> parameters, String body) {
        this.method = method;
        this.path = path;
        this.headers = new HashMap<>(headers);
        this.parameters = new HashMap<>(parameters);
        this.body = body;
        this.clientIp = "127.0.0.1";
    }

    public String getMethod() { return method; }
    public String getPath() { return path; }
    public Map<String, String> getHeaders() { return new HashMap<>(headers); }
    public Map<String, String> getParameters() { return new HashMap<>(parameters); }
    public String getBody() { return body; }
    public String getClientIp() { return clientIp; }

    public void setClientIp(String clientIp) { this.clientIp = clientIp; }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    @Override
    public String toString() {
        return String.format("%s %s [Headers: %s]", method, path, headers);
    }
}