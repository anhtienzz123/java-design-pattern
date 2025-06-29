package behavioral.chainofresponsibility.approvalsystem;

/**
 * Director approval handler - can approve large requests up to $50,000.
 * Handles major budget allocations, project funding, and significant salary adjustments.
 */
public class DirectorApprover extends ApprovalHandler {
    private static final double APPROVAL_LIMIT = 50000.0;
    
    public DirectorApprover(String handlerName) {
        super(handlerName, "Director");
    }
    
    @Override
    protected boolean canApprove(Request request) {
        // Directors can approve all types of requests within their limit
        return request.getAmount() <= APPROVAL_LIMIT;
    }
    
    @Override
    protected void approve(Request request) {
        System.out.println(String.format("[%s - %s] APPROVED: %s", 
                                        title, handlerName, request));
        System.out.println(String.format("🏢 Approved at Director level (limit: $%.2f)", APPROVAL_LIMIT));
        System.out.println("✅ Request has been approved and will be processed.\n");
    }
}