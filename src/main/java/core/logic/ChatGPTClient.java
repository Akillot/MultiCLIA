package core.logic;

import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Collections;

public class ChatGPTClient {
    private static final String API_KEY;

    static {
        Dotenv dotenv = Dotenv.load();
        API_KEY = dotenv.get("OPENAI_API_KEY");

        if (API_KEY == null || API_KEY.isEmpty()) {
            throw new IllegalArgumentException("API key for OpenAI is missing or empty!");
        }
    }

    public static String sendMessage(String message) {
        OpenAiService service = new OpenAiService(API_KEY);

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(Collections.singletonList(new ChatMessage("user", message)))
                .maxTokens(200)
                .temperature(0.7)
                .build();

        return service.createChatCompletion(request).getChoices().get(0).getMessage().getContent();
    }
}
