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
    
    private Map<String, String> _users;
    private List<User> _userList;
    private User loggedInUser;
    
    private UserManager() {
        _users = new HashMap<>();
        _userList = new ArrayList<>();
        loggedInUser = null;
    }

    public boolean addUser(User user) {
        for (User u : _userList) {
            if (u.equals(user)) return false;
        }
        _userList.add(user);
        _users.put(user.getUsername(), user.getPassword());
        return true;
    }

    public void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public boolean containsUser(User user) {
        for (User u : _userList) {
            if (u.equals(user)) return true;
        }
        return false;
    }

    public boolean containsUser(String user) {
        for (User u: _userList) {
            if (u.checkUsername(user)) return true;
        }
        return false;
    }

    public boolean checkUserCredentials(String username, String password) {
        if (!containsUser(username)) {
            return false;
        }
        return password.equals(_users.get(username));
    }
}
