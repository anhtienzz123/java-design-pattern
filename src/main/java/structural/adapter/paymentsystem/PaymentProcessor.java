package structural.adapter.paymentsystem;

// Client Interface
public interface PaymentProcessor {
    boolean processPayment(double amount, String currency);
    
    String getTransactionId();
    
    String getPaymentStatus();
}