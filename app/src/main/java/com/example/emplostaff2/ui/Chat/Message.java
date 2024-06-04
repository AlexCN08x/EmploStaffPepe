package com.example.emplostaff2.ui.Chat;

public class Message {
    private String text;
    private String sender;
    private long timestamp;

    public Message() {
        // Constructor vacío necesario para Firestore
    }

    public Message(String text, String sender, long timestamp) {
        this.text = text;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }

    public long getTimestamp() {
        return timestamp;
    }
}