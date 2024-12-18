package core.logic;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

import static core.command_handling_system.CommandHandler.*;
import static core.logic.ApiConfigs.httpRequest;
import static core.logic.BorderConfigs.*;
import static core.logic.ColorConfigs.*;
import static core.logic.CommandManager.*;
import static core.logic.TextConfigs.*;

import static java.lang.System.*;

public class DisplayManager {
    public static Scanner scanner = new Scanner(in);

    public static void loadingAnimation(int frames, int duration) {
        String[] spinner = {"    |", "    /", "    —", "    \\"};
        for (int i = 0; i < duration; i++) {
            out.print(getAnsi256Color(systemDefaultWhite) + "\r" + spinner[i % spinner.length] + RESET);
            try {
                Thread.sleep(frames);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        out.print(getAnsi256Color(systemDefaultWhite) + "\r    ✓" + RESET);
    }

    private static final String[] errorAscii = {
            "  .oooooo.                                 ",
            " d8P'  `Y8b                                ",
            "888      888  .ooooo.  oo.ooooo.   .oooo.o ",
            "888      888 d88' `88b  888' `88b d88(  \"8 ",
            "888      888 888   888  888   888 `\"Y88b.  ",
            "888      888 888   888  888   888 o.  )88b ",
            "`88b    d88' 888   888  888   888 8\"\"888P' ",
            " `Y8bood8P'  `Y8bod8P'  888bod8P'          ",
            "                        888                ",
            "                       o888o               \n"
    };

    private static final String[] rules = {
            "--cmds: Show all commands",
            "--setts: Show settings of the application",
            "--rerun: Restart the app without clearing context",
            "--ip: Show local and external IP addresses",
            "--info: Show app information",
            "--help: Show description of all commands",
            "--exit: Terminate the application",
            "--exitq: Terminate the application quickly"
    };

    public static void errorAscii() {
        for (String line : errorAscii) {
            message(line, systemDefaultRed, 40, 0, out::print);
        }
    }

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
            message("Unknown error occurred", systemDefaultRed, 58, 0, out::print);
        }
    }

    private static void printOpenOrSkipPrompt() {
        out.print(alignment(58) + getAnsi256Color(systemDefaultWhite) + "Enter '" + RESET
                + getAnsi256Color(systemDefaultColor) + "+" + RESET
                + getAnsi256Color(systemDefaultWhite) + "' to open and '"
                + RESET + getAnsi256Color(systemDefaultColor) + "-" + RESET + getAnsi256Color(systemDefaultWhite)
                + "' to skip" + RESET);
        modifyMessage('n', 1);
        out.print(alignment(58) + getAnsi256Color(systemDefaultWhite) + "Choice: " + RESET);
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
                message("Invalid input", systemDefaultRed, 58, 0, out::print);
                marginBorder();
                break;
        }
    }

    private static void displaySubCommandLists() {
        modifyMessage('n', 1);
        choice("System", displayCommandList(fullSystemCmds, shortSystemCmds));
        choice("Extensions", displayCommandList(extensionCmds));
        marginBorder();
    }

    @Contract(pure = true)
    private static @NotNull Runnable displayCommandList(String[] commands) {
        return () -> {
            for (String command : commands) {
                message("· " + command, systemDefaultWhite, 58, 0, out::print);
            }
        };
    }

    @Contract(pure = true)
    private static @NotNull Runnable displayCommandList(String[] fullCommands, String[] shortCommands) {
        return () -> {
            for (int i = 0; i < fullCommands.length; i++) {
                String shortCmd = i < shortCommands.length ? shortCommands[i] : "";
                message("· " + fullCommands[i] + " (" + shortCmd + ")", systemDefaultWhite, 58, 0, out::print);
            }
        };
    }

    private static void displayAllCommandList() {
        modifyMessage('n', 1);
        message("System Commands" + alignment(0) + "Extensions", systemDefaultColor, 58, 0, out::print);

        int maxRows = Math.max(fullSystemCmds.length, extensionCmds.length);

        for (int i = 0; i < maxRows; i++) {
            String systemCmd = i < fullSystemCmds.length ? "· " + fullSystemCmds[i] + " (" + shortSystemCmds[i] + ")" : "";
            String extensionCmd = i < extensionCmds.length ? "· " + extensionCmds[i] : "";

            out.printf(alignment(58) + getAnsi256Color(systemDefaultWhite) + "%-20s"
                    + alignment(10) + getAnsi256Color(systemDefaultWhite) + "%-20s%n", systemCmd, extensionCmd);
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
            message(rule, systemDefaultWhite, 58, 0, out::print);
            modifyMessage('n', 1);
        }
        marginBorder();
    }
}