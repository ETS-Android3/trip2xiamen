package com.t2xm.dao;

import com.t2xm.entity.User;
import com.t2xm.utils.LoggingUtil;

import org.junit.Test;

public class UserDaoTest extends DaoTest {

    private User user = new User(null, "testuser", "test@mail.com", "password", null);

    @Test
    public void insertUser() {
        Boolean result = UserDao.insertUser(user);
        LoggingUtil.printMessage("insert user", result);
    }

    @Test
    public void insertUserAndGetUserId() {
        long result = UserDao.insertUserAndGetUserId(user);
        LoggingUtil.printMessage("insert user and get userid", result);
    }
}