package com.smarttek.telegramchatbot.controller;

import com.smarttek.telegramchatbot.dto.user.UserLoginRequestDto;
import com.smarttek.telegramchatbot.dto.user.UserLoginResponseDto;
import com.smarttek.telegramchatbot.dto.user.UserRegistrationRequestDto;
import com.smarttek.telegramchatbot.dto.user.UserResponseDto;
import com.smarttek.telegramchatbot.exception.RegistrationException;
import com.smarttek.telegramchatbot.service.AuthenticationService;
import com.smarttek.telegramchatbot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User authentication",description = "Endpoints for managing users")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "login registered user",description = "login registered user")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/register")
    @Operation(summary = "register user",description = "register user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }
}
