package com.server.token.security;

import com.server.token.domain.entity.Role;
import com.server.token.exception.CustomException;
import io.jsonwebtoken.*;
import jdk.jshell.spi.ExecutionControlProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    @Value("${security.jwt.token.secretKey}")
    private String secretKey;

//    public final static long TOKEN_VALIDATION_SECOND = 1000L * 3600 * 24;  //하루를 accessToken 만료 기간으로 잡는다
//    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 3600 * 24 * 210; //7개월을 refreshToken 만료 기간으로 잡는다.

    public final static long TOKEN_VALIDATION_SECOND = 1000L * 60;  // 1분을 accessToken 만료 기간으로 잡는다
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 120; // 1시간을 refreshToken 만료 기간으로 잡는다.

    private final UserDetailsService userDetailsService; // 실제 데이터베이스에서 사용자 인증정보를 가져오는 userDetailsService 선언

    @PostConstruct
    // @PostConstruct가 붙은 메서드는 클래스가 service(로직을 탈 때? 로 생각 됨)를 수행하기 전에 발생한다. 이 메서드는 다른 리소스에서 호출되지 않는다해도 수행된다.
    protected void init() { // 의존성 주입이 이루어진 후 초기화를 수행하는 메서드
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); // secretKey를 Base64(암호화 알고리즘)으로 인코딩 시킴 (암호화 시킴)
    }

    // JWT 토큰 생성
    public String createToken(String userEmail, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(userEmail); // JWT payload 에 저장되는 정보단위
        claims.put("auth", roles.stream().
                map(s -> new SimpleGrantedAuthority(s.getAuthority())).
                filter(Objects::nonNull).collect(Collectors.toList()));; // 정보는 key / value 쌍으로 저장된다.

        Date now = new Date();
        Date validity = new Date(now.getTime() + TOKEN_VALIDATION_SECOND); // 유효시간 선언 [ 지금시간 + expireTime 시간 ]

        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(validity) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    public String createRefreshToken(){
        Claims claims = Jwts.claims().setSubject(null);

        Date now = new Date();
        Date validity = new Date(now.getTime() + REFRESH_TOKEN_VALIDATION_SECOND); //Expire Time

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 유저 정보를 뽑아올 수 있는 메소드
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져온다.
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }else{
            return null;
        }
    }

    public String resolveRefreshToken(HttpServletRequest req){
        String refreshToken = req.getHeader("RefreshToken");
        if(refreshToken != null){
            return refreshToken.substring(7);
        } else {
            return null;
        }
    }

    //token을 검증하는 메소드 (유효한지 안한지, 만료일 검증)
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date()); //
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            return false;
        }
    }


}