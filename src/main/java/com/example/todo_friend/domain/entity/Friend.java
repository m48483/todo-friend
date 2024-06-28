package com.example.todo_friend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("FRIENDS")
public class Friend {
    @Column("USER1_ID")
    private Long user1Id;

    @Column("USER2_ID")
    private Long user2Id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friend friends = (Friend) o;
        return user1Id.equals(friends.user1Id) && user2Id.equals(friends.user2Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user1Id, user2Id);
    }
}
