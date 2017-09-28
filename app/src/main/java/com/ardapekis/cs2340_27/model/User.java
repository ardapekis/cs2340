package com.ardapekis.cs2340_27.model;

/**
 * Created by jason on 9/28/2017.
 */

public class User {
    private String username;
    private String password;
    private boolean admin;

    public User() {
        this("user", "pass", false);
    }

    public User(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return admin;
    }
}
