package com.server.token.controller;

import com.server.token.domain.entity.User;
import com.server.token.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public void join(@RequestBody User user){
        userService.join(user);
    }
}
