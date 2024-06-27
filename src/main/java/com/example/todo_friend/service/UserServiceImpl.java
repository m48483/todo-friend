package com.example.todo_friend.service;

import com.example.todo_friend.domain.entity.User;
import com.example.todo_friend.domain.repositaory.UserRepository;
import com.example.todo_friend.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Mono<Void> processUserSignup(UserInfoDto userInfoDto) {
        System.out.println("Received userId: " + userInfoDto.userId());
        System.out.println("Received nickname: " + userInfoDto.nickname());

        Long userId = userInfoDto.userId();
        String nickname = userInfoDto.nickname();
        String image = userInfoDto.image();

        return userRepository.createUser(userId, nickname, image)
                .then(); // Mono<Integer>를 Mono<Void>로 변환
    }

    @Override
    public Mono<Void> processUserUpdate(UserInfoDto userInfoDto) {
        System.out.println("Received userId: " + userInfoDto.userId());
        System.out.println("Received nickname: " + userInfoDto.nickname());
        System.out.println("Received image: " + userInfoDto.image());

        Long userId = userInfoDto.userId();
        String nickname = userInfoDto.nickname();
        String image = userInfoDto.image();

        return userRepository.updateUser(userId,nickname,image).then();
    }

    @Override
    public Mono<Void> processUserDelete(Long userId) {
        return userRepository.deleteById(userId).then();
    }

    @Override
    public Mono<User> findById(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    User newUser = new User();
                    newUser.setUserId(id);
                    newUser.setUserNickname("default");
                    newUser.setUserImage("default");
                    return userRepository.save(newUser);
                }));
    }
}
