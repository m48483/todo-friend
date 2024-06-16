package com.example.todo_friend.service;

import com.example.todo_friend.global.entity.User;
import reactor.core.publisher.Mono;

public interface UserService {
    public Mono<User> findById(Long id);
    public Mono<User> saveUser(User user);
}
