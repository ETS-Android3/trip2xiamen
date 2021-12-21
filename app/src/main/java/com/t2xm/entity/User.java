package com.t2xm.entity;

import androidx.annotation.Nullable;

import java.util.Arrays;

public class User {
    public Integer userId;
    public String username;
    public String email;
    public String password;
    public byte[] profileImg;

    public User() {
    }

    public User(@Nullable Integer userId, String username, String email, String password, @Nullable byte[] profileImg) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileImg = profileImg;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profileImg=" + Arrays.toString(profileImg) +
                '}';
    }
}
