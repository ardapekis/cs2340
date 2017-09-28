package com.ardapekis.cs2340_27.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 9/28/2017.
 */

public class UserManager {
    private static final UserManager _instance = new UserManager();
    public static UserManager getInstance() { return _instance; }
    
    private List<User> _users;
    
    private UserManager() {
        _users = new ArrayList<>();
    }
    
    public List<User> getUsers() {
        return _users;
    }

    public boolean addUser(User user) {
        for (User u : _users ) {
            if (u.equals(user)) return false;
        }
        _users.add(user);
        return true;
    }
}
