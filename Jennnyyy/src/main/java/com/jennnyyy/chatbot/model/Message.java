package com.jennnyyy.chatbot.model;

public class Message {

    private String category;
    private String text;

    public Message() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}