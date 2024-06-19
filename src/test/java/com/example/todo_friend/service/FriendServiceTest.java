package com.example.todo_friend.service;

import com.example.todo_friend.global.dto.response.FriendResponse;
import com.example.todo_friend.global.entity.Friend;
import com.example.todo_friend.global.repositaory.FriendRepository;
import com.example.todo_friend.global.repositaory.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

class FriendServiceTest {

    @Mock
    private FriendRepository friendRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private FriendServiceImpl friendService;

    private FriendResponse[] friendResponses;

    @Test
    void getFriends() {
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
    void createFriend() {
        // Test for creating friend
    }

    @Test
    void deleteFriend() {
        // Test for deleting friend
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        // Initialize array with test data
        friendResponses = new FriendResponse[]{
                new FriendResponse(2L, "test2", "test2"),
                new FriendResponse(3L, "test3", "test3")
        };
    }
}
