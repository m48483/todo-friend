package com.example.todo_friend.global.repositaory;

import com.example.todo_friend.global.entity.Friend;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FriendRepository extends ReactiveCrudRepository<Friend, Long> {
}
