package core.ui;

import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

import static core.commands.CommandHandler.*;
import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.*;

import static core.pages.AiPage.coloredChatGptLogo;
import static java.lang.System.*;

public class DisplayManager {
    public static Scanner scanner = new Scanner(in);

    //displaying description /h
    private static final String[] rules = {
            formatCommandWithDescription("cmds", "/c", "Shows list of all commands"),
            formatCommandWithDescription("settings", "/s", "Shows settings of the application"),
            formatCommandWithDescription("rerun", "/rr", "Restarts the app without clearing context"),
            formatCommandWithDescription("help", "/h", "Shows description of all commands"),
            formatCommandWithDescription("info", "/i", "Shows app information"),
            formatCommandWithDescription("clear", "/cl", "Clears recent values from terminal"),
            formatCommandWithDescription("time", "/t", "Shows time section"),
            formatCommandWithDescription("calendar", "/ca", "Shows a calendar page"),
            formatCommandWithDescription("network", "/n", "Shows network page"),
            formatCommandWithDescription("security", "/sc", "Shows security page"),
            formatCommandWithDescription("cryptography", "/cr", "Shows cryptography page"),
            formatCommandWithDescription("terminal", "/ter", "Shows terminal page"),
            formatCommandWithDescription("ai-assistance", "/ai", "Shows page with " + coloredChatGptLogo + getColor(sysLayoutColor)),
            formatCommandWithDescription("support", "/su", "Shows support page"),
            formatCommandWithDescription("exit", "/e", "Terminates the application")
    };

    private static @NotNull String formatCommandWithDescription(String commandName, String shortCommand, String description) {
        return formatCommand(commandName + getColor(sysLayoutColor)
                + ": ", "", "") + description + " "
                + formatCommand(shortCommand, "[", "]");
    }

    private static @NotNull String formatCommand(String command, String bracketStart, String bracketEnd) {
        return bracketStart + getColor(sysMainColor) + command + getColor(sysLayoutColor) + bracketEnd;
    }

    public static void displayCommandsDescription() {
        marginBorder(1, 2);
        for (String rule : rules) {
            message(rule, sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
            insertControlChars('n', 1);
        }
        marginBorder(1, 1);
    }

    // displaying command list /c
    public static void displayCommandList() {
        try {
            marginBorder(1,1);
            displayAllCommandList();

        } catch (Exception e) {
            marginBorder(1,1);
            message("Unknown error occurred", sysRejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
    }

    private static void displayAllCommandList() {
        insertControlChars('n', 1);
        out.println(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "Commands: \n"
                + alignment(-68) + getColor(sysMainColor));

        int maxRows = Math.max(fullSystemCmds.length, extensionCmds.length);

        for (int i = 0; i < maxRows; i++) {
            String systemCmd = i < fullSystemCmds.length ? "·  " + fullSystemCmds[i] + " ["
                    + getColor(sysMainColor) + shortSystemCmds[i] + getColor(sysLayoutColor) + "]" : "";
            String extensionCmd = i < extensionCmds.length ? "·  " + extensionCmds[i] : "";

            out.printf(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "%-40s"
                    + alignment(-18) + getColor(sysLayoutColor) + horizontalMargining(21) + "%-40s%n", systemCmd, extensionCmd);
        }
        marginBorder(2,1);
    }

    public static @NotNull String horizontalMargining(int steps) {
        return " ".repeat(Math.max(0, steps));
    }

    // /cl
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
                    sysRejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

            message("Status: " + getColor(sysRejectionColor) + "x",
                    sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
    }
}