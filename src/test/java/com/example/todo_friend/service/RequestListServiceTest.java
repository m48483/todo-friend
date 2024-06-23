package com.example.todo_friend.service;

import com.example.todo_friend.global.dto.request.RequestSendRequest;
import com.example.todo_friend.global.entity.RequestList;
import com.example.todo_friend.global.entity.User;
import com.example.todo_friend.global.repositaory.RequestListRepository;
import com.example.todo_friend.global.repositaory.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RequestListServiceTest {

    @Mock
    private RequestListRepository requestListRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RequestListServiceImpl requestListService;

    @Mock
    private UserServiceImpl userService;

    private RequestList test;
    private RequestSendRequest req;
    private User sender;
    private User receiver;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        test = RequestList.builder()
                .requestSender(1L)
                .requestReceiver(2L)
                .build();

        req = new RequestSendRequest(1L, 2L);

        sender = new User();
        sender.setUserId(1L);
        sender.setUserNickname("Sender");
        sender.setUserImage("sender_image");

        receiver = new User();
        receiver.setUserId(2L);
        receiver.setUserNickname("Receiver");
        receiver.setUserImage("receiver_image");
    }

    @Test
    void sendRequest() {
        when(userService.findById(1L)).thenReturn(Mono.just(sender));
        when(userService.findById(2L)).thenReturn(Mono.just(receiver));
        when(requestListRepository.save(any(RequestList.class))).thenReturn(Mono.just(test));

        Mono<RequestList> result = requestListService.sendRequest(req);

        StepVerifier.create(result)
                .expectNextMatches(requestList ->
                        requestList.getRequestSender().equals(1L)
                                && requestList.getRequestReceiver().equals(2L))
                .verifyComplete();
    }

    @Test
    void respondToRequest() {
    }

    @Test
    void deleteRequest() {
    }
}
