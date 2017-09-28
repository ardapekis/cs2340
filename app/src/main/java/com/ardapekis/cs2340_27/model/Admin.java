package com.ardapekis.cs2340_27.model;

/**
 * Created by jason on 9/28/2017.
 */

public class Admin extends User {

    public Admin() {
        super("user", "pass", true);
    }

    public Admin(String username, String password, boolean admin) {
        super(username, password, admin);
    }
}
