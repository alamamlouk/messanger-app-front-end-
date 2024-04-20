package com.example.messasingchat.home_pages.ui.friend_list;

import com.example.messasingchat.Entity.User;

import java.util.List;

public interface FriendsResponseListener {
    void onSuccess(List<User> users);
    void onError(String message);
}
