package org.foreignworker.hackatone.domain.test;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@RequestMapping("/ai")
public class ChatController {

    private final VertexAiGeminiChatModel chatModel;

    @Autowired
    public ChatController(VertexAiGeminiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    /** 단일 응답 */
    @GetMapping("/generate")
    public Map<String, ?> generate(
            @RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {

        return Map.of("generation", this.chatModel.call(message));
    }

    /** 스트리밍 응답 (Server‑Sent Events) */
    @GetMapping(value = "/generateStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<?> generateStream(
            @RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {

        Prompt prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }
}
