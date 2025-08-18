package behavioral.interpreter.authorizationpolicies;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Context class containing security information for policy evaluation.
 * Holds user details, permissions, and request information.
 */
public record SecurityContext(
    String userId,
    String userRole,
    Set<String> permissions,
    String requestedResource,
    String operation,
    LocalDateTime requestTime,
    String clientIpAddress
) {
    
    /**
     * Checks if the user has a specific permission.
     */
    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }
    
    /**
     * Checks if the user has a specific role.
     */
    public boolean hasRole(String role) {
        return userRole.equals(role);
    }
    
    /**
     * Checks if the request is within business hours (9 AM - 6 PM).
     */
    public boolean isWithinBusinessHours() {
        int hour = requestTime.getHour();
        return hour >= 9 && hour < 18;
    }
    
    /**
     * Checks if the request is from an internal IP address.
     */
    public boolean isInternalIP() {
        return clientIpAddress.startsWith("192.168.") || 
               clientIpAddress.startsWith("10.") ||
               clientIpAddress.equals("127.0.0.1");
    }
}