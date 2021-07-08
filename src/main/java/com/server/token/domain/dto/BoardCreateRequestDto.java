package com.server.token.domain.dto;

import com.server.token.domain.entity.Board;
import com.server.token.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardCreateRequestDto {
    private User user;
    private String title;
    private String content;

    @Builder
    public BoardCreateRequestDto(User user,String title, String content){
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public Board toEntity(){
        return Board.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();
    }

}
