package com.example.todo_friend.service;

import com.example.todo_friend.dto.request.RequestSendRequest;
import com.example.todo_friend.dto.response.RequestListResponse;
import com.example.todo_friend.domain.entity.RequestList;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RequestListService {
    Mono<RequestList> sendRequest(RequestSendRequest request);
    Mono<String> respondToRequest(Long id, boolean status);
    Flux<RequestListResponse> getRequestsForReceiver(Long receiverId);
}
