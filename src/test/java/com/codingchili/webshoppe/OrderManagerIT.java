package com.codingchili.webshoppe;

import com.codingchili.webshoppe.model.*;
import org.junit.*;

import java.util.Optional;

/**
 * Order manager tests.
 */
public class OrderManagerIT {
    private static final int PRODUCT_ID = 1;
    private static final int PRODUCT_COUNT = 2;
    private static final String ACCOUNT_USER_TEST = "account_user_test";
    private static Account account;
    private static Product product;

    @BeforeClass
    public static void setUp() {
        account = AccountManager.register(AccountManagerIT.getAccount()
                .setUsername(ACCOUNT_USER_TEST))
                .getAccount();

        product = new Product();
        product.setId(PRODUCT_ID);
    }

    @AfterClass
    public static void tearDown() {
        AccountManager.deRegister(account);
    }

    @Test
    public void shouldCreateOrder() throws Exception {
        CartManager.addToCart(product.setCount(PRODUCT_COUNT), account);
        OrderManager.createOrder(account, CartManager.getCart(account));
        CartManager.clearCart(account);

        OrderList orders = OrderManager.getOrders(account);

        if (orders.getItems().size() == 0) {
            throw new Exception("No orders returned after placing order.");
        }
    }

    @Test
    public void shouldGetOrderById() throws Exception {
        CartManager.addToCart(product.setCount(PRODUCT_COUNT), account);

        int orderId = OrderManager.createOrder(account, CartManager.getCart(account));
        Assert.assertTrue(OrderManager.getOrderById(orderId).isPresent());

        CartManager.clearCart(account);
        OrderList orderList = OrderManager.getOrders(account);

        for (Order order : orderList.getItems()) {
            Optional<Order> byId = OrderManager.getOrderById(account, order.getOrderId());

            if (!byId.isPresent() || byId.get().getProducts() == null) {
                throw new Exception("No products returned from getOrderById.");
            }
        }
    }

    @Test
    public void shouldClearOrders() throws Exception {
        CartManager.addToCart(product.setCount(1), account);
        OrderManager.createOrder(account, CartManager.getCart(account));

        if (OrderManager.getOrders(account).getItems().size() == 0) {
            throw new Exception("Precondition failed: unable to add items to cart.");
        }

        OrderManager.clearOrders(account);

        if (OrderManager.getOrders(account).getItems().size() != 0) {
            throw new Exception("Orders not cleared.");
        }
    }

    @Test
    public void searchForMissingOrder() {
        Assert.assertFalse(OrderManager.getOrderById(-1).isPresent());
    }

    @Test
    public void shouldGetOrderToPack() {
        CartManager.addToCart(product.setCount(1), account);
        int orderId = OrderManager.createOrder(account, CartManager.getCart(account));
        OrderManager.updateOrder(orderId, OrderStatus.PAYED);
        Optional<Order> order = OrderManager.getOrderForShipping();

        order.ifPresent(o -> {
            if (o.getProducts().size() == 0) {
                throw new RuntimeException("Order to pack is empty.");
            }

            if (o.getAccount() == null) {
                throw new RuntimeException("No account associated with Order.");
            }

            if (ProductManager.findProductById(product.getId()).getCount() == product.getCount()) {
                throw new RuntimeException("Packing order does not deduct stock.");
            }
        });
    }
}
