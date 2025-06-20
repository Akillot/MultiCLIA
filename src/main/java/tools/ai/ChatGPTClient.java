package tools.ai;

import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import core.ui.configs.TextConfigs;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class ChatGPTClient {
    private static final String API_KEY;

    @Getter
    private static String model = "gpt-3.5-turbo";
    @Getter @Setter
    private static int maxTokens = 500;
    @Getter @Setter
    private static double temperature = 1.2;

    static {
        Dotenv dotenv = Dotenv.load();
        API_KEY = dotenv.get("OPENAI_API_KEY");
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

            return TextConfigs.formatResponse(response, 95, "");
        } catch (Exception e) {
            return "Error: Failed to get response from ChatGPT.";
        }
    }
}