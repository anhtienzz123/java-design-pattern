package behavioral.chainofresponsibility.httpmiddleware;

import java.util.Set;

public class AuthenticationMiddleware extends Middleware {
    private final Set<String> validTokens;
    private final Set<String> publicPaths;

    public AuthenticationMiddleware(Set<String> validTokens, Set<String> publicPaths) {
        this.validTokens = validTokens;
        this.publicPaths = publicPaths;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        System.out.println("🔐 AuthenticationMiddleware: Processing request");

        if (isPublicPath(request.getPath())) {
            System.out.println("   ✅ Public path, skipping authentication");
            callNext(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("   ❌ Missing or invalid Authorization header");
            response.setStatus(401, "Unauthorized");
            response.setBody("Authentication required");
            response.addHeader("WWW-Authenticate", "Bearer");
            return;
        }

        String token = authHeader.substring("Bearer ".length());
        if (!validTokens.contains(token)) {
            System.out.println("   ❌ Invalid token: " + token);
            response.setStatus(401, "Unauthorized");
            response.setBody("Invalid token");
            return;
        }

        System.out.println("   ✅ Authentication successful");
        request.addHeader("X-User-ID", getUserIdFromToken(token));
        callNext(request, response);
    }

    private boolean isPublicPath(String path) {
        return publicPaths.contains(path) || path.startsWith("/public");
    }

    private String getUserIdFromToken(String token) {
        return "user_" + token.hashCode();
    }
}