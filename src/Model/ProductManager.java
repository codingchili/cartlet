package Model;

import Model.Exception.NoSuchProductException;
import Model.Exception.ProductStoreException;

import java.util.ArrayList;

/**
 * Created by Robin on 2015-09-30.
 *
 * Communicates with the Product Store.
 */

abstract public class ProductManager {

    /**
     * @return a list of all product categories,
     * if the list could not be retrieved an
     * empty list is returned.
     */
    public static ArrayList<Category> listCategories() {
        ProductStore store = Store.getProductStore();
        ArrayList<Category> categories = new ArrayList<>();

        try {
            categories = store.listCategories();
        } catch (ProductStoreException e) {
            e.printStackTrace();
        }
        return categories;
    }

    /**
     * Returns products that matches the name filter.
     *
     * @param name specifies the filter to match products to.
     * @return all products matching the filter.
     */
    public static ArrayList<Product> findProductsByName(String name) {
        ProductStore store = Store.getProductStore();
        ArrayList<Product> products = new ArrayList<>();
        try {
            products = store.listProductsByName(name);
        } catch (ProductStoreException e) {
            e.printStackTrace();
        }
        return products;
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
    public static ArrayList<Product> findProductsByCategory(Category category) {
        ProductStore store = Store.getProductStore();
        ArrayList<Product> products = new ArrayList<>();
        try {
            products = store.listProductsByCategory(category);
        } catch (ProductStoreException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static void updateProduct(int productId, int cost, int quantity, String name, String description)
            throws NoSuchProductException {
        ProductStore store = Store.getProductStore();
        try {
            store.updateProduct(productId, cost, quantity, name, description);
        } catch (ProductStoreException e) {
            throw new NoSuchProductException();
        }
    }

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

    public static void addCategory(String category) throws ProductStoreException {
        Store.getProductStore().addCategory(category);
    }

    public static void setProductImage(int productId, String image) throws ProductStoreException {
        ProductStore store = Store.getProductStore();
        store.setProductImage(productId, image);
    }

    public static Image getProductImage(int imageId) throws ProductStoreException {
        ProductStore store = Store.getProductStore();
        return store.getProductImage(imageId);
    }
}
