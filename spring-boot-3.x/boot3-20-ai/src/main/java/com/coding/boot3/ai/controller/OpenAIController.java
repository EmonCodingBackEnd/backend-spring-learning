package com.coding.boot3.ai.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@Slf4j
@CrossOrigin
@RestController
public class OpenAIController {
    private final OpenAiChatModel chatModel;

    private final OpenAiImageModel imageModel;

    @Autowired
    public OpenAIController(OpenAiChatModel chatModel, OpenAiImageModel openAiImageModel) {
        this.chatModel = chatModel;
        this.imageModel = openAiImageModel;
    }

    @GetMapping("/ai/generate")
    public Map<String, String> generate(@RequestParam(value = "message", defaultValue = "给我讲个笑话") String message) {
        return Map.of("generation", this.chatModel.call(message));
    }

    /**
     * 生成聊天响应流接口
     * 根据用户输入的消息生成聊天响应流
     *
     * @param message 用户输入的消息，默认为“给我讲个笑话”
     * @return Flux<ChatResponse>类型的聊天响应流
     */
    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "给我讲个笑话") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }

    @GetMapping(value = "/ai/generateStreamSse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> generateStreamSse(@RequestParam(value = "message", defaultValue = "给我讲个笑话") String message) {
        return this.chatModel.stream(message);
    }

    // ==================================================华丽的分割线==================================================


    @GetMapping("/ai/image")
    public String generateImages(String prompt) {
        ImageResponse response = imageModel.call(new ImagePrompt(prompt));
        Image output = response.getResult().getOutput();
        // 图片的url地址
        String url = output.getUrl();
        // 图片的base64编码
        String b64Json = output.getB64Json();
        log.info("url: {}, b64Json: {}", url, b64Json);
        return output.getB64Json();
    }
}
