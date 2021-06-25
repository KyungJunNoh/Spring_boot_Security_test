package com.server.token.service.Impl;

import com.server.token.Security.JwtTokenProvider;
import com.server.token.domain.dto.UserDto;
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
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public String signup(UserDto userDto) {
        if(userRepository.findByUserEmail(userDto.getUserEmail()) != null){
            throw new UserAlreadyExistsException("해당 아이디는 이미 존재합니다.");
        }
        userRepository.save(userDto.toEntity());

        String token = jwtTokenProvider.createToken(userDto.getUserEmail(),userDto.toEntity().getRoles());
        return "Barer " + token;
    }

    @Override
    public User read(Long idx) {
        return userRepository.findById(idx).orElseThrow(UserNotFoundException :: new); // 코드를 수정 후 뭐가 이렇게 많이 나오지?
    }

    @Transactional
    @Override
    public void update(Long idx, UserDto userDto) {
        User user = read(idx);
        user.update(userDto.getUserEmail(), userDto.getUserPw());
    }

    @Override
    public void delete(Long idx) {
        userRepository.deleteById(idx);
    }


}
