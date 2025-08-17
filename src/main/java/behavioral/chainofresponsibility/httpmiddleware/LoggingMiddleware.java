package behavioral.chainofresponsibility.httpmiddleware;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggingMiddleware extends Middleware {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        String timestamp = LocalDateTime.now().format(formatter);
        String clientIp = request.getClientIp();
        
        System.out.println("📝 LoggingMiddleware: Incoming request");
        System.out.printf("   [%s] %s %s %s - User-Agent: %s%n", 
                         timestamp, clientIp, request.getMethod(), request.getPath(),
                         request.getHeader("User-Agent"));

        long startTime = System.currentTimeMillis();
        
        callNext(request, response);
        
        long duration = System.currentTimeMillis() - startTime;
        System.out.printf("   [%s] %s %s %s - %d %s (%dms)%n", 
                         timestamp, clientIp, request.getMethod(), request.getPath(),
                         response.getStatusCode(), response.getStatusMessage(), duration);
    }
}