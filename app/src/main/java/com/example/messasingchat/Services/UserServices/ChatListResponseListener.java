package com.example.messasingchat.Services.UserServices;

import com.example.messasingchat.Entity.FriendsMessageListEntity;

import java.util.List;

public interface ChatListResponseListener {
    void onSuccess(List<FriendsMessageListEntity> messageListEntities);
    void onFailure(String message);
}
