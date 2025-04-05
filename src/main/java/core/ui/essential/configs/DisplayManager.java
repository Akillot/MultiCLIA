package core.ui.essential.configs;

import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

import static core.commands.CommandHandler.*;
import static core.ui.essential.configs.appearance.AppearanceConfigs.*;
import static core.ui.essential.configs.appearance.TextConfigs.*;

import static java.lang.System.*;

public class DisplayManager {
    public static Scanner scanner = new Scanner(in);

    public static void apiKeyChecking(String apiKeyName) {
        Dotenv dotenv = Dotenv.load();
        String API_KEY = dotenv.get(apiKeyName);

        if (API_KEY == null || API_KEY.isEmpty()) {
            message(apiKeyName + " is unavailable" + getColor(layoutColor) + ". " + getColor(220) + "Check your API Key"
                            + getColor(layoutColor) + ".", 220, getDefaultLogoAlignment(), getDefaultDelay(), out::print);
        }
        else{
            message("API Key is available " + getColor(layoutColor) + "[" + apiKeyName + "].", acceptanceColor, getDefaultLogoAlignment(),
                    getDefaultDelay(), out::print);
        }
    }

    // displaying command list ls
    public static void displayCommandList() {
        try {
            marginBorder(1,1);
            displayAllCommandList();

        } catch (Exception e) {
            marginBorder(1,1);
            message("Unknown error occurred", rejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
    }

    private static void displayAllCommandList() {
        insertControlChars('n', 1);
        out.println(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Commands: \n"
                + alignment(-68) + getColor(mainColor));

        int maxRows = Math.max(fullCmds.length, extensionCmds.length);

        for (int i = 0; i < maxRows; i++) {
            String systemCmd = i < fullCmds.length ? "·  " + fullCmds[i] + " ["
                    + getColor(mainColor) + shortCmds[i] + getColor(layoutColor) + "]" : "";
            String extensionCmd = i < extensionCmds.length ? "·  " + extensionCmds[i] : "";

            out.printf(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "%-40s"
                    + alignment(-18) + getColor(layoutColor) + horizontalMargining(21) + "%-40s%n", systemCmd, extensionCmd);
        }
        marginBorder(2,1);
    }

    public static @NotNull String horizontalMargining(int steps) {
        return " ".repeat(Math.max(0, steps));
    }

    // cl
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
                    rejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

            message("Status: " + getColor(rejectionColor) + "x",
                    layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
    }
}