package com.smarttek.telegramchatbot.bot;

import com.smarttek.telegramchatbot.dto.MessageLogDto;
import com.smarttek.telegramchatbot.service.OpenAiService;
import com.smarttek.telegramchatbot.service.MessageService;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


public class TelegramChatBot extends TelegramLongPollingBot {
    @Autowired
    private OpenAiService openAiService;
    @Getter
    private final Set<Long> chatIds = new HashSet<>();
    @Autowired
    private MessageService messageService;
    private final String botUserName;

    public TelegramChatBot(String botToken, String botUserName) {
        super(botToken);
        this.botUserName = botUserName;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() && !update.getMessage().hasText()) {
            return;
        }
        String request = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        String response = openAiService.getResponse(request);
        SendMessage sendMessage = new SendMessage(chatId.toString(), response);
        execute(sendMessage);
        saveToDb(update, request, chatId, response);
    }

    private void saveToDb(Update update, String request, Long chatId, String response) {
        Message message = update.getMessage();
        Integer date = message.getDate();
        messageService.save(new MessageLogDto(chatId, request, response, date));
        chatIds.add(chatId);
    }

}