package com.smarttek.telegramchatbot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.smarttek.telegramchatbot.dto.user.UserRegistrationRequestDto;
import com.smarttek.telegramchatbot.dto.user.UserResponseDto;
import com.smarttek.telegramchatbot.enums.RoleName;
import com.smarttek.telegramchatbot.exception.RegistrationException;
import com.smarttek.telegramchatbot.mapper.UserMapper;
import com.smarttek.telegramchatbot.model.Role;
import com.smarttek.telegramchatbot.model.User;
import com.smarttek.telegramchatbot.repository.RoleRepository;
import com.smarttek.telegramchatbot.repository.UserRepository;
import com.smarttek.telegramchatbot.service.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static UserRegistrationRequestDto userDto;
    private static User user;
    private static UserResponseDto userResponseDto;
    private static Role role;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeAll
    static void beforeAll() {
        userDto = new UserRegistrationRequestDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("johndoe@example.com");
        userDto.setPassword("password123");
        userDto.setRepeatPassword("password123");
        role = new Role();
        role.setId(1L);
        role.setRoleName(RoleName.ROLE_ADMIN);

        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password123");


        userResponseDto = new UserResponseDto();
        userResponseDto.setFirstName("John");
        userResponseDto.setLastName("Doe");
    }

    @SneakyThrows
    @Test
    public void register_validRequest_returnRegisteredUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn(userDto.getPassword());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toRegisterDto(any(User.class))).thenReturn(userResponseDto);
        when(userMapper.toModel(any(UserRegistrationRequestDto.class))).thenReturn(user);
        when(roleRepository.getRoleByRoleName(RoleName.ROLE_ADMIN)).thenReturn(role);

        assertEquals(userResponseDto, userService.register(userDto));
        verify(userRepository).findByEmail(anyString());
        verify(passwordEncoder).encode(anyString());
        verify(userRepository).save(any(User.class));
        verify(userMapper).toRegisterDto(any(User.class));
        verify(userMapper).toModel(any(UserRegistrationRequestDto.class));
        verify(roleRepository).getRoleByRoleName(RoleName.ROLE_ADMIN);
    }

    @SneakyThrows
    @Test
    public void register_nonValidRequest_returnRegisteredUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        RegistrationException exception = assertThrows(
                RegistrationException.class, () -> userService.register(userDto));
        String expected = "User with email: johndoe@example.com already registered";
        assertEquals(expected, exception.getMessage());
        verify(userRepository).findByEmail(anyString());
    }

    @Test
    public void getAuthenticatedUser_invalidRequest_throwsException() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(user.getEmail());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getAuthenticatedUser());
        verify(userRepository).findByEmail(anyString());
    }

    @Test
    public void getAuthenticatedUser_validRequest_returnUser() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(authentication.getName()).thenReturn(user.getEmail());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertEquals(user, userService.getAuthenticatedUser());
        verify(userRepository).findByEmail(anyString());
    }
}
