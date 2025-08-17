package behavioral.chainofresponsibility.httpmiddleware;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimitMiddleware extends Middleware {
    private final int maxRequests;
    private final int windowMinutes;
    private final ConcurrentHashMap<String, RequestWindow> clientRequests;

    public RateLimitMiddleware(int maxRequests, int windowMinutes) {
        this.maxRequests = maxRequests;
        this.windowMinutes = windowMinutes;
        this.clientRequests = new ConcurrentHashMap<>();
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        System.out.println("⏱️ RateLimitMiddleware: Checking rate limits");

        String clientId = getClientId(request);
        RequestWindow window = clientRequests.computeIfAbsent(clientId, k -> new RequestWindow());

        LocalDateTime now = LocalDateTime.now();
        
        if (isWindowExpired(window.getStartTime(), now)) {
            window.reset(now);
        }

        int currentCount = window.incrementAndGet();
        
        if (currentCount > maxRequests) {
            System.out.printf("   ❌ Rate limit exceeded for %s: %d/%d requests%n", 
                            clientId, currentCount, maxRequests);
            response.setStatus(429, "Too Many Requests");
            response.setBody("Rate limit exceeded. Try again later.");
            response.addHeader("Retry-After", String.valueOf(windowMinutes * 60));
            response.addHeader("X-RateLimit-Limit", String.valueOf(maxRequests));
            response.addHeader("X-RateLimit-Remaining", "0");
            return;
        }

        System.out.printf("   ✅ Rate limit OK for %s: %d/%d requests%n", 
                        clientId, currentCount, maxRequests);
        
        response.addHeader("X-RateLimit-Limit", String.valueOf(maxRequests));
        response.addHeader("X-RateLimit-Remaining", String.valueOf(maxRequests - currentCount));
        
        callNext(request, response);
    }

    private String getClientId(HttpRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return "user_" + authHeader.substring("Bearer ".length());
        }
        return "ip_" + request.getClientIp();
    }

    private boolean isWindowExpired(LocalDateTime startTime, LocalDateTime now) {
        return ChronoUnit.MINUTES.between(startTime, now) >= windowMinutes;
    }

    private static class RequestWindow {
        private LocalDateTime startTime;
        private final AtomicInteger count;

        public RequestWindow() {
            this.startTime = LocalDateTime.now();
            this.count = new AtomicInteger(0);
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public int incrementAndGet() {
            return count.incrementAndGet();
        }

        public void reset(LocalDateTime newStartTime) {
            this.startTime = newStartTime;
            this.count.set(0);
        }
    }
}