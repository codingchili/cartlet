package com.codingchili.webshoppe.model;

/**
 * Created by Robin on 2015-10-02.
 *
 * Defines levels of user access.
 */
public class Role {
    public enum Actor {Admin, Manager, User}

    private Actor actor;

    public Role(Actor actor) {
        this.actor = actor;
    }

    protected Role(int roleId) {
        switch (roleId) {
            case 3:
                actor = Actor.Admin;
                break;
            case 2:
                actor = Actor.Manager;
                break;
            case 1:
                actor = Actor.User;
                break;
        }
    }

    public int getId() {
        switch (actor) {
            case Admin:
                return 3;
            case Manager:
                return 2;
            case User:
                return 1;
            default:
                return 1;
        }
    }

    public boolean is(Actor actor) {
        return this.actor.equals(actor);
    }
}
