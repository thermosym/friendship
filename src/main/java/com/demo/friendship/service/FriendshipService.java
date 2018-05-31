package com.demo.friendship.service;

import com.demo.friendship.controller.exception.ConnectionRejectException;
import com.demo.friendship.controller.message.CreateFriendshipConnectionReq;
import com.demo.friendship.repository.FriendConnection;
import com.demo.friendship.repository.FriendConnectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FriendshipService {
    private static final Logger log = LoggerFactory.getLogger(FriendshipService.class);

    @Autowired
    private FriendConnectionRepository friendConnectionRepository;

    public void createConnection(CreateFriendshipConnectionReq req) throws ConnectionRejectException {
        FriendConnection connection = new FriendConnection(req.getFriends());
        FriendConnection existingConnection = friendConnectionRepository.findFirstByUserAAndUserB(connection.getUserA(), connection.getUserB());
        if (existingConnection == null) {
            FriendConnection savedConnection = friendConnectionRepository.save(connection);
            log.info("Created new friend connection {}", savedConnection);
        } else {
            log.info("Duplicated friend connection found, no need to create");
        }
    }
}