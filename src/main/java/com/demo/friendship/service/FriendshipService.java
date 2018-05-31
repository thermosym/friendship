package com.demo.friendship.service;

import com.demo.friendship.controller.exception.ConnectionRejectException;
import com.demo.friendship.controller.message.*;
import com.demo.friendship.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            FriendshipFilter filter1 = friendshipFilterRepository.findBySubjectAndObject(connection.getUserA(), connection.getUserB());
            FriendshipFilter filter2 = friendshipFilterRepository.findBySubjectAndObject(connection.getUserB(), connection.getUserA());
            if ((filter1 != null && filter1.getFilterType() == FilterType.BLOCK) ||
                    (filter2 != null && filter2.getFilterType() == FilterType.BLOCK)) {
                log.error("there is a block relationship between the two user");
                throw new ConnectionRejectException("there is a block relationship between the two user");
            }
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

    public SendUpdateResp getUpdateRecipients(SendUpdateReq req) {
        List<FriendshipFilter> objectFilters = friendshipFilterRepository.findByObject(req.getSender());
        Set<String> blockSet = getUserSetByFilterType(objectFilters, FilterType.BLOCK);
        Set<String> subsSet = getUserSetByFilterType(objectFilters, FilterType.SUBS);
        List<String> yourFriends = friendConnectionRepository.getYourFriendConnections(req.getSender());
        Set<String> mentionedUsers = MentionUserHelper.extractUsers(req.getText());

        Set<String> recipientSet = new HashSet<>();
        recipientSet.addAll(subsSet);
        recipientSet.addAll(yourFriends);
        recipientSet.addAll(mentionedUsers);
        recipientSet.removeAll(blockSet);

        SendUpdateResp resp = new SendUpdateResp();
        resp.setRecipients(new ArrayList<>(recipientSet));
        return resp;
    }

    private Set<String> getUserSetByFilterType(List<FriendshipFilter> objectFilters, FilterType subs) {
        return objectFilters.stream()
                .filter(f -> f.getFilterType() == subs)
                .map(FriendshipFilter::getSubject)
                .collect(Collectors.toSet());
    }
}
