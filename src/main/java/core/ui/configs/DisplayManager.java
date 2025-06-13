package core.ui.configs;

import org.jetbrains.annotations.NotNull;
import java.util.Scanner;

import static core.commands.CommandHandler.*;
import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.TextConfigs.*;

import static java.lang.System.*;

public class DisplayManager {
    public static Scanner scanner = new Scanner(in);

    public static void displayLogo(int alignment, String @NotNull [] logo) {
        String[] colors = getColorsForLogo();

        for (int i = 0; i < logo.length; i++) {
            String coloredText = colors[i % colors.length] + logo[i] + RESET;
            message(coloredText,
                    getLayoutColor(),
                    alignment,
                    getDefaultDelay(),
                    out::print);
        }
    }

    private static String @NotNull [] getColorsForLogo() {
        return new String[]{
                getColor(getLayoutColor()), getColor(getLayoutColor()),
                getColor(getLayoutColor()), getColor(getLayoutColor()),
                getColor(getLayoutColor()), getColor(getLayoutColor()),
        };
    }

    public static void displayCommandList() {
        try {
            marginBorder(1,2);
            out.println(alignment(getDefaultTextAlignment())
                    + getColor(getLayoutColor()) + "List of basic commands: \n"
                    + alignment(getDefaultTextAlignment()) + getColor(getMainColor()));

            for (int i = 0; i < fullCmds.length; i++) {
                message("·  "
                                + fullCmds[i] + " ["
                                + getColor(getMainColor()) + shortCmds[i]
                                + getColor(getLayoutColor()) + "]",
                        getLayoutColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::print);
            }
            marginBorder(2,1);
        } catch (Exception e) {
            marginBorder(1,1);
            message("Unknown error occurred",
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);
        }
    }

    public static void displayToolsCommandList() {
        try {
            marginBorder(1,2);
            out.println(alignment(getDefaultTextAlignment())
                    + getColor(getLayoutColor()) + "List of available tools: \n"
                    + alignment(getDefaultTextAlignment()) + getColor(getMainColor()));

            for (int i = 0; i < fullToolCmds.length; i++) {
                message("·  "
                                + fullToolCmds[i] + " ["
                                + getColor(getMainColor()) + shortToolCmds[i]
                                + getColor(getLayoutColor()) + "]",
                        getLayoutColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::print);
            }

            marginBorder(2,1);
        } catch (Exception e) {
            marginBorder(1,1);
            message("Unknown error occurred",
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);
        }
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
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);

            message("Status: " + getColor(getRejectionColor()) + "x",
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);
        }
    }
}