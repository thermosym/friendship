package com.demo.friendship.service;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MentionUserHelperTest {

    @Test
    public void testExtractTextWithOneUser() {
        String text = "Hello World! kate@example.com";
        Set<String> users = MentionUserHelper.extractUsers(text);
        assertEquals(1, users.size());
        assertTrue(users.contains("kate@example.com"));
    }

    @Test
    public void testExtractTextWithMultipleUsers() {
        String text = "john@example.com Hello World! kate@example.com";
        Set<String> users = MentionUserHelper.extractUsers(text);
        assertEquals(2, users.size());
        assertTrue(users.contains("kate@example.com"));
        assertTrue(users.contains("john@example.com"));
    }

    @Test
    public void testExtractTextWithoutUsers() {
        String text = "Hello World! No mentioned user in here.";
        Set<String> users = MentionUserHelper.extractUsers(text);
        assertEquals(0, users.size());
    }
}