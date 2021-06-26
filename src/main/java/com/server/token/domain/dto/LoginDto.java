package com.server.token.domain.dto;

import com.server.token.domain.entity.Role;
import com.server.token.domain.entity.User;
import lombok.*;

import java.util.Collections;

@Getter @Setter
@NoArgsConstructor
@Builder
public class LoginDto {
    private String userEmail;
    private String userPw;

    public LoginDto(String userEmail,String userPw){
        this.userEmail = userEmail;
        this.userPw = userPw;
    }

    public User toEntity(){
        return User.builder()
                .userEmail(this.getUserEmail())
                .userPw(this.getUserPw())
                .roles(Collections.singletonList(Role.ROLE_CLIENT)) // ?
                .build();
    }
}
