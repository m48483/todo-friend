package com.example.todo_friend.global.dto.request;

//<<<<<<< feat/friendService
//import com.example.todo_friend.global.entity.Friend;

//public record FriendRequest(
//        Long user1Id,
//        Long user2Id
//) {
//    public Friend toEntity(){
//        return new Friend(user1Id, user2Id);
// =======
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
//>>>>>>> main
    }
}
