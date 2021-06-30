package com.server.token.service.Impl;

import com.server.token.security.JwtTokenProvider;
import com.server.token.domain.dto.LoginDto;
import com.server.token.domain.dto.UserDto;
import com.server.token.domain.entity.User;
import com.server.token.exception.UserAlreadyExistsException;
import com.server.token.exception.UserNotFoundException;
import com.server.token.repository.UserRepository;
import com.server.token.service.UserService;
import com.server.token.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    //signup
    @Override
    public String signup(UserDto userDto) {
        if(userRepository.findByUserEmail(userDto.getUserEmail()) != null){
            throw new UserAlreadyExistsException("해당 아이디는 이미 존재합니다.");
        }
        userDto.setUserPw(passwordEncoder.encode(userDto.getUserPw())); // 패스워드를 암호화하여 저장
        userRepository.save(userDto.toEntity());
        return "성공";
    }

    //signin
    @Override
    public Map<String, String> signin(LoginDto loginDto) {
        User findUser = userRepository.findByUserEmail(loginDto.getUserEmail());
        if(findUser == null) throw new UserNotFoundException("해당 유저를 찾을 수 없습니다.");  // 유저가 존재하는지 확인

        boolean passwordCheck = passwordEncoder.matches(loginDto.getUserPw(), findUser.getUserPw()); // 인코딩 후 dto에 저장된 pw값과 db에 저장된 pw를 비교 후 같으면 true 다르면 false 반환
        if(!passwordCheck) throw new UserNotFoundException(); // passwordCheck 후 true가 아니라면 Exception, false면 무시

        Map<String,String> map = new HashMap<>();
        String accessToken = jwtTokenProvider.createToken(loginDto.getUserEmail(),loginDto.toEntity().getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken();

        redisUtil.deleteData(loginDto.getUserEmail());
        redisUtil.setDataExpire(findUser.getUserEmail(), refreshToken, JwtTokenProvider.REFRESH_TOKEN_VALIDATION_SECOND); // redis에 값을 삽입하기위해 key,value,만료시간 설정

        map.put("userEmail", loginDto.getUserEmail());
        map.put("accessToken","Bearer " + accessToken);
        map.put("refreshToken","Bearer " + refreshToken);

        return map;
    }

    @Override
    public User read(Long idx) {
        return userRepository.findById(idx).orElseThrow(UserNotFoundException :: new);
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


    public String 새로운_토큰_발급(String re){

        //A, R => 헤더
        //A 토큰 안에있는 사용자 검증
        // Redis안에 있는 R랑 헤더로 들어온 R ==
        // 새롭게 A발급
        // 틀리면 에러 처리
        return "";
    }
}
