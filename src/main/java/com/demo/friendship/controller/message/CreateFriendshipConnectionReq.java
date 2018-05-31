package com.demo.friendship.controller.message;

import com.demo.friendship.controller.exception.RequestValidationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class CreateFriendshipConnectionReq {

    private List<String> friends;

    public static CreateFriendshipConnectionReq of(List<String> friends) {
        CreateFriendshipConnectionReq req = new CreateFriendshipConnectionReq();
        req.setFriends(friends);
        return req;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public void validate() {
        if (friends == null || friends.size() != 2) {
            throw new RequestValidationException("Invalid request, friends size should be 2");
        }

        for (String friend: friends) {
            if (StringUtils.isBlank(friend)) {
                throw new RequestValidationException("Invalid request, friend email addresses should not be blank");
            }
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("friends", friends)
                .toString();
    }
}
