package com.server.token.controller;

import com.server.token.domain.dto.LoginDto;
import com.server.token.domain.dto.UserDto;
import com.server.token.domain.entity.User;
import com.server.token.service.UserService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public User signup(@ApiParam("Signup User") @RequestBody UserDto userDto){
        return userService.signup(userDto);
    }

    // 로그인
    @PostMapping("/signin")
    public Map<String, String> signin(@ApiParam("Signin User") @RequestBody LoginDto loginDto){
        return userService.signin(loginDto);
    }

}

