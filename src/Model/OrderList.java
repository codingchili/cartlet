package Model;

import java.util.ArrayList;

/**
 * Created by Robin on 2015-10-01.
 *
 * Used to list the items in an order as JSTL cannot handle generic lists.
 */
public class OrderList {
    private ArrayList<Order> items = new ArrayList<>();

    public ArrayList<Order> getItems() {
        return items;
    }

    protected void setItems(ArrayList<Order> items) {
        this.items = items;
    }

    public int getSize() {
        return items.size();
    }
}
