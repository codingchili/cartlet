import Model.Category;
import Model.Image;
import Model.Product;
import Model.ProductManager;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Robin on 2015-09-30.
 * <p>
 * Tests the product info.
 */

public class ProductManagerTest {
    private static final int PRODUCT_ID = 1;
    private static final int PRODUCT_CATEGORY = 1;

    @Test
    public void retrieveAllCategories() throws Exception {
        ArrayList<Category> categories = ProductManager.listCategories();

        if (categories.size() == 0)
            throw new Exception("No categories returned from CategoryInfo!");
    }

    @Test
    public void findProductByName() throws Exception {
        ArrayList<Product> products = ProductManager.findProductsByName("s");

        if (products.size() == 0) {
            throw new Exception("No books returned for query with \"s\".");
        }
    }

    @Test
    public void findProductById() throws Exception {
        Product product = ProductManager.findProductById(PRODUCT_ID);

        if (product.getName() == null) {
            throw new Exception("No products returned for ID = " + PRODUCT_ID);
        }
    }

    @Test
    public void findProductsByCategory() throws Exception {
        Category category = new Category();
        category.setCategoryId(PRODUCT_CATEGORY);
        ArrayList<Product> products = ProductManager.findProductsByCategory(category);

        if (products.size() == 0) {
            throw new Exception("Could not find any products with Category = " + PRODUCT_CATEGORY);
        }
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        ProductManager.addProduct("new-prod", "this.new.prod", 1, 1, 1);
        ArrayList<Product> products = ProductManager.findProductsByName("new-prod");

        for (Product product: products) {
            ProductManager.updateProduct(product.getId(), 2, 2, "new-name", "new-desc");
        }

        products = ProductManager.findProductsByName("new-name");

        if (products.size() == 0) {
            throw new Exception("Product not updated.");
        }
    }

    @Test
    public void shouldAddProduct() throws Exception {
        ProductManager.addProduct("new-prod", "this.new.prod", 1, 1, 1);
        ArrayList<Product> product = ProductManager.findProductsByName("new-prod");

        if (product.size() == 0) {
            throw new Exception("Product was not added to store.");
        }
    }

    @Test
    public void shouldAddCategory() throws Exception {
        ProductManager.addCategory("cat_name");
        ArrayList<Category> categories = ProductManager.listCategories();
        boolean exists = false;

        for (Category category: categories) {
            if (category.getName().equals("cat_name"))
                exists = true;
        }

        if (!exists) {
            throw new Exception("Failed to add category.");
        }
    }

    @Test
    public void shouldSetAndGetImage() throws Exception {
        ProductManager.addProduct("name", "dsc", 1, 1, 1);
        Product product = ProductManager.findProductsByName("name").get(0);
        ProductManager.setProductImage(product.getId(), "IMAGE");

        product = ProductManager.findProductById(product.getId());
        Image image = ProductManager.getProductImage(product.getFrontImage());

        if (!image.getData().equals("IMAGE")) {
            throw new Exception("Image not retrieved.");
        }
    }
}
