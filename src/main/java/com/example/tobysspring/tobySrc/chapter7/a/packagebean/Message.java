package com.example.tobysspring.tobySrc.chapter7.a.packagebean;

public class Message {
    String text;

    private Message(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static Message newMessage(String text) {
        return new Message(text);
    }
}
