package com.smarttek.telegramchatbot.config;

import com.smarttek.telegramchatbot.bot.TelegramChatBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramConfigBot {
    @Value("${bot.name}")
    String botName;
    @Value("${bot.token}")
    String botToken;

    @Bean
    public TelegramChatBot telegramChatBot() {
        return new TelegramChatBot(botToken, botName);
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramChatBot());
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can't initialize bot ", e);
        }
    }
}
