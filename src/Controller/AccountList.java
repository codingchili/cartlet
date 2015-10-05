package Controller;

import java.util.ArrayList;

/**
 * Created by Robin on 2015-10-02.
 *
 * Used as a proxy as JSTL does not deal with generic list.
 */
public class AccountList {
    private ArrayList<Model.Account> accounts = new ArrayList<>();

    public AccountList() {
    }

    public AccountList(ArrayList<Model.Account> accounts) {
        this.accounts = accounts;
    }


    public ArrayList<Model.Account> getAccounts() {
        return accounts;
    }
}
