package core.ui.essential.configs;

import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

import static core.commands.CommandHandler.*;
import static core.ui.essential.configs.AppearanceConfigs.*;
import static core.ui.essential.configs.TextConfigs.*;

import static core.ui.extensions.ai.AiPage.coloredChatGptLogo;
import static java.lang.System.*;

public class DisplayManager {
    public static Scanner scanner = new Scanner(in);

    //displaying description /h
    private static final String[] rules = {
            formatCommandWithDescription(fullCmds[0], shortCmds[0], "Shows list of all commands"),
            formatCommandWithDescription(fullCmds[1], shortCmds[1], "Shows settings of the application"),
            formatCommandWithDescription(fullCmds[2], shortCmds[2], "Restarts the app without clearing context"),
            formatCommandWithDescription(fullCmds[3], shortCmds[3], "Shows description of all commands"),
            formatCommandWithDescription(fullCmds[4], shortCmds[4], "Shows app information"),
            formatCommandWithDescription(fullCmds[5], shortCmds[5], "Clears recent values from terminal"),
            formatCommandWithDescription(fullCmds[6], shortCmds[6], "Shows time section"),
            formatCommandWithDescription(fullCmds[7], shortCmds[7], "Shows a calendar page"),
            formatCommandWithDescription(fullCmds[8], shortCmds[8], "Shows network page"),
            formatCommandWithDescription(fullCmds[9], shortCmds[9], "Shows security page"),
            formatCommandWithDescription(fullCmds[10], shortCmds[10], "Shows cryptography page"),
            formatCommandWithDescription(fullCmds[11], shortCmds[11], "Shows terminal page"),
            formatCommandWithDescription(fullCmds[12], shortCmds[12], "Shows page with " + coloredChatGptLogo + getColor(sysLayoutColor)),
            formatCommandWithDescription(fullCmds[13], shortCmds[13], "Shows support page"),
            formatCommandWithDescription(fullCmds[14], shortCmds[14], "Terminates the application")
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

    // displaying command list /ls
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

        int maxRows = Math.max(fullCmds.length, extensionCmds.length);

        for (int i = 0; i < maxRows; i++) {
            String systemCmd = i < fullCmds.length ? "·  " + fullCmds[i] + " ["
                    + getColor(sysMainColor) + shortCmds[i] + getColor(sysLayoutColor) + "]" : "";
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