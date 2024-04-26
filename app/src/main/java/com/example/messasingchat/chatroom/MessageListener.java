package com.example.messasingchat.chatroom;

import com.example.messasingchat.Entity.ChatMessage;

import java.util.List;

public interface MessageListener {
    public void onSuccess(List<ChatMessage> chatMessages);
}
