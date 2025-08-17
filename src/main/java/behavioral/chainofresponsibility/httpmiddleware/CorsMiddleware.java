package behavioral.chainofresponsibility.httpmiddleware;

import java.util.Set;

public class CorsMiddleware extends Middleware {
    private final Set<String> allowedOrigins;
    private final Set<String> allowedMethods;
    private final Set<String> allowedHeaders;

    public CorsMiddleware(Set<String> allowedOrigins, Set<String> allowedMethods, Set<String> allowedHeaders) {
        this.allowedOrigins = allowedOrigins;
        this.allowedMethods = allowedMethods;
        this.allowedHeaders = allowedHeaders;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        System.out.println("🌐 CorsMiddleware: Processing CORS headers");

        String origin = request.getHeader("Origin");
        String requestMethod = request.getHeader("Access-Control-Request-Method");

        if ("OPTIONS".equals(request.getMethod())) {
            System.out.println("   ✈️ Handling preflight request");
            handlePreflightRequest(request, response, origin, requestMethod);
            return;
        }

        if (origin != null) {
            if (allowedOrigins.contains("*") || allowedOrigins.contains(origin)) {
                response.addHeader("Access-Control-Allow-Origin", origin);
                System.out.println("   ✅ Origin allowed: " + origin);
            } else {
                System.out.println("   ❌ Origin not allowed: " + origin);
                response.setStatus(403, "Forbidden");
                response.setBody("CORS policy violation");
                return;
            }
        }

        response.addHeader("Access-Control-Allow-Credentials", "true");
        callNext(request, response);
    }

    private void handlePreflightRequest(HttpRequest request, HttpResponse response, 
                                      String origin, String requestMethod) {
        if (origin != null && (allowedOrigins.contains("*") || allowedOrigins.contains(origin))) {
            response.addHeader("Access-Control-Allow-Origin", origin);
        }

        if (requestMethod != null && allowedMethods.contains(requestMethod)) {
            response.addHeader("Access-Control-Allow-Methods", String.join(", ", allowedMethods));
        }

        response.addHeader("Access-Control-Allow-Headers", String.join(", ", allowedHeaders));
        response.addHeader("Access-Control-Max-Age", "86400");
        response.setStatus(204, "No Content");
        response.markAsProcessed();
    }
}