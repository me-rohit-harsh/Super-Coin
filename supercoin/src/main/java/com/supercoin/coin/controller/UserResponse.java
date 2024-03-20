package com.supercoin.coin.controller;
import com.supercoin.coin.model.User;

public class UserResponse {
    private boolean success;
    private User user;

    // Constructors, getters, and setters

    public UserResponse(boolean success, User user) {
        this.success = success;
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
