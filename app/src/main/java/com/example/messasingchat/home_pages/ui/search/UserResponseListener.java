package com.example.messasingchat.home_pages.ui.search;

import com.example.messasingchat.Entity.User;

import java.util.List;

public interface UserResponseListener {
    void onSuccess(List<User> users);
    void onError(String message);
}
