package behavioral.chainofresponsibility.httpmiddleware;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private int statusCode;
    private String statusMessage;
    private final Map<String, String> headers;
    private String body;
    private boolean processed;

    public HttpResponse() {
        this.statusCode = 200;
        this.statusMessage = "OK";
        this.headers = new HashMap<>();
        this.body = "";
        this.processed = false;
    }

    public int getStatusCode() { return statusCode; }
    public String getStatusMessage() { return statusMessage; }
    public Map<String, String> getHeaders() { return new HashMap<>(headers); }
    public String getBody() { return body; }
    public boolean isProcessed() { return processed; }

    public void setStatus(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public void setBody(String body) {
        this.body = body;
        this.processed = true;
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public void markAsProcessed() {
        this.processed = true;
    }

    @Override
    public String toString() {
        return String.format("HTTP %d %s [Headers: %s] Body: %s", 
                           statusCode, statusMessage, headers, 
                           body.length() > 50 ? body.substring(0, 50) + "..." : body);
    }
}