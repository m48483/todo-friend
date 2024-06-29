package com.example.todo_friend.domain.repositaory;

import com.example.todo_friend.dto.response.FriendResponse;
import com.example.todo_friend.domain.entity.Friend;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FriendRepository extends ReactiveCrudRepository<Friend, Long> {
    @Query("DELETE FROM FRIENDS WHERE (USER1_ID = :user1Id AND USER2_ID = :user2Id) OR (USER1_ID = :user2Id AND USER2_ID = :user1Id)")
    Mono<Void> deleteFriendship(Long user1Id, Long user2Id);

//    Mono<Void> deleteByUser1IdAndUser2Id(Long user1Id, Long user2Id);

    @Query("SELECT u.USER_ID, u.USER_NICKNAME, u.USER_IMAGE " +
            "FROM USERS u INNER JOIN FRIENDS f ON u.USER_ID = f.USER2_ID WHERE f.USER1_ID = :user1Id")
    Flux<FriendResponse> findFriendsByUser1Id(Long user1Id);

    @Query("INSERT INTO FRIENDS (USER1_ID, USER2_ID) VALUES (:user1Id, :user2Id), (:user2Id, :user1Id)")
    Mono<Friend> createFriendship(Long user1Id, Long user2Id);

    @Query("DELETE FROM FRIENDS WHERE (USER1_ID = :userId) OR (USER2_ID = :userId)")
    Mono<Void> deleteUser(Long userId);
}
