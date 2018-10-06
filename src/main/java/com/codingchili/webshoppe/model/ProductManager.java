package com.codingchili.webshoppe.model;

import com.codingchili.webshoppe.model.exception.NoSuchProductException;
import com.codingchili.webshoppe.model.exception.ProductStoreException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robin on 2015-09-30.
 * <p>
 * Communicates with the Product Store.
 */

abstract public class ProductManager {
    private static List<Category> categories = new ArrayList<>();

    /**
     * @return a list of all product categories,
     * if the list could not be retrieved an
     * empty list is returned.
     */
    public static List<Category> listCategories() {
        if (categories.isEmpty()) {
            ProductStore store = Store.getProductStore();
            categories = store.listCategories();
        }
        return categories;
    }

    /**
     * Returns products that matches the name filter.
     *
     * @param name specifies the filter to match products to.
     * @return all products matching the filter.
     */
    public static List<Product> findProductsByName(String name) {
        ProductStore store = Store.getProductStore();
        return store.listProductsByName(name);
    }

    /**
     * Get a specific product by its unique identifier.
     *
     * @param productId identifier specifying a single product.
     * @return the product specified.
     */
    public static Product findProductById(int productId) {
        ProductStore store = Store.getProductStore();
        Product product = new Product();
        try {
            product = store.getProductById(productId);
        } catch (ProductStoreException e) {
            e.printStackTrace();
        }
        return product;
    }

    /**
     * Finds all products within a category.
     *
     * @param category to list items from.
     * @return all products matching the category id.
     */
    public static List<Product> findProductsByCategory(Category category) {
        ProductStore store = Store.getProductStore();
        return store.listProductsByCategory(category);
    }

    /**
     * Updates the given product.
     * @param productId the id of the product to update.
     * @param cost the cost of the product.
     * @param quantity the number of items to update.
     * @param name the name of the product.
     * @param description the description of the product
     * @throws NoSuchProductException if there is no product with the given id.
     */
    public static void updateProduct(int productId, int cost, int quantity, String name, String description)
            throws NoSuchProductException {
        ProductStore store = Store.getProductStore();
        store.updateProduct(productId, cost, quantity, name, description);
    }

    /**
     * Adds a new product.
     * @param name the name of the production.
     * @param description description of the product.
     * @param categoryId the id of the category.
     * @param quantity the number of items in stock.
     * @param cost the cost per item.
     * @throws ProductStoreException on failure to create the new product.
     */
    public static void addProduct(String name, String description, int categoryId, int quantity, int cost)
            throws ProductStoreException {
        Product product = new Product()
                .setCost(cost)
                .setDescription(description)
                .setName(name)
                .setCount(quantity);
        ProductStore store = Store.getProductStore();
        store.addProduct(product, categoryId);
    }

    /**
     * Adds a new product category.
     * @param category the name of the category to add.
     * @throws ProductStoreException on failure to create the category.
     */
    public static void addCategory(String category) throws ProductStoreException {
        Store.getProductStore().addCategory(category);
        categories.clear();
    }

    /**
     * Sets the image of a product.
     * @param productId the id of the pruduct.
     * @param image the product image.
     * @throws ProductStoreException
     */
    public static void setProductImage(int productId, String image) throws ProductStoreException {
        ProductStore store = Store.getProductStore();
        store.setProductImage(productId, image);
    }

    /**
     * Retrieves the product image of the given product.
     * @param imageId the id of the image to retrieve.
     * @return an image
     * @throws ProductStoreException on failure to retrieve the product image.
     */
    public static Image getProductImage(int imageId) throws ProductStoreException {
        ProductStore store = Store.getProductStore();
        return store.getProductImage(imageId);
    }
}
