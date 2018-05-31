package com.demo.friendship.repository;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class FriendConnectionTest {

    @Parameters(name = "case {index}: with user email list input {0}, the constructed FriendConnection is userA={1}, userB={2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {ImmutableList.of("a@demo.com", "b@demo.com"), "a@demo.com", "b@demo.com"},
                {ImmutableList.of("b@demo.com", "a@demo.com"), "a@demo.com", "b@demo.com"},
                {ImmutableList.of("aa@demo.com", "ac@demo.com"), "aa@demo.com", "ac@demo.com"},
                {ImmutableList.of("ac@demo.com", "aa@demo.com"), "aa@demo.com", "ac@demo.com"},
                {ImmutableList.of("a@demo.com", "aa@demo.com"), "a@demo.com", "aa@demo.com"},
                {ImmutableList.of("aa@demo.com", "a@demo.com"), "a@demo.com", "aa@demo.com"}
        });
    }

    @Parameter
    public List<String> inputUserList;

    @Parameter(1)
    public String expectedUserA;

    @Parameter(2)
    public String expectedUserB;

    @Test
    public void testCreateFriendConnectionEntityByList() {
        FriendConnection connection = new FriendConnection(inputUserList);
        assertEquals(expectedUserA, connection.getUserA());
        assertEquals(expectedUserB, connection.getUserB());
    }
}