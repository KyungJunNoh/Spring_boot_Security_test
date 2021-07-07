package com.server.token.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table
@Entity
public class Board {
    @Id @GeneratedValue
    private Long idx;
    private String title;
    private String contents;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = User.class)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @Builder
    public Board(User user,String title,String contents){
        this.user = user;
        this.title = title;
        this.contents = contents;
    }
}
