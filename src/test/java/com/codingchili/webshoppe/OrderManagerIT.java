package com.codingchili.webshoppe;

import com.codingchili.webshoppe.model.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Order manager tests.
 */
public class OrderManagerIT {
    private static final int PRODUCT_ID = 1;
    private static final int PRODUCT_COUNT = 2;
    public static final String ACCOUNT_USER_TEST = "account_user_test";
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
        OrderManager.createOrder(account, CartManager.getCart(account));
        CartManager.clearCart(account);
        OrderList orderList = OrderManager.getOrders(account);

        for (Order order : orderList.getItems()) {
            Order byId = OrderManager.getOrderById(account, order.getOrderId());

            if (byId.getProducts() == null) {
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
    public void shouldGetOrderToPack() throws Exception {
        CartManager.addToCart(product.setCount(1), account);
        OrderManager.createOrder(account, CartManager.getCart(account));
        Order order = OrderManager.getOrderForShipping();

        if (order.getProducts().size() == 0) {
            throw new Exception("Order to pack is empty.");
        }

        if (order.getAccount() == null) {
            throw new Exception("No account associated with Order.");
        }

        if (ProductManager.findProductById(product.getId()).getCount() == product.getCount()) {
            throw new Exception("Packing order does not deduct stock.");
        }
    }
}
