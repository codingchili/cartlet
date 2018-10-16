package com.codingchili.webshoppe.model;

import com.codingchili.webshoppe.model.exception.NoSuchOrderException;
import com.codingchili.webshoppe.model.exception.OrderStoreException;

/**
 * Created by Robin on 2015-10-01.
 */
interface OrderStore {
    /**
     * Turns an accounts shopping cart into an order.
     *
     * @param account owning the cart to be ordered.
     * @param cart    the items in the cart to create an order of.
     * @throws OrderStoreException when the store failed.
     */
    int createOrder(Account account, Cart cart) throws OrderStoreException;

    /**
     * Retrieve all the orders associated with an account.
     *
     * @param account owner of the orders to be retrieved.
     * @throws OrderStoreException when the store failed.
     */
    OrderList getOrders(Account account) throws OrderStoreException;

    /**
     * Returns an order by its id.
     *
     * @param account account owning the order.
     * @param orderId id of the order to retrieve.
     * @return an order object from the matching order.
     * @throws OrderStoreException when the store has failed.
     */
    Order getOrderById(Account account, int orderId) throws OrderStoreException;

    /**
     * Returns an order by its id.
     *
     * @param orderId id of the order to retrieve.
     * @return an order object from the matching order.
     * @throws OrderStoreException when the store has failed.
     */
    Order getOrderById(int orderId) throws OrderStoreException;

    /**
     * Removes all orders associated to the account.
     *
     * @param account the orders should be removed from.
     */
    void clearOrders(Account account) throws OrderStoreException;

    /**
     * Updates the stock and sets the status of an order that is
     * ready for shipping. Orders ready for shipping must have
     * enough stock for every product ordered. The stock count
     * is deducted and the order is set to packed when the order
     * is fetched.
     *
     * @return An Order ready for shipping.
     */
    Order getOrderForShipping() throws OrderStoreException, NoSuchOrderException;

    /**
     * Updates the status of the given order.
     *
     * @param orderId the order to update the status of.
     * @param status  the status to set.
     */
    void updateOrderStatus(int orderId, OrderStatus status);
}
