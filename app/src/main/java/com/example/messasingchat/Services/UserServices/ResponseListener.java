package com.example.messasingchat.Services.UserServices;

import java.util.List;

public interface ResponseListener {
    void onSuccess(List<Integer> list);
    void onError(String message);
}
