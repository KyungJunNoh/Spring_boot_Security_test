package com.server.token.domain.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    @Id @GeneratedValue
    @Column(name = "USER_ID")
    private Long userIdx;

    @Column(name = "USER_EMAIL")
    private String userEmail;

    @Column(name = "USER_PW")
    private String userPw;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "ROLE")
    @Builder.Default
    private List<Role> roles = new ArrayList<>();

    public void update(String userEmail,String userPw){
        this.userEmail = userEmail;
        this.userPw = userPw;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> rolesConvertString = this.roles.stream().map(Enum::name).collect(Collectors.toList());
        return rolesConvertString.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
