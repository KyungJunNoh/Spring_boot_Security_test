package com.server.token.controller;

import com.server.token.service.LoginCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginCheckController {
    private final LoginCheckService loginCheckService;

    @GetMapping("/logincheck")
    public String loginCheck(HttpServletRequest httpServletRequest){
        return loginCheckService.loginCheck(httpServletRequest);
    }
}
