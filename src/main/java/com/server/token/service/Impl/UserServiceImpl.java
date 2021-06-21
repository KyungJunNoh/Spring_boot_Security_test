package com.server.token.service.Impl;

import com.server.token.domain.entity.User;
import com.server.token.exception.UserNotFoundException;
import com.server.token.repository.UserRepository;
import com.server.token.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void join(User user) {
        userRepository.save(user);
    }

    @Override
    public User read(Long idx) {
        return userRepository.findById(idx).orElseThrow(UserNotFoundException :: new);
    }


}
