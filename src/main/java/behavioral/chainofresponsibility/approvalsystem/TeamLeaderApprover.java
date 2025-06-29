package behavioral.chainofresponsibility.approvalsystem;

/**
 * Team Leader approval handler - can approve small requests up to $1,000.
 * Typically handles expense reimbursements and small purchase orders.
 */
public class TeamLeaderApprover extends ApprovalHandler {
    private static final double APPROVAL_LIMIT = 1000.0;
    
    public TeamLeaderApprover(String handlerName) {
        super(handlerName, "Team Leader");
    }
    
    @Override
    protected boolean canApprove(Request request) {
        // Team leaders can approve expense reimbursements and small purchase orders
        return request.getAmount() <= APPROVAL_LIMIT && 
               (request.getType() == Request.RequestType.EXPENSE_REIMBURSEMENT ||
                request.getType() == Request.RequestType.PURCHASE_ORDER);
    }
    
    @Override
    protected void approve(Request request) {
        System.out.println(String.format("[%s - %s] APPROVED: %s", 
                                        title, handlerName, request));
        System.out.println(String.format("💡 Approved at Team Leader level (limit: $%.2f)", APPROVAL_LIMIT));
        System.out.println("✅ Request has been approved and will be processed.\n");
    }
} 