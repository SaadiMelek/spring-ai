package com.mlk.ai;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
public class LlamaAiService {

    @Autowired
    private OllamaChatModel chatModel;


    public String generateResult(String message) {
        ChatResponse response = chatModel.call(
                new Prompt(
                        message,
                        OllamaOptions.builder()
                                .model(OllamaModel.LLAMA3)
                                .temperature(0.4)
                                .build()
                ));
        return response.getResult().getOutput().getText();
    }

    public Flux<String> generateStream(String message) {
        Prompt prompt = new Prompt(message, OllamaOptions.builder()
                .model(OllamaModel.LLAMA3)
                .temperature(0.4)
                .build());

        return this.chatModel.stream(prompt)
                .map(response -> cleanText(response.getResult().getOutput().getText())) // Clean each chunk
                .filter(text -> text != null && !text.isBlank()) // Remove empty parts
                .buffer(20) // Collect larger chunks before merging
                .map(chunks -> String.join(" ", chunks)) // Join chunks correctly
                .onErrorResume(ex -> {
                    System.err.println("Error occurred: " + ex.getMessage());
                    return Flux.just("Error processing request");
                });
    }

    // Function to clean and format text properly
    private String cleanText(String text) {
        if (text == null || text.isBlank()) return "";

        text = text.replaceAll("(?i)^data:\\s*", ""); // Remove "data:" at the beginning
        text = text.replaceAll("\\s+", " ").trim(); // Normalize spaces
        text = text.replaceAll("(?<=\\d)\\s(?=\\d)", ""); // Fix numbers (e.g., "2 0 2 2" → "2022")
        text = text.replaceAll("(?<![A-Za-z])\\s(?![A-Za-z])", ""); // Fix split words (e.g., "Q atar" → "Qatar")

        return text;
    }


}
