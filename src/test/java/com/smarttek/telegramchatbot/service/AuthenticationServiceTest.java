package com.smarttek.telegramchatbot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.smarttek.telegramchatbot.dto.user.UserLoginRequestDto;
import com.smarttek.telegramchatbot.dto.user.UserLoginResponseDto;
import com.smarttek.telegramchatbot.model.User;
import com.smarttek.telegramchatbot.security.JwtUtil;
import com.smarttek.telegramchatbot.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    private static User user;
    private static UserLoginRequestDto userLoginRequestDto;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeAll
    static void beforeAll() {
        userLoginRequestDto = new UserLoginRequestDto();
        userLoginRequestDto.setPassword("password");
        userLoginRequestDto.setEmail("emaill");
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password123");
    }

    @Test
    public void authenticate_validUser_returnsUser() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(
                UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getName()).thenReturn(user.getEmail());
        String expected = "test token";
        when(jwtUtil.generateToken(anyString())).thenReturn(expected);
        assertEquals(new UserLoginResponseDto(expected),
                authenticationService.authenticate(userLoginRequestDto));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil).generateToken(anyString());
    }
}
