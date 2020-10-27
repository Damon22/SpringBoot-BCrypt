package com.damonteam.shopingmall.controller;

import com.damonteam.shopingmall.configuration.JwtToken;
import com.damonteam.shopingmall.configuration.SecurityConfig;
import com.damonteam.shopingmall.entity.User;
import com.damonteam.shopingmall.services.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.Arrays;
import java.util.List;

@RestController
public class JwtController {
    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private SecurityConfig config;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(String loginName, String password) {
        // 1. search user data, and check username and password
        User user = userService.findLoginName(loginName);
        //
        if (user != null && config.compareBCryptEncoder(password, user.getPassword())) {
            System.out.println("Login success");
        } else {
            System.out.println("Login fail");
            return null;
        }
        System.out.println(loginName + " + " + password);
        // 2. check token success, and return
        String token = jwtToken.generateToken(loginName);
        return token;
    }

    @GetMapping("/getUserInfo")
    public String getUserInfo(@RequestHeader("Authorization") String authHeader) throws AuthenticationException {
        List<String> blacklistToken = Arrays.asList("Forbidden token");
        Claims claims = jwtToken.getClaimByToken(authHeader);
        if (claims == null ||
                JwtToken.isTokenExpired(claims.getExpiration()) ||
                blacklistToken.contains(authHeader))
        {
            throw new AuthenticationException("token not work");
        }

        String userId = claims.getSubject();
        // according userId get info
        return userId;
    }
}
