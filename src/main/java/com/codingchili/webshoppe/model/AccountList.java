package com.codingchili.webshoppe.model;

import com.codingchili.webshoppe.model.Account;

import java.util.ArrayList;

/**
 * Created by Robin on 2015-10-02.
 *
 * Used as a proxy as JSTL does not deal with generic list.
 */
public class AccountList {
    private ArrayList<Account> accounts = new ArrayList<>();

    public AccountList() {
    }

    public AccountList(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }


    public ArrayList<Account> getAccounts() {
        return accounts;
    }
}
