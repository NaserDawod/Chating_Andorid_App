package com.example.chating_app.api_entities;

public class Api_Transfer {
    public String from;
    public String to;
    public String content;

    public Api_Transfer(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }
}
