package behavioral.chainofresponsibility.approvalsystem;

/**
 * Represents a request that needs to be approved in the system.
 * Each request has an amount, description, and requester information.
 */
public class Request {
    private final double amount;
    private final String description;
    private final String requester;
    private final RequestType type;
    
    public enum RequestType {
        EXPENSE_REIMBURSEMENT,
        BUDGET_ALLOCATION,
        PURCHASE_ORDER,
        SALARY_ADJUSTMENT,
        PROJECT_FUNDING
    }
    
    public Request(double amount, String description, String requester, RequestType type) {
        this.amount = amount;
        this.description = description;
        this.requester = requester;
        this.type = type;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getRequester() {
        return requester;
    }
    
    public RequestType getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return String.format("Request{amount=%.2f, type=%s, description='%s', requester='%s'}", 
                           amount, type, description, requester);
    }
} 