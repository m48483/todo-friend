package com.example.todo_friend.service;

import com.example.todo_friend.global.entity.User;
import com.example.todo_friend.global.repositaory.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    private User test;

    @Test
    void findById() {
//        given
        when(userRepository.findById(1L)).thenReturn(Mono.just(test));
//        when
        Mono<User> result = userService.findById(1L);
//        then
        StepVerifier.create(result)
                .expectNextMatches(user-> user.getUserId().equals(1L))
                .verifyComplete();
    }

    @Test
    void saveUser() {
//        given
        when(userRepository.save(test)).thenReturn(Mono.just(test));
//        when
        Mono<User> result = userService.saveUser(test);
//        then
        StepVerifier.create(result)
                .expectNextMatches(user-> user.getUserId().equals(1L))
                .verifyComplete();
    }
    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);

        test = User.builder()
                .userId(1L)
                .userNickname("test")
                .userImage("test")
                .build();
//        이는 실제 저장이 아닌 모킹된 동작이므로 아무런 효과가 없음
//        userRepository.save(test);

    }
}