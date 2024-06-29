package com.example.todo_friend.service;

import com.example.todo_friend.dto.request.FriendRequest;
import com.example.todo_friend.dto.response.FriendResponse;
import com.example.todo_friend.domain.entity.Friend;
import com.example.todo_friend.domain.repositaory.FriendRepository;
import com.example.todo_friend.domain.repositaory.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class FriendServiceTest {

    @Mock
    private FriendRepository friendRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private FriendServiceImpl friendService;

    private Friend friend;
    private FriendResponse[] friendResponses;
    private FriendRequest req;

    @Test
    void 친구_목록_불러오기() {
        when(friendRepository.findFriendsByUser1Id(1L)).thenReturn(Flux.just(friendResponses));

        Flux<FriendResponse> result = friendService.getFriends(1L);

        StepVerifier.create(result)
                .expectNextMatches(friendResponse ->
                        friendResponse.userId().equals(2L) &&
                                friendResponse.userNickname().equals("test2") &&
                                friendResponse.userImage().equals("test2"))
                .expectNextMatches(friendResponse ->
                        friendResponse.userId().equals(3L) &&
                                friendResponse.userNickname().equals("test3") &&
                                friendResponse.userImage().equals("test3"))
                .verifyComplete();
    }

    @Test
    void 친구_관계_추가_성공() {
//        given
        FriendRequest req = new FriendRequest(1L,2L);
        friend = new Friend(req.toEntity().getUser1Id(),req.toEntity().getUser2Id());

        when(friendRepository.createFriendship(req.user1Id(), req.user2Id())).thenReturn(Mono.empty());

//        when
        Mono<Friend> result = friendService.createFriend(req);

//        then
        StepVerifier.create(result)
                .expectNextMatches(save->
                        save.getUser1Id().equals(1L) &&
                        save.getUser2Id().equals(2L))
                .verifyComplete();

    }

    @Test
    void 친구_관계_삭제_성공() {
//        given
        when(friendRepository.deleteFriendship(anyLong(),anyLong()))
                .thenReturn(Mono.empty());

//        given
        Mono<Void> result = friendService.deleteFriend(1L, 2L);
//        then
        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        // Initialize array with test data
        friendResponses = new FriendResponse[]{
                new FriendResponse(2L, "test2", "test2"),
                new FriendResponse(3L, "test3", "test3")
        };

        req = new FriendRequest(1L,2L);
    }
}
