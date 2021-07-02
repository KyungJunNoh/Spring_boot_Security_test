package com.server.token.service.Impl;

import com.server.token.security.JwtTokenProvider;
import com.server.token.domain.dto.LoginDto;
import com.server.token.domain.dto.UserDto;
import com.server.token.domain.entity.User;
import com.server.token.exception.UserAlreadyExistsException;
import com.server.token.exception.UserNotFoundException;
import com.server.token.repository.UserRepository;
import com.server.token.service.EmailService;
import com.server.token.service.UserService;
import com.server.token.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;
    private final EmailService emailService;

    // 회원가입
    @Override
    public User signup(UserDto userDto) {
        if(userRepository.findByUserEmail(userDto.getUserEmail()) != null){
            throw new UserAlreadyExistsException("해당 아이디는 이미 존재합니다.");
        }
        userDto.setUserPw(passwordEncoder.encode(userDto.getUserPw())); // 패스워드를 암호화하여 저장
        User userInfo = userRepository.save(userDto.toEntity());
        return userInfo;
    }

    // 로그인
    @Override
    public Map<String, String> signin(LoginDto loginDto) {
        User findUser = userRepository.findByUserEmail(loginDto.getUserEmail()); // loginDto 에서 유저의 이메일을 가져와서 findUser변수에 저장
        if(findUser == null) throw new UserNotFoundException("해당 유저를 찾을 수 없습니다.");  // findUser의 값이 null이라면 UserNotFoundException메소드 실행

        boolean passwordCheck = passwordEncoder.matches(loginDto.getUserPw(), findUser.getUserPw()); // 인코딩 후 dto에 저장된 pw값과 db에 저장된 pw를 비교 후 같으면 true 다르면 false 반환
        if(!passwordCheck) throw new UserNotFoundException("비밀번호가 올바르지 않습니다."); // passwordCheck 후 true가 아니라면 Exception, false면 무시

        Map<String,String> map = new HashMap<>(); // 값을 반환할 KEY : VALUE 를 담을 수있는 Map 생성
        String accessToken = jwtTokenProvider.createToken(loginDto.getUserEmail(),loginDto.toEntity().getRoles()); // accessToken 생성
        String refreshToken = jwtTokenProvider.createRefreshToken(); // refreshToken 생성

        redisUtil.deleteData(loginDto.getUserEmail()); // redis에 값을 삽입하기 전 해당 아이디의 refreshToken 삭제
        redisUtil.setDataExpire(findUser.getUserEmail(), refreshToken, JwtTokenProvider.REFRESH_TOKEN_VALIDATION_SECOND); // redis에 값을 삽입하기위해 key,value,만료시간 설정

        // response를 하기위한 map에 userEmail,accessToken,refreshToken put
        map.put("userEmail", loginDto.getUserEmail());
        map.put("accessToken","Bearer " + accessToken);
        map.put("refreshToken","Bearer " + refreshToken);

        return map;
    }


}
