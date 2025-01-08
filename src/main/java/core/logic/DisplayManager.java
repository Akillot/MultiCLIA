package core.logic;

import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

import static core.command_handling_system.CommandHandler.*;
import static core.logic.ApiConfigs.httpRequest;
import static core.logic.AppearanceConfigs.*;
import static core.logic.CommandManager.*;
import static core.logic.TextConfigs.*;

import static core.pages.InfoPage.getVersion;
import static java.lang.System.*;

public class DisplayManager {
    public static Scanner scanner = new Scanner(in);

    //displaying description /h command
    private static final String[] rules = {
            formatCommandWithDescription("cmds", "/c", "Shows all commands"),
            formatCommandWithDescription("setts", "/s", "Shows settings of the application"),
            formatCommandWithDescription("rerun", "/rr", "Restarts the app without clearing context"),
            formatCommandWithDescription("ip", "/ip", "Shows local and external IP addresses"),
            formatCommandWithDescription("info", "/i", "Shows app information"),
            formatCommandWithDescription("help", "/h", "Shows description of all commands"),
            formatCommandWithDescription("version", "/v", "Shows version"),
            formatCommandWithDescription("clear", "/cl", "Clears recent values from terminal"),
            formatCommandWithDescription("time", "/t", "Shows time section"),
            formatCommandWithDescription("exit", "/e", "Terminates the application"),
            formatCommandWithDescription("exitq", "/eq", "Terminates the application quickly")
    };

    private static @NotNull String formatCommandWithDescription(String commandName, String shortCommand, String description) {
        return formatCommand(commandName + getAnsi256Color(systemLayoutColor) + ": ", "", "") + description + " "
                + formatCommand(shortCommand, "[", "]");
    }

    private static @NotNull String formatCommand(String command, String bracketStart, String bracketEnd) {
        return bracketStart + getAnsi256Color(systemMainColor) + command + getAnsi256Color(systemLayoutColor) + bracketEnd;
    }

    public static void displayCommandsDescription() {
        marginBorder(1, 2);
        for (String rule : rules) {
            message(rule, systemLayoutColor, 58, 0, out::print);
            modifyMessage('n', 1);
        }
        marginBorder(1, 1);
    }

    // displaying command list /c command
    public static void displayCommandList() {
        try {
            marginBorder(1,1);
            displayAllCommandList();

        } catch (Exception e) {
            marginBorder(1,1);
            message("Unknown error occurred", systemRejectionColor, 58, 0, out::print);
        }
    }

    private static void displayAllCommandList() {
        modifyMessage('n', 1);
        out.println(alignment(38) + getAnsi256Color(systemMainColor) + "System Commands"
                + alignment(-68) + getAnsi256Color(systemMainColor) + "Extensions");

        int maxRows = Math.max(fullSystemCmds.length, extensionCmds.length);

        for (int i = 0; i < maxRows; i++) {
            String systemCmd = i < fullSystemCmds.length ? "· " + fullSystemCmds[i] + " ["
                    + getAnsi256Color(systemMainColor) + shortSystemCmds[i] + getAnsi256Color(systemLayoutColor) + "]" : "";
            String extensionCmd = i < extensionCmds.length ? "· " + extensionCmds[i] : "";

            out.printf(alignment(38) + getAnsi256Color(systemLayoutColor) + "%-40s"
                    + alignment(-18) + getAnsi256Color(systemLayoutColor) + horizontalMargining(21) + "%-40s%n", systemCmd, extensionCmd);
        }
        marginBorder(2,1);
    }

    //displaying external and local IP /ip
    public static void displayUserIp() {
        marginBorder(1,2);
        getUserLocalIp();
        httpRequest("https://api.ipify.org?format=json", "GET", "Your external IP:", "ip");
        marginBorder(2,1);
    }

    //displaying current version of the app /v
    public static void displayCurrentVersion() {
        modifyMessage('n', 2);
        message("Version: " + getVersion(), systemLayoutColor,58,0,out::print);
        marginBorder(2,1);
    }
}