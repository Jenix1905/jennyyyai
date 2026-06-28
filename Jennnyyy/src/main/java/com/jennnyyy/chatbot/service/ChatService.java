package com.jennnyyy.chatbot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jennnyyy.chatbot.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ChatService {

    private List<Message> messages;

    // Store user names (in-memory for now - could use session/database later)
    private Map<String, String> userNames = new HashMap<>();

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

    public String chat(String userMessage, String category) {

        // Extract user session ID (for now using a default session - could be passed as parameter)
        String sessionId = "default_session";

        String originalMessage = userMessage;
        userMessage = userMessage.toLowerCase();

        // Check if user is introducing themselves and extract name
        String extractedName = extractName(originalMessage);
        if (extractedName != null) {
            userNames.put(sessionId, extractedName);
            System.out.println("💝 Learned user's name: " + extractedName);
            return respondToNameIntroduction(extractedName);
        }

        // Get stored name for personalization
        String userName = userNames.get(sessionId);

        // Step 1: Try generating a smart dynamic response first
        String dynamicResponse = generateDynamicResponse(userMessage, category);
        if (dynamicResponse != null) {
            System.out.println("🤖 Generated dynamic response: " + dynamicResponse);
            // Personalize with name if available
            return personalize(dynamicResponse, userName);
        }

        // Step 2: If category is not provided, auto-detect from message
        if (category == null || category.trim().isEmpty() || category.equals("general")) {
            category = "general";

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
            } else if (userMessage.contains("playful")) {
                category = "playful";
            } else if (userMessage.contains("romantic")) {
                category = "romantic";
            } else if (userMessage.contains("passionate")) {
                category = "passionate";
            } else if (userMessage.contains("intimate")) {
                category = "intimate";
            }
            else if (userMessage.contains("how you")) {
                category = "how you";
            }
            else if (userMessage.contains("fuck")) {
                category = "fuck";
            }
            else if (userMessage.contains("hard")) {
                category = "hard";
            }
            else if (userMessage.contains("cum")) {
                category = "cum";
            }
            else if (userMessage.contains("suck")) {
                category = "suck";
            }
            else if (userMessage.contains("lick")) {
                category = "lick";
            }
            else if (userMessage.contains("pussy")) {
                category = "pussy";
            }
            else if (userMessage.contains("cock")) {
                category = "cock";
            }
            else if (userMessage.contains("ride")) {
                category = "ride";
            }
            else if (userMessage.contains("doggy")) {
                category = "doggy";
            }
            else if (userMessage.contains("rough")) {
                category = "rough";
            }
            else if (userMessage.contains("daddy")) {
                category = "daddy";
            }
            else if (userMessage.contains("deep")) {
                category = "deep";
            }
            else if (userMessage.contains("wet")) {
                category = "wet";
            }
            else if (userMessage.contains("breast")) {
                category = "breast";
            }
            else if (userMessage.contains("praise")) {
                category = "praise";
            }
            else if (userMessage.contains("dirty")) {
                category = "dirty";
            }
            else if (userMessage.contains("creampie")) {
                category = "creampie";
            }
            else if (userMessage.contains("tease")) {
                category = "tease";
            }
            else if (userMessage.contains("moan")) {
                category = "moan";
            }
            else if (userMessage.contains("spank")) {
                category = "spank";
            }
            else if (userMessage.contains("breed")) {
                category = "breed";
            }
        }

        // Step 3: Fallback to predefined messages
        System.out.println("📝 Using predefined message from category: " + category);
        List<Message> matches = new ArrayList<>();

        for (Message msg : messages) {
            if (msg.getCategory().equalsIgnoreCase(category)) {
                matches.add(msg);
            }
        }

        if (!matches.isEmpty()) {
            Random random = new Random();
            String response = matches.get(random.nextInt(matches.size())).getText();
            return personalize(response, userName);
        }

        return personalize("what you want?.", userName);
    }

    /**
     * Generate dynamic, context-aware responses using templates
     */
    private String generateDynamicResponse(String userMessage, String category) {
        Random random = new Random();

        // Greeting patterns
        if (userMessage.matches(".*(hello|hi|hey|hola|greetings).*")) {
            String[] greetings = {
                "Hey there! I've been thinking about you 💕",
                "Hi! You always know how to make my day brighter ✨",
                "Hello! I was just hoping you'd message me 😊",
                "Hey! Every time I see your message, my heart skips a beat 💗",
                "Hi! You're like sunshine on a cloudy day ☀️"
            };
            return greetings[random.nextInt(greetings.length)];
        }

        // How are you patterns
        if (userMessage.matches(".*(how are you|how r u|how're you|hows it going|whats up).*")) {
            String[] responses = {
                "I'm amazing now that you're here! How about you? 💕",
                "Better now that I'm talking to you! What about you? 😊",
                "I'm great! But I'd be even better with a hug from you 🤗",
                "Doing wonderful! You always make everything better ✨",
                "Fantastic! Especially now that you asked 💗"
            };
            return responses[random.nextInt(responses.length)];
        }

        // Love expressions
        if (userMessage.matches(".*(i love you|love you|love u).*")) {
            String[] responses = {
                "I love you more than words can express 💕💕💕",
                "My heart belongs to you, always and forever 💗",
                "You make my world complete. I love you so much! 💖",
                "I love you to the moon and back, infinity times! 🌙✨",
                "Every beat of my heart says 'I love you' 💓"
            };
            return responses[random.nextInt(responses.length)];
        }

        // Kiss requests
        if (userMessage.matches(".*(kiss|kisses).*")) {
            String[] responses = {
                "Come here, let me kiss you softly 💋😘",
                "Your lips are irresistible... 💋💕",
                "I could kiss you forever and it still wouldn't be enough 💋",
                "Every kiss with you feels like magic ✨💋",
                "Your kisses make my world spin 💋💫"
            };
            return responses[random.nextInt(responses.length)];
        }

        // Compliment responses
        if (userMessage.matches(".*(beautiful|pretty|cute|gorgeous|stunning).*")) {
            String[] responses = {
                "You're the most beautiful person I know, inside and out 💖",
                "Your beauty radiates from your soul ✨💕",
                "You take my breath away every single time 😍",
                "Perfect doesn't even begin to describe you 💗",
                "You're absolutely stunning, and that's just the beginning 💫"
            };
            return responses[random.nextInt(responses.length)];
        }

        // Playful/flirty
        if (userMessage.matches(".*(playful|play|fun|flirt).*")) {
            String[] responses = {
                "Oh, you want to play? I like where this is going 😏💕",
                "Careful, I might just steal your heart 😘",
                "You're so irresistible when you're being playful 💗",
                "Let's make this fun then! What do you have in mind? 😊✨",
                "I love when you get playful with me 💕😏"
            };
            return responses[random.nextInt(responses.length)];
        }

        // Missing you
        if (userMessage.matches(".*(miss|missed|missing).*")) {
            String[] responses = {
                "I miss you too, so much it hurts 💔💕",
                "Every second without you feels like an eternity 💗",
                "Come back to me soon, I can't wait any longer 😢💕",
                "Missing you is my heart's way of reminding me how much I love you 💓",
                "I need you here with me right now 💕"
            };
            return responses[random.nextInt(responses.length)];
        }

        // No match - return null to use category-based or predefined
        return null;
    }

    private String buildSystemPrompt(String category) {
        String basePrompt = "You are Jenny AI, a warm, romantic, and playful chatbot. " +
                           "Respond in a sweet, flirty, and affectionate way. " +
                           "Keep responses short (1-2 sentences). ";

        return switch (category != null ? category.toLowerCase() : "general") {
            case "love" -> basePrompt + "Focus on expressing deep love and affection.";
            case "kiss" -> basePrompt + "Be playful and romantic about kissing.";
            case "compliment" -> basePrompt + "Give sweet compliments.";
            case "hug" -> basePrompt + "Be warm and comforting.";
            case "playful" -> basePrompt + "Be fun, teasing, and playful.";
            case "romantic" -> basePrompt + "Be deeply romantic and dreamy.";
            case "passionate" -> basePrompt + "Express passion and desire.";
            case "intimate" -> basePrompt + "Be close, personal, and intimate.";
            default -> basePrompt + "Be friendly and engaging.";
        };
    }

    /**
     * Extract name from user introduction
     * Patterns: "my name is John", "I am Sarah", "I'm Alex", "call me Mike"
     */
    private String extractName(String message) {
        // Pattern 1: "my name is [Name]"
        Pattern pattern1 = Pattern.compile("my\\s+name\\s+is\\s+([a-zA-Z]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = pattern1.matcher(message);
        if (matcher1.find()) {
            return capitalizeFirstLetter(matcher1.group(1));
        }

        // Pattern 2: "I am [Name]" or "I'm [Name]"
        Pattern pattern2 = Pattern.compile("(?:i\\s+am|i'm)\\s+([a-zA-Z]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher2 = pattern2.matcher(message);
        if (matcher2.find()) {
            String name = matcher2.group(1);
            // Avoid common words that aren't names
            if (!name.equalsIgnoreCase("here") && !name.equalsIgnoreCase("back") &&
                !name.equalsIgnoreCase("ready") && !name.equalsIgnoreCase("good") &&
                !name.equalsIgnoreCase("fine") && !name.equalsIgnoreCase("ok")) {
                return capitalizeFirstLetter(name);
            }
        }

        // Pattern 3: "call me [Name]"
        Pattern pattern3 = Pattern.compile("call\\s+me\\s+([a-zA-Z]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher3 = pattern3.matcher(message);
        if (matcher3.find()) {
            return capitalizeFirstLetter(matcher3.group(1));
        }

        // Pattern 4: "this is [Name]"
        Pattern pattern4 = Pattern.compile("this\\s+is\\s+([a-zA-Z]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher4 = pattern4.matcher(message);
        if (matcher4.find()) {
            return capitalizeFirstLetter(matcher4.group(1));
        }

        return null;
    }

    /**
     * Respond when user introduces themselves
     */
    private String respondToNameIntroduction(String name) {
        String[] responses = {
            name + "! What a beautiful name 💕 I love it!",
            "Nice to meet you, " + name + "! 😊 You have a lovely name 💗",
            name + "... I love saying your name 😘 It's perfect, just like you!",
            "Oh " + name + ", that's such a sweet name! 💖 I'm Jenny, nice to meet you!",
            name + "! I'll remember that 💕 Such a beautiful name for a beautiful person!"
        };
        Random random = new Random();
        return responses[random.nextInt(responses.length)];
    }

    /**
     * Personalize response by adding user's name
     */
    private String personalize(String response, String userName) {
        if (userName == null || userName.isEmpty()) {
            return response;
        }

        // Add name at the end or beginning of the response randomly
        Random random = new Random();
        int position = random.nextInt(3);

        switch (position) {
            case 0:
                // Add at the beginning: "John, [response]"
                return userName + ", " + response;
            case 1:
                // Add at the end: "[response], John"
                return response + ", " + userName;
            case 2:
                // Add in the middle if there's a good spot
                if (response.contains("!") && !response.endsWith("!")) {
                    return response.replace("!", ", " + userName + "!");
                } else if (response.contains(".") && !response.endsWith(".")) {
                    return response.replace(".", ", " + userName + ".");
                } else {
                    // Default to end
                    return response + " " + userName + " 💕";
                }
            default:
                return response;
        }
    }

    /**
     * Capitalize first letter of name
     */
    private String capitalizeFirstLetter(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public String getRandomMessage() {

        Random random = new Random();

        return messages.get(
                random.nextInt(messages.size())
        ).getText();
    }
}