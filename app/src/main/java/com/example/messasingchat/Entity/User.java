package com.example.messasingchat.Entity;

public class User {
    private int userId;
    private String email,fullName,photo;

    public User(String email, String fullName, String photo) {
        this.email = email;
        this.fullName = fullName;
        this.photo = photo;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
