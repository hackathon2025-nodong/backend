package com.example.chatbot.chatbot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final GeminiChatService gemini;

    // 단순 텍스트 요청/응답
    @PostMapping
    public ResponseEntity<String> chat(@RequestBody String userMessage) {
        return ResponseEntity.ok(gemini.chat(userMessage));
    }

    @GetMapping
    public String chatPage() {
        return "chat";
    }
}
