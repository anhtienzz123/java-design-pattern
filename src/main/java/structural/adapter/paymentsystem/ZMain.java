package structural.adapter.paymentsystem;

public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== Adapter Pattern - Payment System Demo ===\n");
        
        // Create third-party payment gateways
        PayPalGateway payPalGateway = new PayPalGateway();
        StripeGateway stripeGateway = new StripeGateway();
        
        // Create adapters to make them compatible with our PaymentProcessor interface
        PaymentProcessor payPalAdapter = new PayPalAdapter(payPalGateway);
        PaymentProcessor stripeAdapter = new StripeAdapter(stripeGateway);
        
        // Use the same payment service with different payment gateways
        PaymentService paymentService;
        
        System.out.println("Processing payment via PayPal:");
        paymentService = new PaymentService(payPalAdapter);
        paymentService.processCustomerPayment(99.99, "USD");
        
        System.out.println("Processing payment via Stripe:");
        paymentService = new PaymentService(stripeAdapter);
        paymentService.processCustomerPayment(149.50, "EUR");
        
        System.out.println("Processing another PayPal payment:");
        paymentService = new PaymentService(payPalAdapter);
        paymentService.processCustomerPayment(25.00, "GBP");
    }
}