package com.example.todo_friend.controller;

import com.example.todo_friend.dto.request.RequestSendRequest;
import com.example.todo_friend.dto.response.RequestListResponse;
import com.example.todo_friend.global.utils.JwtUtils;
import com.example.todo_friend.service.RequestListService;
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
    private final JwtUtils jwtUtils;

    @PostMapping("/{receiverId}")
    public Mono<Void> sendFriendRequest(@RequestHeader("Authorization") String token
            , @PathVariable Long receiverId) {
        String bearerToken = token.substring(7);
        Long senderId = jwtUtils.getUserInfoFromToken(bearerToken).getUserId();
        RequestSendRequest request = new RequestSendRequest(senderId, receiverId);
        return requestListService.sendRequest(request).then();
    }

    @GetMapping
    public Flux<RequestListResponse> getFriendRequests(@RequestHeader("Authorization") String token) {
        String bearerToken = token.substring(7);
        Long receiverId = jwtUtils.getUserInfoFromToken(bearerToken).getUserId();
        return requestListService.getRequestsForReceiver(receiverId);
    }

    @DeleteMapping("/{requestId}")
    public Mono<ResponseEntity<String>> respondToRequest(@RequestHeader("Authorization") String token
            ,@PathVariable Long requestId, @RequestParam boolean status) {
        String bearerToken = token.substring(7);
        return requestListService.respondToRequest(requestId, status)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(e.getMessage())));
    }
}
