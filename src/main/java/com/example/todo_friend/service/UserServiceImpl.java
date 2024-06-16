package com.example.todo_friend.service;

import com.example.todo_friend.global.entity.User;
import com.example.todo_friend.global.repositaory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Mono<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<User> saveUser(User user) {
        return userRepository.save(user);
    }
}
