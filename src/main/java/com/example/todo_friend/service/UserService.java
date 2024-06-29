package com.example.todo_friend.service;

import com.example.todo_friend.domain.entity.User;
import com.example.todo_friend.dto.UserInfoDto;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<Void> processUserSignup(UserInfoDto userInfoDto);
    Mono<Void> processUserUpdate(UserInfoDto userInfoDto);
    Mono<Void> processUserDelete(Long userId);
}
