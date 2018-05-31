package com.demo.friendship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendConnectionRepository extends JpaRepository<FriendConnection, Long> {

    FriendConnection findFirstByUserAAndUserB(String userA, String userB);

    @Query(value = "select distinct user_b as uid from friend_connection where user_a = :userId " +
            " union all " +
            " select user_a as uid from friend_connection where user_b = :userId", nativeQuery = true)
    List<String> getYourFriendConnections(@Param("userId") String userId);
}
