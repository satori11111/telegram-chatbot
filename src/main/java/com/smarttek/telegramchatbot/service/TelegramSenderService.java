package com.smarttek.telegramchatbot.service;

public interface TelegramSenderService {
    String sendMessageByChatId(Long chatId, String text);
}
