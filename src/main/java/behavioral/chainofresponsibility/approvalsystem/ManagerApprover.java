package behavioral.chainofresponsibility.approvalsystem;

/**
 * Manager approval handler - can approve medium requests up to $10,000.
 * Handles budget allocations, larger purchase orders, and some salary adjustments.
 */
public class ManagerApprover extends ApprovalHandler {
    private static final double APPROVAL_LIMIT = 10000.0;
    
    public ManagerApprover(String handlerName) {
        super(handlerName, "Manager");
    }
    
    @Override
    protected boolean canApprove(Request request) {
        // Managers can approve most types except project funding
        return request.getAmount() <= APPROVAL_LIMIT && 
               request.getType() != Request.RequestType.PROJECT_FUNDING;
    }
    
    @Override
    protected void approve(Request request) {
        System.out.println(String.format("[%s - %s] APPROVED: %s", 
                                        title, handlerName, request));
        System.out.println(String.format("💼 Approved at Manager level (limit: $%.2f)", APPROVAL_LIMIT));
        System.out.println("✅ Request has been approved and will be processed.\n");
    }
} 