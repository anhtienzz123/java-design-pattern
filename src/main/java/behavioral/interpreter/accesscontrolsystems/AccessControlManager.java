package behavioral.interpreter.accesscontrolsystems;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

/**
 * Central access control manager that provides high-level access control services.
 * Manages multiple policy evaluators and provides audit logging and caching.
 */
public class AccessControlManager {
    private static final Logger LOGGER = Logger.getLogger(AccessControlManager.class.getName());
    
    private final ConcurrentMap<String, PolicyEvaluator> evaluators;
    private final AccessAuditLog auditLog;
    private final AccessCache accessCache;
    private final boolean cachingEnabled;
    
    public AccessControlManager(boolean enableCaching) {
        this.evaluators = new ConcurrentHashMap<>();
        this.auditLog = new AccessAuditLog();
        this.accessCache = enableCaching ? new AccessCache() : null;
        this.cachingEnabled = enableCaching;
    }
    
    public AccessControlManager() {
        this(false);
    }
    
    /**
     * Registers a new policy evaluator for a specific domain or resource type.
     */
    public void registerEvaluator(String domain, PolicyEvaluator evaluator) {
        evaluators.put(domain, evaluator);
        LOGGER.info("Registered policy evaluator for domain: " + domain);
    }
    
    /**
     * Checks access for a given context using the appropriate evaluator.
     */
    public AccessControlResult checkAccess(String domain, AccessContext context) {
        long startTime = System.currentTimeMillis();
        
        try {
            // Check cache first if enabled
            if (cachingEnabled) {
                AccessControlResult cachedResult = accessCache.get(domain, context);
                if (cachedResult != null) {
                    LOGGER.fine("Cache hit for access check");
                    return cachedResult;
                }
            }
            
            PolicyEvaluator evaluator = evaluators.get(domain);
            if (evaluator == null) {
                AccessControlResult result = AccessControlResult.error(
                    "No evaluator registered for domain: " + domain
                );
                auditLog.logAccess(domain, context, result, System.currentTimeMillis() - startTime);
                return result;
            }
            
            // Evaluate policies
            PolicyEvaluator.PolicyEvaluationResult evaluation = evaluator.evaluate(context);
            AccessControlResult result = new AccessControlResult(
                evaluation.finalResult(),
                evaluation,
                System.currentTimeMillis() - startTime
            );
            
            // Cache result if enabled
            if (cachingEnabled) {
                accessCache.put(domain, context, result);
            }
            
            // Log access attempt
            auditLog.logAccess(domain, context, result, result.evaluationTimeMs());
            
            return result;
            
        } catch (Exception e) {
            LOGGER.severe("Error during access evaluation: " + e.getMessage());
            AccessControlResult result = AccessControlResult.error(
                "Access evaluation failed: " + e.getMessage()
            );
            auditLog.logAccess(domain, context, result, System.currentTimeMillis() - startTime);
            return result;
        }
    }
    
    /**
     * Convenience method for simple access checks.
     */
    public boolean isAccessAllowed(String domain, AccessContext context) {
        return checkAccess(domain, context).isAllowed();
    }
    
    /**
     * Gets the audit log for access monitoring.
     */
    public AccessAuditLog getAuditLog() {
        return auditLog;
    }
    
    /**
     * Clears the access cache if caching is enabled.
     */
    public void clearCache() {
        if (cachingEnabled && accessCache != null) {
            accessCache.clear();
            LOGGER.info("Access cache cleared");
        }
    }
    
    /**
     * Gets statistics about the access control manager.
     */
    public AccessControlStats getStats() {
        return new AccessControlStats(
            evaluators.size(),
            auditLog.getTotalAccessAttempts(),
            auditLog.getSuccessfulAccesses(),
            auditLog.getDeniedAccesses(),
            cachingEnabled ? accessCache.getStats() : null
        );
    }
    
    /**
     * Represents the result of an access control check.
     */
    public static class AccessControlResult {
        private final AccessResult accessResult;
        private final PolicyEvaluator.PolicyEvaluationResult evaluation;
        private final long evaluationTimeMs;
        private final String errorMessage;
        
        public AccessControlResult(AccessResult accessResult, 
                                 PolicyEvaluator.PolicyEvaluationResult evaluation, 
                                 long evaluationTimeMs) {
            this.accessResult = accessResult;
            this.evaluation = evaluation;
            this.evaluationTimeMs = evaluationTimeMs;
            this.errorMessage = null;
        }
        
        private AccessControlResult(String errorMessage) {
            this.accessResult = AccessResult.indeterminate(errorMessage, "AccessControlManager");
            this.evaluation = null;
            this.evaluationTimeMs = 0;
            this.errorMessage = errorMessage;
        }
        
        public static AccessControlResult error(String message) {
            return new AccessControlResult(message);
        }
        
        public boolean isAllowed() {
            return accessResult.granted();
        }
        
        public AccessResult getAccessResult() {
            return accessResult;
        }
        
        public PolicyEvaluator.PolicyEvaluationResult getEvaluation() {
            return evaluation;
        }
        
        public long evaluationTimeMs() {
            return evaluationTimeMs;
        }
        
        public boolean hasError() {
            return errorMessage != null;
        }
        
        public String getErrorMessage() {
            return errorMessage;
        }
        
        @Override
        public String toString() {
            if (hasError()) {
                return "AccessControlResult{ERROR: " + errorMessage + "}";
            }
            return String.format("AccessControlResult{allowed=%s, decision=%s, time=%dms}", 
                isAllowed(), accessResult.decision(), evaluationTimeMs);
        }
    }
    
    /**
     * Statistics about access control operations.
     */
    public record AccessControlStats(
        int registeredEvaluators,
        long totalAccessAttempts,
        long successfulAccesses,
        long deniedAccesses,
        AccessCache.CacheStats cacheStats
    ) {}
}