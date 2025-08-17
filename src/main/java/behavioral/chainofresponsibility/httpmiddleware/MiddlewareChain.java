package behavioral.chainofresponsibility.httpmiddleware;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MiddlewareChain {
    private final List<Middleware> middlewares;
    private Middleware head;

    public MiddlewareChain() {
        this.middlewares = new ArrayList<>();
    }

    public MiddlewareChain use(Middleware middleware) {
        middlewares.add(middleware);
        rebuildChain();
        return this;
    }

    public void handle(HttpRequest request, HttpResponse response) {
        if (head != null) {
            head.handle(request, response);
        } else {
            response.setStatus(404, "Not Found");
            response.setBody("No middleware configured");
        }
    }

    private void rebuildChain() {
        if (middlewares.isEmpty()) {
            head = null;
            return;
        }

        head = middlewares.get(0);
        Middleware current = head;

        for (int i = 1; i < middlewares.size(); i++) {
            current.setNext(middlewares.get(i));
            current = middlewares.get(i);
        }
    }

    public int size() {
        return middlewares.size();
    }

    public static class Builder {
        private final MiddlewareChain chain;

        public Builder() {
            this.chain = new MiddlewareChain();
        }

        public Builder addLogging() {
            chain.use(new LoggingMiddleware());
            return this;
        }

        public Builder addCors(String... allowedOrigins) {
            chain.use(new CorsMiddleware(
                Set.of(allowedOrigins),
                Set.of("GET", "POST", "PUT", "DELETE", "OPTIONS"),
                Set.of("Content-Type", "Authorization", "X-Requested-With")
            ));
            return this;
        }

        public Builder addRateLimit(int maxRequests, int windowMinutes) {
            chain.use(new RateLimitMiddleware(maxRequests, windowMinutes));
            return this;
        }

        public Builder addAuthentication(String... validTokens) {
            chain.use(new AuthenticationMiddleware(
                Set.of(validTokens),
                Set.of("/health", "/login", "/public")
            ));
            return this;
        }

        public Builder addRequestHandler(java.util.Map<String, RequestHandlerMiddleware.RouteHandler> routes) {
            chain.use(new RequestHandlerMiddleware(routes));
            return this;
        }

        public Builder addCustom(Middleware middleware) {
            chain.use(middleware);
            return this;
        }

        public MiddlewareChain build() {
            return chain;
        }
    }
}