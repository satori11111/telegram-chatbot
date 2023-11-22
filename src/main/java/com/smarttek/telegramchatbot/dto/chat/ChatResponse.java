package com.smarttek.telegramchatbot.dto.chat;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatResponse {

    private List<Choice> choices;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {

        private int index;
        private OpenAiMessage openAiMessage;
    }
}
