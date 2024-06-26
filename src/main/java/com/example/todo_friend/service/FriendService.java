package com.example.todo_friend.service;

import com.example.todo_friend.dto.request.FriendRequest;
import com.example.todo_friend.dto.response.FriendResponse;
import com.example.todo_friend.domain.entity.Friend;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface FriendService {
    Flux<FriendResponse> getFriends(Long userId);
    Mono<Friend> createFriend(FriendRequest req);
    Mono<Void> deleteFriend(Long user1Id, Long user2Id);
}
