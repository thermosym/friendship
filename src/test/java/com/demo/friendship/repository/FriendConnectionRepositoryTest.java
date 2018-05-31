package com.demo.friendship.repository;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class FriendConnectionRepositoryTest {

    @Autowired
    private FriendConnectionRepository friendConnectionRepository;

    @Before
    public void setUp() {
        FriendConnection connection1 = new FriendConnection(ImmutableList.of("andy@example.com", "common@example.com"));
        FriendConnection connection2 = new FriendConnection(ImmutableList.of("andy@example.com", "henry@example.com"));
        FriendConnection connection3 = new FriendConnection(ImmutableList.of("john@example.com", "common@example.com"));
        FriendConnection connection4 = new FriendConnection(ImmutableList.of("andy@example.com", "common@example.com")); // duplicated with connection 1
        List<FriendConnection> allConnections = Arrays.asList(connection1, connection2, connection3, connection4);
        friendConnectionRepository.saveAll(allConnections);
    }

    @Test
    public void testGetFriendConnectionWhenConnectionNotExistWillReturnNull() {
        FriendConnection result = friendConnectionRepository.findFirstByUserAAndUserB("andy@example.com", "christ@example.com");
        assertNull(result);
    }

    @Test
    public void testGetFriendConnectionWhenConnectionExistWillReturnTheConnection() {
        FriendConnection result = friendConnectionRepository.findFirstByUserAAndUserB("andy@example.com", "common@example.com");
        assertNotNull(result);
    }

    @Test
    public void testGetYourFriendConnectionsWhenUserHasNoConnectionWillReturnEmptyList() {
        List<String> friendsList = friendConnectionRepository.getYourFriendConnections("notfound@example.com");
        assertEquals(0, friendsList.size());
    }

    @Test
    public void testGetYourFriendConnectionsWhenUserHasOneConnectionWillReturnListSize1() {
        List<String> friendsList = friendConnectionRepository.getYourFriendConnections("henry@example.com");
        assertEquals(1, friendsList.size());
        assertEquals("andy@example.com", friendsList.get(0));
    }

    @Test
    public void testGetYourFriendConnectionsWhenUserHasMultipleConnectionsWillReturnList() {
        List<String> friendsList = friendConnectionRepository.getYourFriendConnections("andy@example.com");
        assertEquals(2, friendsList.size());
        assertTrue(friendsList.contains("common@example.com"));
        assertTrue(friendsList.contains("henry@example.com"));
    }
}