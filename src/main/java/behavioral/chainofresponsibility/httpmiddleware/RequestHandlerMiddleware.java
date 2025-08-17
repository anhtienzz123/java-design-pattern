package behavioral.chainofresponsibility.httpmiddleware;

import java.util.Map;

public class RequestHandlerMiddleware extends Middleware {
    private final Map<String, RouteHandler> routes;

    public RequestHandlerMiddleware(Map<String, RouteHandler> routes) {
        this.routes = routes;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        System.out.println("🎯 RequestHandlerMiddleware: Processing request");

        String routeKey = request.getMethod() + " " + request.getPath();
        RouteHandler handler = routes.get(routeKey);

        if (handler == null) {
            System.out.println("   ❌ No handler found for: " + routeKey);
            response.setStatus(404, "Not Found");
            response.setBody("Route not found: " + request.getPath());
            response.addHeader("Content-Type", "text/plain");
            return;
        }

        System.out.println("   ✅ Handler found for: " + routeKey);
        try {
            handler.handle(request, response);
        } catch (Exception e) {
            System.out.println("   ❌ Handler error: " + e.getMessage());
            response.setStatus(500, "Internal Server Error");
            response.setBody("Internal server error");
            response.addHeader("Content-Type", "text/plain");
        }

        callNext(request, response);
    }

    @FunctionalInterface
    public interface RouteHandler {
        void handle(HttpRequest request, HttpResponse response);
    }
}