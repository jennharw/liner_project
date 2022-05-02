package com.example.demo.user.service;


import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import com.example.demo.user.dto.SignupReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public void register(SignupReq signupReq) throws Exception {

        //중복확인
        Optional<User> found = userRepository.findByUserId(signupReq.getUsername());
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        User user = User.builder()
                        .userId(signupReq.getUsername())
                        .password(passwordEncoder.encode(signupReq.getPassword()))
                        .nickname(signupReq.getNickname())
                        .build();
        userRepository.save(user);
    }

}
