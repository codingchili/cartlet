package com.codingchili.webshoppe.model;

import com.codingchili.webshoppe.model.exception.ProductStoreException;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Robin on 2015-09-30.
 * <p>
 * An implementation of a Product Store using a MySQL database.
 */

class ProductDB implements ProductStore {

    @Override
    public List<Category> listCategories() throws ProductStoreException {
        List<Category> categories = new ArrayList<>();
        try {
            return Database.prepared(ProductTable.ListCategories.QUERY, (connection, statement) -> {
                ResultSet result = statement.executeQuery();

                while (result.next()) {
                    Category category = new Category();
                    category.setName(result.getString(ProductTable.ListCategories.OUT.NAME));
                    category.setCategoryId(result.getInt(ProductTable.ListCategories.OUT.ID));
                    categories.add(category);
                }
                return categories;
            });
        } catch (SQLException e) {
            throw new ProductStoreException(e);
        }
    }

    @Override
    public List<Product> listProductsByName(String nameFilter) throws ProductStoreException {
        try {
            return Database.prepared(ProductTable.ListProductsByName.QUERY, (connection, statement) -> {
                statement.setString(ProductTable.ListProductsByName.IN.NAME, "%" + nameFilter + "%");
                ResultSet result = statement.executeQuery();
                return productsFromResult(result);
            });
        } catch (SQLException e) {
            throw new ProductStoreException(e);
        }
    }

    private Product productFromResult(ResultSet result) throws SQLException, ProductStoreException {
        Product product = new Product();
        product.setId(result.getInt(ProductTable.Product.OUT.ID));
        product.setName(result.getString(ProductTable.Product.OUT.NAME));
        product.setDescription(result.getString(ProductTable.Product.OUT.DESCRIPTION));
        product.setCount(result.getInt(ProductTable.Product.OUT.COUNT));
        product.setCost(result.getInt(ProductTable.Product.OUT.COST));
        product.setImageId(result.getInt(ProductTable.Product.OUT.IMAGE_ID));
        //product.setImageIds(listProductImages(product)); - to support multiple images later.
        return product;
    }

    private List<Product> productsFromResult(ResultSet result) throws SQLException, ProductStoreException {
        List<Product> products = new ArrayList<>();

        while (result.next()) {
            products.add(productFromResult(result));
        }
        return products;
    }

    @Override
    public List<Integer> listProductImages(Product product) throws ProductStoreException {
        try {
            return Database.prepared(ProductTable.ListProductImages.QUERY, (connection, statement) -> {
                List<Integer> ids = new ArrayList<>();

                statement.setInt(ProductTable.ListProductImages.IN.PRODUCT_ID, product.getId());
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    ids.add(result.getInt(ProductTable.ListProductImages.OUT.IMAGE_ID));
                }
                return ids;
            });
        } catch (SQLException e) {
            throw new ProductStoreException(e);
        }
    }

    @Override
    public List<Product> listProductsByCategory(Category category) throws ProductStoreException {
        try {
            return Database.prepared(ProductTable.ListProductsByCategory.QUERY, (connection, statement) -> {
                statement.setInt(ProductTable.ListProductsByCategory.IN.ID, category.getCategoryId());
                ResultSet result = statement.executeQuery();
                return productsFromResult(result);
            });
        } catch (SQLException e) {
            throw new ProductStoreException(e);
        }
    }

    @Override
    public Product getProductById(int id) throws ProductStoreException {
        try {
            return Database.prepared(ProductTable.GetProductById.QUERY, (connection, statement) -> {
                statement.setInt(ProductTable.GetProductById.IN.ID, id);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    return productFromResult(result);
                } else
                    throw new ProductStoreException("Product not found: " + id);
            });
        } catch (SQLException e) {
            throw new ProductStoreException(e);
        }
    }

    @Override
    public void updateProduct(int id, int cost, int quantity, String name, String description) {
        try {
            Database.prepared(ProductTable.UpdateProductById.QUERY, (connection, statement) -> {
                statement.setInt(ProductTable.UpdateProductById.IN.PRODUCT_ID, id);
                statement.setInt(ProductTable.UpdateProductById.IN.COST, cost);
                statement.setInt(ProductTable.UpdateProductById.IN.QUANTITY, quantity);
                statement.setString(ProductTable.UpdateProductById.IN.NAME, name);
                statement.setString(ProductTable.UpdateProductById.IN.DESCRIPTION, description);
                statement.execute();
                return null;
            });
        } catch (SQLException e) {
            throw new ProductStoreException(e);
        }
    }

    @Override
    public void addProduct(Product product, int categoryId) throws ProductStoreException {
        try {
            Database.prepared(ProductTable.AddProduct.QUERY, (connection, statement) -> {
                int productId = 1;
                connection.setAutoCommit(false);

                statement.setInt(ProductTable.AddProduct.IN.COST, product.getCost());
                statement.setInt(ProductTable.AddProduct.IN.COUNT, product.getCount());
                statement.setString(ProductTable.AddProduct.IN.DESCRIPTION, product.getDescription());
                statement.setString(ProductTable.AddProduct.IN.NAME, product.getName());
                statement.execute();

                ResultSet result = statement.getGeneratedKeys();

                if (result.next()) {
                    productId = result.getInt(1);
                }

                try (PreparedStatement update =
                             connection.prepareStatement(ProductTable.SetProductCategory.QUERY)) {
                    update.setInt(ProductTable.SetProductCategory.IN.CATEGORY_ID, categoryId);
                    update.setInt(ProductTable.SetProductCategory.IN.PRODUCT_ID, productId);
                    update.execute();
                }

                connection.commit();
                return null;
            });
        } catch (SQLException e) {
            throw new ProductStoreException(e);
        }
    }

    @Override
    public void addCategory(String category) throws ProductStoreException {
        try {
            Database.prepared(ProductTable.AddCategory.QUERY, (connection, statement) -> {
                statement.setString(ProductTable.AddCategory.IN.NAME, category);
                statement.execute();
                return null;
            });
        } catch (SQLException e) {
            throw new ProductStoreException(e);
        }
    }

    @Override
    public void setProductImage(int productId, String image) throws ProductStoreException {
        try {
            Database.prepared(ProductTable.InsertProductImage.QUERY, (connection, statement) -> {
                connection.setAutoCommit(false);

                InputStream input = new ByteArrayInputStream(image.getBytes());
                statement.setInt(ProductTable.InsertProductImage.IN.PRODUCT_ID, productId);
                statement.setBinaryStream(ProductTable.InsertProductImage.IN.IMAGE, input, image.getBytes().length);
                statement.execute();

                ResultSet generated = statement.getGeneratedKeys();
                if (generated.next()) {
                    PreparedStatement update = connection.prepareStatement(ProductTable.SetProductImage.QUERY);
                    update.setInt(ProductTable.SetProductImage.IN.PRODUCT_ID, productId);
                    update.setInt(ProductTable.SetProductImage.IN.IMAGE, generated.getInt(ProductTable.InsertProductImage.OUT.IMAGE));
                    update.execute();
                }

                connection.commit();
                return null;
            });
        } catch (SQLException e) {
            throw new ProductStoreException(e);
        }
    }

    @Override
    public Image getProductImage(int imageId) throws ProductStoreException {
        Image image = new Image();
        try {
            Database.prepared(ProductTable.GetProductImage.QUERY, (connection, statement) -> {
                statement.setInt(ProductTable.GetProductImage.IN.IMAGE_ID, imageId);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    image.setImageId(imageId);
                    InputStream stream = result.getBlob(ProductTable.GetProductImage.OUT.IMAGE).getBinaryStream();
                    String data = new BufferedReader(new InputStreamReader(stream)).lines()
                            .collect(Collectors.joining());

                    image.setData(data);
                } else {
                    throw new ProductStoreException("Product image not found: " + imageId);
                }
                return null;
            });
        } catch (SQLException e) {
            throw new ProductStoreException(e);
        }
        return image;
    }
}
