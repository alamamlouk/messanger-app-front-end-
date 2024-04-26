package com.example.messasingchat.Entity;

public class ChatMessage {
    private String textMessage;
    private String  sendTime;
    private int senderId,ReceiverId;

    @Override
    public String toString() {
        return "ChatMessage{" +
                "textMessage='" + textMessage + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", senderId=" + senderId +
                ", ReceiverId=" + ReceiverId +
                '}';
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return ReceiverId;
    }

    public void setReceiverId(int receiverId) {
        ReceiverId = receiverId;
    }
}
