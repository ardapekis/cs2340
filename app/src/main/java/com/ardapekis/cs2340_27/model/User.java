package com.ardapekis.cs2340_27.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 9/28/2017.
 */

public class User {
    private String username;
    private String password;

    public User() {
        this("user", "pass");
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean checkUsername(String username) {
        return this.username.equalsIgnoreCase(username);
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof User)) {
            return false;
        }

        User u = (User) o;
        return u.checkUsername(username);
    }

    public static List<String> getUserTypes() {
        List<String> userTypes = new ArrayList<>();
        userTypes.add("User");
        userTypes.add("Admin");
        return userTypes;
    }
}
