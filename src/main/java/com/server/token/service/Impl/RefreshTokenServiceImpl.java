package com.server.token.service.Impl;

import com.server.token.domain.dto.UserDto;
import com.server.token.domain.entity.Role;
import com.server.token.domain.entity.User;
import com.server.token.security.JwtTokenProvider;
import com.server.token.service.jwt.RefreshTokenService;
import com.server.token.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    @Override
    public Map<String,String> refresh(HttpServletRequest request) {
        UserDto userDto = new UserDto();
        List<Role> roles = userDto.toEntity().getRoles();

        String accessToken = jwtTokenProvider.resolveToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        String newAccessToken = null;
        String newRefreshToken = null;
        String nickname = jwtTokenProvider.getUsername(accessToken);

        Map<String,String> map = new HashMap<>();

        if(redisUtil.getData(nickname) == null){ // redisUtil 로 nickname을 보내주어 토큰이 유효한지 검증하는 조건문
            map.put("message","로그아웃된 토큰입니다.");
        }

        // redis에 있는 refreshToken과 reqeust 요청으로 들어온 refreshToken을 비교하여 같고,
        // JwtTokenProvider클래스의 validateToken 메소드를 이용하여 토큰이 유효한 조건이 맞다면
        // 아래 코드 실행
        if(redisUtil.getData(nickname).equals(refreshToken) && jwtTokenProvider.validateToken(refreshToken)){
            redisUtil.deleteData(nickname); // redis에 있는 refreshToken를 초기화
            newAccessToken = jwtTokenProvider.createToken(nickname, roles);
            newRefreshToken = jwtTokenProvider.createRefreshToken();
            redisUtil.setDataExpire(nickname,newRefreshToken,jwtTokenProvider.REFRESH_TOKEN_VALIDATION_SECOND);
            map.put("nickname",nickname);
            map.put("newAccessToken",newAccessToken); // newAccessToken 반환
            map.put("newRefreshToken",newRefreshToken); // newRefreshToken 반환
            return map;
        }

        return map;
    }
}
