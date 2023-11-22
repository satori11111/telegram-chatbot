package com.smarttek.telegramchatbot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.smarttek.telegramchatbot.dto.MessageLogDto;
import com.smarttek.telegramchatbot.dto.MessageLogResponseDto;
import com.smarttek.telegramchatbot.mapper.MessageMapper;
import com.smarttek.telegramchatbot.model.Chat;
import com.smarttek.telegramchatbot.model.MessageLog;
import com.smarttek.telegramchatbot.repository.ChatRepository;
import com.smarttek.telegramchatbot.repository.MessageRepository;
import com.smarttek.telegramchatbot.service.impl.MessageServiceImpl;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {
    @InjectMocks
    private MessageServiceImpl messageService;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private ChatRepository chatRepository;
    @Mock
    private MessageMapper messageMapper;

    private MessageLogDto messageLogDto;
    private MessageLog messageLog;
    private Chat chat;
    private MessageLogResponseDto messageLogResponseDto;

    @BeforeEach
    void setUp() {
        chat = new Chat();
        chat.setChatId(1L);
        chat.setMessages(Collections.EMPTY_LIST);

        messageLogDto = new MessageLogDto(
                1L, "hey", "Hello, How can i help you", 1641043200);

        messageLog = new MessageLog();
        messageLog.setChat(chat);
        messageLog.setRequest(messageLogDto.getRequest());
        messageLog.setResponse(messageLogDto.getResponse());
        messageLog.setLocalDateTime(LocalDateTime.now());
        messageLog.setId(1L);

        messageLogResponseDto = new MessageLogResponseDto();
        messageLogResponseDto.setResponse(messageLogDto.getResponse());
        messageLogResponseDto.setRequest(messageLogDto.getRequest());
        messageLogResponseDto.setChatId(messageLogDto.getChatId());
    }

    @Test
    public void save_ValidRequestWithExistingChat_ReturnsMessageLog() {
        when(messageMapper.toModel(any())).thenReturn(messageLog);
        when(messageRepository.save(any())).thenReturn(messageLog);
        when(chatRepository.existsByChatId(any())).thenReturn(true);
        when(chatRepository.findByChatId(any())).thenReturn(chat);
        assertEquals(messageLog, messageService.save(messageLogDto));

    }

    @Test
    public void save_ValidRequestWithNonExistingChat_ReturnsMessageLog() {
        when(messageMapper.toModel(any())).thenReturn(messageLog);
        when(messageRepository.save(any())).thenReturn(messageLog);
        when(chatRepository.existsByChatId(any())).thenReturn(false);
        when(chatRepository.save(any())).thenReturn(chat);
        assertEquals(messageLog, messageService.save(messageLogDto));

    }

    @Test
    public void findAll() {
        when(messageRepository.findAllWithChats(any()))
                .thenReturn(List.of(messageLog));
        when(messageMapper.toDto(any())).thenReturn(messageLogResponseDto);
        assertEquals(List.of(messageLogResponseDto),
                messageService.findAll(Pageable.ofSize(5)));
    }

    @Test
    public void findByChatId() {
        when(messageRepository.findAllByChatIdWithChat(any(), any()))
                .thenReturn(List.of(messageLog));
        when(messageMapper.toDto(any())).thenReturn(messageLogResponseDto);
        assertEquals(List.of(messageLogResponseDto),
                messageService.findByChatId(chat.getChatId(), Pageable.ofSize(5)));
    }
}