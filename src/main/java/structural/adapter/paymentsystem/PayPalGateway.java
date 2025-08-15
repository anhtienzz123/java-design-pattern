package structural.adapter.paymentsystem;

// Service
public class PayPalGateway {
    private String sessionId;
    private boolean paymentCompleted;
    
    public String initiatePayment(double totalAmount, String currencyCode) {
        System.out.println("PayPal: Initiating payment of " + totalAmount + " " + currencyCode);
        this.sessionId = "PP_" + System.currentTimeMillis();
        return sessionId;
    }
    
    public boolean executePayment(String sessionId) {
        System.out.println("PayPal: Executing payment with session " + sessionId);
        this.paymentCompleted = true;
        return true;
    }
    
    public String getPaymentSessionId() {
        return sessionId;
    }
    
    public boolean isPaymentCompleted() {
        return paymentCompleted;
    }
}