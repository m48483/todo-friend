package com.example.todo_friend.global.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("REQUESTS")
public class RequestList {
    @Id
    @Column("REQUEST_ID")
    private Long requestId;
    @Column("REQUEST_SENDER")
    private Long requestSender;
    @Column("REQUEST_RECEIVER")
    private Long requestReceiver;
    @Column("REQUEST_CREATED_AT")
    private LocalDateTime requestCreatedAt;
}
