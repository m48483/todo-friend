package com.example.todo_friend.service;

import com.example.todo_friend.domain.entity.Friend;
import com.example.todo_friend.dto.request.FriendRequest;
import com.example.todo_friend.dto.request.RequestSendRequest;
import com.example.todo_friend.dto.response.RequestListResponse;
import com.example.todo_friend.domain.entity.RequestList;
import com.example.todo_friend.domain.entity.User;
import com.example.todo_friend.domain.repositaory.RequestListRepository;
import com.example.todo_friend.domain.repositaory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RequestListServiceImpl implements RequestListService {
    private final RequestListRepository requestListRepository;
    private final FriendService friendService;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @Override
    public Mono<RequestList> sendRequest(RequestSendRequest request) {
        Long senderId = request.senderId();
        Long receiverId = request.receiverId();

        return requestListRepository.existsByRequestSenderAndRequestReceiver(senderId, receiverId)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalArgumentException("이미 처리된 요청입니다."));
                    } else {
                        return saveUsersIfNotExist(senderId, receiverId)
                                .flatMap(tuple -> {
                                    User sender = tuple.getT1();
                                    User receiver = tuple.getT2();
                                    RequestList req = RequestList.builder()
                                            .requestSender(sender.getUserId())
                                            .requestReceiver(receiver.getUserId())
                                            .requestCreatedAt(LocalDateTime.now())
                                            .build();
                                    return requestListRepository.save(req);
                                });
                    }
                })
                .doOnError(e -> {
                    System.err.println("Error sending friend request: " + e.getMessage());
                });
    }

    private Mono<Tuple2<User, User>> saveUsersIfNotExist(Long senderId, Long receiverId) {
        Mono<User> senderMono = userRepository.insertIfNotExistAndReturn(senderId)
                .then(userRepository.findByUserId(senderId));

        Mono<User> receiverMono = userRepository.insertIfNotExistAndReturn(receiverId)
                .then(userRepository.findByUserId(receiverId));

        return Mono.zip(senderMono, receiverMono);
    }

    @Override
    public Flux<RequestListResponse> getRequestsForReceiver(Long receiverId) {
        return requestListRepository.findAllByRequestReceiver(receiverId)
                .flatMap(requestList -> userRepository.findByUserId(requestList.getRequestSender())
                        .map(user -> RequestListResponse.from(requestList, user))
                );
    }

    @Override
    public Mono<String> respondToRequest(Long id, boolean status) {
        return requestListRepository.findById(id)
                .flatMap(request -> {
                    if (status) {
                        FriendRequest friendRequest = new FriendRequest(request.getRequestSender(), request.getRequestReceiver());
                        return friendService.createFriend(friendRequest)
                                .then(sendFriendInfoToTodoService(request.getRequestSender(), request.getRequestReceiver()))
                                .then(requestListRepository.deleteById(id))
                                .thenReturn("요청을 수락하였습니다.");
                    } else {
                        return requestListRepository.deleteById(id)
                                .thenReturn("요청을 거절하였습니다.");
                    }
                })
                .onErrorResume(e -> {
                    System.out.println("친구 요청에 대한 응답 중 에러 발생: " + e.getMessage());
                    return Mono.error(new IllegalArgumentException("친구 요청에 대한 응답 중 에러 발생"));
                });
    }

    private Mono<Void> sendFriendInfoToTodoService(Long user1Id, Long user2Id) {
        Friend savedFriends = new Friend(user1Id, user2Id); // 예시로 만든 Friend 객체 생성
        return Mono.fromRunnable(() -> {
            restTemplate.postForEntity(
                    "http://35.238.87.27/todos/friend-add",
                    savedFriends,
                    Void.class
            );
            System.out.println("친구 정보를 todo 서버로 전송했습니다.");
        });
    }
}
