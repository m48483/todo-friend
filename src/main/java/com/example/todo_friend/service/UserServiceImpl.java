package com.example.todo_friend.service;

import com.example.todo_friend.global.entity.User;
import reactor.core.publisher.Mono;

public class UserServiceImpl implements UserService {
    @Override
    public Mono<User> findById(Long id) {
        return null;
    }

    @Override
    public Mono<User> saveUser(User user) {
        return null;
    }
}
