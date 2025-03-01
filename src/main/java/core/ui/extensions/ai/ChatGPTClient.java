package core.ui.extensions.ai;

import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

import static core.ui.essential.configs.TextConfigs.formatResponse;

public class ChatGPTClient {
    private static final String API_KEY;

    @Getter
    private static String model = "gpt-3.5-turbo";
    @Getter @Setter
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

    public static @NotNull String sendMessage(String message) {
        try {
            OpenAiService service = new OpenAiService(API_KEY);

            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model(model)
                    .messages(Collections.singletonList(new ChatMessage("user", message)))
                    .maxTokens(maxTokens)
                    .temperature(temperature)
                    .build();

            String response = service.createChatCompletion(request)
                    .getChoices()
                    .get(0)
                    .getMessage()
                    .getContent();

            return formatResponse(response, 95, "");
        } catch (Exception e) {
            return "Error: Failed to get response from ChatGPT.";
        }
    }
}