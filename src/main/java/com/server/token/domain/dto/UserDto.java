package com.server.token.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class UserDto {
    private String userEmail;
    private String userPw;
}
