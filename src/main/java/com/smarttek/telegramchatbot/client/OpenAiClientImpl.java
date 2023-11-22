package com.smarttek.telegramchatbot.client;

import com.smarttek.telegramchatbot.client.OpenAiClient;
import com.smarttek.telegramchatbot.dto.ChatRequest;
import com.smarttek.telegramchatbot.dto.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAiClientImpl implements OpenAiClient {
    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate opeanaiRestTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;
    @Override
    public String getResponse(String prompt) {

            ChatRequest request = new ChatRequest(model, prompt);

            ChatResponse response = opeanaiRestTemplate.postForObject(apiUrl, request, ChatResponse.class);

            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                return "No response";
            }
            return response.getChoices().get(0).getMessage().getContent();
        }
}
