package com.example.messasingchat.Entity;

import java.util.Date;

public class FriendsMessageListEntity {
    private String userName,lastMessageSend;
    private Date timeSend;
    private String userImage;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastMessageSend() {
        return lastMessageSend;
    }

    public void setLastMessageSend(String lastMessageSend) {
        this.lastMessageSend = lastMessageSend;
    }

    public Date getTimeSend() {
        return timeSend;
    }

    public void setTimeSend(Date timeSend) {
        this.timeSend = timeSend;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
