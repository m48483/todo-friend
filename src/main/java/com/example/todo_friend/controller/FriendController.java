package com.example.todo_friend.controller;

import com.example.todo_friend.dto.response.FriendResponse;
import com.example.todo_friend.global.utils.JwtUtils;
import com.example.todo_friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;
    private final JwtUtils jwtUtils;

    @GetMapping
    public Flux<FriendResponse> getFriends(@RequestHeader("Authorization") String token) {
        String bearerToken = token.substring(7);
        Long userId = jwtUtils.getUserInfoFromToken(bearerToken).getUserId();
        return friendService.getFriends(userId);
    }

    @DeleteMapping("/{user2Id}")
    public Mono<Void> deleteFriend(@RequestHeader("Authorization") String token
            , @PathVariable Long user2Id) {
        String bearerToken = token.substring(7);
        Long user1Id = jwtUtils.getUserInfoFromToken(bearerToken).getUserId();
        return friendService.deleteFriend(user1Id, user2Id);
    }
}
