package Model;

/**
 * Created by Robin on 2015-09-29.
 *
 * Table and query parameters for the Account Table.
 */

abstract class AccountTable {
    private static final String TABLE_NAME = "account";

    class Add {
        public static final String QUERY = "" +
                "INSERT INTO " + TABLE_NAME + " (name, password, salt, zip, street, role) VALUES (?, ?, ?, ?, ?, ?);";

        class IN {
            public static final int NAME = 1;
            public static final int PASSWORD = 2;
            public static final int SALT = 3;
            public static final int ZIP = 4;
            public static final int STREET = 5;
            public static final int ROLE = 6;
        }
    }

    class Exists {
        public static final String QUERY = "SELECT COUNT (*) FROM " + TABLE_NAME + " WHERE name = ?;";

        class IN {
            public static final int NAME = 1;
        }
    }

    class FindByName {
        public static final String QUERY = "SELECT id, name, password, salt, zip, street, role FROM " + TABLE_NAME + " WHERE name = ?;";

        class IN {
            public static final int NAME = 1;
        }

        class OUT {
            public static final int ID = 1;
            public static final int NAME = 2;
            public static final int PASSWORD = 3;
            public static final int SALT = 4;
            public static final int ZIP = 5;
            public static final int STREET = 6;
            public static final int ROLE = 7;
        }
    }

    class FindById {
        public static final String QUERY =
                "SELECT id, name, password, salt, zip, street, role FROM " + TABLE_NAME + " WHERE id = ?;";

        class IN {
            public static final int ACCOUNT_ID = 1;
        }
    }

    class RemoveById {
        public static final String QUERY = "DELETE FROM " + TABLE_NAME + " WHERE id = ?;";

        class IN {
            public static final int ACCOUNT_ID = 1;
        }
    }

    /**
     * Uses OUT from FindByName.
     */
    class GetManagers {
        public static final String QUERY =
                "SELECT id, name, password, salt, zip, street, role FROM " + TABLE_NAME
                        + " WHERE role = 2;";
    }
}
