package com.damonteam.shopingmall.services;

import com.damonteam.shopingmall.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User findLoginName(String loginName) {
        return new User();
    }
}
