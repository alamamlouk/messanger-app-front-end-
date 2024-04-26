package com.example.messasingchat.home_pages.ui.friend_list;

import com.example.messasingchat.Entity.User;

import java.util.List;

public interface ListFriendResponse {

    void onSuccess(List<User> userList);
    void onError(String message);
}
