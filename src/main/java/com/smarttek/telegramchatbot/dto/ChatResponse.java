package com.smarttek.telegramchatbot.dto;

import java.util.List;

public class ChatResponse {

    private List<Choice> choices;

    public ChatResponse(List<Choice> choices) {
        this.choices = choices;
    }
    public ChatResponse() {
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public static class Choice {

        private int index;
        private OpenAiMessage openAiMessage;

        public Choice(int index, OpenAiMessage openAiMessage) {
            this.index = index;
            this.openAiMessage = openAiMessage;
        }
        public Choice() {
        }
        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public OpenAiMessage getMessage() {
            return openAiMessage;
        }

        public void setMessage(OpenAiMessage openAiMessage) {
            this.openAiMessage = openAiMessage;
        }
    }
}
