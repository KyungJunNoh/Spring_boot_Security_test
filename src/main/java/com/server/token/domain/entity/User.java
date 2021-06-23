package com.server.token.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
public class User {
    @Id @GeneratedValue
    private Long userIdx;
    private String userEmail;
    private String userPw;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public void update(String userEmail,String userPw){
        this.userEmail = userEmail;
        this.userPw = userPw;
    }
}
