package com.example.todo_friend.service;

import com.example.todo_friend.domain.entity.User;
import com.example.todo_friend.domain.repositaory.FriendRepository;
import com.example.todo_friend.domain.repositaory.RequestListRepository;
import com.example.todo_friend.domain.repositaory.UserRepository;
import com.example.todo_friend.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RequestListRepository requestListRepository;
    private final FriendRepository friendRepository;

    @Override
    public Mono<Void> processUserSignup(UserInfoDto userInfoDto) {
        log.info("Received userId: " + userInfoDto.userId());
        log.info("Received nickname: " + userInfoDto.nickname());

        Long userId = userInfoDto.userId();
        String nickname = userInfoDto.nickname();
        String image = userInfoDto.image();

        return userRepository.createUser(userId, nickname, image)
                .then(); // Mono<Integer>를 Mono<Void>로 변환
    }

    @Override
    public Mono<Void> processUserUpdate(UserInfoDto userInfoDto) {
        log.info("Received userId: " + userInfoDto.userId());
        log.info("Received nickname: " + userInfoDto.nickname());
        log.info("Received image: " + userInfoDto.image());

        Long userId = userInfoDto.userId();
        String nickname = userInfoDto.nickname();
        String image = userInfoDto.image();

        return userRepository.updateUser(userId,nickname,image).then();
    }

    @Override
    public Mono<Void> processUserDelete(Long userId) {
        return requestListRepository.deleteAllById(Collections.singleton(userId)) // requests 테이블에서 해당 userId를 참조하는 데이터 삭제
                .then(userRepository.deleteById(userId)) // 사용자 삭제 시도
                .onErrorResume(e -> {
                    if (e instanceof DataIntegrityViolationException) {
                        return Mono.error(new RuntimeException("사용자 삭제 실패: 관련 데이터가 존재합니다.", e));
                    }
                    return Mono.error(new RuntimeException("사용자 삭제 중 오류 발생: " + e.getMessage(), e));
                });
    }
}
