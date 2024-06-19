package com.example.todo_friend.service;

import com.example.todo_friend.global.dto.request.FriendRequest;
import com.example.todo_friend.global.dto.response.FriendResponse;
import com.example.todo_friend.global.entity.Friend;
import com.example.todo_friend.global.repositaory.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;

    @Override
    public Flux<FriendResponse> getFriends(Long userId) {
        return friendRepository.findAllByUser1Id(userId);
    }

    @Override
    public Mono<Friend> createFriend(FriendRequest req) {
        return friendRepository.save(req.toEntity());
    }

    @Override
    public Mono<Void> deleteFriend(Long user1Id, Long user2Id) {
        return friendRepository.deleteByUser1IdAndUser2Id(user1Id,user2Id);
    }
}
