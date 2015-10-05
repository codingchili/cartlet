package Model;

/**
 * Created by Robin on 2015-10-01.
 *
 * A single point to define the stores used, a store
 * may be dependent on another store.
 */

 class Store {
    public static AccountStore getAccountStore() {
        return new AccountDB();
    }

    public static OrderStore getOrderStore() {
        return new OrderDB();
    }

    public static ProductStore getProductStore() {
        return new ProductDB();
    }

    public static CartStore getCartStore() {
        return new CartDB();
    }
}
