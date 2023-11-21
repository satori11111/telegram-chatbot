package com.smarttek.telegramchatbot.service;

import com.smarttek.telegramchatbot.dto.user.UserLoginRequestDto;
import com.smarttek.telegramchatbot.dto.user.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto authenticate(UserLoginRequestDto requestDto);
}
