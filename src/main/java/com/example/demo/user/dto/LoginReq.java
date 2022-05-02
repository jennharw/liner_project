package com.example.demo.user.dto;

import lombok.Getter;

@Getter
public class LoginReq {
    private String username;
    private String password;
    private String refreshToken;
}
