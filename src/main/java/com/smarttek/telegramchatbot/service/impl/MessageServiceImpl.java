package com.smarttek.telegramchatbot.service.impl;

import com.smarttek.telegramchatbot.dto.message.MessageLogDto;
import com.smarttek.telegramchatbot.dto.message.MessageLogResponseDto;
import com.smarttek.telegramchatbot.mapper.MessageMapper;
import com.smarttek.telegramchatbot.model.Chat;
import com.smarttek.telegramchatbot.model.MessageLog;
import com.smarttek.telegramchatbot.repository.ChatRepository;
import com.smarttek.telegramchatbot.repository.MessageRepository;
import com.smarttek.telegramchatbot.service.MessageService;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper messageMapper;

    @Override
    public MessageLog save(MessageLogDto messageLogDto) {
        Long chatId = messageLogDto.getChatId();
        Chat chat;
        chat = saveIfNotExists(chatId);
        MessageLog model = messageMapper.toModel(messageLogDto);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(messageLogDto.getDate()),
                ZoneId.systemDefault()
        );
        model.setLocalDateTime(localDateTime);
        model.setChat(chat);
        messageRepository.save(model);
        return model;
    }

    private Chat saveIfNotExists(Long chatId) {
        Chat chat;
        if (!chatRepository.existsByChatId(chatId)) {
            chat = new Chat();
            chat.setChatId(chatId);
            chatRepository.save(chat);
        } else {
            chat = chatRepository.findByChatId(chatId);
        }
        return chat;
    }

    @Override
    public List<MessageLogResponseDto> findAll(Pageable pageable) {
        return messageRepository.findAllWithChats(pageable).stream()
                .map(messageMapper::toDto)
                .toList();
    }

    @Override
    public List<MessageLogResponseDto> findByChatId(Long chatId, Pageable pageable) {
        return messageRepository.findAllByChatIdWithChat(chatId, pageable)
                .stream()
                .map(messageMapper::toDto)
                .toList();
    }
}
