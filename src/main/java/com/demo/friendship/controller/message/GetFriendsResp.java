package com.demo.friendship.controller.message;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class GetFriendsResp extends BaseResp {

    private List<String> friends;

    private int count;

    public List<String> getFriends() {
        return friends;
    }

    public GetFriendsResp() {
        super(true);
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("friends", friends)
                .append("count", count)
                .append("success", success)
                .toString();
    }
}
