package com.server.token.service.jwt;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface RefreshTokenService {
    Map<String,String> refresh(HttpServletRequest request);
}
