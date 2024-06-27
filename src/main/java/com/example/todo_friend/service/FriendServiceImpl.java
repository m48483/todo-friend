package com.example.todo_friend.service;

import com.example.todo_friend.dto.request.FriendRequest;
import com.example.todo_friend.dto.response.FriendResponse;
import com.example.todo_friend.domain.entity.Friend;
import com.example.todo_friend.domain.repositaory.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    private final RestTemplate restTemplate;

    @Override
    public Flux<FriendResponse> getFriends(Long userId) {
        return friendRepository.findFriendsByUser1Id(userId)
                .switchIfEmpty(Flux.error(new IllegalArgumentException("해당 사용자의 친구가 없습니다.")))
                .onErrorResume(e -> {
                    System.err.println("친구 목록 조회 중 에러 발생: " + e.getMessage());
                    return Flux.error(e);
                });
    }

    @Override
    public Mono<Friend> createFriend(FriendRequest req) {
        Long user1Id = req.toEntity().getUser1Id();
        Long user2Id = req.toEntity().getUser2Id();
        return friendRepository.createFriendship(user1Id, user2Id)
                .then(Mono.just(new Friend(user1Id, user2Id)))
                .onErrorResume(e -> {
                    System.out.println("친구 생성 중 에러 발생: " + e.getMessage());
                    return Mono.error(e);
                });
    }

    @Override
    public Mono<Void> deleteFriend(Long user1Id, Long user2Id) {
        return friendRepository.deleteFriendship(user1Id, user2Id)
//                .switchIfEmpty(Mono.error(new IllegalArgumentException("친구 관계 삭제에 실패했습니다.")))
                .then(deleteFriendsToTodoService(user1Id, user2Id))
                .onErrorResume(e -> {
                    System.err.println("친구 삭제 중 에러 발생: " + e.getMessage());
                    return Mono.error(e);
                });
    }

    private Mono<Void> deleteFriendsToTodoService(Long user1Id, Long user2Id) {
        return Mono.fromRunnable(() -> {
            restTemplate.delete(
                    "http://35.238.87.27/todos/friend-delete/{user1Id}/{user2Id}",
                    user1Id,user2Id
            );
            System.out.println("친구 정보 삭제를 todo 서버로 전송했습니다.");
        });
    }
}
