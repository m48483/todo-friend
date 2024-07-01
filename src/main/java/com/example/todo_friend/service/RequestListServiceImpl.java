package com.example.todo_friend.service;

import com.example.todo_friend.domain.repositaory.FriendRepository;
import com.example.todo_friend.dto.request.FriendRequest;
import com.example.todo_friend.dto.request.RequestSendRequest;
import com.example.todo_friend.dto.response.RequestListResponse;
import com.example.todo_friend.domain.entity.RequestList;
import com.example.todo_friend.domain.entity.User;
import com.example.todo_friend.domain.repositaory.RequestListRepository;
import com.example.todo_friend.domain.repositaory.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestListServiceImpl implements RequestListService {
    private final RequestListRepository requestListRepository;
    private final FriendService friendService;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final FriendInfoService friendInfoService;

    @Override
    public Mono<RequestList> sendRequest(RequestSendRequest request) {
        Long senderId = request.senderId();
        Long receiverId = request.receiverId();

        if (senderId.equals(receiverId)){
            return Mono.error(new IllegalArgumentException("나는 나와 친구한다"));
        }
        return friendRepository.existsByUser1IdAndUser2Id(senderId, receiverId)
                .flatMap(count -> {
                    if (count > 0) {
                        return Mono.error(new IllegalArgumentException("이미 친구입니다."));
                    }
                    return requestListRepository.existsByRequestSenderAndRequestReceiver(senderId, receiverId)
                            .flatMap(existsRequest -> {
                                if (existsRequest) {
                                    return Mono.error(new IllegalArgumentException("이미 처리된 요청입니다."));
                                }
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
                            });
                })
                .doOnError(e -> {
                    log.error("Error sending friend request: " + e.getMessage());
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
                                .then(friendInfoService
                                        .sendFriendInfoToTodoService(request.getRequestSender(), request.getRequestReceiver()))
                                .then(requestListRepository.deleteById(id))
                                .thenReturn("요청을 수락하였습니다.");
                    }
                    return requestListRepository.deleteById(id)
                            .thenReturn("요청을 거절하였습니다.");
                })
                .onErrorResume(e -> {
                    System.out.println("친구 요청에 대한 응답 중 에러 발생: " + e.getMessage());
                    return Mono.error(new IllegalArgumentException("친구 요청에 대한 응답 중 에러 발생"));
                });
    }
}
