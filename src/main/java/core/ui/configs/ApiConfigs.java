package core.ui.configs;

import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.AppearanceConfigs.getDefaultDelay;
import static core.ui.configs.AppearanceConfigs.getDefaultTextAlignment;
import static core.ui.configs.TextConfigs.alignment;
import static core.ui.configs.TextConfigs.message;
import static java.lang.System.out;

public class ApiConfigs {
    public static void apiKeyChecking(@NotNull ArrayList<String> apiKeyNames) {

        Dotenv dotenv = Dotenv.load();
        boolean allKeysValid = true;

        for (String apiKeyName : apiKeyNames) {
            String API_KEY = dotenv.get(apiKeyName);
            if (API_KEY == null || API_KEY.isEmpty()) allKeysValid = false;
        }

        if (allKeysValid)
            out.print(alignment(getDefaultLogoAlignment())
                    + getBackColor(getRejectionColor()) + getColor(getLayoutColor())
                    + " All API keys are valid " + getColor(getAcceptanceColor()) + "✓");
        else
            out.print(alignment(getDefaultLogoAlignment())
                    + getBackColor(getRejectionColor()) + getColor(getLayoutColor())
                    + " Some API keys are missing or invalid " + getColor(getRejectionColor()) + "✗");
    }

    public static void checkApiKeys(String @NotNull [] apiKeys) {
        message("API Status: ",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        for (String apiKey : apiKeys) {
            String statusMark;
            if (apiKey != null && apiKey.length() > 10) {
                statusMark = getColor(getAcceptanceColor()) + "✓";
            } else {
                statusMark = getColor(getRejectionColor()) + "✗";
            }

            message("[" + statusMark + getColor(getLayoutColor()) + "] " + apiKey,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);
        }
    }
}
