package core.ui.configs;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Map;
import java.util.Objects;

import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.TextConfigs.message;
import static java.lang.System.out;

public class ApiConfigs {

    public static void checkApiKeys() {
        Dotenv dotenv = Dotenv.load();

        Map<String, String> keys = Map.of(
                "OPEN_WEATHER_API_KEY", Objects.requireNonNull(dotenv.get("OPEN_WEATHER_API_KEY")),
                "DEEPL_API_KEY", Objects.requireNonNull(dotenv.get("DEEPL_API_KEY")),
                "OPENAI_API_KEY", Objects.requireNonNull(dotenv.get("OPENAI_API_KEY"))
        );

        message("API Status:", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);

        keys.forEach((name, value) -> {
            String statusMark;
            if (value != null && value.length() > 10)
                statusMark = "[" + getColor(getAcceptanceColor()) + "✓" + getColor(getLayoutColor()) + "]";
            else
                statusMark = "[" + getColor(getRejectionColor()) + "✗" + getColor(getLayoutColor()) + "]";


            message(statusMark + " " + name,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);
        });
    }
}
