package Model;

import Model.Exception.ProductStoreException;
import com.mysql.jdbc.Blob;
import sun.misc.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Robin on 2015-09-30.
 *
 * An implementation of a Product Store using a MySQL database.
 */

class ProductDB implements ProductStore {
    @Override
    public ArrayList<Category> listCategories() throws ProductStoreException {
        ArrayList<Category> categories = new ArrayList<>();

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(ProductTable.ListCategories.QUERY)) {

                ResultSet result = statement.executeQuery();

                while (result.next()) {
                    Category category = new Category();
                    category.setName(result.getString(ProductTable.ListCategories.OUT.NAME));
                    category.setCategoryId(result.getInt(ProductTable.ListCategories.OUT.ID));
                    categories.add(category);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ProductStoreException();
        }
        return categories;
    }

    @Override
    public ArrayList<Product> listProductsByName(String nameFilter) throws ProductStoreException {
        ArrayList<Product> products;

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(ProductTable.ListProductsByName.QUERY)) {

                statement.setString(ProductTable.ListProductsByName.IN.NAME, "%" + nameFilter + "%");
                ResultSet result = statement.executeQuery();
                products = productsFromResult(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ProductStoreException();
        }

        return products;
    }

    private Product productFromResult(ResultSet result) throws SQLException, ProductStoreException {
        Product product = new Product();
        product.setId(result.getInt(ProductTable.Product.OUT.ID));
        product.setName(result.getString(ProductTable.Product.OUT.NAME));
        product.setDescription(result.getString(ProductTable.Product.OUT.DESCRIPTION));
        product.setCount(result.getInt(ProductTable.Product.OUT.COUNT));
        product.setCost(result.getInt(ProductTable.Product.OUT.COST));
        product.setImageIds(listProductImages(product));
        return product;
    }

    private ArrayList<Product> productsFromResult(ResultSet result) throws SQLException, ProductStoreException {
        ArrayList<Product> products = new ArrayList<>();

        while (result.next()) {
            products.add(productFromResult(result));
        }

        return products;
    }

    private ArrayList<Integer> listProductImages(Product product) throws ProductStoreException {
        ArrayList<Integer> ids = new ArrayList<>();

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(ProductTable.ListProductImages.QUERY)) {

                statement.setInt(ProductTable.ListProductImages.IN.PRODUCT_ID, product.getId());
                ResultSet result = statement.executeQuery();

                while (result.next()) {
                    ids.add(result.getInt(ProductTable.ListProductImages.OUT.IMAGE_ID));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ProductStoreException();
        }
        return ids;
    }

    @Override
    public ArrayList<Product> listProductsByCategory(Category category) throws ProductStoreException {
        ArrayList<Product> products;

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(ProductTable.ListProductsByCategory.QUERY)) {

                statement.setInt(ProductTable.ListProductsByCategory.IN.ID, category.getCategoryId());
                ResultSet result = statement.executeQuery();
                products = productsFromResult(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ProductStoreException();
        }
        return products;
    }

    @Override
    public Product getProductById(int id) throws ProductStoreException {
        Product product;

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(ProductTable.GetProductById.QUERY)) {

                statement.setInt(ProductTable.GetProductById.IN.ID, id);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    product = productFromResult(result);
                } else
                    throw new ProductStoreException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ProductStoreException();
        }
        return product;
    }

    @Override
    public void updateProduct(int id, int cost, int quantity, String name, String description) throws ProductStoreException {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(ProductTable.UpdateProductById.QUERY)) {

                statement.setInt(ProductTable.UpdateProductById.IN.PRODUCT_ID, id);
                statement.setInt(ProductTable.UpdateProductById.IN.COST, cost);
                statement.setInt(ProductTable.UpdateProductById.IN.QUANTITY, quantity);
                statement.setString(ProductTable.UpdateProductById.IN.NAME, name);
                statement.setString(ProductTable.UpdateProductById.IN.DESCRIPTION, description);

                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ProductStoreException();
        }
    }

    @Override
    public void addProduct(Product product, int categoryId) throws ProductStoreException {
        int productId = -1;

        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement =
                         connection.prepareStatement(ProductTable.AddProduct.QUERY)) {

                statement.setInt(ProductTable.AddProduct.IN.COST, product.getCost());
                statement.setInt(ProductTable.AddProduct.IN.COUNT, product.getCount());
                statement.setString(ProductTable.AddProduct.IN.DESCRIPTION, product.getDescription());
                statement.setString(ProductTable.AddProduct.IN.NAME, product.getName());
                statement.execute();

                ResultSet result = statement.getGeneratedKeys();

                if (result.next()) {
                    productId = result.getInt(1);
                }
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(ProductTable.SetProductCategory.QUERY)) {
                statement.setInt(ProductTable.SetProductCategory.IN.CATEGORY_ID, categoryId);
                statement.setInt(ProductTable.SetProductCategory.IN.PRODUCT_ID, productId);
                statement.execute();
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ProductStoreException();
        }
    }

    @Override
    public void addCategory(String category) throws ProductStoreException {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(ProductTable.AddCategory.QUERY)) {
                statement.setString(ProductTable.AddCategory.IN.NAME, category);
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ProductStoreException();
        }
    }

    @Override
    public void setProductImage(int productId, String image) throws ProductStoreException {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(ProductTable.SetProductImage.QUERY)) {


                InputStream input = new ByteArrayInputStream(image.getBytes());
                statement.setInt(ProductTable.SetProductImage.IN.PRODUCT_ID, productId);
                statement.setBinaryStream(ProductTable.SetProductImage.IN.IMAGE, input, image.getBytes().length);
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ProductStoreException();
        }
    }

    @Override
    public Image getProductImage(int imageId) throws ProductStoreException {
        Image image = new Image();

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(ProductTable.GetProductImage.QUERY)) {


                statement.setInt(ProductTable.GetProductImage.IN.IMAGE_ID, imageId);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    image.setImageId(imageId);
                    InputStream stream = result.getBlob(ProductTable.GetProductImage.OUT.IMAGE).getBinaryStream();
                    String data = new String(IOUtils.readFully(stream, stream.available(), true));
                    image.setData(data);

                } else
                    throw new ProductStoreException();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw new ProductStoreException();
        }
        return image;
    }
}
