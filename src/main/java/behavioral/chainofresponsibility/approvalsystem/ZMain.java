package behavioral.chainofresponsibility.approvalsystem;

/**
 * Demonstration of the Chain of Responsibility pattern using an approval system.
 * This example shows how different levels of management can approve requests
 * based on their authority limits and request types.
 */
public class ZMain {
    
    public static void main(String[] args) {
        System.out.println("=== Chain of Responsibility Pattern: Approval System Demo ===\n");
        
        // Create the chain of approval handlers
        ApprovalHandler approvalChain = createApprovalChain();
        
        // Test various requests
        testApprovalRequests(approvalChain);
    }
    
    /**
     * Creates and configures the approval chain.
     * Chain: Team Leader -> Manager -> Director -> Vice President
     */
    private static ApprovalHandler createApprovalChain() {
        System.out.println("🔗 Setting up approval chain:");
        System.out.println("   Team Leader ($1,000) -> Manager ($10,000) -> Director ($50,000) -> VP ($200,000)\n");
        
        // Create handlers
        ApprovalHandler teamLeader = new TeamLeaderApprover("Alice Johnson");
        ApprovalHandler manager = new ManagerApprover("Bob Smith");
        ApprovalHandler director = new DirectorApprover("Carol Davis");
        ApprovalHandler vicePresident = new VicePresidentApprover("David Wilson");
        
        // Build the chain
        teamLeader.setNext(manager)
                  .setNext(director)
                  .setNext(vicePresident);
        
        return teamLeader;
    }
    
    /**
     * Tests the approval system with various request scenarios.
     */
    private static void testApprovalRequests(ApprovalHandler approvalChain) {
        Request[] testRequests = {
            // Small expense - should be approved by Team Leader
            new Request(750.0, "Conference attendance fee", "John Doe", 
                       Request.RequestType.EXPENSE_REIMBURSEMENT),
            
            // Medium purchase - should be approved by Manager
            new Request(5000.0, "New development server", "Jane Smith", 
                       Request.RequestType.PURCHASE_ORDER),
            
            // Large budget allocation - should be approved by Director
            new Request(25000.0, "Q4 marketing campaign budget", "Mike Johnson", 
                       Request.RequestType.BUDGET_ALLOCATION),
            
            // Major project funding - should be approved by VP
            new Request(150000.0, "New product development initiative", "Sarah Wilson", 
                       Request.RequestType.PROJECT_FUNDING),
            
            // Salary adjustment - should be approved by Manager
            new Request(8000.0, "Senior developer promotion salary increase", "HR Department", 
                       Request.RequestType.SALARY_ADJUSTMENT),
            
            // Very expensive request - should be rejected (exceeds VP limit)
            new Request(350000.0, "Acquisition of startup company", "Strategy Team", 
                       Request.RequestType.PROJECT_FUNDING),
            
            // Small request with wrong type for Team Leader - should go to Manager
            new Request(500.0, "Team building budget", "Team Lead", 
                       Request.RequestType.BUDGET_ALLOCATION)
        };
        
        // Process each request through the chain
        for (int i = 0; i < testRequests.length; i++) {
            System.out.println(String.format("📋 Processing Request #%d:", i + 1));
            approvalChain.handleRequest(testRequests[i]);
            
            // Add separator between requests
            if (i < testRequests.length - 1) {
                System.out.println("================================================================================\n");
            }
        }
        
        System.out.println("=== Approval System Demo Complete ===");
    }
} 