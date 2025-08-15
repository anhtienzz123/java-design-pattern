package structural.adapter.paymentsystem;

import java.util.Map;
import java.util.HashMap;

// Service
public class StripeGateway {
    private String chargeId;
    private String status;
    
    public Map<String, Object> createCharge(int amountInCents, String currency) {
        System.out.println("Stripe: Creating charge for " + amountInCents + " cents in " + currency);
        this.chargeId = "ch_" + System.currentTimeMillis();
        this.status = "succeeded";
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", chargeId);
        response.put("status", status);
        response.put("paid", true);
        return response;
    }
    
    public String getChargeId() {
        return chargeId;
    }
    
    public String getChargeStatus() {
        return status;
    }
}