package com.smarttek.telegramchatbot.mapper;

import com.smarttek.telegramchatbot.bot.MapperConfig;
import com.smarttek.telegramchatbot.dto.message.MessageLogDto;
import com.smarttek.telegramchatbot.dto.message.MessageLogResponseDto;
import com.smarttek.telegramchatbot.model.MessageLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface MessageMapper {
    MessageLog toModel(MessageLogDto messageLogDto);

    @Mapping(target = "chatId", source = "chat.id")
    MessageLogResponseDto toDto(MessageLog messageLog);
}
