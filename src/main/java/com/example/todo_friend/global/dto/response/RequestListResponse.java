package com.example.todo_friend.global.dto.response;

import com.example.todo_friend.global.entity.RequestList;
import com.example.todo_friend.global.entity.User;
import com.example.todo_friend.global.repositaory.UserRepository;

import java.time.LocalDateTime;

public record RequestListResponse(
        Long requestId,
        Long requestSenderId,
        String userNickname,
        LocalDateTime requestCreatedAt
) {
    public static RequestListResponse from(RequestList requestList, User sender) {
        return new RequestListResponse(
                requestList.getRequestId(),
                sender.getUserId(),
                sender.getUserNickname(),
                requestList.getRequestCreatedAt()
        );
    }
}
