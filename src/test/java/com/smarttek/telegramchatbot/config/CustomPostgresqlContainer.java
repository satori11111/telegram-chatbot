package com.smarttek.telegramchatbot.config;

import org.testcontainers.containers.PostgreSQLContainer;

public class CustomPostgresqlContainer extends PostgreSQLContainer<CustomPostgresqlContainer> {
    private static final String DB_IMAGE = "postgres:latest";
    private static final String DB_URL = "CONTAINER.URL=";
    private static final String DB_USERNAME = "CONTAINER.USERNAME=";
    private static final String DB_PASSWORD = "CONTAINER.PASSWORD=";
    private static CustomPostgresqlContainer mysqlContainer;

    private CustomPostgresqlContainer() {
        super(DB_IMAGE);
    }

    public static synchronized CustomPostgresqlContainer getInstance() {
        if (mysqlContainer == null) {
            mysqlContainer = new CustomPostgresqlContainer();
        }
        return mysqlContainer;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty(DB_URL, mysqlContainer.getJdbcUrl());
        System.setProperty(DB_USERNAME, mysqlContainer.getUsername());
        System.setProperty(DB_PASSWORD, mysqlContainer.getPassword());
    }

    @Override
    public void stop() {

    }
}

