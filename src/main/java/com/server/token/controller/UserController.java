package com.server.token.controller;

import com.server.token.domain.dto.FindPasswordDto;
import com.server.token.domain.dto.LoginDto;
import com.server.token.domain.dto.UserDto;
import com.server.token.domain.dto.UserEmailDto;
import com.server.token.domain.entity.User;
import com.server.token.service.UserService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
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

    // 이메일 인증
    @PostMapping("/email")
    public String emailAuthentication(@RequestBody UserEmailDto userEmailDto){
        return userService.emailAuthentication(userEmailDto);
    }

    // 비밀번호 찾기 (변경)
    @PostMapping("/findPassword")
    public String changePassword(@RequestBody FindPasswordDto findPasswordDto){
        return userService.findPassword(findPasswordDto);
    }

}

