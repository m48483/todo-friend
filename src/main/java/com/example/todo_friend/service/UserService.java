package com.example.todo_friend.service;

import com.example.todo_friend.domain.entity.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> findById(Long id);
}
