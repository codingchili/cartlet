package com.codingchili.webshoppe.model;

import com.codingchili.webshoppe.model.exception.NoSuchOrderException;
import com.codingchili.webshoppe.model.exception.OrderStoreException;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Robin on 2015-10-01.
 * <p>
 * Implementation of the OrderStore using MySQL for storage.
 */

class OrderDB implements OrderStore {

    @Override
    public int createOrder(Account account, Cart cart) throws OrderStoreException {
        try {
            return Database.prepared(OrderTable.CreateOrder.QUERY, (connection, create) -> {
                connection.setAutoCommit(false);
                int orderId = -1;

                // Create the order post in order table.
                create.setString(OrderTable.CreateOrder.IN.CREATED, getTimeStamp());
                create.setString(OrderTable.CreateOrder.IN.CHANGED, getTimeStamp());
                create.setInt(OrderTable.CreateOrder.IN.OWNER, account.getId());
                create.setInt(OrderTable.CreateOrder.IN.TOTAL, cart.getTotalCost());
                create.setInt(OrderTable.CreateOrder.IN.ITEM_COUNT, cart.getProductCount());

                create.execute();

                ResultSet result = create.getGeneratedKeys();

                if (result.next()) {
                    orderId = result.getInt(OrderTable.CreateOrder.OUT.ORDER_ID);
                }

                // Move all objects from the users cart to the order_product table.
                try (PreparedStatement items =
                             connection.prepareStatement(OrderTable.AddToOrder.QUERY)) {

                    for (Product product : cart.getProducts()) {
                        items.setInt(OrderTable.AddToOrder.IN.ORDER, orderId);
                        items.setInt(OrderTable.AddToOrder.IN.PRODUCT, product.getId());
                        items.setInt(OrderTable.AddToOrder.IN.COUNT, product.getCount());
                        items.execute();
                    }
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

                Optional<Order> order;
                do {
                    order = orderFromResult(result);
                    order.ifPresent(orders::add);
                } while (order.isPresent());
                return orders;
            });
        } catch (SQLException e) {
            throw new OrderStoreException(e);
        }
        list.setItems(orders);
        return list;
    }

    private Optional<Order> orderFromResult(ResultSet result) throws SQLException {
        if (result.next()) {
            Order order = new Order();
            order.setOrderId(result.getInt(OrderTable.GetOrder.OUT.ORDER_ID));
            order.setOwner(result.getInt(OrderTable.GetOrder.OUT.OWNER));
            order.setCreated(result.getString(OrderTable.GetOrder.OUT.CREATED));
            order.setChanged(result.getString(OrderTable.GetOrder.OUT.CHANGED));
            order.setStatus(OrderStatus.values()[result.getInt(OrderTable.GetOrder.OUT.STATUS)]);
            order.setOrderTotal(result.getInt(OrderTable.GetOrders.OUT.TOTAL));
            order.setItemCount(result.getInt(OrderTable.GetOrders.OUT.ITEM_COUNT));
            return Optional.of(order);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Order> getOrderById(Account account, int orderId) throws OrderStoreException {
        try {
            return Database.prepared(OrderTable.GetOrder.QUERY, (connection, statement) -> {
                statement.setInt(OrderTable.GetOrder.IN.ORDER_ID, orderId);
                statement.setInt(OrderTable.GetOrder.IN.OWNER_ID, account.getId());
                ResultSet result = statement.executeQuery();
                return createOrderFromResult(result);
            });
        } catch (SQLException e) {
            throw new OrderStoreException(e);
        }
    }

    @Override
    public Optional<Order> getOrderById(int orderId) throws OrderStoreException {
        try {
            return Database.prepared(OrderTable.GetOrderUnchecked.QUERY, (connection, statement) -> {
                statement.setInt(OrderTable.GetOrder.IN.ORDER_ID, orderId);
                ResultSet result = statement.executeQuery();
                return createOrderFromResult(result);
            });
        } catch (SQLException e) {
            throw new OrderStoreException(e);
        }
    }

    private Optional<Order> createOrderFromResult(ResultSet result) throws SQLException {
        Optional<Order> order = orderFromResult(result);
        order.ifPresent(o -> o.setProducts(getOrderItems(o.getOrderId())));
        return order;
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
    public Optional<Order> getOrderForShipping() throws OrderStoreException, NoSuchOrderException {
        try {
            return Database.prepared(OrderTable.GetOrderForShipping.QUERY, (connection, statement) -> {
                connection.setAutoCommit(false);

                // get an order ready for shipping
                ResultSet result = statement.executeQuery();

                Optional<Order> order = orderFromResult(result);

                if (order.isPresent()) {
                    Order o = order.get();

                    // todo: deduct the stock count = do this when order is complete packing
                    // todo: only find orders in the correct status when shipping.

                    try (PreparedStatement stock =
                                 connection.prepareStatement(OrderTable.DeductStockByOrder.QUERY)) {

                        stock.setInt(OrderTable.DeductStockByOrder.IN.ORDER_ID1, o.getOrderId());
                        stock.setInt(OrderTable.DeductStockByOrder.IN.ORDER_ID2, o.getOrderId());
                        stock.execute();
                    }

                    updateOrderStatus(o.getOrderId(), OrderStatus.PACKING, connection);
                    o.setProducts(getOrderItems(o.getOrderId()));
                }

                connection.commit();
                return order;
            });
        } catch (SQLException e) {
            throw new OrderStoreException(e);
        }
    }

    @Override
    public void updateOrderStatus(int orderId, OrderStatus status) {
        try (Connection connection = Database.getConnection()) {
            updateOrderStatus(orderId, status, connection);
        } catch (Exception e) {
            throw new OrderStoreException(e);
        }
    }

    @Override
    public OrderStatistics getOrderStatistics() {
        OrderStatistics statistics = new OrderStatistics();
        try {
            return Database.prepared(OrderTable.GetStatistics.QUERY, (connection, statement) -> {
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        OrderStatistic statistic = new OrderStatistic();
                        statistic.setStatus(OrderStatus.values()[result.getInt(OrderTable.GetStatistics.OUT.STATUS)]);
                        statistic.setCount(result.getInt(OrderTable.GetStatistics.OUT.COUNT_STATUS));
                        statistic.setCost(result.getInt(OrderTable.GetStatistics.OUT.AVG_COST));
                        statistic.setItems(result.getInt(OrderTable.GetStatistics.OUT.AVG_ITEMS));

                        statistics.add(statistic);
                    }
                }
                return statistics;
            });
        } catch (SQLException e) {
            throw new OrderStoreException(e);
        }
    }

    private void updateOrderStatus(int orderId, OrderStatus status, Connection connection) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(OrderTable.SetOrderStatus.QUERY)) {
            statement.setInt(OrderTable.SetOrderStatus.IN.ORDER_ID, orderId);
            statement.setInt(OrderTable.SetOrderStatus.IN.STATUS, status.ordinal());
            statement.setString(OrderTable.SetOrderStatus.IN.CHANGED, getTimeStamp());
            statement.execute();
        }
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













