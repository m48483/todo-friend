package com.example.todo_friend.global.repositaory;

import com.example.todo_friend.global.entity.User;
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
}
