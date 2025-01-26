package core.ui;

import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import static core.commands.CommandHandler.*;
import static core.configs.AppearanceConfigs.*;
import static core.logic.CommandManager.httpRequest;
import static core.configs.TextConfigs.*;

import static java.lang.System.*;

public class DisplayManager {
    public static Scanner scanner = new Scanner(in);

    //displaying description /h
    private static final String[] rules = {
            formatCommandWithDescription("cmds", "/c", "Shows list of all commands"),
            formatCommandWithDescription("settings", "/s", "Shows settings of the application"),
            formatCommandWithDescription("rerun", "/rr", "Restarts the app without clearing context"),
            formatCommandWithDescription("ip address", "/ip", "Shows local and external IP addresses"),
            formatCommandWithDescription("info", "/i", "Shows app information"),
            formatCommandWithDescription("help", "/h", "Shows description of all commands"),
            formatCommandWithDescription("clear", "/cl", "Clears recent values from terminal"),
            formatCommandWithDescription("time", "/t", "Shows time section"),
            formatCommandWithDescription("network", "/n", "Shows network page"),
            formatCommandWithDescription("security", "/sc", "Shows security page"),
            formatCommandWithDescription("cryptography", "/cr", "Shows cryptography page"),
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
            message(rule, sysLayoutColor, 58, 0, out::print);
            modifyMessage('n', 1);
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
            message("Unknown error occurred", sysRejectionColor, 58, 0, out::print);
        }
    }

    private static void displayAllCommandList() {
        modifyMessage('n', 1);
        out.println(alignment(38) + getColor(sysMainColor) + "System Commands"
                + alignment(-68) + getColor(sysMainColor));

        int maxRows = Math.max(fullSystemCmds.length, extensionCmds.length);

        for (int i = 0; i < maxRows; i++) {
            String systemCmd = i < fullSystemCmds.length ? "· " + fullSystemCmds[i] + " ["
                    + getColor(sysMainColor) + shortSystemCmds[i] + getColor(sysLayoutColor) + "]" : "";
            String extensionCmd = i < extensionCmds.length ? "· " + extensionCmds[i] : "";

            out.printf(alignment(38) + getColor(sysLayoutColor) + "%-40s"
                    + alignment(-18) + getColor(sysLayoutColor) + horizontalMargining(21) + "%-40s%n", systemCmd, extensionCmd);
        }
        marginBorder(2,1);
    }

    //displaying external and local IP /ip
    public static void displayUserIp() {
        marginBorder(1,2);
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            message("Your local IP: " + getColor(sysMainColor)
                    + localHost, sysLayoutColor, 58, 0, out::print);
            httpRequest("https://api.ipify.org?format=json", "GET", "Your external IP:"
                    + getColor(sysMainColor), "ip");
        } catch (UnknownHostException e) {
            message("IP is undefined", sysRejectionColor, 58, 0, out::print);
            message("Status: " + getColor(sysRejectionColor)
                    + "x", sysLayoutColor, 58, 0, out::print);
        }
        marginBorder(2,1);
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
            message("Error executing action", sysRejectionColor, 58, 0, out::print);
            message("Status: " + getColor(sysRejectionColor) + "x", sysLayoutColor, 58, 0, out::print);
        }
    }
}