package com.codingchili.webshoppe.model;

import com.codingchili.webshoppe.model.exception.*;

import java.util.ArrayList;
import java.util.List;

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


    public static RegisterResult registerAdmin(Account account) {
        return register(account, new Role(Role.Actor.Admin));
    }


    public static RegisterResult register(Account account) {
        return register(account, new Role(Role.Actor.User));
    }

    private static RegisterResult register(Account register, Role role) {
        RegisterResult registerResult = verifyInput(register);
        AccountStore store = Store.getAccountStore();

        if (!registerResult.isErroneous()) {
            try {
                Account account = new Account(register.getUsername(), register.getPassword())
                        .setRole(role)
                        .setStreet(register.getStreet())
                        .setZip(register.getZip());

                store.add(account);
                registerResult.setAccount(
                        store.findByUsername(account.getUsername())
                );
            } catch (AccountExistsException e) {
                registerResult.setAccountExists(true);
            } catch (AccountStoreException e) {
                registerResult.setServerError(true);
            }
        }
        return registerResult;
    }

    private static RegisterResult verifyInput(Account account) {
        return new RegisterResult()
                .setZipSet(account.getZip().length() != 0)
                .setStreetSet(account.getStreet().length() != 0)
                .setAccount(account)
                .setPasswordLowEntropy(account.getPassword().length() < PASSWORD_MIN_LENGTH)
                .setAccountNameTooShort(account.getUsername().length() < ACCOUNT_MIN_LENGTH)
                .setPasswordMismatch(!account.getPassword().equals(account.getPasswordRepeat()));
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

    public static RegisterResult registerManager(Account account) {
        return register(account, new Role(Role.Actor.Manager));
    }

    public static List<Account> getManagers() {
        AccountStore accounts = Store.getAccountStore();
        return accounts.getManagers();
    }
}
