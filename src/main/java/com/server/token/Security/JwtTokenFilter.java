package com.server.token.security;

import com.server.token.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 토큰을 만들고 이 필터클래스(JwtAuthenticationFilter)에서 인증 작업 진행
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 JWT를 받아옴
        String token = jwtTokenProvider.resolveToken(request);

        try{
            if (token != null && jwtTokenProvider.validateToken(token)) {
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                // SecurityContext 에 Authentication 객체를 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            SecurityContextHolder.clearContext();
        }catch(CustomException e){
            response.sendError(e.getHttpStatus().value(), e.getMessage());
            return ;
        }

        filterChain.doFilter(request, response);
    }
}
