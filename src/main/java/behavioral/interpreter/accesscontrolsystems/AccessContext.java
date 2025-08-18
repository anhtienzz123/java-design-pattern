package behavioral.interpreter.accesscontrolsystems;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * Context class containing all information needed for access control evaluation.
 * Follows the Subject-Object-Action-Environment model.
 */
public record AccessContext(
    Subject subject,
    AccessObject object,
    Action action,
    Environment environment
) {
    
    /**
     * Represents the subject (user/entity) requesting access.
     */
    public record Subject(
        String id,
        String name,
        Set<String> roles,
        Set<String> groups,
        Map<String, String> attributes,
        int clearanceLevel
    ) {
        public boolean hasRole(String role) {
            return roles.contains(role);
        }
        
        public boolean belongsToGroup(String group) {
            return groups.contains(group);
        }
        
        public String getAttribute(String key) {
            return attributes.get(key);
        }
        
        public boolean hasClearanceLevel(int requiredLevel) {
            return clearanceLevel >= requiredLevel;
        }
    }
    
    /**
     * Represents the object/resource being accessed.
     */
    public record AccessObject(
        String id,
        String type,
        String path,
        String owner,
        Set<String> tags,
        Map<String, String> properties,
        int classificationLevel
    ) {
        public boolean hasTag(String tag) {
            return tags.contains(tag);
        }
        
        public String getProperty(String key) {
            return properties.get(key);
        }
        
        public boolean isOwnedBy(String subjectId) {
            return owner.equals(subjectId);
        }
        
        public boolean requiresClearanceLevel(int level) {
            return classificationLevel >= level;
        }
    }
    
    /**
     * Represents the action being performed.
     */
    public record Action(
        String type,
        String verb,
        Map<String, String> parameters
    ) {
        public String getParameter(String key) {
            return parameters.get(key);
        }
        
        public boolean isReadOperation() {
            return "read".equalsIgnoreCase(verb) || "view".equalsIgnoreCase(verb) || "get".equalsIgnoreCase(verb);
        }
        
        public boolean isWriteOperation() {
            return "write".equalsIgnoreCase(verb) || "update".equalsIgnoreCase(verb) || "modify".equalsIgnoreCase(verb);
        }
        
        public boolean isDeleteOperation() {
            return "delete".equalsIgnoreCase(verb) || "remove".equalsIgnoreCase(verb);
        }
    }
    
    /**
     * Represents the environment context.
     */
    public record Environment(
        LocalDateTime requestTime,
        String clientIp,
        String userAgent,
        String location,
        Map<String, String> contextAttributes
    ) {
        public String getContextAttribute(String key) {
            return contextAttributes.get(key);
        }
        
        public boolean isWithinBusinessHours() {
            int hour = requestTime.getHour();
            int dayOfWeek = requestTime.getDayOfWeek().getValue();
            return (dayOfWeek >= 1 && dayOfWeek <= 5) && (hour >= 9 && hour < 18);
        }
        
        public boolean isWeekend() {
            int dayOfWeek = requestTime.getDayOfWeek().getValue();
            return dayOfWeek == 6 || dayOfWeek == 7; // Saturday or Sunday
        }
        
        public boolean isFromTrustedNetwork() {
            return clientIp.startsWith("192.168.") || 
                   clientIp.startsWith("10.") ||
                   clientIp.startsWith("172.16.");
        }
    }
}