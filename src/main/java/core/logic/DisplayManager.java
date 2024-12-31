package core.logic;

import org.jetbrains.annotations.Contract;
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

    private static final String[] errorAscii = {
            "  .oooooo.                                           ",
            " d8P'  `Y8b                                          ",
            "888      888   .ooooo.   .ooooo.  oo.ooooo.   .oooo.o ",
            "888      888  d88' `88b d88' `88b  888' `88b d88(  \"8 ",
            "888      888  888   888 888   888  888   888 `\"Y88b.  ",
            "`88b    d88'  888   888 888   888  888   888 o.  )88b ",
            " `Y8bood8P'   `Y8bod8P' `Y8bod8P'  888bod8P' 8\"\"888P' ",
            "                                   888                 ",
            "                                  o888o                ",
            " "
    };

    public static void errorAscii() {
        for (String line : errorAscii) {
            message(line, systemRejectionColor, -1, 0, out::print);
        }
    }

    private static final String[] rules = {
            "cmds: Shows all commands [/c]",
            "setts: Shows settings of the application [/s]",
            "rerun: Restarts the app without clearing context [/rr]",
            "ip: Shows local and external IP addresses [/ip]",
            "info: Shows app information [/i]",
            "help: Shows description of all commands [/h]",
            "exit: Terminates the application [/e]",
            "exitq: Terminates the application quickly [/eq]",
            "version: Shows version [/v]",
            "clear: Clears recent values from terminal [/cl]"
    };

    public static void displayCommandList() {
        try {
            modifyMessage('n', 2);
            alert("i", "Show all lists together", 58);

            printOpenOrSkipPrompt();
            String choice = getUserChoice();

            handleUserChoice(choice);

        } catch (Exception e) {
            marginBorder();
            errorAscii();
            message("Unknown error occurred", systemRejectionColor, 58, 0, out::print);
        }
    }

    private static void printOpenOrSkipPrompt() {
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Enter '" + RESET
                + getAnsi256Color(systemFirstColor) + "+" + RESET
                + getAnsi256Color(systemLayoutColor) + "' to open and '"
                + RESET + getAnsi256Color(systemFirstColor) + "-" + RESET + getAnsi256Color(systemLayoutColor)
                + "' to skip" + RESET);
        modifyMessage('n', 1);
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Choice: " + RESET);
    }

    private static @NotNull String getUserChoice() {
        return scanner.nextLine().trim();
    }

    private static void handleUserChoice(@NotNull String choice) {
        switch (choice) {
            case "+":
                displayAllCommandList();
                break;
            case "-":
                displaySubCommandLists();
                break;
            case "exit":
                marginBorder();
                break;
            default:
                message("Invalid input", systemRejectionColor, 58, 0, out::print);
                marginBorder();
                break;
        }
    }

    private static void displaySubCommandLists() {
        modifyMessage('n', 1);
        choice("System", displayCommandList(fullSystemCmds, shortSystemCmds),
                systemFirstColor, systemLayoutColor, systemFirstColor);
        choice("Extensions", displayCommandList(extensionCmds),
                systemFirstColor, systemLayoutColor, systemFirstColor);
        marginBorder();
    }

    @Contract(pure = true)
    private static @NotNull Runnable displayCommandList(String[] commands) {
        return () -> {
            for (String command : commands) {
                message("· " + command, systemLayoutColor, 58, 0, out::print);
            }
        };
    }

    @Contract(pure = true)
    private static @NotNull Runnable displayCommandList(String[] fullCommands, String[] shortCommands) {
        return () -> {
            for (int i = 0; i < fullCommands.length; i++) {
                String shortCmd = i < shortCommands.length ? shortCommands[i] : "";
                message("· " + fullCommands[i] + " [" + shortCmd + "]", systemLayoutColor, 58, 0, out::print);
            }
        };
    }

    private static void displayAllCommandList() {
        modifyMessage('n', 1);
        out.println(alignment(38) + getAnsi256Color(systemFirstColor) + "System Commands"
                + alignment(-68) + getAnsi256Color(systemFirstColor) + "Extensions");

        int maxRows = Math.max(fullSystemCmds.length, extensionCmds.length);

        for (int i = 0; i < maxRows; i++) {
            String systemCmd = i < fullSystemCmds.length ? "· " + fullSystemCmds[i] + " [" + shortSystemCmds[i] + "]" : "";
            String extensionCmd = i < extensionCmds.length ? "· " + extensionCmds[i] : "";

            out.printf(alignment(38) + getAnsi256Color(systemLayoutColor) + "%-40s"
                    + alignment(-18) + getAnsi256Color(systemLayoutColor) + "%-40s%n", systemCmd, extensionCmd);
        }
        modifyMessage('n', 1);
        marginBorder();
    }

    public static void displayUserIp() {
        modifyMessage('n', 2);
        getUserLocalIp();
        httpRequest("https://api.ipify.org?format=json", "GET", "Your external IP:", "ip");
        modifyMessage('n', 1);
        marginBorder();
    }

    public static void displayCommandsDescription() {
        modifyMessage('n', 2);
        for (String rule : rules) {
            message(rule, systemLayoutColor, 58, 0, out::print);
            modifyMessage('n', 1);
        }
        marginBorder();
    }

    public static void displayCurrentVersion() {
        modifyMessage('n', 2);
        message("Version: " + getVersion(), systemLayoutColor,58,0,out::print);
        modifyMessage('n', 1);
        marginBorder();
    }
}