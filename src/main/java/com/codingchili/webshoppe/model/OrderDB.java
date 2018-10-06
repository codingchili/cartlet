package com.codingchili.webshoppe.model;

import com.codingchili.webshoppe.model.exception.AccountStoreException;
import com.codingchili.webshoppe.model.exception.NoSuchOrderException;
import com.codingchili.webshoppe.model.exception.OrderStoreException;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Robin on 2015-10-01.
 * <p>
 * Implementation of the OrderStore using MySQL for storage.
 */

class OrderDB implements OrderStore {
    @Override
    public int createOrder(Account account) throws OrderStoreException {
        try {
            return Database.prepared(OrderTable.CreateOrder.QUERY, (connection, create) -> {
                connection.setAutoCommit(false);
                int orderId = -1;

                // Create the order post in order table.
                create.setString(OrderTable.CreateOrder.IN.CREATED, getTimeStamp());
                create.setString(OrderTable.CreateOrder.IN.CHANGED, getTimeStamp());
                create.setInt(OrderTable.CreateOrder.IN.OWNER, account.getId());
                create.execute();

                ResultSet result = create.getGeneratedKeys();

                if (result.next()) {
                    orderId = result.getInt(OrderTable.CreateOrder.OUT.ORDER_ID);
                }

                // Move all objects from the users cart to the order_product table.
                try (PreparedStatement move =
                             connection.prepareStatement(OrderTable.CopyFromCart.QUERY)) {
                    move.setInt(OrderTable.CopyFromCart.IN.ORDER, orderId);
                    move.setInt(OrderTable.CopyFromCart.IN.OWNER, account.getId());
                    move.execute();
                }
                connection.commit();
                return orderId;
            });
        } catch (SQLException e) {
            throw new OrderStoreException(e);
        }
    }

    @Override
    public OrderList getOrders(Account account) throws OrderStoreException {
        List<Order> orders = new ArrayList<>();
        OrderList list = new OrderList();
        try {
            Database.prepared(OrderTable.GetOrders.QUERY, (connection, statement) -> {
                statement.setInt(OrderTable.GetOrders.IN.OWNER, account.getId());
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    Order order = orderFromResult(result);
                    order.setProducts(getOrderItems(result.getInt(OrderTable.GetOrder.OUT.ORDER_ID)));
                    orders.add(order);
                }
                return orders;
            });
        } catch (SQLException e) {
            throw new OrderStoreException(e);
        }
        list.setItems(orders);
        return list;
    }

    private Order orderFromResult(ResultSet result) throws SQLException {
        Order order = new Order();
        order.setOrderId(result.getInt(OrderTable.GetOrder.OUT.ORDER_ID));
        order.setCreated(result.getString(OrderTable.GetOrder.OUT.CREATED));
        order.setChanged(result.getString(OrderTable.GetOrder.OUT.CHANGED));
        order.setStatus(result.getString(OrderTable.GetOrder.OUT.STATUS));
        return order;
    }

    @Override
    public Order getOrderById(Account account, int orderId) throws OrderStoreException {
        try {
            return Database.prepared(OrderTable.GetOrder.QUERY, (connection, statement) -> {
                statement.setInt(OrderTable.GetOrder.IN.ORDER_ID, orderId);
                statement.setInt(OrderTable.GetOrder.IN.OWNER_ID, account.getId());

                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    Order order = orderFromResult(result);
                    order.setProducts(getOrderItems(order.getOrderId()));
                    return order;
                } else {
                    throw new OrderStoreException("Unable to find the order.");
                }
            });
        } catch (SQLException e) {
            throw new OrderStoreException(e);
        }
    }

    @Override
    public void clearOrders(Account account) throws OrderStoreException {
        try {
            Database.prepared(OrderTable.ClearOrders.CLEAR_ORDERS_ITEMS, (connection, items) -> {
                connection.setAutoCommit(false);

                items.setInt(OrderTable.ClearOrders.IN.OWNER_ID, account.getId());
                items.execute();

                try (PreparedStatement orders =
                             connection.prepareStatement(OrderTable.ClearOrders.CLEAR_ORDERS)) {
                    orders.setInt(OrderTable.ClearOrders.IN.OWNER_ID, account.getId());
                    orders.execute();
                }
                connection.commit();
                return null;
            });
        } catch (SQLException e) {
            throw new OrderStoreException(e);
        }
    }

    @Override
    public Order getOrderForShipping() throws OrderStoreException, NoSuchOrderException {
        Order order = new Order();
        AtomicReference<Integer> ownerId = new AtomicReference<>();

        try {
            Database.prepared(OrderTable.GetOrderForShipping.QUERY, (connection, statement) -> {
                connection.setAutoCommit(false);

                // get an order ready for shipping
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    order.setOrderId(result.getInt(OrderTable.GetOrderForShipping.OUT.ORDER_ID));
                    order.setCreated(result.getString(OrderTable.GetOrderForShipping.OUT.CREATED));
                    ownerId.set(result.getInt(OrderTable.GetOrderForShipping.OUT.OWNER_ID));
                } else {
                    throw new NoSuchOrderException();
                }

                // deduct the stock count
                try (PreparedStatement stock =
                             connection.prepareStatement(OrderTable.DeductStockByOrder.QUERY)) {
                    stock.setInt(OrderTable.DeductStockByOrder.IN.ORDER_ID1, order.getOrderId());
                    stock.setInt(OrderTable.DeductStockByOrder.IN.ORDER_ID2, order.getOrderId());
                    stock.execute();
                }

                // update the order status
                try (PreparedStatement status =
                             connection.prepareStatement(OrderTable.SetOrderStatus.QUERY)) {
                    status.setInt(OrderTable.SetOrderStatus.IN.ORDER_ID, order.getOrderId());
                    status.setInt(OrderTable.SetOrderStatus.IN.STATUS, Order.Status.SHIPPED);
                    status.setString(OrderTable.SetOrderStatus.IN.CHANGED, getTimeStamp());
                    status.execute();
                }

                connection.commit();
                order.setProducts(getOrderItems(order.getOrderId()));
                order.setAccount(new AccountDB().findById(ownerId.get()));
                return null;
            });
        } catch (SQLException | AccountStoreException e) {
            throw new OrderStoreException(e);
        }

        return order;
    }

    private List<Product> getOrderItems(int orderId) throws OrderStoreException {
        List<Product> products = new ArrayList<>();

        try {
            Database.prepared(OrderTable.GetOrderItems.QUERY, (connection, statement) -> {
                statement.setInt(OrderTable.GetOrderItems.IN.ORDER_ID, orderId);
                ResultSet result = statement.executeQuery();

                while (result.next()) {
                    products.add(productFromResult(result));
                }
                return null;
            });
        } catch (SQLException e) {
            throw new OrderStoreException(e);
        }
        return products;
    }

    private Product productFromResult(ResultSet result) throws SQLException {
        Product product = new Product();
        product.setId(result.getInt(OrderTable.GetOrderItems.OUT.PRODUCT_ID));
        product.setCount(result.getInt(OrderTable.GetOrderItems.OUT.COUNT));
        product.setCost(result.getInt(OrderTable.GetOrderItems.OUT.COST));
        product.setName(result.getString(OrderTable.GetOrderItems.OUT.NAME));
        product.setImageId(result.getInt(OrderTable.GetOrderItems.OUT.IMAGE));
        return product;
    }

    private String getTimeStamp() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return localDateTime.format(formatter);
    }

}













