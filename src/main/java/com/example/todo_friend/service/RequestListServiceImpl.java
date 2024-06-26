package com.example.todo_friend.service;

import com.example.todo_friend.global.dto.request.FriendRequest;
import com.example.todo_friend.global.dto.request.RequestSendRequest;
import com.example.todo_friend.global.dto.response.RequestListResponse;
import com.example.todo_friend.global.entity.Friend;
import com.example.todo_friend.global.entity.RequestList;
import com.example.todo_friend.global.entity.User;
import com.example.todo_friend.global.repositaory.RequestListRepository;
import com.example.todo_friend.global.repositaory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    public Mono<String> respondToRequest(Long id, boolean status) {
        return requestListRepository.findById(id)
                .flatMap(byId -> {
                    if (status) {
                        FriendRequest request = new FriendRequest(byId.getRequestSender(), byId.getRequestReceiver());
                        return friendService.createFriend(request)
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

    @Override
    public Flux<RequestListResponse> getRequestsForReceiver(Long receiverId) {
        return requestListRepository.findAllByRequestReceiver(receiverId)
                .flatMap(requestList -> userRepository.findByUserId(requestList.getRequestSender())
                        .map(user -> RequestListResponse.from(requestList, user))
                );
    }
}
