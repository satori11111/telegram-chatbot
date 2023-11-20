package com.smarttek.telegramchatbot.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ChatRequest {

    private String model;
    private List<Message> messages;
    private int n;
    private double temperature;

    public ChatRequest(String model, String prompt) {
        this.model = model;

        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt));
        this.n = 1;
        this.temperature = 1;
    }

}
