package com.example.todo_friend.global.repositaory;

import com.example.todo_friend.global.entity.RequestList;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface RequestListRepository extends ReactiveCrudRepository<RequestList,Long> {
}
