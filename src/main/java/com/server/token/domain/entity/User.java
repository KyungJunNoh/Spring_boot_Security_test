package com.server.token.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class User {
    @Id @GeneratedValue
    private Long userIdx;
    private String userId;
    private String userPw;

    public void update(String userId,String userPw){
        this.userId = userId;
        this.userPw = userPw;
    }
}
