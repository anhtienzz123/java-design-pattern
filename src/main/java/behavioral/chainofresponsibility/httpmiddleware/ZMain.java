package behavioral.chainofresponsibility.httpmiddleware;

import java.util.Map;

public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== HTTP Middleware Chain of Responsibility Pattern Demo ===\n");

        MiddlewareChain server = new MiddlewareChain.Builder()
            .addLogging()
            .addCors("*")
            .addRateLimit(5, 1)
            .addAuthentication("valid-token-123", "admin-token-456")
            .addRequestHandler(createRoutes())
            .build();

        System.out.println("🚀 Server started with " + server.size() + " middleware layers\n");

        demonstrateRequests(server);
    }

    private static void demonstrateRequests(MiddlewareChain server) {
        System.out.println("📋 Request Scenarios:\n");

        scenario1_PublicEndpoint(server);
        scenario2_AuthenticatedEndpoint(server);
        scenario3_UnauthorizedRequest(server);
        scenario4_CorsPreflightRequest(server);
        scenario5_RateLimitExceeded(server);
        scenario6_NotFoundRequest(server);
    }

    private static void scenario1_PublicEndpoint(MiddlewareChain server) {
        System.out.println("🔍 Scenario 1: Public endpoint access");
        System.out.println("─".repeat(50));

        HttpRequest request = new HttpRequest("GET", "/health");
        request.addHeader("User-Agent", "HealthChecker/1.0");
        request.setClientIp("192.168.1.100");

        HttpResponse response = new HttpResponse();
        server.handle(request, response);

        System.out.println("Response: " + response);
        System.out.println();
    }

    private static void scenario2_AuthenticatedEndpoint(MiddlewareChain server) {
        System.out.println("🔍 Scenario 2: Authenticated API access");
        System.out.println("─".repeat(50));

        HttpRequest request = new HttpRequest("GET", "/api/users");
        request.addHeader("Authorization", "Bearer valid-token-123");
        request.addHeader("User-Agent", "WebApp/2.0");
        request.setClientIp("10.0.0.5");

        HttpResponse response = new HttpResponse();
        server.handle(request, response);

        System.out.println("Response: " + response);
        System.out.println();
    }

    private static void scenario3_UnauthorizedRequest(MiddlewareChain server) {
        System.out.println("🔍 Scenario 3: Unauthorized API access");
        System.out.println("─".repeat(50));

        HttpRequest request = new HttpRequest("POST", "/api/admin");
        request.addHeader("User-Agent", "HackerTool/1.0");
        request.setClientIp("203.0.113.42");

        HttpResponse response = new HttpResponse();
        server.handle(request, response);

        System.out.println("Response: " + response);
        System.out.println();
    }

    private static void scenario4_CorsPreflightRequest(MiddlewareChain server) {
        System.out.println("🔍 Scenario 4: CORS preflight request");
        System.out.println("─".repeat(50));

        HttpRequest request = new HttpRequest("OPTIONS", "/api/data");
        request.addHeader("Origin", "https://webapp.example.com");
        request.addHeader("Access-Control-Request-Method", "POST");
        request.addHeader("Access-Control-Request-Headers", "Content-Type,Authorization");

        HttpResponse response = new HttpResponse();
        server.handle(request, response);

        System.out.println("Response: " + response);
        System.out.println();
    }

    private static void scenario5_RateLimitExceeded(MiddlewareChain server) {
        System.out.println("🔍 Scenario 5: Rate limit exceeded");
        System.out.println("─".repeat(50));

        String token = "valid-token-123";
        
        for (int i = 1; i <= 7; i++) {
            System.out.println("Request #" + i + ":");
            
            HttpRequest request = new HttpRequest("GET", "/api/data");
            request.addHeader("Authorization", "Bearer " + token);
            request.setClientIp("172.16.0.10");

            HttpResponse response = new HttpResponse();
            server.handle(request, response);

            System.out.println("  Status: " + response.getStatusCode() + " " + response.getStatusMessage());
            if (response.getHeader("X-RateLimit-Remaining") != null) {
                System.out.println("  Rate Limit Remaining: " + response.getHeader("X-RateLimit-Remaining"));
            }
            
            if (i == 6) {
                System.out.println("  ⚠️ Rate limit should be exceeded on next request");
            }
            System.out.println();
        }
    }

    private static void scenario6_NotFoundRequest(MiddlewareChain server) {
        System.out.println("🔍 Scenario 6: Route not found");
        System.out.println("─".repeat(50));

        HttpRequest request = new HttpRequest("GET", "/api/nonexistent");
        request.addHeader("Authorization", "Bearer valid-token-123");
        request.setClientIp("10.0.0.1");

        HttpResponse response = new HttpResponse();
        server.handle(request, response);

        System.out.println("Response: " + response);
        System.out.println();
    }

    private static Map<String, RequestHandlerMiddleware.RouteHandler> createRoutes() {
        return Map.of(
            "GET /health", (req, res) -> {
                res.setStatus(200, "OK");
                res.setBody("{\"status\":\"healthy\",\"timestamp\":\"" + 
                           java.time.Instant.now() + "\"}");
                res.addHeader("Content-Type", "application/json");
            },
            
            "GET /api/users", (req, res) -> {
                String userId = req.getHeader("X-User-ID");
                res.setStatus(200, "OK");
                res.setBody("{\"users\":[{\"id\":1,\"name\":\"John\"},{\"id\":2,\"name\":\"Jane\"}]," +
                           "\"requestedBy\":\"" + userId + "\"}");
                res.addHeader("Content-Type", "application/json");
            },
            
            "POST /api/admin", (req, res) -> {
                res.setStatus(200, "OK");
                res.setBody("{\"message\":\"Admin operation completed\"}");
                res.addHeader("Content-Type", "application/json");
            },
            
            "GET /api/data", (req, res) -> {
                res.setStatus(200, "OK");
                res.setBody("{\"data\":[1,2,3,4,5]}");
                res.addHeader("Content-Type", "application/json");
            }
        );
    }
}