package com.server.token.service;

import com.server.token.domain.dto.LoginDto;
import com.server.token.domain.dto.UserDto;
import com.server.token.domain.entity.User;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    String signup(UserDto userDto);
    Map<String, String> signin(LoginDto loginDto);
    User read(Long idx);
    void update(Long idx, UserDto userDto);
    void delete(Long idx);
}
