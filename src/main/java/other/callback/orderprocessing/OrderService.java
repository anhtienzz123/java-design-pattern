package other.callback.orderprocessing;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Places orders and reports the result through two callbacks.
 * Uses Java's built-in {@link Consumer} instead of a custom callback interface.
 */
public class OrderService {

    private final Map<String, Integer> stock = Map.of("Laptop", 2, "Mouse", 10);

    public void placeOrder(Order order, Consumer<Order> onSuccess, Consumer<String> onFailure) {
        System.out.println("  [service] processing " + order.id() + ": "
                + order.quantity() + " x " + order.product());

        int available = stock.getOrDefault(order.product(), 0);
        if (order.quantity() > available) {
            onFailure.accept(order.id() + " - only " + available + " " + order.product() + " left");
            return;
        }
        // ... charge payment, reserve stock ...
        onSuccess.accept(order);
    }
}
