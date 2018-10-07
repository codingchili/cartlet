package com.codingchili.webshoppe.model;

/**
 * Created by Robin on 2015-09-28.
 *
 * A user account.
 */

public class Account implements Bean {
    private String username;
    private String password;
    private int id;
    private String zip;
    private String street;
    private Role role = new Role(Role.Actor.User);

    public Account() {
    }

    public Account(int accountId) {
        this.id = accountId;
    }

    /**
     * Constructor for creating a NEW account.
     * @param username Must be unique within the Store.
     * @param password In plaintext format.
     */
    protected Account(String username, String password) {
        this.username = username;
        this.password = HashHelper.hash(password);
    }

    protected Account setRole(Role role) {
        this.role = role;
        return this;
    }

    public Role getRole() {
        return this.role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    protected Account setUsername(String username) {
        this.username = username;
        return this;
    }

    protected Account setPassword(String password) {
        this.password = password;
        return this;
    }

    protected Account setId(int id) {
        this.id = id;
        return this;
    }

    public int getId() {
        return id;
    }

    public Account setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public String getZip() {
        return zip;
    }

    public Account setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getStreet() {
        return street;
    }

    @Override
    public String toString() {
        return username;
    }
}
