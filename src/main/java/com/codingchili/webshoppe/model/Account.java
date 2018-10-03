package com.codingchili.webshoppe.model;

/**
 * Created by Robin on 2015-09-28.
 *
 * A user account.
 */

public class Account implements Bean {
    private String username;
    private String password;
    private String salt;
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
        this.salt = HashHelper.generateSalt();
        this.password = HashHelper.hash(password, this.salt);
    }

    protected Account setRole(Role role) {
        this.role = role;
        return this;
    }

    public Role getRole() {
        return this.role;
    }

    /**
     * @param password In plaintext format.
     */
    protected boolean authenticate(String password) {
        return HashHelper.hash(password, salt).equals(this.password);
    }

    public String getUsername() {
        return username;
    }

    protected String getPassword() {
        return password;
    }

    protected String getSalt() {
        return salt;
    }

    protected Account setSalt(String salt) {
        this.salt = salt;
        return this;
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

}
