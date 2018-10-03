package com.codingchili.webshoppe.model;

import com.codingchili.webshoppe.model.exception.NoSuchOrderException;
import com.codingchili.webshoppe.model.exception.OrderStoreException;

/**
 * Created by Robin on 2015-10-01.
 *
 */
abstract public class OrderManager {

    /**
     * Creates an order form an existing cart.
     * @param account owning the cart to create the order from.
     * @return indicates whether the order was placed or not.
     */
    public static boolean createOrder(Account account) {
        OrderStore store = Store.getOrderStore();
        boolean created = false;
        try {
            if (CartManager.getCart(account).getItems().size() > 0) {
                store.createOrder(account);
                created = true;
            }
        } catch (OrderStoreException e) {
            e.printStackTrace();
        }
        return created;
    }

    /**
     * Returns orders from a specified account.
     * @param account associated with the orders.
     * @return all orders associated with the account.
     */
    public static OrderList getOrders(Account account) {
        OrderStore store = Store.getOrderStore();
        OrderList orders = new OrderList();
        try {
            orders = store.getOrders(account);
        } catch (OrderStoreException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Get an order by its id.
     * @param account owning the order.
     * @param orderId of the order to retrieve.
     * @return an order matching the criteria.
     */
    public static Order getOrderById(Account account, int orderId) {
        OrderStore store = Store.getOrderStore();
        Order order = new Order();
        try {
            order = store.getOrderById(account, orderId);
        } catch (OrderStoreException e) {
            e.printStackTrace();
        }
        return order;
    }

    /**
     * Clear all orders assigned to an account.
     * @param account owning the orders to be cleared.
     */
    public static void clearOrders(Account account) {
        OrderStore store = Store.getOrderStore();
        try {
            store.clearOrders(account);
        } catch (OrderStoreException ignored) {
        }
    }

    /**
     * @return an order that is ready for shipping with the
     * precondition that the orders products is in stock and
     * no other manager has packed or currently is packing the order.
     * Before returning the order the in-store product count
     * must be decremented.
     */
    public static Order getOrderForShipping() throws NoSuchOrderException {
        OrderStore store = Store.getOrderStore();
        Order order = new Order();
        try {
            order = store.getOrderForShipping();
        } catch (NoSuchOrderException ignored) {
            throw new NoSuchOrderException();
        } catch (OrderStoreException e) {
            e.printStackTrace();
        }
        return order;
    }
}
