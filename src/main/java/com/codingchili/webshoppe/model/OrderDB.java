package com.codingchili.webshoppe.model;

import com.codingchili.webshoppe.model.exception.AccountStoreException;
import com.codingchili.webshoppe.model.exception.NoSuchOrderException;
import com.codingchili.webshoppe.model.exception.OrderStoreException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by Robin on 2015-10-01.
 *
 * Implementation of the OrderStore using MySQL for storage.
 */

class OrderDB implements OrderStore {
    @Override
    public void createOrder(Account account) throws OrderStoreException {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);
            int orderId = -1;

            // Create the order post in order table.
            try (PreparedStatement statement =
                         connection.prepareStatement(OrderTable.CreateOrder.QUERY)) {
                statement.setString(OrderTable.CreateOrder.IN.CREATED, getTimeStamp());
                statement.setString(OrderTable.CreateOrder.IN.CHANGED, getTimeStamp());
                statement.setInt(OrderTable.CreateOrder.IN.OWNER, account.getId());
                statement.execute();
                ResultSet result = statement.getGeneratedKeys();

                if (result.next())
                    orderId = result.getInt(OrderTable.CreateOrder.OUT.ORDER_ID);
            }
            // Move all objects from the users cart to the order_product table.
            try (PreparedStatement statement =
                         connection.prepareStatement(OrderTable.CopyFromCart.QUERY)) {
                statement.setInt(OrderTable.CopyFromCart.IN.ORDER, orderId);
                statement.setInt(OrderTable.CopyFromCart.IN.OWNER, account.getId());
                statement.execute();
            }
            connection.commit();
        } catch (SQLException e) {
            throw new OrderStoreException(e);
        }
    }

    @Override
    public OrderList getOrders(Account account) throws OrderStoreException {
        ArrayList<Order> orders = new ArrayList<>();
        OrderList list = new OrderList();

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement =
                    connection.prepareStatement(OrderTable.GetOrders.QUERY)) {

                statement.setInt(OrderTable.GetOrders.IN.OWNER, account.getId());
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    Order order = orderFromResult(result);
                    order.setProducts(getOrderItems(result.getInt(OrderTable.GetOrder.OUT.ORDER_ID)));
                    orders.add(order);
                }
            }
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
        Order order = new Order();

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement =
                    connection.prepareStatement(OrderTable.GetOrder.QUERY)) {

                statement.setInt(OrderTable.GetOrder.IN.ORDER_ID, orderId);
                statement.setInt(OrderTable.GetOrder.IN.OWNER_ID, account.getId());

                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    order = orderFromResult(result);
                    order.setProducts(getOrderItems(order.getOrderId()));
                }
            }
        } catch (SQLException e) {
            throw new OrderStoreException(e);
        }
        return order;
    }

    @Override
    public void clearOrders(Account account) throws OrderStoreException {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statement =
                    connection.prepareStatement(OrderTable.ClearOrders.CLEAR_ORDERS_ITEMS)) {
                statement.setInt(OrderTable.ClearOrders.IN.OWNER_ID, account.getId());
                statement.execute();
            }

            try (PreparedStatement statement =
                    connection.prepareStatement(OrderTable.ClearOrders.CLEAR_ORDERS)) {
                statement.setInt(OrderTable.ClearOrders.IN.OWNER_ID, account.getId());
                statement.execute();
            }

            connection.commit();
        } catch (SQLException e) {
            throw new OrderStoreException(e);
        }
    }

    @Override
    public Order getOrderForShipping() throws OrderStoreException, NoSuchOrderException {
        Order order = new Order();
        int ownerId;

        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);

            // get an order ready for shipping
            try (PreparedStatement statement =
                         connection.prepareStatement(OrderTable.GetOrderForShipping.QUERY)) {

                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    order.setOrderId(result.getInt(OrderTable.GetOrderForShipping.OUT.ORDER_ID));
                    order.setCreated(result.getString(OrderTable.GetOrderForShipping.OUT.CREATED));
                    ownerId = result.getInt(OrderTable.GetOrderForShipping.OUT.OWNER_ID);
                } else
                    throw new NoSuchOrderException();
            }

            // deduct the stock count
            try (PreparedStatement statement =
                    connection.prepareStatement(OrderTable.DeductStockByOrder.QUERY)) {
                statement.setInt(OrderTable.DeductStockByOrder.IN.ORDER_ID1, order.getOrderId());
                statement.setInt(OrderTable.DeductStockByOrder.IN.ORDER_ID2, order.getOrderId());
                statement.execute();
            }

            // update the order status
            try (PreparedStatement statement =
                    connection.prepareStatement(OrderTable.SetOrderStatus.QUERY)) {
                statement.setInt(OrderTable.SetOrderStatus.IN.ORDER_ID, order.getOrderId());
                statement.setInt(OrderTable.SetOrderStatus.IN.STATUS, Order.Status.SHIPPED);
                statement.setString(OrderTable.SetOrderStatus.IN.CHANGED, getTimeStamp());
                statement.execute();
            }

            connection.commit();
            order.setProducts(getOrderItems(order.getOrderId()));
            order.setAccount(new AccountDB().findById(ownerId));
        } catch (SQLException | AccountStoreException e) {
            throw new OrderStoreException(e);
        }

        return order;
    }

    private ArrayList<Product> getOrderItems(int orderId) throws OrderStoreException {
        ArrayList<Product> products = new ArrayList<>();

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement =
                    connection.prepareStatement(OrderTable.GetOrderItems.QUERY)) {

                statement.setInt(OrderTable.GetOrderItems.IN.ORDER_ID, orderId);
                ResultSet result = statement.executeQuery();

                while (result.next()) {
                    products.add(productFromResult(result));
                }

            }
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
        return product;
    }

    private String getTimeStamp() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return localDateTime.format(formatter);
    }

}













