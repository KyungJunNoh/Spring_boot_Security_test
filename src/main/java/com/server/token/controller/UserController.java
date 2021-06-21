package com.server.token.controller;

import com.server.token.domain.entity.User;
import com.server.token.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public User join(@RequestBody User user){
        userService.join(user);
        return user;
    }

    @GetMapping("/read/{idx}")
    public Optional<User> read(@PathVariable("idx") Long idx){
        Optional<User> user = userService.read(idx);
        return user;
    }
//    @PutMapping("/delete")
//    public User delete(@RequestBody String idx){
//
//    }
}
