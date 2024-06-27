package com.example.todo_friend.domain.repositaory;

import com.example.todo_friend.domain.entity.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User,Long> {
    @Query("INSERT INTO USERS (USER_ID, USER_NICKNAME, USER_IMAGE) " +
            "VALUES (:userId, 'default', 'default') " +
            "ON DUPLICATE KEY UPDATE USER_ID = USER_ID ")
    Mono<Void> insertIfNotExistAndReturn(Long userId);

    @Query(value = "SELECT * FROM USERS WHERE USER_ID = :userId")
    Mono<User> findByUserId(Long userId);

    @Modifying
    @Query("INSERT INTO USERS (USER_ID, USER_NICKNAME, USER_IMAGE) VALUES (:userId, :nickname, :image)")
    Mono<Integer> createUser(Long userId, String nickname, String image);
}
