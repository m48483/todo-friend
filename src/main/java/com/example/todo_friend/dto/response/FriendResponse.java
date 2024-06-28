package com.example.todo_friend.dto.response;

import com.example.todo_friend.domain.entity.User;

public record FriendResponse(
        Long userId,
        String userNickname,
        String userImage
) {
    public static FriendResponse from(User user) {
        return new FriendResponse(
                user.getUserId(),
                user.getUserNickname(),
                user.getUserImage()
        );
    }
}
