package com.example.todo_friend.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Setter
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
