package com.ats.user.query.api.dto;

import com.ats.user.core.dto.BaseResponse;
import com.ats.user.core.models.User;

import java.util.HashSet;
import java.util.Set;

public class UserDataResponse extends BaseResponse {
    private Set<User> users;

    public UserDataResponse(String message, User user) {
        super(message);
        this.users = new HashSet<>();
        this.users.add(user);
    }

    public UserDataResponse(Set<User> users) {
        super(null);
        this.users = users;
    }

    public UserDataResponse(String message) {
        super(message);
    }

    public UserDataResponse(User user) {
        super(null);
        this.users = new HashSet<>();
        this.users.add(user);
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
