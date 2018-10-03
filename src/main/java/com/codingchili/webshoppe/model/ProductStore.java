package com.codingchili.webshoppe.model;

import com.codingchili.webshoppe.model.exception.ProductStoreException;

import java.util.ArrayList;

/**
 * Created by Robin on 2015-09-30.
 *
 * Interface to a product store.
 */

interface ProductStore {
    /**
     * Lists all available categories in the product store.
     * @return the names as strings.
     * @throws ProductStoreException when the store query has failed.
     */
    ArrayList<Category> listCategories() throws ProductStoreException;

    /**
     * Retrieves all products from the store matching the name-pattern.
     * @param nameFilter matches product names partially.
     * @return all products mathching the nameFilter.
     * @throws ProductStoreException when the store query has failed.
     */
    ArrayList<Product> listProductsByName(String nameFilter) throws ProductStoreException;

    /**
     * Retrieves all products from a specified category.
     * @param category to match.
     * @return all products matching the categoryFilter.
     * @throws ProductStoreException when the store query has failed.
     */
    ArrayList<Product> listProductsByCategory(Category category) throws ProductStoreException;

    /**
     * Get a product from the store by its id.
     * @param id of the product to be retrieved.
     * @return a product matching the id.
     * @throws ProductStoreException when the store query failed.
     */
    Product getProductById(int id) throws ProductStoreException;

    /**
     * Updates the information of an existing product.
     * @param id of the product to be updated.
     * @param cost of the product.
     * @param quantityModifier change in stock.
     * @param name the name of the product.
     * @param description the description text for the product.
     */
    void updateProduct(int id, int cost, int quantityModifier, String name, String description) throws ProductStoreException;

    /**
     * Adds a product to the store.
     * @param product to be added to the store.
     * @param categoryId id of the product to be added.
     */
    void addProduct(Product product, int categoryId) throws ProductStoreException;

    /**
     * Adds a category to the store.
     * @param category name of the category to be added.
     */
    void addCategory(String category) throws ProductStoreException;

    /**
     * Set the image of a product.
     * @param productId id of the product to change image.
     * @param image base64 encoded image data.
     */
    void setProductImage(int productId, String image) throws ProductStoreException;

    /**
     * Get the image of a product.
     * @param imageId the image to retrieve.
     * @return the image of a product.
     */
    Image getProductImage(int imageId) throws ProductStoreException;
}
