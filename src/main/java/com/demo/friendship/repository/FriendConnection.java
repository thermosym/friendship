package com.demo.friendship.repository;

import com.demo.friendship.controller.exception.RequestValidationException;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "friend_connection")
public class FriendConnection {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_a")
    private String userA;

    @Column(name = "user_b")
    private String userB;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserA() {
        return userA;
    }

    public void setUserA(String userA) {
        this.userA = userA;
    }

    public String getUserB() {
        return userB;
    }

    public void setUserB(String userB) {
        this.userB = userB;
    }

    public FriendConnection(List<String> users) {
        if (users.size() != 2) {
            throw new RequestValidationException("FriendConnection initialization error, users size should be 2");
        }
        List<String> orderedUsers = new ArrayList<>(users);
        Collections.sort(orderedUsers);
        this.userA = orderedUsers.get(0);
        this.userB = orderedUsers.get(1);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("userA", userA)
                .append("userB", userB)
                .toString();
    }
}
