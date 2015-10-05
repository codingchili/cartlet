package Model;

/**
 * Created by Robin on 2015-09-30.
 *
 * SQL Queries and prepared statement indexes for the product storage.
 */

abstract class ProductTable {
    class ListCategories {
        public final static String QUERY = "SELECT id, name FROM category;";

        class OUT {
            public final static int ID = 1;
            public final static int NAME = 2;
        }
    }

    class ListProductImages {
        public final static String QUERY = "SELECT id FROM image WHERE product = ?;";

        class IN {
            public final static int PRODUCT_ID = 1;
        }

        class OUT {
            public final static int IMAGE_ID = 1;
        }
    }

    class ListProductsByName {
        public final static String QUERY = "SELECT * FROM product WHERE name LIKE ?;";

        class IN {
            public final static int NAME = 1;
        }
    }

    class Product {
        class OUT {
            public final static int ID = 1;
            public final static int NAME = 2;
            public final static int DESCRIPTION = 3;
            public final static int COUNT = 4;
            public final static int COST = 5;
        }
    }

    class GetProductById {
        public final static String QUERY = "SELECT * FROM product WHERE id = ?;";

        class IN {
            public final static int ID = 1;
        }
    }

    class ListProductsByCategory {
     public final static String QUERY = "SELECT * FROM product, product_category " +
             "WHERE product.id = product_category.product " +
             "AND product_category.category = ?;";

        class IN {
            public final static int ID = 1;
        }
    }

    class UpdateProductById {
        public final static String QUERY =
                "UPDATE product SET cost = ?, count = count + ?, name = ?, description = ? WHERE id = ?;";

        class IN {
            public final static int COST = 1;
            public final static int QUANTITY = 2;
            public final static int NAME = 3;
            public final static int DESCRIPTION = 4;
            public final static int PRODUCT_ID = 5;
        }
    }

    class AddProduct {
        public final static String QUERY =
                "INSERT INTO product (name, description, count, cost) VALUES (?, ?, ?, ?);";

        class IN {
            public final static int NAME = 1;
            public final static int DESCRIPTION = 2;
            public final static int COUNT = 3;
            public final static int COST = 4;
        }
    }

    class SetProductCategory {
        public final static String QUERY =
                "INSERT INTO product_category (product, category) VALUES (?, ?);";

        class IN {
            public final static int PRODUCT_ID = 1;
            public final static int CATEGORY_ID = 2;
        }
    }

    class AddCategory {
        public final static String QUERY =
                "INSERT INTO category (name) VALUES (?);";

        class IN {
            public final static int NAME = 1;
        }
    }

    class SetProductImage {
        public final static String QUERY =
                "REPLACE INTO image (product, data) VALUES (?, ?);";

        class IN {
            public final static int PRODUCT_ID = 1;
            public final static int IMAGE = 2;
        }
    }

    class GetProductImage {
        public final static String QUERY =
                "SELECT data FROM image WHERE id = ?;";

        class IN {
            public final static int IMAGE_ID = 1;
        }

        class OUT {
            public final static int IMAGE = 1;
        }
    }
}
