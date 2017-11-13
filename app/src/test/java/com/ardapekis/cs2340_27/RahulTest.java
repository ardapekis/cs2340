package com.ardapekis.cs2340_27;

import com.ardapekis.cs2340_27.model.Facade;
import com.ardapekis.cs2340_27.model.User;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RahulTest {
    @Test
    public void addUser_isCorrect() throws Exception {
        Assert.assertTrue(Facade.getInstance().getUserManager().addUser(new User()));
        Assert.assertFalse(Facade.getInstance().getUserManager().addUser(new User()));
        Assert.assertTrue(Facade.getInstance().getUserManager().addUser(new User("hello", "itsme")));
        Assert.assertTrue(Facade.getInstance().getUserManager().addUser(new User("xf", "itsme")));
        Assert.assertTrue(Facade.getInstance().getUserManager().addUser(new User("xfd", "edfas")));
        Assert.assertFalse(Facade.getInstance().getUserManager().addUser(new User("hello", "itsme")));
    }
}