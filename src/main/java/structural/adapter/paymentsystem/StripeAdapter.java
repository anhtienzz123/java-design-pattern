package structural.adapter.paymentsystem;

import java.util.Map;

// Adapter
public class StripeAdapter implements PaymentProcessor {
    private StripeGateway stripeGateway;
    private Map<String, Object> lastChargeResponse;
    
    public StripeAdapter(StripeGateway stripeGateway) {
        this.stripeGateway = stripeGateway;
    }
    
    @Override
    public boolean processPayment(double amount, String currency) {
        int amountInCents = (int) (amount * 100);
        lastChargeResponse = stripeGateway.createCharge(amountInCents, currency);
        return (Boolean) lastChargeResponse.get("paid");
    }
    
    @Override
    public String getTransactionId() {
        return lastChargeResponse != null ? (String) lastChargeResponse.get("id") : null;
    }
    
    @Override
    public String getPaymentStatus() {
        if (lastChargeResponse == null) return "UNKNOWN";
        String status = (String) lastChargeResponse.get("status");
        return status.toUpperCase();
    }
}