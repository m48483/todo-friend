package com.example.todo_friend.global.dto.request;

import com.example.todo_friend.global.entity.RequestList;

import java.time.LocalDateTime;

public record FriendRequest(
        Long senderId,
        Long receiverId
) {
    public RequestList toEntity(){
        return RequestList.builder()
                .requestSender(senderId)
                .requestReceiver(receiverId)
                .requestCreatedAt(LocalDateTime.now())
                .build();
    }
}
