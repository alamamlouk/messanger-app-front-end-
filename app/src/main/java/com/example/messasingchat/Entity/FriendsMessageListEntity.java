package com.example.messasingchat.Entity;

public class FriendsMessageListEntity {
    private int id,idTheTalker;
    private String userName,lastMessageSend;
    private String timeSend;
    private String userImage;

    public int getIdTheTalker() {
        return idTheTalker;
    }

    public void setIdTheTalker(int idTheTalker) {
        this.idTheTalker = idTheTalker;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String  getTimeSend() {
        return timeSend;
    }

    public void setTimeSend(String timeSend) {
        this.timeSend = timeSend;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
