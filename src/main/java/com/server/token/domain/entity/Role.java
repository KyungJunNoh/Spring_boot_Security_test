package com.server.token.domain.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,ROLE_CLIENT,ROLE_NOT_PERMIT;

    @Override
    public String getAuthority() {
        return name();
    }
}
