package behavioral.interpreter.accesscontrolsystems;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * Demonstration of the Interpreter pattern for Access Control Systems.
 * 
 * This example shows how to use the Interpreter pattern to build a comprehensive
 * Attribute-Based Access Control (ABAC) system with policy evaluation, caching,
 * and audit logging.
 */
public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== Access Control Systems using Interpreter Pattern ===\n");
        
        // Create access control manager with caching enabled
        AccessControlManager accessManager = new AccessControlManager(true);
        
        // Set up different policy evaluators for different domains
        setupDocumentManagementPolicies(accessManager);
        setupFinancialSystemPolicies(accessManager);
        setupHRSystemPolicies(accessManager);
        
        // Test various access scenarios
        testDocumentAccess(accessManager);
        testFinancialAccess(accessManager);
        testHRAccess(accessManager);
        testConditionalAccess(accessManager);
        
        // Show audit log and statistics
        showAuditAndStats(accessManager);
    }
    
    private static void setupDocumentManagementPolicies(AccessControlManager accessManager) {
        System.out.println("=== Setting up Document Management Policies ===");
        
        PolicyEvaluator docEvaluator = new PolicyEvaluator(PolicyEvaluator.PolicyCombiningAlgorithm.DENY_OVERRIDES);
        
        // Admin can access everything
        docEvaluator.addPolicy("Admin Full Access", 
            "subject.role = admin", 
            PolicyEvaluator.PolicyEffect.PERMIT);
        
        // Users can read their own documents
        docEvaluator.addPolicy("Owner Read Access", 
            "object.owner = $self AND action.category = read", 
            PolicyEvaluator.PolicyEffect.PERMIT);
        
        // Managers can read documents in their department
        docEvaluator.addPolicy("Manager Department Access", 
            "subject.role = manager AND object.property = department=$dept AND action.category = read", 
            PolicyEvaluator.PolicyEffect.PERMIT);
        
        // Confidential documents require high clearance
        docEvaluator.addPolicy("Confidential Access Control", 
            "object.tag = confidential AND subject.clearance < object.classification", 
            PolicyEvaluator.PolicyEffect.DENY);
        
        // External access restrictions
        docEvaluator.addPolicy("External Access Restriction", 
            "env.network = untrusted AND object.tag = internal", 
            PolicyEvaluator.PolicyEffect.DENY);
        
        accessManager.registerEvaluator("documents", docEvaluator);
        
        for (PolicyEvaluator.AccessPolicy policy : docEvaluator.getPolicies()) {
            System.out.println("- " + policy.name() + " (" + policy.effect() + "): " + policy.rule());
        }
        System.out.println();
    }
    
    private static void setupFinancialSystemPolicies(AccessControlManager accessManager) {
        System.out.println("=== Setting up Financial System Policies ===");
        
        PolicyEvaluator finEvaluator = new PolicyEvaluator(PolicyEvaluator.PolicyCombiningAlgorithm.PERMIT_OVERRIDES);
        
        // Financial analysts can read reports during business hours
        finEvaluator.addPolicy("Analyst Report Access", 
            "subject.role = financial_analyst AND action.verb = read AND env.time = business_hours", 
            PolicyEvaluator.PolicyEffect.PERMIT);
        
        // CFO has full access
        finEvaluator.addPolicy("CFO Full Access", 
            "subject.role = cfo", 
            PolicyEvaluator.PolicyEffect.PERMIT);
        
        // High-value transactions require senior approval
        finEvaluator.addPolicy("High Value Transaction Control", 
            "object.type = transaction AND object.property = amount>100000 AND subject.role != senior_manager", 
            PolicyEvaluator.PolicyEffect.DENY);
        
        // Weekend restrictions for modifications
        finEvaluator.addPolicy("Weekend Modification Restriction", 
            "env.time = weekend AND action.category = write", 
            PolicyEvaluator.PolicyEffect.DENY);
        
        accessManager.registerEvaluator("financial", finEvaluator);
        
        for (PolicyEvaluator.AccessPolicy policy : finEvaluator.getPolicies()) {
            System.out.println("- " + policy.name() + " (" + policy.effect() + "): " + policy.rule());
        }
        System.out.println();
    }
    
    private static void setupHRSystemPolicies(AccessControlManager accessManager) {
        System.out.println("=== Setting up HR System Policies ===");
        
        PolicyEvaluator hrEvaluator = new PolicyEvaluator(PolicyEvaluator.PolicyCombiningAlgorithm.FIRST_APPLICABLE);
        
        // HR can access employee records
        hrEvaluator.addPolicy("HR Employee Access", 
            "subject.group = hr_department AND object.type = employee_record", 
            PolicyEvaluator.PolicyEffect.PERMIT);
        
        // Managers can access their direct reports
        hrEvaluator.addPolicy("Manager Direct Report Access", 
            "subject.role = manager AND object.property = manager_id=$id", 
            PolicyEvaluator.PolicyEffect.PERMIT);
        
        // Salary information requires special permission
        hrEvaluator.addPolicy("Salary Information Control", 
            "object.tag = salary AND NOT subject.attribute = clearance=payroll", 
            PolicyEvaluator.PolicyEffect.DENY);
        
        // External contractors cannot access HR data
        hrEvaluator.addPolicy("Contractor Restriction", 
            "subject.role = contractor", 
            PolicyEvaluator.PolicyEffect.DENY);
        
        accessManager.registerEvaluator("hr", hrEvaluator);
        
        for (PolicyEvaluator.AccessPolicy policy : hrEvaluator.getPolicies()) {
            System.out.println("- " + policy.name() + " (" + policy.effect() + "): " + policy.rule());
        }
        System.out.println();
    }
    
    private static void testDocumentAccess(AccessControlManager accessManager) {
        System.out.println("=== Testing Document Access ===");
        
        // Admin accessing any document
        testAccess(accessManager, "documents", createAdminContext());
        
        // User accessing own document
        testAccess(accessManager, "documents", createUserOwnDocumentContext());
        
        // User trying to access confidential document without clearance
        testAccess(accessManager, "documents", createUserConfidentialDocumentContext());
        
        // External user trying to access internal document
        testAccess(accessManager, "documents", createExternalUserContext());
        
        System.out.println();
    }
    
    private static void testFinancialAccess(AccessControlManager accessManager) {
        System.out.println("=== Testing Financial Access ===");
        
        // Financial analyst accessing report during business hours
        testAccess(accessManager, "financial", createAnalystContext());
        
        // CFO accessing high-value transaction
        testAccess(accessManager, "financial", createCFOContext());
        
        // Regular user trying to access high-value transaction
        testAccess(accessManager, "financial", createUserHighValueTransactionContext());
        
        // Weekend modification attempt
        testAccess(accessManager, "financial", createWeekendModificationContext());
        
        System.out.println();
    }
    
    private static void testHRAccess(AccessControlManager accessManager) {
        System.out.println("=== Testing HR Access ===");
        
        // HR accessing employee record
        testAccess(accessManager, "hr", createHRContext());
        
        // Manager accessing direct report
        testAccess(accessManager, "hr", createManagerDirectReportContext());
        
        // Regular user trying to access salary information
        testAccess(accessManager, "hr", createUserSalaryAccessContext());
        
        // Contractor trying to access HR data
        testAccess(accessManager, "hr", createContractorContext());
        
        System.out.println();
    }
    
    private static void testConditionalAccess(AccessControlManager accessManager) {
        System.out.println("=== Testing Conditional Access Policies ===");
        
        PolicyEvaluator conditionalEvaluator = new PolicyEvaluator(PolicyEvaluator.PolicyCombiningAlgorithm.DENY_OVERRIDES);
        
        // Conditional policy: IF user is manager THEN allow read OR write, ELSE deny
        conditionalEvaluator.addPolicy("Manager Conditional Access",
            "IF subject.role = manager THEN action.category = read OR action.category = write",
            PolicyEvaluator.PolicyEffect.PERMIT);
        
        accessManager.registerEvaluator("conditional", conditionalEvaluator);
        
        // Test manager access
        AccessContext managerContext = new AccessContext(
            new AccessContext.Subject("mgr001", "Manager Smith", Set.of("manager"), Set.of("management"), 
                Map.of("department", "sales"), 5),
            new AccessContext.AccessObject("doc001", "document", "/docs/report.pdf", "system", 
                Set.of("business"), Map.of(), 2),
            new AccessContext.Action("document", "read", Map.of()),
            new AccessContext.Environment(LocalDateTime.of(2024, 1, 15, 14, 0), 
                "192.168.1.100", "Chrome", "Office", Map.of())
        );
        
        testAccess(accessManager, "conditional", managerContext);
        
        // Test non-manager access
        AccessContext userContext = new AccessContext(
            new AccessContext.Subject("user001", "User Smith", Set.of("employee"), Set.of("staff"), 
                Map.of("department", "sales"), 2),
            new AccessContext.AccessObject("doc001", "document", "/docs/report.pdf", "system", 
                Set.of("business"), Map.of(), 2),
            new AccessContext.Action("document", "read", Map.of()),
            new AccessContext.Environment(LocalDateTime.of(2024, 1, 15, 14, 0), 
                "192.168.1.100", "Chrome", "Office", Map.of())
        );
        
        testAccess(accessManager, "conditional", userContext);
        System.out.println();
    }
    
    private static void testAccess(AccessControlManager accessManager, String domain, AccessContext context) {
        AccessControlManager.AccessControlResult result = accessManager.checkAccess(domain, context);
        
        System.out.printf("Access Check: %s@%s -> %s:%s [%s] - %s (%dms)%n",
            context.subject().name(),
            context.environment().clientIp(),
            context.object().type(),
            context.object().id(),
            context.action().verb(),
            result.isAllowed() ? "GRANTED" : "DENIED",
            result.evaluationTimeMs());
        
        if (result.getEvaluation() != null) {
            System.out.println("  Reason: " + result.getAccessResult().reason());
        }
        
        if (result.hasError()) {
            System.out.println("  Error: " + result.getErrorMessage());
        }
    }
    
    private static void showAuditAndStats(AccessControlManager accessManager) {
        System.out.println("=== Access Control Statistics ===");
        AccessControlManager.AccessControlStats stats = accessManager.getStats();
        System.out.println("Total Evaluators: " + stats.registeredEvaluators());
        System.out.println("Total Access Attempts: " + stats.totalAccessAttempts());
        System.out.println("Successful Accesses: " + stats.successfulAccesses());
        System.out.println("Denied Accesses: " + stats.deniedAccesses());
        
        if (stats.cacheStats() != null) {
            System.out.println("Cache Stats: " + stats.cacheStats());
        }
        
        System.out.println();
        accessManager.getAuditLog().printRecentEntries(10);
    }
    
    // Context creation helper methods
    private static AccessContext createAdminContext() {
        return new AccessContext(
            new AccessContext.Subject("admin001", "Admin User", Set.of("admin"), Set.of("administrators"), 
                Map.of("clearance", "10"), 10),
            new AccessContext.AccessObject("doc001", "document", "/docs/confidential.pdf", "user001", 
                Set.of("confidential", "internal"), Map.of("department", "finance"), 8),
            new AccessContext.Action("document", "read", Map.of()),
            new AccessContext.Environment(LocalDateTime.of(2024, 1, 15, 14, 0), 
                "192.168.1.50", "Chrome", "Office", Map.of())
        );
    }
    
    private static AccessContext createUserOwnDocumentContext() {
        return new AccessContext(
            new AccessContext.Subject("user001", "Regular User", Set.of("employee"), Set.of("staff"), 
                Map.of("department", "sales"), 3),
            new AccessContext.AccessObject("doc002", "document", "/docs/mydoc.pdf", "user001", 
                Set.of("personal"), Map.of("department", "sales"), 2),
            new AccessContext.Action("document", "read", Map.of()),
            new AccessContext.Environment(LocalDateTime.of(2024, 1, 15, 14, 0), 
                "192.168.1.100", "Chrome", "Office", Map.of())
        );
    }
    
    private static AccessContext createUserConfidentialDocumentContext() {
        return new AccessContext(
            new AccessContext.Subject("user002", "Low Clearance User", Set.of("employee"), Set.of("staff"), 
                Map.of("department", "sales"), 2),
            new AccessContext.AccessObject("doc003", "document", "/docs/confidential.pdf", "admin001", 
                Set.of("confidential", "internal"), Map.of("department", "finance"), 8),
            new AccessContext.Action("document", "read", Map.of()),
            new AccessContext.Environment(LocalDateTime.of(2024, 1, 15, 14, 0), 
                "192.168.1.100", "Chrome", "Office", Map.of())
        );
    }
    
    private static AccessContext createExternalUserContext() {
        return new AccessContext(
            new AccessContext.Subject("ext001", "External User", Set.of("guest"), Set.of("external"), 
                Map.of(), 1),
            new AccessContext.AccessObject("doc004", "document", "/docs/internal.pdf", "system", 
                Set.of("internal"), Map.of(), 3),
            new AccessContext.Action("document", "read", Map.of()),
            new AccessContext.Environment(LocalDateTime.of(2024, 1, 15, 14, 0), 
                "203.0.113.45", "Chrome", "Remote", Map.of())
        );
    }
    
    private static AccessContext createAnalystContext() {
        return new AccessContext(
            new AccessContext.Subject("analyst001", "Financial Analyst", Set.of("financial_analyst"), 
                Set.of("finance"), Map.of("department", "finance"), 4),
            new AccessContext.AccessObject("report001", "financial_report", "/reports/quarterly.pdf", "system", 
                Set.of("financial"), Map.of("quarter", "Q1"), 3),
            new AccessContext.Action("report", "read", Map.of()),
            new AccessContext.Environment(LocalDateTime.of(2024, 1, 15, 14, 0), 
                "192.168.1.200", "Chrome", "Office", Map.of())
        );
    }
    
    private static AccessContext createCFOContext() {
        return new AccessContext(
            new AccessContext.Subject("cfo001", "CFO", Set.of("cfo", "executive"), Set.of("executives"), 
                Map.of("department", "finance"), 10),
            new AccessContext.AccessObject("txn001", "transaction", "/transactions/large", "system", 
                Set.of("financial"), Map.of("amount", "500000"), 5),
            new AccessContext.Action("transaction", "approve", Map.of()),
            new AccessContext.Environment(LocalDateTime.of(2024, 1, 15, 14, 0), 
                "192.168.1.150", "Chrome", "Office", Map.of())
        );
    }
    
    private static AccessContext createUserHighValueTransactionContext() {
        return new AccessContext(
            new AccessContext.Subject("user003", "Regular Employee", Set.of("employee"), Set.of("staff"), 
                Map.of("department", "operations"), 3),
            new AccessContext.AccessObject("txn002", "transaction", "/transactions/large", "system", 
                Set.of("financial"), Map.of("amount", "250000"), 5),
            new AccessContext.Action("transaction", "modify", Map.of()),
            new AccessContext.Environment(LocalDateTime.of(2024, 1, 15, 14, 0), 
                "192.168.1.180", "Chrome", "Office", Map.of())
        );
    }
    
    private static AccessContext createWeekendModificationContext() {
        return new AccessContext(
            new AccessContext.Subject("analyst002", "Financial Analyst", Set.of("financial_analyst"), 
                Set.of("finance"), Map.of("department", "finance"), 4),
            new AccessContext.AccessObject("report002", "financial_report", "/reports/monthly.pdf", "system", 
                Set.of("financial"), Map.of(), 3),
            new AccessContext.Action("report", "update", Map.of()),
            new AccessContext.Environment(LocalDateTime.of(2024, 1, 13, 14, 0), // Saturday
                "192.168.1.200", "Chrome", "Office", Map.of())
        );
    }
    
    private static AccessContext createHRContext() {
        return new AccessContext(
            new AccessContext.Subject("hr001", "HR Manager", Set.of("hr_manager"), Set.of("hr_department"), 
                Map.of("department", "hr"), 6),
            new AccessContext.AccessObject("emp001", "employee_record", "/hr/employees/john_doe", "system", 
                Set.of("hr_data"), Map.of("employee_id", "12345"), 4),
            new AccessContext.Action("record", "view", Map.of()),
            new AccessContext.Environment(LocalDateTime.of(2024, 1, 15, 14, 0), 
                "192.168.1.220", "Chrome", "Office", Map.of())
        );
    }
    
    private static AccessContext createManagerDirectReportContext() {
        return new AccessContext(
            new AccessContext.Subject("mgr002", "Department Manager", Set.of("manager"), Set.of("management"), 
                Map.of("id", "mgr002", "department", "engineering"), 5),
            new AccessContext.AccessObject("emp002", "employee_record", "/hr/employees/jane_smith", "system", 
                Set.of("hr_data"), Map.of("manager_id", "mgr002"), 4),
            new AccessContext.Action("record", "view", Map.of()),
            new AccessContext.Environment(LocalDateTime.of(2024, 1, 15, 14, 0), 
                "192.168.1.230", "Chrome", "Office", Map.of())
        );
    }
    
    private static AccessContext createUserSalaryAccessContext() {
        return new AccessContext(
            new AccessContext.Subject("user004", "Regular Employee", Set.of("employee"), Set.of("staff"), 
                Map.of("department", "marketing"), 3),
            new AccessContext.AccessObject("salary001", "salary_info", "/hr/salary/jane_smith", "system", 
                Set.of("salary", "confidential"), Map.of(), 7),
            new AccessContext.Action("info", "read", Map.of()),
            new AccessContext.Environment(LocalDateTime.of(2024, 1, 15, 14, 0), 
                "192.168.1.240", "Chrome", "Office", Map.of())
        );
    }
    
    private static AccessContext createContractorContext() {
        return new AccessContext(
            new AccessContext.Subject("cont001", "External Contractor", Set.of("contractor"), Set.of("external"), 
                Map.of("company", "TechCorp"), 2),
            new AccessContext.AccessObject("emp003", "employee_record", "/hr/employees/all", "system", 
                Set.of("hr_data"), Map.of(), 4),
            new AccessContext.Action("record", "list", Map.of()),
            new AccessContext.Environment(LocalDateTime.of(2024, 1, 15, 14, 0), 
                "203.0.113.100", "Chrome", "Remote", Map.of())
        );
    }
}