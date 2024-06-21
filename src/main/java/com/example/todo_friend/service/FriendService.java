package com.example.todo_friend.service;

import com.example.todo_friend.global.dto.request.FriendRequest;
import com.example.todo_friend.global.dto.response.FriendResponse;
import com.example.todo_friend.global.entity.Friend;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface FriendService {
    Flux<FriendResponse> getFriends(Long userId);
    Mono<Friend> createFriend(FriendRequest req);
    Mono<Void> deleteFriend(FriendRequest req);
}
