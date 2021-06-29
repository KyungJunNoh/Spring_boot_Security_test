package com.server.token.controller.jwt;

import com.server.token.service.jwt.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RefreshTokenController {
    private final RefreshTokenService refreshTokenService;

    @GetMapping("/refreshtoken")
    public Map<String,String> refresh(HttpServletRequest request){ // HttpServletRequest 입력값없이 그냥 요청만 들어왔을때,
        return refreshTokenService.refresh(request);
    }
}
