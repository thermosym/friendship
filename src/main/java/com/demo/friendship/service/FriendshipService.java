package com.demo.friendship.service;

import com.demo.friendship.controller.exception.ConnectionRejectException;
import com.demo.friendship.controller.message.CreateFriendshipConnectionReq;
import com.demo.friendship.controller.message.GetAllCommonFriendsReq;
import com.demo.friendship.controller.message.RelationshipFilterReq;
import com.demo.friendship.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class FriendshipService {
    private static final Logger log = LoggerFactory.getLogger(FriendshipService.class);

    @Autowired
    private FriendConnectionRepository friendConnectionRepository;
    @Autowired
    private FriendshipFilterRepository friendshipFilterRepository;

    @Transactional(rollbackFor = Exception.class)
    public void createConnection(CreateFriendshipConnectionReq req) throws ConnectionRejectException {
        FriendConnection connection = new FriendConnection(req.getFriends());
        long count = friendConnectionRepository.countByUserAAndUserB(connection.getUserA(), connection.getUserB());
        if (count == 0) {
            FriendConnection savedConnection = friendConnectionRepository.save(connection);
            log.info("Created new friend connection {}", savedConnection);
        } else {
            log.info("Duplicated friend connection found, no need to create");
        }
    }

    public List<String> getAllUserConnections(String user) {
        List<String> allConnections = friendConnectionRepository.getYourFriendConnections(user);
        return allConnections;
    }

    public List<String> getCommonConnections(GetAllCommonFriendsReq req) {
        List<String> friends = req.getFriends();
        List<String> commonConnections = friendConnectionRepository.getCommonConnections(friends.get(0), friends.get(1));
        return commonConnections;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createSubscription(RelationshipFilterReq req) {
        updateFilter(req.getRequestor(), req.getTarget(), FilterType.SUBS);
    }

    @Transactional(rollbackFor = Exception.class)
    public void blockUserUpdate(RelationshipFilterReq req) {
        updateFilter(req.getRequestor(), req.getTarget(), FilterType.BLOCK);
    }

    void updateFilter(String requestor, String target, FilterType filterType) {
        FriendshipFilter existingFilter = friendshipFilterRepository.findBySubjectAndObject(requestor, target);
        if (existingFilter != null) {
            existingFilter.setFilterType(filterType);
            friendshipFilterRepository.save(existingFilter);
        } else {
            FriendshipFilter filter = new FriendshipFilter(requestor, target, filterType);
            friendshipFilterRepository.save(filter);
        }
    }
}
