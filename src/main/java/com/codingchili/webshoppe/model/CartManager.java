package com.codingchili.webshoppe.model;

import java.util.List;

/**
 * Created by Robin on 2015-09-30.
 * <p>
 * Manages a cart object.
 */

abstract public class CartManager {

    /**
     * Adds an item to an users cart.
     *
     * @param account owner of othe cart to add to.
     */
    public static void setCartItems(List<Product> products, Account account) {
        CartStore store = Store.getCartStore();
        store.setCartItems(products, account);
    }

    /**
     * Adds an item to an users cart.
     *
     * @param product added to the cart.
     * @param account owner of othe cart to add to.
     */
    public static void setCartItems(Product product, Account account) {
        CartStore store = Store.getCartStore();
        store.setCartItems(product, account);
    }

    /**
     * Remove items from an users cart.
     *
     * @param product to be removed.
     * @param account owner of the cart in which the items should be removed.
     */
    public static void removeFromCart(Product product, Account account) {
        CartStore cart = Store.getCartStore();
        cart.removeFromCart(product, account);
    }

    /**
     * Clears an users cart.
     *
     * @param account owning the cart.
     */
    public static void clearCart(Account account) {
        CartStore store = Store.getCartStore();
        store.clearCart(account);
    }

    /**
     * Get the contents of an users cart.
     *
     * @param account owning the cart to be retrieved.
     * @return a ProductList bean.
     */
    public static Cart getCart(Account account) {
        CartStore store = Store.getCartStore();
        return store.getCart(account);
    }

    /**
     * Get the number of products in the cart.
     *
     * @param account owner of the cart.
     * @return the number of products in the cart.
     */
    public static int getProductCount(Account account) {
        CartStore store = Store.getCartStore();
        return store.productCount(account);
    }
}
