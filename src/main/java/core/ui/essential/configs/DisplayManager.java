package core.ui.essential.configs;

import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Scanner;

import static core.commands.CommandHandler.*;
import static core.ui.essential.configs.appearance.AppearanceConfigs.*;
import static core.ui.essential.configs.appearance.TextConfigs.*;

import static java.lang.System.*;

public class DisplayManager {
    public static Scanner scanner = new Scanner(in);

    public static ArrayList<String> apiKeyNames = new ArrayList<>();

    public static void displayCommandList() {
        try {
            marginBorder(1,1);
            displayAllCommandList();

        } catch (Exception e) {
            marginBorder(1,1);
            message("Unknown error occurred", getRejectionColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
    }

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

    private static void displayAllCommandList() {
        insertControlChars('n', 1);
        out.println(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Commands: \n"
                + alignment(-68) + getColor(getMainColor()));

        for (int i = 0; i < fullCmds.length; i++) {
            message("·  " + fullCmds[i] + " ["
                    + getColor(getMainColor()) + shortCmds[i] + getColor(getLayoutColor()) + "]", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);

        }
        marginBorder(2,1);
    }

    public static void clearTerminal() {
        try {
            String operatingSystem = System.getProperty("os.name");
            if (operatingSystem.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            message("Error executing action",
                    getRejectionColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);

            message("Status: " + getColor(getRejectionColor()) + "x",
                    getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
    }
}