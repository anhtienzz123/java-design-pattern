package behavioral.chainofresponsibility.approvalsystem;

/**
 * Vice President approval handler - can approve very large requests up to $200,000.
 * This is typically the highest level in the approval chain for most organizations.
 */
public class VicePresidentApprover extends ApprovalHandler {
    private static final double APPROVAL_LIMIT = 200000.0;
    
    public VicePresidentApprover(String handlerName) {
        super(handlerName, "Vice President");
    }
    
    @Override
    protected boolean canApprove(Request request) {
        // VPs can approve all types of requests within their limit
        return request.getAmount() <= APPROVAL_LIMIT;
    }
    
    @Override
    protected void approve(Request request) {
        System.out.println(String.format("[%s - %s] APPROVED: %s", 
                                        title, handlerName, request));
        System.out.println(String.format("🎖️ Approved at Vice President level (limit: $%.2f)", APPROVAL_LIMIT));
        System.out.println("✅ Request has been approved and will be processed.\n");
    }
    
    @Override
    protected void reject(Request request) {
        System.out.println(String.format("❌ REJECTED by %s: %s", title, request));
        System.out.println(String.format("💰 Request amount ($%.2f) exceeds VP approval limit ($%.2f)", 
                                        request.getAmount(), APPROVAL_LIMIT));
        System.out.println("🔴 This request requires Board of Directors approval.\n");
    }
} 