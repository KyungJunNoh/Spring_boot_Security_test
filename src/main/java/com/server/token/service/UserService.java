package com.server.token.service;

import com.server.token.domain.dto.LoginDto;
import com.server.token.domain.dto.UserDto;
import com.server.token.domain.entity.User;

import java.util.Map;

public interface UserService {
    User signup(UserDto userDto);
    Map<String, String> signin(LoginDto loginDto);
//    String findPassword();
    void testEmail();
}
