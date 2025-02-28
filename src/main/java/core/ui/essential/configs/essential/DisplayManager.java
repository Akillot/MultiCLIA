package core.ui.essential.configs.essential;

import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

import static core.commands.CommandHandler.*;
import static core.ui.essential.configs.essential.AppearanceConfigs.*;
import static core.ui.essential.configs.essential.TextConfigs.*;

import static core.ui.extensions.ai.AiPage.coloredChatGptLogo;
import static java.lang.System.*;

public class DisplayManager {
    public static Scanner scanner = new Scanner(in);

    //displaying description /h
    private static final String[] rules = {
            formatCommandWithDescription(fullSystemCmds[0], shortSystemCmds[0], "Shows list of all commands"),
            formatCommandWithDescription(fullSystemCmds[1], shortSystemCmds[1], "Shows settings of the application"),
            formatCommandWithDescription(fullSystemCmds[2], shortSystemCmds[2], "Restarts the app without clearing context"),
            formatCommandWithDescription(fullSystemCmds[3], shortSystemCmds[3], "Shows description of all commands"),
            formatCommandWithDescription(fullSystemCmds[4], shortSystemCmds[4], "Shows app information"),
            formatCommandWithDescription(fullSystemCmds[5], shortSystemCmds[5], "Clears recent values from terminal"),
            formatCommandWithDescription(fullSystemCmds[6], shortSystemCmds[6], "Shows time section"),
            formatCommandWithDescription(fullSystemCmds[7], shortSystemCmds[7], "Shows a calendar page"),
            formatCommandWithDescription(fullSystemCmds[8], shortSystemCmds[8], "Shows network page"),
            formatCommandWithDescription(fullSystemCmds[9], shortSystemCmds[9], "Shows security page"),
            formatCommandWithDescription(fullSystemCmds[10], shortSystemCmds[10], "Shows cryptography page"),
            formatCommandWithDescription(fullSystemCmds[11], shortSystemCmds[11], "Shows terminal page"),
            formatCommandWithDescription(fullSystemCmds[12], shortSystemCmds[12], "Shows page with " + coloredChatGptLogo + getColor(sysLayoutColor)),
            formatCommandWithDescription(fullSystemCmds[13], shortSystemCmds[13], "Shows support page"),
            formatCommandWithDescription(fullSystemCmds[14], shortSystemCmds[14], "Terminates the application")
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