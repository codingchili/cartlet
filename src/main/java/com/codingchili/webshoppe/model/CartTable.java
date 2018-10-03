package com.codingchili.webshoppe.model;

/**
 * Created by Robin on 2015-09-30.
 *
 * MySQL queries and column indexes.
 */

abstract class CartTable {
    class AddToCart {
        public static final String QUERY = "INSERT INTO cart (product, owner, count) VALUES (?, ?, ?);";

        class IN {
            public static final int PRODUCT = 1;
            public static final int OWNER = 2;
            public static final int COUNT = 3;
        }
    }

    class UpdateCartCount {
        public static final String QUERY = "UPDATE cart SET count = ? WHERE product = ? AND owner = ?;";

        class IN {
            public static final int COUNT = 1;
            public static final int PRODUCT = 2;
            public static final int OWNER = 3;
        }
    }

    class RemoveFromCart {
        public static final String QUERY = "DELETE FROM cart WHERE product = ? AND owner = ?;";

        class IN {
            public static final int PRODUCT = 1;
            public static final int OWNER = 2;
        }
    }

    class ClearCart {
        public static final String QUERY = "DELETE FROM cart WHERE owner = ?;";

        class IN {
            public static final int OWNER = 1;
        }
    }

    class ProductCount {
        public static final String QUERY = "SELECT sum(count) FROM cart WHERE owner = ?;";

        class IN {
            public static final int OWNER = 1;
        }

        class OUT {
            public static final int COUNT = 1;
        }
    }

    class ProductIdCount {
        public static final String QUERY = "SELECT count FROM cart WHERE owner = ? AND product = ?;";

        class IN {
            public static final int OWNER = 1;
            public static final int PRODUCT = 2;
        }

        class OUT {
            public static final int COUNT = 1;
        }
    }

    class GetCart {
        public static final String QUERY =
                "SELECT product.id, cart.count, product.name, product.cost " +
                "FROM cart, product " +
                "WHERE cart.owner = ? AND cart.product = product.id;";

        class IN {
            public static final int OWNER = 1;
        }

        class OUT {
            public static final int PRODUCT = 1;
            public static final int COUNT = 2;
            public static final int NAME = 3;
            public static final int COST = 4;
        }
    }
}