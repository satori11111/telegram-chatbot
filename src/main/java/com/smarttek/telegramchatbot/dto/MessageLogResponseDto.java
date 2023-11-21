package com.smarttek.telegramchatbot.dto;

import com.smarttek.telegramchatbot.model.Chat;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MessageLogResponseDto {
    private Long id;
    private Long chatId;
    private String request;
    private String response;
    private LocalDateTime localDateTime;
}
