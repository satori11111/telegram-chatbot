package com.smarttek.telegramchatbot.service.impl;

import com.smarttek.telegramchatbot.bot.TelegramChatBot;
import com.smarttek.telegramchatbot.service.TelegramSenderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@RequiredArgsConstructor
@Service
public class TelegramSenderServiceImpl implements TelegramSenderService {
    private final TelegramChatBot telegramChatBot;
    private static final String MESSAGE_SENT = "Message Sent!";

    @SneakyThrows
    @Override
    public String sendMessageByChatId(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId.toString(), text);
        telegramChatBot.execute(sendMessage);
        return MESSAGE_SENT;
    }
}
