package com.ardapekis.cs2340_27.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jason on 9/28/2017.
 */

public class UserManager {
    private static final UserManager _instance = new UserManager();
    public static UserManager getInstance() { return _instance; }
    
    private Map<String, String> _usersPasswords;
    private Map<String, User> _users;
    private User loggedInUser;
    
    private UserManager() {
        _usersPasswords = new HashMap<>();
        _users = new HashMap<>();
        loggedInUser = null;
    }

    public boolean addUser(User user) {
        if (_users.containsValue(user)) {
            return false;
        }
        _users.put(user.getUsername().toLowerCase(), user);
        _usersPasswords.put(user.getUsername().toLowerCase(), user.getPassword());
        return true;
    }

    public User getUser(String username) {
        return _users.get(username.toLowerCase());
    }

    public void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public boolean containsUser(User user) {
        return _users.containsValue(user);
    }

    public boolean containsUser(String username) {
        return _users.containsKey(username.toLowerCase());
    }

    public boolean checkUserCredentials(String username, String password) {
        if (!containsUser(username)) {
            return false;
        }
        return password.equals(_usersPasswords.get(username.toLowerCase()));
    }
}
