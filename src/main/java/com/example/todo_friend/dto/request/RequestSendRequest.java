package com.example.todo_friend.dto.request;

import com.example.todo_friend.domain.entity.RequestList;

import java.time.LocalDateTime;

// jwt 완성 되면 수정해야할 부분
public record RequestSendRequest(
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
