package com.ardapekis.cs2340_27;

import org.junit.Test;

import com.ardapekis.cs2340_27.model.RatReportItem;
import com.ardapekis.cs2340_27.model.User;

import static org.junit.Assert.*;

/**
 * Created by Austin on 11/13/2017
 */
public class AustinTest {
    @Test

    public void testEquals() throws Exception{
        User user1 = new User("username", "password");
        User user2 = new User("username", "password1");
        User user3 = new User("username1", "password");
        String fakeuser = "username";
        assertEquals(false, user1.equals(fakeuser));
        assertEquals(true, user1.equals(user1));
        assertEquals(true, user1.equals(user2));
        assertEquals(false, user1.equals(user3));
    }
}