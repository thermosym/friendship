package com.demo.friendship.service;

import com.demo.friendship.App;
import com.demo.friendship.controller.exception.ConnectionRejectException;
import com.demo.friendship.controller.message.CreateFriendshipConnectionReq;
import com.demo.friendship.controller.message.RelationshipFilterReq;
import com.demo.friendship.repository.FilterType;
import com.demo.friendship.repository.FriendConnectionRepository;
import com.demo.friendship.repository.FriendshipFilter;
import com.demo.friendship.repository.FriendshipFilterRepository;
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
    @Autowired
    private FriendshipFilterRepository friendshipFilterRepository;

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

    @Test
    public void testCreateConnectionSuccessfulWhenNoBlocked() throws Exception {
        FriendshipFilter blockFilter = new FriendshipFilter("henry@example.com", "andy@example.com", FilterType.SUBS);
        friendshipFilterRepository.save(blockFilter);

        CreateFriendshipConnectionReq req1 = CreateFriendshipConnectionReq.of(Arrays.asList("andy@example.com", "henry@example.com"));
        try {
            friendshipService.createConnection(req1);
        } catch (Exception e) {
            fail("It should not throw ConnectionRejectException");
        }

        List<String> andyFriends = friendConnectionRepository.getYourFriendConnections("andy@example.com");
        assertEquals(1, andyFriends.size());
        assertEquals("henry@example.com", andyFriends.get(0));
    }

    @Test
    public void testCreateConnectionFailedWhenBlocked() throws Exception {
        FriendshipFilter blockFilter = new FriendshipFilter("henry@example.com", "andy@example.com", FilterType.BLOCK);
        friendshipFilterRepository.save(blockFilter);

        CreateFriendshipConnectionReq req1 = CreateFriendshipConnectionReq.of(Arrays.asList("andy@example.com", "henry@example.com"));
        try {
            friendshipService.createConnection(req1);
            fail("It should throw ConnectionRejectException");
        } catch (Exception e) {
            assertTrue(e instanceof ConnectionRejectException);
        }

        List<String> andyFriends = friendConnectionRepository.getYourFriendConnections("andy@example.com");
        assertEquals(0, andyFriends.size());
    }

    @Test
    public void testCreateSubscriptionWithoutOldFilter() throws Exception {
        friendshipService.createSubscription(new RelationshipFilterReq("andy@example.com", "henry@example.com"));
        FriendshipFilter updated = friendshipFilterRepository.findBySubjectAndObject("andy@example.com", "henry@example.com");
        assertEquals(FilterType.SUBS, updated.getFilterType());
    }

    @Test
    public void testCreateSubscriptionWillReplaceOldBlockFilter() throws Exception {
        FriendshipFilter blockFilter = new FriendshipFilter("andy@example.com", "henry@example.com", FilterType.BLOCK);
        friendshipFilterRepository.save(blockFilter);
        friendshipService.createSubscription(new RelationshipFilterReq("andy@example.com", "henry@example.com"));
        FriendshipFilter updated = friendshipFilterRepository.findBySubjectAndObject("andy@example.com", "henry@example.com");
        assertEquals(FilterType.SUBS, updated.getFilterType());
    }

    @Test
    public void testBlockUserWithoutOldFilter() throws Exception {
        friendshipService.blockUserUpdate(new RelationshipFilterReq("andy@example.com", "henry@example.com"));
        FriendshipFilter updated = friendshipFilterRepository.findBySubjectAndObject("andy@example.com", "henry@example.com");
        assertEquals(FilterType.BLOCK, updated.getFilterType());
    }

    @Test
    public void testBlockUserWillReplaceOldSubscriptionFilter() throws Exception {
        FriendshipFilter blockFilter = new FriendshipFilter("andy@example.com", "henry@example.com", FilterType.SUBS);
        friendshipFilterRepository.save(blockFilter);
        friendshipService.blockUserUpdate(new RelationshipFilterReq("andy@example.com", "henry@example.com"));
        FriendshipFilter updated = friendshipFilterRepository.findBySubjectAndObject("andy@example.com", "henry@example.com");
        assertEquals(FilterType.BLOCK, updated.getFilterType());
    }
}