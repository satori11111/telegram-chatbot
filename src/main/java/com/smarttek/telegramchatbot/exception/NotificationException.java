package com.smarttek.telegramchatbot.exception;

public class NotificationException extends RuntimeException {
    public NotificationException(String message) {
        super(message);
    }

    public NotificationException(String message, Throwable e) {
        super(message, e);
    }
}