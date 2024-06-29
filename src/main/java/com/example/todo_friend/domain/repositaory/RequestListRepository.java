package com.example.todo_friend.domain.repositaory;

import com.example.todo_friend.domain.entity.RequestList;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RequestListRepository extends ReactiveCrudRepository<RequestList,Long> {
    Flux<RequestList> findAllByRequestReceiver(Long receiverId);
    Mono<Boolean> existsByRequestSenderAndRequestReceiver(Long requestSender, Long requestReceiver);
}
