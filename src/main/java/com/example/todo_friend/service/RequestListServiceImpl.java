package com.example.todo_friend.service;

import com.example.todo_friend.global.entity.RequestList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
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
