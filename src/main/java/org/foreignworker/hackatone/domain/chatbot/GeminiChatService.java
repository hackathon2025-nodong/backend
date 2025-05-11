package org.foreignworker.hackatone.domain.chatbot;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class GeminiChatService {
    private final ChatLanguageModel chatLanguageModel;

    public GeminiChatService(@Value("${gemini.api-key}") String apiKey) {
        this.chatLanguageModel = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-1.5-flash")
                .temperature(0.2)
                .build();
    }

    public String chat(String s3Url, String prompt) {
        String systemPrompt = """
            """;

        PromptTemplate promptTemplate = PromptTemplate.from(systemPrompt + "\n\n이미지 URL: {{imageUrl}}\n\n질문: {{prompt}}");
        Prompt finalPrompt = promptTemplate.apply(Map.of(
            "imageUrl", s3Url,
            "prompt", prompt
        ));

        return chatLanguageModel.chat(finalPrompt.text());
    }
}