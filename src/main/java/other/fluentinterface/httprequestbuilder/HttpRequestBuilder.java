package other.fluentinterface.httprequestbuilder;

import java.util.HashMap;
import java.util.Map;

// Fluent Interface (HTTP Request Builder)
public class HttpRequestBuilder {
    private String url;
    private String method = "GET";
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> queryParams = new HashMap<>();
    private String body;
    private int timeout = 30000; // 30 seconds default

    // Private constructor to enforce factory method usage
    private HttpRequestBuilder() {
    }

    // Factory method to start the fluent chain
    public static HttpRequestBuilder url(String url) {
        HttpRequestBuilder builder = new HttpRequestBuilder();
        builder.url = url;
        return builder;
    }

    // Chaining method: Set HTTP method
    public HttpRequestBuilder method(String method) {
        this.method = method.toUpperCase();
        return this;
    }

    // Convenience methods for common HTTP methods
    public HttpRequestBuilder get() {
        return method("GET");
    }

    public HttpRequestBuilder post() {
        return method("POST");
    }

    public HttpRequestBuilder put() {
        return method("PUT");
    }

    public HttpRequestBuilder delete() {
        return method("DELETE");
    }

    // Chaining method: Add header
    public HttpRequestBuilder header(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    // Convenience method for content type
    public HttpRequestBuilder contentType(String contentType) {
        return header("Content-Type", contentType);
    }

    // Convenience method for authorization
    public HttpRequestBuilder authorization(String token) {
        return header("Authorization", token);
    }

    // Chaining method: Add query parameter
    public HttpRequestBuilder queryParam(String name, String value) {
        this.queryParams.put(name, value);
        return this;
    }

    // Chaining method: Set request body
    public HttpRequestBuilder body(String body) {
        this.body = body;
        return this;
    }

    // Chaining method: Set timeout
    public HttpRequestBuilder timeout(int timeoutMs) {
        this.timeout = timeoutMs;
        return this;
    }

    // Terminal method: Build the HTTP request representation
    public HttpRequest build() {
        String fullUrl = buildUrl();
        return new HttpRequest(fullUrl, method, headers, body, timeout);
    }

    private String buildUrl() {
        if (queryParams.isEmpty()) {
            return url;
        }

        StringBuilder urlBuilder = new StringBuilder(url);
        urlBuilder.append("?");
        
        boolean first = true;
        for (Map.Entry<String, String> param : queryParams.entrySet()) {
            if (!first) {
                urlBuilder.append("&");
            }
            urlBuilder.append(param.getKey()).append("=").append(param.getValue());
            first = false;
        }
        
        return urlBuilder.toString();
    }

    // Inner class representing the built HTTP request
    public static class HttpRequest {
        private final String url;
        private final String method;
        private final Map<String, String> headers;
        private final String body;
        private final int timeout;

        public HttpRequest(String url, String method, Map<String, String> headers, String body, int timeout) {
            this.url = url;
            this.method = method;
            this.headers = new HashMap<>(headers);
            this.body = body;
            this.timeout = timeout;
        }

        // Getters
        public String getUrl() { return url; }
        public String getMethod() { return method; }
        public Map<String, String> getHeaders() { return new HashMap<>(headers); }
        public String getBody() { return body; }
        public int getTimeout() { return timeout; }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("HTTP Request:\n");
            sb.append("Method: ").append(method).append("\n");
            sb.append("URL: ").append(url).append("\n");
            sb.append("Timeout: ").append(timeout).append("ms\n");
            
            if (!headers.isEmpty()) {
                sb.append("Headers:\n");
                headers.forEach((key, value) -> sb.append("  ").append(key).append(": ").append(value).append("\n"));
            }
            
            if (body != null && !body.isEmpty()) {
                sb.append("Body: ").append(body).append("\n");
            }
            
            return sb.toString();
        }
    }
}