package com.example.todo_friend.service;

import com.example.todo_friend.global.dto.request.FriendRequest;
import com.example.todo_friend.global.entity.RequestList;
import com.example.todo_friend.global.repositaory.RequestListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RequestListServiceImpl implements RequestListService{
    private final RequestListRepository requestListRepository;
    @Override
    public Mono<RequestList> sendRequest(FriendRequest request) {
        return requestListRepository.save(request.toEntity());
    }

    @Override
    public Mono<RequestList> respondToRequest(Long id) {
        return null;
    }

//    프론트에서 수락 버튼을 눌렀을 때
//    request 테이블에서 해당 열을 삭제할 것
//    고민인 부분: 백에서만 해결할 수 있는 방법은 없을까?
//    Friend 테이블에 데이터를 추가하는 동시에
//    Request 테이블에서 행을 삭제하는 방법도 있지만
//    가능하면 타 도메인 테이블을 안 건드렸으면 좋겠음
//    컨트롤러에서 가능한가?
    @Override
    public Mono<Void> deleteRequest(Long id) {
        return requestListRepository.deleteById(id);
    }
}
