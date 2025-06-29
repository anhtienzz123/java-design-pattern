package behavioral.chainofresponsibility.approvalsystem;

/**
 * Abstract base class for approval handlers in the chain of responsibility.
 * Each handler can either approve a request or pass it to the next handler in the chain.
 */
public abstract class ApprovalHandler {
    protected ApprovalHandler nextHandler;
    protected String handlerName;
    protected String title;
    
    public ApprovalHandler(String handlerName, String title) {
        this.handlerName = handlerName;
        this.title = title;
    }
    
    /**
     * Sets the next handler in the chain.
     */
    public ApprovalHandler setNext(ApprovalHandler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }
    
    /**
     * Handles the approval request. This method defines the chain logic.
     */
    public final void handleRequest(Request request) {
        if (canApprove(request)) {
            approve(request);
        } else if (nextHandler != null) {
            System.out.println(String.format("[%s] Cannot approve request of $%.2f - forwarding to next level", 
                                            title, request.getAmount()));
            nextHandler.handleRequest(request);
        } else {
            reject(request);
        }
    }
    
    /**
     * Determines if this handler can approve the given request.
     */
    protected abstract boolean canApprove(Request request);
    
    /**
     * Approves the request.
     */
    protected void approve(Request request) {
        System.out.println(String.format("[%s - %s] APPROVED: %s", 
                                        title, handlerName, request));
        System.out.println("✅ Request has been approved and will be processed.\n");
    }
    
    /**
     * Rejects the request when no handler in the chain can approve it.
     */
    protected void reject(Request request) {
        System.out.println(String.format("❌ REJECTED: %s", request));
        System.out.println("No handler in the chain can approve this request.\n");
    }
} 