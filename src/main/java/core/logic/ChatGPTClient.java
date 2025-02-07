package core.logic;

import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;

public class ChatGPTClient {
    private static final String API_KEY;

    @Getter
    private static String model = "gpt-3.5-turbo";
    @Getter
    private static int maxTokens = 200;
    @Getter @Setter
    private static double temperature = 0.7;

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
                .model(model)
                .messages(Collections.singletonList(new ChatMessage("user", message)))
                .maxTokens(maxTokens)
                .temperature(temperature)
                .build();

        return service.createChatCompletion(request).getChoices().get(0).getMessage().getContent();
    }
}
