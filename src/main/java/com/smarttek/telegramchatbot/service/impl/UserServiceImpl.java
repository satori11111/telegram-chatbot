package com.smarttek.telegramchatbot.service.impl;


import com.smarttek.telegramchatbot.dto.user.UserRegistrationRequestDto;
import com.smarttek.telegramchatbot.dto.user.UserResponseDto;
import com.smarttek.telegramchatbot.enums.RoleName;
import com.smarttek.telegramchatbot.exception.RegistrationException;
import com.smarttek.telegramchatbot.mapper.UserMapper;
import com.smarttek.telegramchatbot.model.Role;
import com.smarttek.telegramchatbot.model.User;
import com.smarttek.telegramchatbot.repository.RoleRepository;
import com.smarttek.telegramchatbot.repository.UserRepository;
import com.smarttek.telegramchatbot.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("User with email: "
                    + request.getEmail() + " already registered");
        }
        User user = userMapper.toModel(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.getRoleByRoleName(RoleName.ROLE_ADMIN);
        user.setRoles(Set.of(role));
        User savedUser = userRepository.save(user);
        return userMapper.toRegisterDto(savedUser);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find user with email " + authentication.getName()));
    }
}
