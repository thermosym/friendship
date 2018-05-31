package com.demo.friendship.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipFilterRepository extends JpaRepository<FriendshipFilter, Long> {

    FriendshipFilter findBySubjectAndObject(String subject, String object);
}
