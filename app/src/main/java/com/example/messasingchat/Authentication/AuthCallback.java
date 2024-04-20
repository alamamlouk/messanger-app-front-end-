package com.example.messasingchat.Authentication;

public interface AuthCallback {
    void onSuccess();
    void onFailure(String errorMessage);
}
