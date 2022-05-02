package com.example.demo.user.dto;

import lombok.Getter;

@Getter
public class SignupReq {
    private String username;

    private String nickname;

    private String password;

    public SignupReq(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
