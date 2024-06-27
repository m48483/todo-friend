package com.example.todo_friend.service;

import com.example.todo_friend.domain.entity.User;
import com.example.todo_friend.dto.UserInfoDto;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> findById(Long id);
    Mono<Void> processUserSignup(UserInfoDto userInfoDto);
}
