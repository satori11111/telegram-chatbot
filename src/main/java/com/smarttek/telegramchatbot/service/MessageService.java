package com.smarttek.telegramchatbot.service;

import com.smarttek.telegramchatbot.dto.MessageLogDto;
import com.smarttek.telegramchatbot.dto.MessageLogResponseDto;
import com.smarttek.telegramchatbot.model.MessageLog;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface MessageService {
   MessageLog save(MessageLogDto messageLogDto);

   List<MessageLogResponseDto> findAll(Pageable pageable);

   List<MessageLogResponseDto> findByChatId(Long chatId,Pageable pageable);

}
