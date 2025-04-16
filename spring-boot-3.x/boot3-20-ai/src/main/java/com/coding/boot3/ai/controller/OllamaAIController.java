package com.coding.boot3.ai.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
public class OllamaAIController {
    private final OllamaChatModel chatModel;

    public OllamaAIController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ollama/ai/chat")
    public String ollamaHello(String prompt) {
        String call = chatModel.call(prompt);
        return call;
    }
}
