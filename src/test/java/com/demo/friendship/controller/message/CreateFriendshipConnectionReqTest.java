package com.demo.friendship.controller.message;

import com.demo.friendship.controller.exception.RequestValidationException;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CreateFriendshipConnectionReqTest {

    @Test
    public void testValidateHappyCase() {
        CreateFriendshipConnectionReq req = CreateFriendshipConnectionReq.of(
                Arrays.asList("andy@example.com", "henry@example.com"));
        try {
            req.validate();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testValidateWithFailureWhenOnlyOneUserInRequest() {
        CreateFriendshipConnectionReq req = CreateFriendshipConnectionReq.of(
                Arrays.asList("andy@example.com"));
        try {
            req.validate();
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof RequestValidationException);
            assertEquals("Invalid request, friends size should be 2", e.getMessage());
        }
    }

    @Test
    public void testValidateWithFailureWhenUserEmailIsBlank() {
        CreateFriendshipConnectionReq req = CreateFriendshipConnectionReq.of(
                Arrays.asList("andy@example.com", ""));
        try {
            req.validate();
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof RequestValidationException);
            assertEquals("Invalid request, friend email addresses should not be blank", e.getMessage());
        }
    }

    @Test
    public void testValidateWithFailureWhenUserConnectToSelf() {
        CreateFriendshipConnectionReq req = CreateFriendshipConnectionReq.of(
                Arrays.asList("andy@example.com", "andy@example.com"));
        try {
            req.validate();
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof RequestValidationException);
            assertEquals("Invalid request, you cannot connect to yourself", e.getMessage());
        }
    }
}