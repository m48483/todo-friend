package com.example.todo_friend.global.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("USERS")
public class User {
    @Id
    @Column("USER_ID")
    private Long userId;
    @Column("USER_NICKNAME")
    private String userNickname;
    @Column("USER_IMAGE")
    private String userImage;
}
