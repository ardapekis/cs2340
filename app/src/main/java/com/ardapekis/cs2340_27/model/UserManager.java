package com.ardapekis.cs2340_27.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages all registered users, keeps track of logged in user, provides some
 * services
 */

public class UserManager {

    /** Hashmap containing the usernames as keys and passwords as values
     *  Used for checking usernames to passwords */
    // private Map<String, String> _usersPasswords;
    private final Map<String, String> _usersPasswords;

    /** Hashmap containing the usernames as keys and User objects as values
     *  Used for checking if a User/Username is already registered */
    // private Map<String, User> _users;
    private final Map<String, User> _users;

    /** The currently logged in user, changes the appActivity text */
    private User loggedInUser;

    /** Private constructor for singleton */
    public UserManager() {
        _usersPasswords = new HashMap<>();
        _users = new HashMap<>();
        loggedInUser = null;
    }

    /**
     *
     * Setter
     *
     * @param user the user object to be set
     */
    public void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    /**
     *
     * Getter
     *
     * @return the currently logged in user
     */
//    public User getLoggedInUser() {
//        return loggedInUser;
//    }

    /**
     * Adds a new user to the system, checks if the username is already
     * registered and does not add if they are
     * @param user      The user to add to the system
     * @return          True if the user is successfully added, false if the
     *                  user is a duplicate
     */
    public boolean addUser(User user) {
        if (_users.containsValue(user)) {
            return false;
        }
        _users.put(user.getUsername().toLowerCase(), user);
        _usersPasswords.put(user.getUsername().toLowerCase(), user.getPassword());
        return true;
    }

    /**
     * Returns a user object based on their username
     * @param username      The username of the desired User
     * @return              The desired User matched with their username
     */
    public User getUser(String username) {
        return _users.get(username.toLowerCase());
    }

//    /**
//     * Check if this user is already registered with a User object
//     * @param user      The user object to check
//     * @return          True if the user is already registered, false if not
//     */
//    public boolean containsUser(User user) {
//        return _users.containsValue(user);
//    }

    /**
     * Check if this user is already registered using their username as a String
     * @param username      The String representation of their username which is
     *                      checked ignoring case
     * @return              True if the user is already registered, false if not
     */
    public boolean containsUser(String username) {
        return _users.containsKey(username.toLowerCase());
    }

    /**
     * Checks if a username, password combination is valid
     * @param username      The username to search for
     * @param password      The password to match the username with
     * @return              True if the combination is valid, false if user is
     *                      not registered or invalid password
     */
    public boolean checkUserCredentials(String username, String password) {
        // if (!containsUser(username)) {
        //     return false;
        // }
        // return password.equals(_usersPasswords.get(username.toLowerCase()));

        return containsUser(username)
                && password.equals(_usersPasswords.get(username.toLowerCase()));

    }

    /**
     * Just for UI, helps set up spinner values in RegisterActivity
     * @return      An List containing the user types: User and Admin
     */
    public List<String> getUserTypes() {
        List<String> userTypes = new ArrayList<>();
        userTypes.add("User");
        userTypes.add("Admin");
        return userTypes;
    }
}
