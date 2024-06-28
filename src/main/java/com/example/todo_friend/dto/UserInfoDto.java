package com.example.todo_friend.dto;

import com.example.todo_friend.domain.entity.User;

public record UserInfoDto(
        Long userId,
        String nickname,
        String image
) {
    public User toEntity(){
        return User.builder()
                .userId(userId)
                .userNickname(nickname)
                .userImage(image)
                .build();
    }
}
