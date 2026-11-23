package other.callback.orderprocessing;

import java.util.function.Consumer;

/**
 * Callback Pattern demo (success / failure callbacks with Consumer).
 *
 * The OrderService only decides whether an order succeeds. What happens next
 * (send email, show error, ...) is decided by the callbacks the client passes in.
 */
public class ZMain {

    public static void main(String[] args) {
        OrderService service = new OrderService();

        Consumer<Order> sendEmail = order ->
                System.out.println("  [callback] email: order " + order.id() + " confirmed, total $" + order.total());
        Consumer<String> showError = reason ->
                System.out.println("  [callback] error: " + reason);

        System.out.println("--- Order in stock ---");
        service.placeOrder(new Order("ORD-1", "Mouse", 3, 75.0), sendEmail, showError);

        System.out.println("\n--- Order out of stock ---");
        service.placeOrder(new Order("ORD-2", "Laptop", 5, 5000.0), sendEmail, showError);

        System.out.println("\n--- Different callbacks (mobile app) ---");
        service.placeOrder(new Order("ORD-3", "Laptop", 1, 1000.0),
                order -> System.out.println("  [callback] push notification: " + order.product() + " is on its way!"),
                reason -> System.out.println("  [callback] popup: " + reason));
    }
}
