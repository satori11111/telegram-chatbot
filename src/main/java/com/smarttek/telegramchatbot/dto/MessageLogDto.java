package com.smarttek.telegramchatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageLogDto{
   private Long chatId;
   private String request;
   private String response;
   private Integer date;
}
