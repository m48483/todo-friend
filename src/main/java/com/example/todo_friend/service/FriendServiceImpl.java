package com.example.todo_friend.service;

import com.example.todo_friend.global.entity.Friend;
import reactor.core.publisher.Flux;

public class FriendServiceImpl implements FriendService {
    @Override
    public Flux<Friend> getFriends(Long userId) {
        return null;
    }
}
