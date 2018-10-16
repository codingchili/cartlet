package com.codingchili.webshoppe.model;

/**
 * Created by Robin on 2015-10-01.
 *
 * Queries related to orders, contains mappings in/out for prepared statements.
 */

abstract class OrderTable {
    class CreateOrder {
        public static final String QUERY = "INSERT INTO `order` (owner, created, status, changed, total, item_count) VALUES (?, ?, 0, ?, ?, ?);";

        class IN {
            public static final int OWNER = 1;
            public static final int CREATED = 2;
            public static final int CHANGED = 3;
            public static final int TOTAL = 4;
            public static final int ITEM_COUNT = 5;
        }

        class OUT {
            public static final int ORDER_ID = 1;
        }
    }

    class AddToOrder {
        public static final String QUERY = "INSERT INTO `order_product` (`order`, product, count) VALUES (?, ?, ?);";

        class IN {
            public static final int ORDER = 1;
            public static final int PRODUCT = 2;
            public static final int COUNT = 3;
        }
    }

    class GetOrder {
        public static final String QUERY =
                "SELECT * " +
                        "FROM `order` " +
                        "WHERE " +
                        "`order`.id = ? " +
                        "AND `order`.owner = ?;";

        class IN {
            public static final int ORDER_ID = 1;
            public static final int OWNER_ID = 2;
        }

        class OUT {
            public static final int ORDER_ID = 1;
            public static final int OWNER = 2;
            public static final int CREATED = 3;
            public static final int STATUS = 4;
            public static final int CHANGED = 5;
            public static final int TOTAL = 6;
            public static final int ITEM_COUNT = 7;
        }
    }

    class GetOrderUnchecked {
        public static final String QUERY =
                "SELECT * " +
                        "FROM `order` " +
                        "WHERE " +
                        "`order`.id = ?;";

        class IN {
            public static final int ORDER_ID = 1;
        }
    }

    class GetOrders {
        public static final String QUERY = "SELECT * FROM `order` WHERE `order`.owner = ? ORDER BY created DESC;";

        class IN {
            public static final int OWNER = 1;
        }

        class OUT {
            public static final int ORDER_ID = 1;
            public static final int OWNER = 2;
            public static final int CREATED = 3;
            public static final int STATUS = 4;
            public static final int CHANGED = 5;
            public static final int TOTAL = 6;
            public static final int ITEM_COUNT = 7;
        }
    }

    class GetOrderItems {
        public static final String QUERY =
                "SELECT product.name, order_product.count, product.cost, product.id, product.image FROM order_product, product WHERE " +
                        "order_product.order = ? " +
                        "AND order_product.product = product.id;";

        class IN {
            public static final int ORDER_ID = 1;
        }

        class OUT {
            public static final int NAME = 1;
            public static final int COUNT = 2;
            public static final int COST = 3;
            public static final int PRODUCT_ID = 4;
            public static final int IMAGE = 5;
        }
    }

    class ClearOrders {
        public static final String CLEAR_ORDERS = "DELETE FROM `order` WHERE owner = ?;";
        public static final String CLEAR_ORDERS_ITEMS = "DELETE FROM order_product WHERE " +
                "`order` IN (SELECT id FROM `order` WHERE owner = ?);";

        class IN {
            public static final int OWNER_ID = 1;
        }
    }

    class GetStatistics {
        public static final String QUERY = "SELECT STATUS,COUNT(status),AVG(total),AVG(item_count) " +
                "FROM `order` GROUP BY status order by STATUS ASC;";

        class OUT {
            public static final int STATUS = 1;
            public static final int COUNT_STATUS = 2;
            public static final int AVG_COST = 3;
            public static final int AVG_ITEMS = 4;
        }
    }

    class GetOrderForShipping {
        public static final String QUERY = "" +
                "SELECT * FROM `order` WHERE status = 1 " +
                "AND `order`.id " +
                "NOT IN (" +
                "SELECT order_product.`order` " +
                "FROM order_product, product " +
                "WHERE order_product.count > product.count " +
                "AND order_product.product = product.id) " +
                "LIMIT 1;";

        class OUT {
            public static final int ORDER_ID = 1;
            public static final int OWNER_ID = 2;
            public static final int CREATED = 3;
            public static final int STATUS = 4;
            public static final int CHANGED = 5;
            public static final int TOTAL = 6;
            public static final int ITEM_COUNT = 7;
        }
    }

    class SetOrderStatus {
        public static final String QUERY = "" +
                "UPDATE `order` SET `order`.status = ?, `order`.changed = ? WHERE `order`.id = ?;";

        class IN {
            public static final int STATUS = 1;
            public static final int CHANGED = 2;
            public static final int ORDER_ID = 3;
        }
    }

    class DeductStockByOrder {
        public static final String QUERY = "" +
                "UPDATE product SET count = count - (" +
                "SELECT count FROM order_product " +
                "WHERE `order` = ? " +
                "AND order_product.product = product.id) " +
                "WHERE product.id IN (SELECT product FROM order_product " +
                "WHERE order_product.`order` = ?)";

        class IN {
            public static final int ORDER_ID1 = 1;
            public static final int ORDER_ID2 = 2;
        }
    }
}
