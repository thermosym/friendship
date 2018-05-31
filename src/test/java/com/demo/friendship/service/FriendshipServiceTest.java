package com.demo.friendship.service;

import com.demo.friendship.App;
import com.demo.friendship.controller.exception.ConnectionRejectException;
import com.demo.friendship.controller.message.CreateFriendshipConnectionReq;
import com.demo.friendship.repository.FriendConnectionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class, FriendshipService.class})
@DataJpaTest
@Transactional
public class FriendshipServiceTest {

    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private FriendConnectionRepository friendConnectionRepository;

    @Test
    public void testCreateConnectionSuccessfully() throws Exception {
        CreateFriendshipConnectionReq req1 = CreateFriendshipConnectionReq.of(Arrays.asList("andy@example.com", "henry@example.com"));
        friendshipService.createConnection(req1);

        List<String> andyFriends = friendConnectionRepository.getYourFriendConnections("andy@example.com");
        assertEquals(1, andyFriends.size());
        assertEquals("henry@example.com", andyFriends.get(0));
    }

    @Test
    public void testCreateConnectionTwiceStillSuccessfully() throws Exception {
        CreateFriendshipConnectionReq req1 = CreateFriendshipConnectionReq.of(Arrays.asList("andy@example.com", "henry@example.com"));
        friendshipService.createConnection(req1);
        friendshipService.createConnection(req1);

        List<String> andyFriends = friendConnectionRepository.getYourFriendConnections("andy@example.com");
        assertEquals(1, andyFriends.size());
        assertEquals("henry@example.com", andyFriends.get(0));
    }
}