package com.example.todo_friend.service;

import com.example.todo_friend.global.dto.request.FriendRequest;
import com.example.todo_friend.global.entity.Friend;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface FriendService {
    Flux<Friend> getFriends(Long userId);
    Mono<Friend> createFriend(FriendRequest req);
    Mono<Friend> deleteFriend(Long user1Id, Long user2Id);
}
