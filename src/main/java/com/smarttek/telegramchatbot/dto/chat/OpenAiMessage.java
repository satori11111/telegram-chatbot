package com.smarttek.telegramchatbot.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OpenAiMessage {
    private String role;
    private String content;
}
