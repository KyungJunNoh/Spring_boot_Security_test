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
public class FindPasswordDto {
    private String userEmail;

    private String newPassword;

    public FindPasswordDto(String userEmail,String newPassword){
        this.userEmail = userEmail;
        this.newPassword = newPassword;
    }
}
