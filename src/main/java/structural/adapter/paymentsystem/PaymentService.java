package structural.adapter.paymentsystem;

// Client
public class PaymentService {
    private PaymentProcessor paymentProcessor;
    
    public PaymentService(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }
    
    public void processCustomerPayment(double amount, String currency) {
        System.out.println("Processing payment through Payment Service...");
        
        boolean success = paymentProcessor.processPayment(amount, currency);
        
        if (success) {
            System.out.println("Payment successful!");
            System.out.println("Transaction ID: " + paymentProcessor.getTransactionId());
            System.out.println("Status: " + paymentProcessor.getPaymentStatus());
        } else {
            System.out.println("Payment failed!");
        }
        
        System.out.println("------------------------");
    }
}