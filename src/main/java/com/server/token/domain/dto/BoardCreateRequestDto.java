package com.server.token.domain.dto;

import com.server.token.domain.entity.Board;
import com.server.token.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class BoardCreateRequestDto {
    private User user;
    private String title;
    private String content;

    public Board toEntity(){
        return Board.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();
    }

}
