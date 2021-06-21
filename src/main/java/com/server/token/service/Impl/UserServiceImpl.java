package com.server.token.service.Impl;

import com.server.token.domain.dto.UserRequestDto;
import com.server.token.domain.entity.User;
import com.server.token.exception.UserAlreadyExistsException;
import com.server.token.exception.UserNotFoundException;
import com.server.token.repository.UserRepository;
import com.server.token.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void join(User user) {
        if(userRepository.findByUserId(user.getUserId()) != null){
            throw new UserAlreadyExistsException("해당 아이디는 이미 존재합니다.");
        }
        userRepository.save(user);
    }

    @Override
    public User read(Long idx) {
        if(userRepository.findById(idx) != null){
            throw new UserNotFoundException("해당 유저를 찾을 수 없습니다.");
        }
        return userRepository.findById(idx).orElseThrow(UserNotFoundException :: new);
    }

    @Transactional
    @Override
    public void update(Long idx, UserRequestDto userRequestDto) {
        User user = read(idx);
        user.update(userRequestDto.getUserId(), userRequestDto.getUserPw());
    }

    @Override
    public void delete(Long idx) {
        userRepository.deleteById(idx);
    }


}
