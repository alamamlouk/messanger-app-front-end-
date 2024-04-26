package com.example.messasingchat.home_pages.ui.friend_list;

import com.example.messasingchat.Entity.RequestFriendShip;

import java.util.List;

public interface FriendsResponseListener {
    void onSuccess(List<RequestFriendShip> users);
    void onError(String message);
}
