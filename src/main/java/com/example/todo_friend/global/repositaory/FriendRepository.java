package com.example.todo_friend.global.repositaory;

import com.example.todo_friend.global.dto.response.FriendResponse;
import com.example.todo_friend.global.entity.Friend;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FriendRepository extends ReactiveCrudRepository<Friend, Long> {
    @Query("DELETE FROM FRIENDS WHERE USER1_ID = :user1Id AND USER2_ID = :user2Id")
    Mono<Void> deleteByUser1IdAndUser2Id(Long user1Id, Long user2Id);
    Flux<FriendResponse> findAllByUser1Id(Long user1Id);
}
