package com.smarttek.telegramchatbot.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarttek.telegramchatbot.dto.user.UserLoginRequestDto;
import com.smarttek.telegramchatbot.dto.user.UserLoginResponseDto;
import com.smarttek.telegramchatbot.dto.user.UserRegistrationRequestDto;
import com.smarttek.telegramchatbot.dto.user.UserResponseDto;
import com.smarttek.telegramchatbot.enums.RoleName;
import com.smarttek.telegramchatbot.model.Role;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {
    protected static MockMvc mockMvc;
    private static UserLoginRequestDto loginRequestDto;
    private static UserLoginRequestDto loginInvalidRequestDto;
    private static UserRegistrationRequestDto userDto;
    private static UserRegistrationRequestDto userInvalidDto;
    private static UserResponseDto userResponseDto;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        Role role = new Role();
        role.setId(1L);
        role.setRoleName(RoleName.ROLE_ADMIN);

        userDto = new UserRegistrationRequestDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("johndoe@example.com");
        userDto.setPassword("password123");
        userDto.setRepeatPassword("password123");

        userInvalidDto = new UserRegistrationRequestDto();
        userInvalidDto = new UserRegistrationRequestDto();
        userInvalidDto.setFirstName("J");
        userInvalidDto.setLastName("Doe");
        userInvalidDto.setEmail("joh");
        userInvalidDto.setPassword("password1");
        userInvalidDto.setRepeatPassword("password123");

        userResponseDto = new UserResponseDto();
        userResponseDto.setId(2L);
        userResponseDto.setFirstName(userDto.getFirstName());
        userResponseDto.setLastName(userDto.getLastName());

        loginRequestDto = new UserLoginRequestDto();
        loginRequestDto.setEmail("romakuch@gmail.com");
        loginRequestDto.setPassword("romaaaa");

        loginInvalidRequestDto = new UserLoginRequestDto();
        loginInvalidRequestDto.setEmail("nn");
        loginInvalidRequestDto.setPassword("kj");
    }

    @SneakyThrows
    @Test
    @WithUserDetails("romakuch@gmail.com")
    public void register_nonValidRequest_return400Status() {
        String jsonRequest = objectMapper.writeValueAsString(userInvalidDto);
        MvcResult result = mockMvc.perform(post("/auth/register")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInvalidDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        String actual = result.getResponse().getContentAsString();
        assertTrue(actual.contains("Passwords do not match!"));
        assertTrue(actual.contains("email must be a well-formed email address"));
    }

    @SneakyThrows
    @Test
    public void login_invalidRequest_throwsException() {
        String jsonRequest = objectMapper.writeValueAsString(loginInvalidRequestDto);
        MvcResult result = mockMvc.perform(post("/auth/login")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginInvalidRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        String actual = result.getResponse().getContentAsString();
        assertTrue(actual.contains("email must be a well-formed email address"));
        assertTrue(actual.contains("password length must be between 4 and 255"));
    }

    @SneakyThrows
    @Test
    public void login_validUser_returnsUser() {
        System.out.println(loginRequestDto);
        String jsonRequest = objectMapper.writeValueAsString(loginRequestDto);
        MvcResult result = mockMvc.perform(post("/auth/login")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        UserLoginResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                UserLoginResponseDto.class);
        assertNotNull(actual);
    }
}
