package com.server.token.service;

import com.server.token.domain.dto.UserRequestDto;
import com.server.token.domain.entity.User;

import java.util.Optional;

public interface UserService {
    void join(User user);
    User read(Long idx);
    void update(Long idx, UserRequestDto userRequestDto);
    void delete(Long idx);
}
