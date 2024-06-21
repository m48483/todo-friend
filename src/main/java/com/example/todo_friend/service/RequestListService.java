package com.example.todo_friend.service;

import com.example.todo_friend.global.dto.request.FriendRequest;
import com.example.todo_friend.global.dto.request.RequestSendRequest;
import com.example.todo_friend.global.entity.RequestList;
import reactor.core.publisher.Mono;

public interface RequestListService {
    Mono<RequestList> sendRequest(RequestSendRequest request);
    Mono<String> respondToRequest(Long id, boolean status);
}
