package com.example.todo_friend.global.dto.request;

import com.example.todo_friend.global.entity.Friend;

public record FriendRequest(
        Long user1Id,
        Long user2Id
) {
    public Friend toEntity(){
        return new Friend(user1Id, user2Id);
    }
}
