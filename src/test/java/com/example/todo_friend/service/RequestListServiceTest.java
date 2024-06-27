package com.example.todo_friend.service;

import com.example.todo_friend.dto.request.FriendRequest;
import com.example.todo_friend.dto.request.RequestSendRequest;
import com.example.todo_friend.domain.entity.Friend;
import com.example.todo_friend.domain.entity.RequestList;
import com.example.todo_friend.domain.entity.User;
import com.example.todo_friend.domain.repositaory.RequestListRepository;
import com.example.todo_friend.domain.repositaory.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

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
    private FriendServiceImpl friendService;
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
    void 친구_신청_성공() {
        RequestList requestList = new RequestList(1L,1L,2L, LocalDateTime.now());

        when(requestListRepository.existsByRequestSenderAndRequestReceiver(req.senderId(), req.receiverId())).thenReturn(Mono.just(false));

        when(userRepository.insertIfNotExistAndReturn(req.senderId())).thenReturn(Mono.empty());
        when(userRepository.findByUserId(req.senderId())).thenReturn(Mono.just(sender));

        when(userRepository.insertIfNotExistAndReturn(req.receiverId())).thenReturn(Mono.empty());
        when(userRepository.findByUserId(req.receiverId())).thenReturn(Mono.just(receiver));

        when(requestListRepository.save(any(RequestList.class))).thenReturn(Mono.just(requestList));

        StepVerifier.create(requestListService.sendRequest(req))
                .expectNextMatches(savedRequest -> savedRequest.getRequestSender().equals(req.senderId())
                        && savedRequest.getRequestReceiver().equals(req.receiverId()))
                .verifyComplete();
    }

    @Test
    void 친구_요청_응답_수락_성공() {
        FriendRequest req = new FriendRequest(1L, 2L);

        RequestList requestList = new RequestList(1L, 1L, 2L, LocalDateTime.now());
        when(requestListRepository.findById(1L)).thenReturn(Mono.just(requestList));
        when(requestListRepository.deleteById(1L)).thenReturn(Mono.empty());
        when(friendService.createFriend(req)).thenReturn(Mono.just(new Friend()));

        StepVerifier.create(requestListService.respondToRequest(1L, true))
                .expectNext("요청을 수락하였습니다.")
                .verifyComplete();
    }

    @Test
    void 친구_요청_응답_거절_성공() {
        RequestList requestList = new RequestList(1L, 1L, 2L, LocalDateTime.now());
        when(requestListRepository.findById(1L)).thenReturn(Mono.just(requestList));
        when(requestListRepository.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(requestListService.respondToRequest(1L, false))
                .expectNext("요청을 거절하였습니다.")
                .verifyComplete();
    }

}
