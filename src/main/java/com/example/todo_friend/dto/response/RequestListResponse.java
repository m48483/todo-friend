package com.example.todo_friend.dto.response;

import com.example.todo_friend.domain.entity.RequestList;
import com.example.todo_friend.domain.entity.User;

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
