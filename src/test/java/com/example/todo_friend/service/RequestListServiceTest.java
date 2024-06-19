package com.example.todo_friend.service;

import com.example.todo_friend.global.dto.request.FriendRequest;
import com.example.todo_friend.global.entity.RequestList;
import com.example.todo_friend.global.repositaory.RequestListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

class RequestListServiceTest {

    @Mock
    private RequestListRepository requestListRepository;
    @InjectMocks
    private RequestListServiceImpl requestListService;

    private RequestList test;
    @Test
    void sendRequest() {
//        given
        FriendRequest req = new FriendRequest(1L,2L);
//        when
        when(requestListRepository.save(test)).thenReturn(Mono.just(test));
        Mono<RequestList> result = requestListService.sendRequest(req);
//        then
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

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        test = RequestList.builder()
                .requestSender(1L)
                .requestReceiver(2L)
                .build();
    }
}