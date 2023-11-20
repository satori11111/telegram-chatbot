package com.smarttek.telegramchatbot.mapper;

import com.smarttek.telegramchatbot.bot.MapperConfig;
import com.smarttek.telegramchatbot.dto.user.UserLoginResponseDto;
import com.smarttek.telegramchatbot.dto.user.UserRegistrationRequestDto;
import com.smarttek.telegramchatbot.dto.user.UserResponseDto;
import com.smarttek.telegramchatbot.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserLoginResponseDto toDto(User user);

    UserResponseDto toRegisterDto(User user);

    User toModel(UserRegistrationRequestDto requestDto);
}
