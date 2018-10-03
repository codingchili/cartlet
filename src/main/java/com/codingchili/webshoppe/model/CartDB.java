package com.codingchili.webshoppe.model;

import com.codingchili.webshoppe.model.exception.CartStoreException;
import com.codingchili.webshoppe.model.exception.ProductStoreException;
import com.codingchili.webshoppe.model.exception.StoreException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Robin on 2015-09-30.
 * <p>
 * Implementation of CartStore using MySQL Database.
 */

class CartDB implements CartStore {
    @Override
    public void addToCart(Product product, int count, Account account) throws CartStoreException {
        int productsInCart = getProductCountInCart(product, account);

        if (count + productsInCart <= 0) {
            removeFromCart(product, account);
        } else if (productsInCart > 0) {
            updateCartCount(product, count + productsInCart, account);
        } else {
            try (Connection connection = Database.getConnection()) {
                try (PreparedStatement statement =
                             connection.prepareStatement(CartTable.AddToCart.QUERY)) {

                    statement.setInt(CartTable.AddToCart.IN.COUNT,
                            count + getProductCountInCart(product,
                                    account));

                    statement.setInt(CartTable.AddToCart.IN.OWNER, account.getId());
                    statement.setInt(CartTable.AddToCart.IN.PRODUCT, product.getId());
                    statement.execute();
                }
            } catch (SQLException e) {
                throw new CartStoreException(e);
            }
        }
    }

    private void updateCartCount(Product product, int count, Account account) throws CartStoreException {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(CartTable.UpdateCartCount.QUERY)) {

                statement.setInt(CartTable.UpdateCartCount.IN.COUNT, count);
                statement.setInt(CartTable.UpdateCartCount.IN.PRODUCT, product.getId());
                statement.setInt(CartTable.UpdateCartCount.IN.OWNER, account.getId());
                statement.execute();
            }

        } catch (SQLException e) {
            throw new CartStoreException(e);
        }
    }

    private int getProductCountInCart(Product product, Account account) throws CartStoreException {
        int count;

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(CartTable.ProductIdCount.QUERY)) {

                statement.setInt(CartTable.ProductIdCount.IN.OWNER, account.getId());
                statement.setInt(CartTable.ProductIdCount.IN.PRODUCT, product.getId());

                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    count = result.getInt(CartTable.ProductIdCount.OUT.COUNT);
                } else
                    count = 0;
            }
        } catch (SQLException e) {
            throw new CartStoreException(e);
        }
        return count;
    }

    @Override
    public void removeFromCart(Product product, Account account) throws CartStoreException {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(CartTable.RemoveFromCart.QUERY)) {

                statement.setInt(CartTable.RemoveFromCart.IN.OWNER, account.getId());
                statement.setInt(CartTable.RemoveFromCart.IN.PRODUCT, product.getId());
                statement.execute();
            }
        } catch (SQLException e) {
            throw new CartStoreException(e);
        }
    }

    @Override
    public void clearCart(Account account) throws CartStoreException {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(CartTable.ClearCart.QUERY)) {

                statement.setInt(CartTable.ClearCart.IN.OWNER, account.getId());
                statement.execute();
            }
        } catch (SQLException e) {
            throw new CartStoreException(e);
        }
    }

    @Override
    public int productCount(Account account) throws CartStoreException {
        int count;

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(CartTable.ProductCount.QUERY)) {

                statement.setInt(CartTable.ProductCount.IN.OWNER, account.getId());
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    count = result.getInt(CartTable.ProductCount.OUT.COUNT);
                } else
                    count = 0;
            }
        } catch (SQLException e) {
            throw new CartStoreException(e);
        }
        return count;
    }

    @Override
    public Cart getCart(Account account) throws StoreException {
        Cart cart = new Cart();
        cart.setOwner(account);

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(CartTable.GetCart.QUERY)) {

                statement.setInt(CartTable.GetCart.IN.OWNER, account.getId());
                ResultSet result = statement.executeQuery();
                cart = cartFromResult(result);
            }
        } catch (SQLException e) {
            throw new CartStoreException(e);
        }
        return cart;
    }

    private Cart cartFromResult(ResultSet result) throws SQLException, ProductStoreException {
        Cart cart = new Cart();

        while (result.next()) {
            Product product = new Product();

            product.setId(result.getInt(CartTable.GetCart.OUT.PRODUCT));
            product.setName(result.getString(CartTable.GetCart.OUT.NAME));
            product.setCount(result.getInt(CartTable.GetCart.OUT.COUNT));
            product.setCost(result.getInt(CartTable.GetCart.OUT.COST));
            product.setImageIds(new ProductDB().listProductImages(product));
            cart.addProduct(product);
        }

        return cart;
    }
}
