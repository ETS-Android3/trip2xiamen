package com.t2xm.dao;

import com.t2xm.entity.User;

import org.junit.Test;

public class UserDaoTest {

    private User user = new User(null, "testuser", "test@mail.com", "password", null);

    @Test
    public void insertUser() {
        Boolean result = UserDao.insertUser(user);
    }

    @Test
    public void insertUserAndGetUserId() {
        long result = UserDao.insertUserAndGetUserId(user);
    }
}