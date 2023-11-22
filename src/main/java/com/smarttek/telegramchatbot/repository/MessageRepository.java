package com.smarttek.telegramchatbot.repository;

import com.smarttek.telegramchatbot.model.MessageLog;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageLog, Long> {
    @Query("FROM MessageLog m JOIN FETCH m.chat c WHERE c.chatId = :chatId")
    List<MessageLog> findAllByChatIdWithChat(Long chatId, Pageable pageable);
    @Query("FROM MessageLog m JOIN FETCH m.chat")
    List<MessageLog> findAllWithChats(Pageable pageable);
}
