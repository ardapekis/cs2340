package com.ardapekis.cs2340_27.model;

/**
 * Information holder - Admin type of user
 */

public class Admin extends User {

//    /**
//     * Default constructor
//     */
//    public Admin() {
//        super("user", "pass");
//    }

    /**
     * Makes a new Admin
     * @param username      The admin's username
     * @param password      The admin's password
     */
    public Admin(String username, String password) {
        super(username, password);
    }
}
