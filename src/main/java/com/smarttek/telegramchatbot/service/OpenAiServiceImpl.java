package com.smarttek.telegramchatbot.service;

import com.smarttek.telegramchatbot.dto.ChatRequest;
import com.smarttek.telegramchatbot.dto.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAiServiceImpl implements OpenAiService {
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
