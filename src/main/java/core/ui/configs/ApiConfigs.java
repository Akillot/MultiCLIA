package core.ui.configs;

import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static core.ui.configs.AppearanceConfigs.getAcceptanceColor;
import static core.ui.configs.AppearanceConfigs.getBackColor;
import static core.ui.configs.AppearanceConfigs.getColor;
import static core.ui.configs.AppearanceConfigs.getDefaultLogoAlignment;
import static core.ui.configs.AppearanceConfigs.getLayoutColor;
import static core.ui.configs.AppearanceConfigs.getRejectionColor;
import static core.ui.configs.TextConfigs.alignment;
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
}
