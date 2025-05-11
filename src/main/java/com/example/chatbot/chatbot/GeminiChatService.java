package com.example.chatbot.chatbot;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import org.springframework.stereotype.Service;

@Service
public class GeminiChatService {

    private final ChatLanguageModel model;

    public GeminiChatService() {
        // 환경 변수에서 API 키를 읽어 모델 인스턴스를 생성
        this.model = GoogleAiGeminiChatModel.builder()
                .apiKey(System.getenv("GEMINI_API_KEY"))
                .modelName("gemini-1.5-flash")   // 최신 Flash 버전을 선택
                .temperature(0.2)                // 답변 다양성 조절
                .build();                        // :contentReference[oaicite:3]{index=3}
    }

    public String chat(String userMessage) {
        return model.chat(userMessage);          // LangChain4j 기본 메서드 :contentReference[oaicite:4]{index=4}
    }
}
