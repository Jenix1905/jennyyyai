package com.jennnyyy.chatbot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jennnyyy.chatbot.model.Message;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ChatService {

    private List<Message> messages;

    public ChatService() {

        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream inputStream =
                    getClass().getClassLoader().getResourceAsStream("messages.json");

            messages = mapper.readValue(
                    inputStream,
                    new TypeReference<List<Message>>() {}
            );

            System.out.println("Loaded Messages: " + messages.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String chat(String userMessage) {

        userMessage = userMessage.toLowerCase();

        String category = "general";

        if (userMessage.contains("love")) {
            category = "love";
        } else if (userMessage.contains("kiss")) {
            category = "kiss";
        } else if (userMessage.contains("beautiful")
                || userMessage.contains("pretty")
                || userMessage.contains("cute")) {
            category = "compliment";
        } else if (userMessage.contains("hug")) {
            category = "hug";
        }

        List<Message> matches = new ArrayList<>();

        for (Message msg : messages) {

            if (msg.getCategory().equalsIgnoreCase(category)) {
                matches.add(msg);
            }
        }

        if (!matches.isEmpty()) {
            Random random = new Random();
            return matches.get(random.nextInt(matches.size())).getText();
        }

        return "what you want?.";
    }

    public String getRandomMessage() {

        Random random = new Random();

        return messages.get(
                random.nextInt(messages.size())
        ).getText();
    }
}