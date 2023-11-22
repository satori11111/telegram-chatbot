package com.smarttek.telegramchatbot.repository;

import static com.smarttek.telegramchatbot.config.SqlFilesPaths.MESSAGE_LOG_CHAT_DELETE;
import static com.smarttek.telegramchatbot.config.SqlFilesPaths.MESSAGE_LOG_CHAT_INSERT;

import com.smarttek.telegramchatbot.model.MessageLog;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = {
        MESSAGE_LOG_CHAT_INSERT
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        MESSAGE_LOG_CHAT_DELETE
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;
    MessageLog firstMessageLog;
    MessageLog secondMessageLog;

    @BeforeEach
    void setUp() {
        firstMessageLog = new MessageLog();
        firstMessageLog.setId(1L);
        firstMessageLog.setRequest("hey");
        firstMessageLog.setResponse("hello, how can I help you");
        firstMessageLog.setLocalDateTime(LocalDateTime.parse("2023-10-23T01:44:55"));
        secondMessageLog = new MessageLog();
        secondMessageLog.setId(2L);
        secondMessageLog.setRequest("hello");
        secondMessageLog.setResponse("hey");
        secondMessageLog.setLocalDateTime(LocalDateTime.parse("2023-10-23T01:44:55"));
    }

    @Test
    void findAllByChatIdWithChat_ValidId_returnsList() {
        List<MessageLog> allByChatIdWithChat = messageRepository.findAllByChatIdWithChat(1L, Pageable.ofSize(5));
        Assertions.assertEquals(List.of(firstMessageLog,secondMessageLog), allByChatIdWithChat);
    }
    @Test
    void findAllWithChats_ValidRequest_returnsList() {
        List<MessageLog> allWithChat = messageRepository.findAllWithChats( Pageable.ofSize(5));
        Assertions.assertEquals(List.of(firstMessageLog,secondMessageLog), allWithChat);
    }

}
//    @Query("FROM MessageLog m JOIN FETCH m.chat c WHERE c.id = :chatId")
//    List<MessageLog> findAllByChatIdWithChat(Long chatId, Pageable pageable);
//    @Query("FROM MessageLog m JOIN FETCH m.chat")
//    List<MessageLog> findAllWithChats(Pageable pageable);
