package com.example.messasingchat.Entity;

public class RequestFriendShip {
    String requestFriendShipId,userFullName,picture,workPlace;

    public String getRequestFriendShipId() {
        return requestFriendShipId;
    }

    public void setRequestFriendShipId(String requestFriendShipId) {
        this.requestFriendShipId = requestFriendShipId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }
}
