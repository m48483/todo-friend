package com.example.todo_friend.service;
import reactor.core.publisher.Mono;

public interface FriendInfoService {
    Mono<Void> deleteFriendsToTodoService(Long user1Id, Long user2Id);
    Mono<Void> sendFriendInfoToTodoService(Long user1Id, Long user2Id);
}
