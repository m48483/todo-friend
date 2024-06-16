package com.example.todo_friend.service;

import com.example.todo_friend.global.entity.RequestList;
import reactor.core.publisher.Mono;

public class RequestListServiceImpl implements RequestListService{
    @Override
    public Mono<RequestList> sendRequest(Long senderId, Long receiverId) {
        return null;
    }

    @Override
    public Mono<RequestList> respondToRequest(Long id) {
        return null;
    }
}
