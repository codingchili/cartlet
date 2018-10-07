package com.codingchili.webshoppe.model;

import com.codingchili.webshoppe.model.exception.CartStoreException;
import com.codingchili.webshoppe.model.exception.StoreException;

import java.util.List;

/**
 * Created by Robin on 2015-09-30.
 *
 */

interface CartStore {

    /**
     * Adds an item to specified users cart.
     * @param product to be added.
     * @param account owning the cart.
     */
    void setCartItems(List<Product> product, Account account) throws CartStoreException;

    /**
     * Adds an item to specified users cart.
     * @param product to be added.
     * @param account owning the cart.
     */
    void setCartItems(Product product, Account account) throws CartStoreException;

    /**
     * Removes an item from specified users cart.
     * @param product to be removed.
     */
    void removeFromCart(Product product, Account account) throws CartStoreException;


    /**
     * Clears an users cart.
     * @param account owning the cart to be removed.
     */
    void clearCart(Account account) throws CartStoreException;

    /**
     * Check how many items are in the cart.
     * @param account associated with the cart.
     * @return the number of unique products in the cart.
     */
    int productCount(Account account) throws CartStoreException;

    /**
     * Get the cart of the owner.
     * @param account owning the the cart.
     * @return the specified users cart.
     */
    Cart getCart(Account account) throws CartStoreException, StoreException;
}
