package com.server.token.domain.dto;

import com.server.token.domain.entity.Role;
import com.server.token.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserDto {
    private String userEmail;
    private String userPw;

    public UserDto(String userEmail,String userPw){
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
