package com.example.todo_friend.controller;

import com.example.todo_friend.global.dto.request.FriendRequest;
import com.example.todo_friend.global.dto.response.FriendResponse;
import com.example.todo_friend.global.entity.Friend;
import com.example.todo_friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

//    request header 로 수정 해야함

    @GetMapping("/{userId}")
    public Flux<FriendResponse> getFriends(@PathVariable Long userId) {
        return friendService.getFriends(userId);
    }

    @DeleteMapping//("/{friendId}")
    public Mono<Void> deleteFriend(@RequestBody FriendRequest req) {
        return friendService.deleteFriend(req);
    }
}
