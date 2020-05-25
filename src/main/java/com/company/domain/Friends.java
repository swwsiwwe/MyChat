package com.company.domain;

import java.io.Serializable;

public class Friends implements Serializable {
    String username;
    String friend;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    @Override
    public String toString() {
        return "Friends{" +
                "username='" + username + '\'' +
                ", friend='" + friend + '\'' +
                '}';
    }
}
