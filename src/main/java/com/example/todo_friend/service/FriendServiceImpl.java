package com.example.todo_friend.service;

import com.example.todo_friend.global.dto.request.FriendRequest;
import com.example.todo_friend.global.entity.Friend;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FriendServiceImpl implements FriendService {
    @Override
    public Flux<Friend> getFriends(Long userId) {
        return null;
    }

    @Override
    public Mono<Friend> createFriend(FriendRequest req) {
        return null;
    }

    @Override
    public Mono<Friend> deleteFriend(Long user1Id, Long user2Id) {
        return null;
    }
}
