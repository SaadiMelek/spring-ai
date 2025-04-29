package com.mlk.ai.controller;

import com.mlk.ai.LlamaAiService;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class AIController {

    @Autowired
    LlamaAiService llamaAiService;

    @GetMapping("/ai/v1/generate")
    public String generate(@RequestParam(value = "promptMessage") String promptMessage) {
        return llamaAiService.generateResult(promptMessage);
    }

    @GetMapping(value = "/ai/v1/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return llamaAiService.generateStream(message);
    }
}
