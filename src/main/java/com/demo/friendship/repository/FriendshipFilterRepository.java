package com.demo.friendship.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipFilterRepository extends JpaRepository<FriendshipFilter, Long> {

    FriendshipFilter findBySubjectAndObject(String subject, String object);

    List<FriendshipFilter> findByObject(String object);
}
