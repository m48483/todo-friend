package com.example.todo_friend.service;

import com.example.todo_friend.global.entity.Friend;
import reactor.core.publisher.Flux;


public interface FriendService {
    public Flux<Friend> getFriends(Long userId);
}
