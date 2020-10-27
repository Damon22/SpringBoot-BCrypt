package com.damonteam.shopingmall.entity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class User {
    String name;
    String password;
    public String getPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode("password");
    }
    public String getName() {
        return "name";
    }
}
