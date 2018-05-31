package com.demo.friendship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendConnectionRepository extends JpaRepository<FriendConnection, Long> {

    long countByUserAAndUserB(String userA, String userB);

    @Query(value = "select distinct user_b as uid from friend_connection where user_a = :userId " +
            " union all " +
            " select user_a as uid from friend_connection where user_b = :userId", nativeQuery = true)
    List<String> getYourFriendConnections(@Param("userId") String userId);

    @Query(value = "select distinct friends1.uid from " +
            "(select user_b as uid from friend_connection where user_a = :user1 union all " +
            "select user_a as uid from friend_connection where user_b = :user1) as friends1 " +
            "inner join " +
            "(select user_b as uid from friend_connection where user_a = :user2 union all " +
            "select user_a as uid from friend_connection where user_b = :user2) as friends2 " +
            "on friends1.uid = friends2.uid" , nativeQuery = true)
    List<String> getCommonConnections(@Param("user1") String userId1, @Param("user2") String userId2);
}
