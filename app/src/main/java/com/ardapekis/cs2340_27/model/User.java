package com.ardapekis.cs2340_27.model;

/**
 * Information holder - represents a regular user in model
 */

public class User {

    /** The user's username, will be stored case sensitive, but checked case
     * insensitive */
    private final String username;

    /** The user's password, case sensitive */
    private final String password;

    /** Make a new user, default "user", "pass" */
    public User() {
        this("user", "pass");
    }

    /** Make a new user
     *
     * @param username      The user's username
     * @param password      The user's password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     *
     * Getter
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * Getter
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Checks that the user's username matches a provided one, case insensitive
     * @param username      Username to check
     * @return              True if the usernames match, false if not
     */
    private boolean checkUsername(String username) {
        return this.username.equalsIgnoreCase(username);
    }

//    /**
//     * Checks that the user's password matches a provided one, case sensitive
//     * @param password      The password to check
//     * @return              True if the passwords match, false if not
//     */
//    public boolean checkPassword(String password) {
//        return this.password.equals(password);
//    }

    /**
     * Equals method, checks that usernames match, case insensitive
     * @param o     The object to check this object with
     * @return      True if the objects are the same or their usernames are the
     *              same
     */
    @Override
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
}
