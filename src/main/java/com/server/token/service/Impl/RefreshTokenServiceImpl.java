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

        Map<String,String> map = new HashMap<>();

        return map;
    }
}
