package com.example.todo_friend.controller;

import com.example.todo_friend.global.dto.request.RequestSendRequest;
import com.example.todo_friend.global.dto.response.RequestListResponse;
import com.example.todo_friend.global.entity.RequestList;
import com.example.todo_friend.service.RequestListService;
import com.example.todo_friend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/friends/requests")
@RequiredArgsConstructor
public class RequestListController {
    private final RequestListService requestListService;
    private final UserService userService;
    @PostMapping
    public Mono<Void> sendFriendRequest(@RequestBody RequestSendRequest request) {
        return requestListService.sendRequest(request).then();
    }
//    토큰 받는 거로 수정해야 함
    @GetMapping("/{receiverId}")
    public Flux<RequestListResponse> getFriendRequests(@PathVariable Long receiverId) {
        return requestListService.getRequestsForReceiver(receiverId);
    }

    @DeleteMapping("/{requestId}")
    public Mono<ResponseEntity<String>> respondToRequest(@PathVariable Long requestId, @RequestParam boolean status) {
        return requestListService.respondToRequest(requestId, status)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(e.getMessage())));
    }
}
