package com.server.token.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.lang.reflect.Member;

@Getter
@NoArgsConstructor
@Table
@Entity
public class Board {
    @Id @GeneratedValue
    @Column(name = "BOARD_ID")
    private Long idx;

    @Column(name = "BOARD_TITLE")
    private String title;

    @Column(name = "BOARD_CONTENT")
    private String content;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = User.class)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @Builder
    public Board(User user,String title,String content){
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
