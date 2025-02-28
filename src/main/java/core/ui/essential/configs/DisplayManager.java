package core.ui.essential.configs;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Scanner;

import static core.commands.CommandHandler.*;

import static core.ui.essential.configs.essential.AppearanceConfigs.*;
import static core.ui.essential.configs.essential.TextConfigs.*;
import static core.ui.extensions.ai.AiPage.coloredChatGptLogo;
import static java.lang.System.*;

public class DisplayManager {
    public static Scanner scanner = new Scanner(in);

    private static final String[] rules = generateCommandDescriptions();

    private static @NotNull String[] generateCommandDescriptions() {
        String[] commands = {
                formatCommandWithDescription(fullSysCmds[0], shortSysCmds[0], "Shows list of all commands"),
                formatCommandWithDescription(fullSysCmds[1], shortSysCmds[1], "Shows settings of the application"),
                formatCommandWithDescription(fullSysCmds[2], shortSysCmds[2], "Restarts the app without clearing context"),
                formatCommandWithDescription(fullSysCmds[3], shortSysCmds[3], "Shows description of all commands"),
                formatCommandWithDescription(fullSysCmds[4], shortSysCmds[4], "Shows app information"),
                formatCommandWithDescription(fullSysCmds[5], shortSysCmds[5], "Clears recent values from terminal"),

                formatCommandWithDescription(fullExtCmds[0], shortExtCmds[0], "Shows time section"),
                formatCommandWithDescription(fullExtCmds[1], shortExtCmds[1], "Shows a calendar page"),
                formatCommandWithDescription(fullExtCmds[2], shortExtCmds[2], "Shows network page"),
                formatCommandWithDescription(fullExtCmds[3], shortExtCmds[3], "Shows security page"),
                formatCommandWithDescription(fullExtCmds[4], shortExtCmds[4], "Shows cryptography page"),
                formatCommandWithDescription(fullExtCmds[5], shortExtCmds[5], "Shows terminal page"),
                formatCommandWithDescription(fullExtCmds[6], shortExtCmds[6], "Shows page with " + coloredChatGptLogo + getColor(sysLayoutColor)),
                formatCommandWithDescription(fullExtCmds[7], shortExtCmds[7], "Shows connection page"),

                formatCommandWithDescription(fullSysCmds[6], shortSysCmds[6], "Shows support page"),
                formatCommandWithDescription(fullSysCmds[7], shortSysCmds[7], "Terminates the application")
        };

        Arrays.sort(commands);
        return commands;
    }

    private static @NotNull String formatCommandWithDescription(String commandName, String shortCommand, String description) {
        return formatCommand(commandName + getColor(sysLayoutColor) + ": ", "", "") +
                description + " " +
                formatCommand(shortCommand, "[", "]");
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

        // Создаём копии массивов команд и сортируем их перед отображением
        String[] sortedFullSysCmds = fullSysCmds.clone();
        String[] sortedShortSysCmds = shortSysCmds.clone();
        String[] sortedFullExtCmds = fullExtCmds.clone();
        String[] sortedShortExtCmds = shortExtCmds.clone();

        Arrays.sort(sortedFullSysCmds);
        Arrays.sort(sortedShortSysCmds);
        Arrays.sort(sortedFullExtCmds);
        Arrays.sort(sortedShortExtCmds);

        int maxRows = Math.max(sortedFullSysCmds.length, sortedFullExtCmds.length);

        for (int i = 0; i < maxRows; i++) {
            String systemCmd = i < sortedFullSysCmds.length ? "·  " + sortedFullSysCmds[i] + " ["
                    + getColor(sysMainColor) + sortedShortSysCmds[i] + getColor(sysLayoutColor) + "]" : "";
            String extensionCmd = i < sortedFullExtCmds.length ? "·  " + sortedFullExtCmds[i] : "";

            out.printf(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "%-40s"
                    + alignment(-18) + getColor(sysLayoutColor) + horizontalMargining(21) + "%-40s%n", systemCmd, extensionCmd);
        }
        marginBorder(2,1);
    }

    public static @NotNull String horizontalMargining(int steps) {
        return " ".repeat(Math.max(0, steps));
    }

    public static void clearTerminal() {
        try {
            String operatingSystem = getProperty("os.name");
            if (operatingSystem.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                out.print("\033[H\033[2J");
                out.flush();
            }
        } catch (Exception e) {
            message("Error executing action",
                    sysRejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

            message("Status: " + getColor(sysRejectionColor) + "x",
                    sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
    }
}
