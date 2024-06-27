package com.example.todo_friend.controller;

import com.example.todo_friend.dto.UserInfoDto;
import com.example.todo_friend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
    @PutMapping("/user-update")
    public Mono<Void> update(@RequestBody UserInfoDto userDto) {
        return userService.processUserUpdate(userDto).then();
    }
    @DeleteMapping("/user-delete/{userId}")
    public Mono<Void> delete(@PathVariable Long userId) {
        return userService.processUserDelete(userId).then();
    }
}
