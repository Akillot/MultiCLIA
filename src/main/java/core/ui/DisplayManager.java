package core.ui;

import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static core.commands.CommandHandler.*;
import static core.configs.AppearanceConfigs.*;
import static core.logic.CommandManager.httpRequest;
import static core.configs.TextConfigs.*;

import static core.pages.InfoPage.*;
import static java.lang.System.*;

public class DisplayManager {
    public static Scanner scanner = new Scanner(in);

    //displaying description /h
    private static final String[] rules = {
            formatCommandWithDescription("cmds", "/c", "Shows list of all commands"),
            formatCommandWithDescription("setts", "/s", "Shows settings of the application"),
            formatCommandWithDescription("rerun", "/rr", "Restarts the app without clearing context"),
            formatCommandWithDescription("ip", "/ip", "Shows local and external IP addresses"),
            formatCommandWithDescription("info", "/i", "Shows app information"),
            formatCommandWithDescription("help", "/h", "Shows description of all commands"),
            formatCommandWithDescription("clear", "/cl", "Clears recent values from terminal"),
            formatCommandWithDescription("time", "/t", "Shows time section"),
            formatCommandWithDescription("ports", "/p", "Scans open ports on the local machine"),
            formatCommandWithDescription("appinfo", "/ai", "Shows app information"),
            formatCommandWithDescription("cryptography", "/cr", "Shows page with password generation, and de"),
            formatCommandWithDescription("exit", "/e", "Terminates the application"),
    };

    private static @NotNull String formatCommandWithDescription(String commandName, String shortCommand, String description) {
        return formatCommand(commandName + getAnsi256Color(systemLayoutColor)
                + ": ", "", "") + description + " "
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

    // displaying command list /c
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
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            message("Your local IP: " + getAnsi256Color(systemMainColor)
                    + localHost, systemLayoutColor, 58, 0, out::print);
            httpRequest("https://api.ipify.org?format=json", "GET", "Your external IP:"
                    + getAnsi256Color(systemMainColor), "ip");
        } catch (UnknownHostException e) {
            message("IP is undefined", systemRejectionColor, 58, 0, out::print);
            message("Status: " + getAnsi256Color(systemRejectionColor)
                    + "x", systemLayoutColor, 58, 0, out::print);
        }
        marginBorder(2,1);
    }

    //clearing terminal
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
            message("Error executing action", systemRejectionColor, 58, 0, out::print);
            message("Status: " + getAnsi256Color(systemRejectionColor) + "x", systemLayoutColor, 58, 0, out::print);
        }
    }

    //displaying currently open ports /p
    public static void multiThreadedPortScanner() {
        int startPort = 1;
        int endPort = 65535;
        int threads = 100;

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        marginBorder(1,2);
        slowMotionText(0,58,false,
                getAnsi256Color(systemLayoutColor) + "Scanning ports from "
                        + startPort + " to " + endPort + " using " + threads + " threads","");
        modifyMessage('n',2);

        for (int port = startPort; port <= endPort; port++) {
            final int currentPort = port;
            executor.submit(() -> {
                try (Socket socket = new Socket("localhost", currentPort)) {
                    message("· Port " + getAnsi256Color(systemMainColor) + currentPort
                            + getAnsi256Color(systemLayoutColor) + " is open", systemLayoutColor, 58, 0, out::print);
                } catch (Exception ignored) {}
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {}
        modifyMessage('n',1);
        message("Scanning completed.", systemLayoutColor, 58, 0, out::print);
        marginBorder(2,1);
    }

    //displaying app info /ai
    public static void displayAppInfo(){
        marginBorder(1,2);
        message("Application info", systemLayoutColor, 58, 0, out::print);
        modifyMessage('n',1);
        message("Current version: " + getVersion(), systemLayoutColor,58,0,out::print);
        message("Author: Nick Zozulia", systemLayoutColor,58,0,out::print);
        modifyMessage('n', 1);

        displayOs();
        displayApplicationDirectory();

        modifyMessage('n', 1);
        displayJavaInfo();
        marginBorder(2,1);
    }
}