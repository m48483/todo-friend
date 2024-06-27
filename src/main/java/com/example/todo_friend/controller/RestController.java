package com.example.todo_friend.controller;

import com.example.todo_friend.dto.UserInfoDto;
import com.example.todo_friend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class RestController {
    private final UserService userService;

    @PostMapping("/user-signup")
    public Mono<Void> signUp(@RequestBody UserInfoDto userDto) {
        return userService.processUserSignup(userDto).then();
    }
}
