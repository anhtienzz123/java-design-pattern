package behavioral.nullobject.customerservicelogger;

public class CustomerServiceSystem {
    private final Logger logger;
    
    public CustomerServiceSystem(Logger logger) {
        // No null check needed - we always have a logger (even if it's NullLogger)
        this.logger = logger;
    }
    
    public void handleCustomerRequest(String customerName, String request) {
        logger.log("Processing request from customer: " + customerName);
        
        // Simulate processing logic
        if (request.toLowerCase().contains("urgent")) {
            logger.logWarning("Urgent request detected for customer: " + customerName);
            escalateToSupervisor(customerName, request);
        } else if (request.toLowerCase().contains("complaint")) {
            logger.logError("Complaint received from customer: " + customerName);
            handleComplaint(customerName, request);
        } else {
            handleStandardRequest(customerName, request);
        }
        
        logger.log("Request completed for customer: " + customerName);
    }
    
    private void escalateToSupervisor(String customerName, String request) {
        logger.log("Escalating to supervisor - Customer: " + customerName);
        // Escalation logic here
    }
    
    private void handleComplaint(String customerName, String request) {
        logger.log("Processing complaint - Customer: " + customerName);
        // Complaint handling logic here
    }
    
    private void handleStandardRequest(String customerName, String request) {
        logger.log("Processing standard request - Customer: " + customerName);
        // Standard request handling logic here
    }
    
    public void generateDailyReport() {
        if (logger.isEnabled()) {
            logger.log("Generating daily customer service report");
            // Report generation logic
            logger.log("Daily report generated successfully");
        }
    }
}