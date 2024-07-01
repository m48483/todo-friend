package com.example.todo_friend.service;
import com.example.todo_friend.domain.entity.Friend;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendInfoServiceImpl implements FriendInfoService {
    private final RestTemplate restTemplate;
    @Override
    public Mono<Void> deleteFriendsToTodoService(Long user1Id, Long user2Id) {
        return Mono.fromRunnable(() -> {
            restTemplate.delete(
                    "http://35.238.87.27/todos/friend-delete/{user1Id}/{user2Id}",
                    user1Id,user2Id
            );
            log.info("친구 정보 삭제를 todo 서버로 전송했습니다. user1Id: {}, user2Id: {}", user1Id, user2Id);
        });
    }

    @Override
    public Mono<Void> sendFriendInfoToTodoService(Long user1Id, Long user2Id) {
        Friend savedFriends = new Friend(user1Id, user2Id); // 예시로 만든 Friend 객체 생성
        return Mono.fromRunnable(() -> {
            restTemplate.postForEntity(
                    "http://35.238.87.27/todos/friend-add",
                    savedFriends,
                    Void.class
            );
            log.info("친구 정보를 todo 서버로 전송했습니다.");
        });
    }

}
