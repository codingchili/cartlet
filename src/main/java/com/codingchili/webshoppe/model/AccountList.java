package com.codingchili.webshoppe.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robin on 2015-10-02.
 *
 * Used as a proxy as JSTL does not deal with generic list.
 */
public class AccountList {
    private List<Account> accounts = new ArrayList<>();

    public AccountList() {
    }

    public AccountList(List<Account> accounts) {
        this.accounts = accounts;
    }


    public List<Account> getAccounts() {
        return accounts;
    }
}
