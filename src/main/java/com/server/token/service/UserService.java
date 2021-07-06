package com.server.token.service;

import com.server.token.domain.dto.*;
import com.server.token.domain.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService {
    User signup(UserDto userDto);
    Map<String, String> signin(LoginDto loginDto);
    String emailAuthentication(UserEmailDto userEmailDto);
    String findPassword(FindPasswordDto findPasswordDto);
    String changePassword(HttpServletRequest httpServletRequest, ChangePasswordRequestDto changePasswordRequestDto);
    String logout(HttpServletRequest httpServletRequest);
    String tokenCheck(String token);
}
