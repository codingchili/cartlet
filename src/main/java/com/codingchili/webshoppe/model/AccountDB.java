package com.codingchili.webshoppe.model;

import com.codingchili.webshoppe.model.exception.*;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Robin on 2015-09-28.
 * <p>
 * Uses a database connection to Implement an AccountStore.
 */

class AccountDB implements AccountStore {

    @Override
    public boolean exists(String username) throws AccountStoreException {
        try {
            return Database.prepared(AccountTable.Exists.QUERY, (connection, statement) -> {
                statement.setString(AccountTable.Exists.IN.NAME, username);
                return statement.executeQuery().getInt(0) != 0;
            });
        } catch (SQLException e) {
            throw new AccountStoreException(e);
        }
    }

    @Override
    public void add(Account account) throws AccountStoreException {
        try {
            Database.prepared(AccountTable.Add.QUERY, (connection, statement) -> {
                statement.setString(AccountTable.Add.IN.NAME, account.getUsername());
                statement.setString(AccountTable.Add.IN.PASSWORD, account.getPassword());
                statement.setString(AccountTable.Add.IN.ZIP, account.getZip());
                statement.setString(AccountTable.Add.IN.STREET, account.getStreet());
                statement.setInt(AccountTable.Add.IN.ROLE, account.getRole().getId());
                statement.execute();
                return null;
            });
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                throw new AccountExistsException(e);
            } else {
                throw new AccountStoreException(e);
            }
        }
    }


    @Override
    public Account findByUsername(String username) throws AccountStoreException {
        try {
            return Database.prepared(AccountTable.FindByName.QUERY, (connection, statement) -> {
                statement.setString(AccountTable.FindByName.IN.NAME, username);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    return accountFromResult(result);
                } else
                    throw new NoSuchAccountException("User not found: " + username);
            });
        } catch (SQLException e) {
            throw new AccountStoreException(e);
        }
    }

    private Account accountFromResult(ResultSet result) throws SQLException {
        return new Account()
                .setId(result.getInt(AccountTable.FindByName.OUT.ID))
                .setUsername(result.getString(AccountTable.FindByName.OUT.NAME))
                .setPassword(result.getString(AccountTable.FindByName.OUT.PASSWORD))
                .setStreet(result.getString(AccountTable.FindByName.OUT.STREET))
                .setZip(result.getString(AccountTable.FindByName.OUT.ZIP))
                .setRole(new Role(result.getInt(AccountTable.FindByName.OUT.ROLE)));
    }

    @Override
    public void deRegister(Account account) throws AccountStoreException {
        try {
            Database.prepared(AccountTable.RemoveById.QUERY, (connection, statement) -> {
                statement.setInt(AccountTable.RemoveById.IN.ACCOUNT_ID, account.getId());
                statement.execute();

                if (statement.getUpdateCount() == 0) {
                    throw new NoSuchAccountException("Account not found: " + account.getUsername());
                } else {
                    return null;
                }
            });
        } catch (SQLException e) {
            throw new AccountStoreException(e);
        }
    }

    @Override
    public ArrayList<Account> getManagers() throws AccountStoreException {
        ArrayList<Account> managers = new ArrayList<>();
        try {
            Database.prepared(AccountTable.GetManagers.QUERY, (connection, statement) -> {
                ResultSet result = statement.executeQuery();

                while (result.next()) {
                    managers.add(accountFromResult(result));
                }
                return null;
            });
        } catch (SQLException e) {
            throw new AccountStoreException(e);
        }
        return managers;
    }

    @Override
    public Account findById(int accountId) throws AccountStoreException {
        try {
            return Database.prepared(AccountTable.FindById.QUERY, (connection, statement) -> {
                statement.setInt(AccountTable.FindById.IN.ACCOUNT_ID, accountId);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    return accountFromResult(result);
                } else {
                    throw new AccountStoreException("account does not exist");
                }
            });
        } catch (SQLException e) {
            throw new AccountStoreException(e);
        }
    }
}
