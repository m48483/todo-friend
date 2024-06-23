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

//    REST 템플릿 이용해서 수정해야 함
    @Override
    public Mono<User> findById(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    User newUser = new User();
                    newUser.setUserId(id);
                    newUser.setUserNickname("default");
                    newUser.setUserImage("default");
                    return userRepository.save(newUser);
                }));
    }
}
