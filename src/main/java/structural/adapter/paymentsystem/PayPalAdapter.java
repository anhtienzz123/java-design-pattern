package structural.adapter.paymentsystem;

// Adapter
public class PayPalAdapter implements PaymentProcessor {
    private PayPalGateway payPalGateway;
    
    public PayPalAdapter(PayPalGateway payPalGateway) {
        this.payPalGateway = payPalGateway;
    }
    
    @Override
    public boolean processPayment(double amount, String currency) {
        String sessionId = payPalGateway.initiatePayment(amount, currency);
        return payPalGateway.executePayment(sessionId);
    }
    
    @Override
    public String getTransactionId() {
        return payPalGateway.getPaymentSessionId();
    }
    
    @Override
    public String getPaymentStatus() {
        return payPalGateway.isPaymentCompleted() ? "COMPLETED" : "PENDING";
    }
}