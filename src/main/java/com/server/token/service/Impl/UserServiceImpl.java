package com.server.token.service.Impl;

import com.server.token.Security.JwtTokenProvider;
import com.server.token.domain.dto.LoginDto;
import com.server.token.domain.dto.UserDto;
import com.server.token.domain.entity.User;
import com.server.token.exception.UserAlreadyExistsException;
import com.server.token.exception.UserNotFoundException;
import com.server.token.repository.UserRepository;
import com.server.token.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    //signup
    @Override
    public String signup(UserDto userDto) {
        if(userRepository.findByUserEmail(userDto.getUserEmail()) != null){
            throw new UserAlreadyExistsException("해당 아이디는 이미 존재합니다.");
        }
        userDto.setUserPw(passwordEncoder.encode(userDto.getUserPw())); // 패스워드를 암호화하여 저장
        userRepository.save(userDto.toEntity());
        String token = jwtTokenProvider.createToken(userDto.getUserEmail(),userDto.toEntity().getRoles());
        return "Barer " + token;
    }

    //signin
    @Override
    public Map<String, String> signin(LoginDto loginDto) {
        User findUser = userRepository.findByUserEmail(loginDto.getUserEmail());
        if(findUser == null) throw new UserNotFoundException("해당 유저를 찾을 수 없습니다.");  // 유저가 존재하는지 확인

        Map<String,String> map = new HashMap<>();
        String accessToken = jwtTokenProvider.createToken(loginDto.getUserEmail(),loginDto.toEntity().getRoles());

        map.put("userEmail",loginDto.getUserEmail());
        map.put("accessToken","Bearer " + accessToken);


        return map;
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
