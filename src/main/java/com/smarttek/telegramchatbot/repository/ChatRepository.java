package com.smarttek.telegramchatbot.repository;

import com.smarttek.telegramchatbot.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("FROM Chat c JOIN FETCH c.messages")
    Chat findByChatId(Long chatId);

    Boolean existsByChatId(Long chatId);
}
