package com.example.todo_friend.global.repositaory;

import com.example.todo_friend.global.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User,Long> {
}
