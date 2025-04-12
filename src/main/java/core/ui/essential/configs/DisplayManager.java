package core.ui.essential.configs;

import io.github.cdimascio.dotenv.Dotenv;

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
            message("API Key is unavailable " + getColor(layoutColor) + "[" + apiKeyName + "].", 220, getDefaultLogoAlignment(),
                    getDefaultDelay(), out::print);
        }
        else{
            message("API Key is available " + getColor(layoutColor) + "[" + apiKeyName + "].", acceptanceColor, getDefaultLogoAlignment(),
                    getDefaultDelay(), out::print);
        }
    }

    // displaying help command
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

        for (int i = 0; i < fullCmds.length; i++) {
            message("·  " + fullCmds[i] + " ["
                    + getColor(mainColor) + shortCmds[i] + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        }
        marginBorder(2,1);
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