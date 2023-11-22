package com.smarttek.telegramchatbot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringExclude;

@Entity
@Data
@Table(name = "message_log", schema = "public")
public class MessageLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToStringExclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;
    @Column(nullable = false)
    private String request;
    @Column(nullable = false)
    private String response;
    @Column(nullable = false, name = "date")
    private LocalDateTime localDateTime;

    @Override
    public String toString() {
        return "MessageLog{" +
                "id=" + id +
                ", request='" + request + '\'' +
                ", response='" + response + '\'' +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
