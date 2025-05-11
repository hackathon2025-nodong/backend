package org.foreignworker.hackatone.domain.chatbot;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final GeminiChatService gemini;

    // 단순 텍스트 요청/응답
    @PostMapping
    public ResponseEntity<String> chat(@RequestParam String s3Url, @RequestParam String prompt) {
        return ResponseEntity.ok(gemini.chat(s3Url, prompt));
    }

    @GetMapping
    public String chatPage() {
        return "chat";
    }
}