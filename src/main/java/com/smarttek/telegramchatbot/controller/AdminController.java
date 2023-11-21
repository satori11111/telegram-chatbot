package com.smarttek.telegramchatbot.controller;

import com.smarttek.telegramchatbot.dto.MessageLogResponseDto;
import com.smarttek.telegramchatbot.service.MessageService;
import com.smarttek.telegramchatbot.service.TelegramSenderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "admin controllers",description = "Endpoints for retrieving message logs")
@RequiredArgsConstructor
@RestController
public class AdminController {
    private final MessageService messageService;
    private final TelegramSenderService telegramSenderService;

    @GetMapping("/messages")
    @Operation(summary = "Get all messages")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<MessageLogResponseDto> findAll(Pageable pageable) {
        return messageService.findAll(pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get messages by chat ID")
    @GetMapping("/messages/{chatId}")
    public List<MessageLogResponseDto> findByChatId(@PathVariable Long chatId, Pageable pageable) {
       return messageService.findByChatId(chatId, pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Send a message by chat ID")
    @GetMapping("/messages/{chatId}/send")
    public String sendMessageByChatId(@PathVariable Long chatId, @RequestParam String text) {
        return telegramSenderService.sendMessageByChatId(chatId, text);
    }
}
