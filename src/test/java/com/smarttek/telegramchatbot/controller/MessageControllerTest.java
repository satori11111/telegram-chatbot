package com.smarttek.telegramchatbot.controller;

import static com.smarttek.telegramchatbot.config.SqlFilesPaths.MESSAGE_LOG_CHAT_DELETE;
import static com.smarttek.telegramchatbot.config.SqlFilesPaths.MESSAGE_LOG_CHAT_INSERT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarttek.telegramchatbot.dto.message.MessageLogDto;
import com.smarttek.telegramchatbot.dto.message.MessageLogResponseDto;
import com.smarttek.telegramchatbot.model.Chat;
import com.smarttek.telegramchatbot.model.MessageLog;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Sql(scripts = {
        MESSAGE_LOG_CHAT_INSERT
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        MESSAGE_LOG_CHAT_DELETE
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageControllerTest {
    protected static MockMvc mockMvc;
    private MessageLogDto messageLogDto;
    private MessageLog messageLog;
    private Chat chat;
    private MessageLogResponseDto messageLog1;
    private MessageLogResponseDto messageLog2;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @BeforeEach
    void setUp() {
        chat = new Chat();
        chat.setChatId(1L);
        chat.setMessages(Collections.EMPTY_LIST);

        messageLogDto = new MessageLogDto(
                1L, "hey", "Hello, How can i help you", 1641043200);

        messageLog = new MessageLog();
        messageLog.setChat(chat);
        messageLog.setRequest(messageLogDto.getRequest());
        messageLog.setResponse(messageLogDto.getResponse());
        messageLog.setLocalDateTime(LocalDateTime.now());
        messageLog.setId(1L);

        messageLog1 = new MessageLogResponseDto();
        messageLog1.setId(1L);
        messageLog1.setChatId(1L);
        messageLog1.setRequest("hey");
        messageLog1.setResponse("hello, how can I help you");
        messageLog1.setLocalDateTime(LocalDateTime.parse("2023-10-23T01:44:55"));

        messageLog2 = new MessageLogResponseDto();
        messageLog2.setId(2L);
        messageLog2.setChatId(1L);
        messageLog2.setRequest("hello");
        messageLog2.setResponse("hey");
        messageLog2.setLocalDateTime(LocalDateTime.parse("2023-10-23T01:44:55"));
    }
    @Test
    @SneakyThrows
    @WithUserDetails("romakuch@gmail.com")
    public void findAll_validRequest_ReturnsList() {
        MvcResult result = mockMvc.perform(get("/messages?page=0&size=2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful()).andReturn();
        List<MessageLogResponseDto> actual =
                objectMapper.readValue(result.getResponse().getContentAsString(), ArrayList.class);
        List<MessageLogResponseDto> actualDtos = objectMapper.convertValue(actual,
                new TypeReference<List<MessageLogResponseDto>>() {
                });
        Assertions.assertEquals(List.of(messageLog1, messageLog2), actualDtos);
    }

    @Test
    @SneakyThrows
    @WithUserDetails("romakuch@gmail.com")
    public void findByChatId_validRequest_ReturnsList() {
        MvcResult result = mockMvc.perform(get("/messages/1?page=0&size=2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful()).andReturn();
        List<MessageLogResponseDto> actual =
                objectMapper.readValue(result.getResponse().getContentAsString(), ArrayList.class);
        List<MessageLogResponseDto> actualDtos = objectMapper.convertValue(actual,
                new TypeReference<List<MessageLogResponseDto>>() {
                });
        Assertions.assertEquals(List.of(messageLog1, messageLog2), actualDtos);
    }
}
