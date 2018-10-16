package com.codingchili.webshoppe.model;

import com.codingchili.webshoppe.model.exception.NoSuchOrderException;
import com.codingchili.webshoppe.model.exception.OrderStoreException;

import java.util.Optional;

/**
 * Created by Robin on 2015-10-01.
 */
abstract public class OrderManager {

    /**
     * Creates an order form an existing cart.
     *
     * @param account owning the cart to create the order from.
     * @return the id of the placed order.
     */
    public static int createOrder(Account account, Cart cart) {
        OrderStore store = Store.getOrderStore();
        if (CartManager.getCart(account).getProducts().size() > 0) {
            return store.createOrder(account, cart);
        } else {
            throw new OrderStoreException("No items in order.");
        }
    }

    /**
     * Updates the status of the given order.
     *
     * @param orderId the order to update the status of.
     * @param status  the status to set.
     */
    public static void updateOrder(int orderId, OrderStatus status) {
        Store.getOrderStore().updateOrderStatus(orderId, status);
    }

    /**
     * Returns orders from a specified account.
     *
     * @param account associated with the orders.
     * @return all orders associated with the account.
     */
    public static OrderList getOrders(Account account) {
        OrderStore store = Store.getOrderStore();
        return store.getOrders(account);
    }

    /**
     * Get an order by its id.
     *
     * @param account owning the order.
     * @param orderId of the order to retrieve.
     * @return an order matching the criteria.
     */
    public static Optional<Order> getOrderById(Account account, int orderId) {
        OrderStore store = Store.getOrderStore();
        return store.getOrderById(account, orderId);
    }

    /**
     * Get an order by its id.
     *
     * @param orderId of the order to retrieve.
     * @return an order matching the criteria.
     */
    public static Optional<Order> getOrderById(int orderId) {
        OrderStore store = Store.getOrderStore();
        Optional<Order> order = store.getOrderById(orderId);

        order.ifPresent(theOrder ->
                theOrder.setAccount(Store.getAccountStore().findById(theOrder.getOwnerId())));

        return order;
    }

    /**
     * Clear all orders assigned to an account.
     *
     * @param account owning the orders to be cleared.
     */
    public static void clearOrders(Account account) {
        OrderStore store = Store.getOrderStore();
        store.clearOrders(account);
    }

    /**
     * @return an order that is ready for shipping with the
     * precondition that the orders products is in stock and
     * no other manager has packed or currently is packing the order.
     * Before returning the order the in-store product count
     * must be decremented.
     */
    public static Optional<Order> getOrderForShipping() throws NoSuchOrderException {
        OrderStore store = Store.getOrderStore();
        Optional<Order> order = store.getOrderForShipping();

        order.ifPresent(o -> o.setAccount(Store.getAccountStore().findById(o.getOwnerId())));

        return order;
    }

    /**
     *
     * @return
     */
    public static OrderStatistics getStatistics() {
        return Store.getOrderStore().getOrderStatistics();
    }
}
