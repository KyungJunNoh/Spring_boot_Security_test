package com.server.token.service;

import com.server.token.domain.entity.User;

import java.util.Optional;

public interface UserService {
    void join(User user);
    Optional<User> read(Long idx);
}
