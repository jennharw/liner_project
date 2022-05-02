package com.example.demo.user.controller;

import com.example.demo.user.dto.SignupReq;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup") // ResponseEntity
    public void register(@RequestBody SignupReq registerRequestUser) throws Exception {

        userService.register(registerRequestUser);

    }

}
