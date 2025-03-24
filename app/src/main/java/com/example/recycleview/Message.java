package com.example.recycleview;

public class Message {
    private String text;
    private String sender;
    private String role;
    private long timestamp;

    public Message() {
    }

    public Message(String text, String sender, String role, long timestamp) {
        this.text = text;
        this.sender = sender;
        this.role = role;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }
    public String getRole() {
        return role;
    }
    public long getTimestamp() {
        return timestamp;
    }
}