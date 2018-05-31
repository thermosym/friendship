package com.demo.friendship.controller.message;

import com.demo.friendship.controller.exception.RequestValidationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class GetAllCommonFriendsReq {

    private List<String> friends;

    public static GetAllCommonFriendsReq of(List<String> emails) {
        GetAllCommonFriendsReq req = new GetAllCommonFriendsReq();
        req.setFriends(emails);
        return req;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("friends", friends)
                .toString();
    }

    public void validate() {
        if (friends == null || friends.size() != 2) {
            throw new RequestValidationException("Invalid request, friends size should be 2");
        }

        if (StringUtils.isBlank(friends.get(0)) || StringUtils.isBlank(friends.get(1))) {
            throw new RequestValidationException("Invalid request, friend email addresses should not be blank");
        }

        if (friends.get(0).equals(friends.get(1))) {
            throw new RequestValidationException("Invalid request, you cannot get common user with yourself");
        }
    }
}
