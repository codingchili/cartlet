import Model.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Robin on 2015-10-01.
 *
 */
public class OrderManagerTest {
    private static final int PRODUCT_ID = 1;
    private static final int PRODUCT_COUNT = 2;
    private static Account account;
    private static Product product;

    @BeforeClass
    public static void setUp() {
        account = AccountManager.register("account_user_test", "password99", "zip", "street").getAccount();
        product = new Product();
        product.setId(PRODUCT_ID);
    }

    @AfterClass
    public static void tearDown() {
        AccountManager.deRegister(account);
    }

    @Test
    public void shouldCreateOrder() throws Exception {
        CartManager.addToCart(product, PRODUCT_COUNT, account);
        OrderManager.createOrder(account);
        CartManager.clearCart(account);

        OrderList orders = OrderManager.getOrders(account);

        if (orders.getItems().size() == 0) {
            throw new Exception("No orders returned after placing order.");
        }
    }

    @Test
    public void shouldGetOrderById() throws Exception {
        CartManager.addToCart(product, PRODUCT_COUNT, account);
        OrderManager.createOrder(account);
        CartManager.clearCart(account);
        OrderList orderList = OrderManager.getOrders(account);

        for (Order order: orderList.getItems()) {
            Order byId = OrderManager.getOrderById(account, order.getOrderId());

            if (byId.getProducts() == null) {
                throw new Exception("No products returned from getOrderById.");
            }
        }
    }

    @Test
    public void shouldClearOrders() throws Exception {
        CartManager.addToCart(product, 1, account);
        OrderManager.createOrder(account);

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
        CartManager.addToCart(product, 1, account);
        if (OrderManager.createOrder(account)) {
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
        } else
            throw new Exception("Failed to create the order.");
    }
}
