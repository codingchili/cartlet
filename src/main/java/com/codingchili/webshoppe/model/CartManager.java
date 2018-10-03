package com.codingchili.webshoppe.model;

import com.codingchili.webshoppe.model.exception.CartStoreException;
import com.codingchili.webshoppe.model.exception.StoreException;

/**
 * Created by Robin on 2015-09-30.
 *
 * Manages a cart object.
 */

abstract public class CartManager {

    /**
     * Adds an item to an users cart.
     *
     * @param product added to the cart.
     * @param count   number of products to add.
     * @param account owner of othe cart to add to.
     */
    public static void addToCart(Product product, int count, Account account) {
        CartStore store = Store.getCartStore();
        try {
            store.addToCart(product, count, account);
        } catch (StoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove items from an users cart.
     *
     * @param product to be removed.
     * @param account owner of the cart in which the items should be removed.
     */
    public static void removeFromCart(Product product, Account account) {
        CartStore cart = Store.getCartStore();
        try {
            cart.removeFromCart(product, account);
        } catch (CartStoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears an users cart.
     *
     * @param account owning the cart.
     */
    public static void clearCart(Account account) {
        CartStore store = Store.getCartStore();
        try {
            store.clearCart(account);
        } catch (CartStoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the contents of an users cart.
     *
     * @param account owning the cart to be retrieved.
     * @return a ProductList bean.
     */
    public static Cart getCart(Account account) {
        CartStore store = Store.getCartStore();
        Cart cart = new Cart();
        try {
            cart = store.getCart(account);
        } catch (StoreException e) {
            e.printStackTrace();
        }
        return cart;
    }

    /**
     * Get the number of products in the cart.
     *
     * @param account owner of the cart.
     * @return the number of products in the cart.
     */
    public static int getProductCount(Account account) {
        int count = 0;
        CartStore store = Store.getCartStore();
        try {
            count = store.productCount(account);
        } catch (CartStoreException e) {
            e.printStackTrace();
        }
        return count;
    }
}
