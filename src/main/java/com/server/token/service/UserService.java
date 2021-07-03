package com.server.token.service;

import com.server.token.domain.dto.FindPasswordDto;
import com.server.token.domain.dto.LoginDto;
import com.server.token.domain.dto.UserDto;
import com.server.token.domain.dto.UserEmailDto;
import com.server.token.domain.entity.User;

import java.util.Map;

public interface UserService {
    User signup(UserDto userDto);
    Map<String, String> signin(LoginDto loginDto);
    String emailAuthentication(UserEmailDto userEmailDto);
    String findPassword(FindPasswordDto findPasswordDto);
}
