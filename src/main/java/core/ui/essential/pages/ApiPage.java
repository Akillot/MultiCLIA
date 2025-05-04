package core.ui.essential.pages;

import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static core.ui.essential.configs.appearance.AppearanceConfigs.*;
import static core.ui.essential.configs.appearance.TextConfigs.alignment;
import static java.lang.System.out;

public class ApiPage {

    public static ArrayList<String> apiKeyNames = new ArrayList<>();

    static {
        apiKeyNames.add("OPENAI_API_KEY");
        apiKeyNames.add("DEEPL_API_KEY");
        apiKeyNames.add("OPEN_WEATHER_API_KEY");
    }

    public static void apiKeyChecking(@NotNull ArrayList<String> apiKeyNames) {
        Dotenv dotenv = Dotenv.load();
        boolean allKeysValid = true;

        for (String apiKeyName : apiKeyNames) {
            String API_KEY = dotenv.get(apiKeyName);
            if (API_KEY == null || API_KEY.isEmpty()) {
                allKeysValid = false;
            }
        }

        if (allKeysValid) {
            out.print(alignment(getDefaultLogoAlignment()) + getColor(getLayoutColor())
                    + "All API keys are valid " + getColor(getAcceptanceColor()) + "✓");
        } else {
            out.print(alignment(getDefaultLogoAlignment()) + getColor(getLayoutColor())
                    + "Some API keys are missing or invalid " + getColor(getRejectionColor()) + "✗");
        }
    }
}

