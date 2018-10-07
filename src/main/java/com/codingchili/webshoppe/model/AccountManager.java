package com.codingchili.webshoppe.model;

import com.codingchili.webshoppe.model.exception.*;

import java.util.ArrayList;

/**
 * Created by Robin on 2015-09-28.
 * <p>
 * Handles the registration and authentication of accounts.
 */

public abstract class AccountManager {
    private static final int PASSWORD_MIN_LENGTH = 9;
    private static final int ACCOUNT_MIN_LENGTH = 4;

    /**
     * @param username of the account to be authenticate.
     * @param password for use in the authentication attempt.
     */
    public static LoginResult authenticate(String username, String password) {
        LoginResult result = new LoginResult();
        AccountStore accounts = Store.getAccountStore();
        Account account;

        try {
            account = accounts.findByUsername(username);
            result.setAccount(account);

            if (!HashHelper.verify(account.getPassword(), password)) {
                throw new AccountStoreException("Failed to authenticate user: " + username);
            }
        } catch (AccountStoreException e) {
            result.setErroneous(true);
        }
        return result;
    }


    public static RegisterResult registerAdmin(
            String username, String password, String zip, String street) {
        return register(username, password, zip, street, new Role(Role.Actor.Admin));
    }


    public static RegisterResult register(
            String username, String password, String zip, String street) {
        return register(username, password, zip, street, new Role(Role.Actor.User));
    }

    private static RegisterResult register(String username, String password, String zip, String street, Role role) {
        RegisterResult registerResult = verifyInput(username, password, zip, street);
        AccountStore store = Store.getAccountStore();

        if (!registerResult.isErroneous()) {
            try {
                Account account = new Account(username, password);
                account.setZip(zip);
                account.setStreet(street);
                account.setRole(role);
                store.add(account);
                registerResult.setAccount(store.findByUsername(username));
            } catch (AccountExistsException e) {
                registerResult.setAccountExists(true);
            } catch (AccountStoreException e) {
                registerResult.setServerError(true);
            }
        }
        return registerResult;
    }

    private static RegisterResult verifyInput(
            String username, String password, String zip, String street) {
        return new RegisterResult()
                .setZipSet(zip.length() != 0)
                .setStreetSet(street.length() != 0)
                .setAccount(new Account()
                        .setUsername(username)
                        .setZip(zip)
                        .setStreet(street)
                        .setPassword(password)
                )
                .setPasswordLowEntropy(password.length() < PASSWORD_MIN_LENGTH)
                .setAccountNameTooShort(username.length() < ACCOUNT_MIN_LENGTH);
    }

    public static DeRegisterResult deRegister(int accountId) {
        Account account = new Account();
        account.setId(accountId);
        return deRegister(account);
    }

    /**
     * Removes a registered account from the Store.
     *
     * @param account to be removed.
     */
    public static DeRegisterResult deRegister(Account account) {
        DeRegisterResult result = new DeRegisterResult();
        AccountStore accounts = Store.getAccountStore();
        OrderStore orders = Store.getOrderStore();
        try {
            accounts.deRegister(account);
            orders.clearOrders(account);
        } catch (NoSuchAccountException e) {
            result.setAccountNotFound(true);
        } catch (StoreException e) {
            result.setErroneous(true);
        }
        return result;
    }

    public static RegisterResult registerManager(String username, String password, String zip, String street) {
        return register(username, password, zip, street, new Role(Role.Actor.Manager));
    }

    public static ArrayList<Account> getManagers() {
        ArrayList<Account> managers = new ArrayList<>();
        AccountStore accounts = Store.getAccountStore();
        try {
            managers = accounts.getManagers();
        } catch (AccountStoreException ignored) {
        }
        return managers;
    }
}
