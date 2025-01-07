package core.logic;

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

    //displaying description /h
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
            "clear: Clears recent values from terminal [/cl]",
            "time: Shows current time and additional information about time [/t]"
    };

    public static void displayCommandsDescription() {
        modifyMessage('n', 2);
        for (String rule : rules) {
            message(rule, systemLayoutColor, 58, 0, out::print);
            modifyMessage('n', 1);
        }
        marginBorder(1,1);
    }

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
            String systemCmd = i < fullSystemCmds.length ? "· " + fullSystemCmds[i] + " [" + shortSystemCmds[i] + "]" : "";
            String extensionCmd = i < extensionCmds.length ? "· " + extensionCmds[i] : "";

            out.printf(alignment(38) + getAnsi256Color(systemLayoutColor) + "%-40s"
                    + alignment(-18) + getAnsi256Color(systemLayoutColor) + "%-40s%n", systemCmd, extensionCmd);
        }
        marginBorder(2,1);
    }

    //displaying external and local IP /ip
    public static void displayUserIp() {
        modifyMessage('n', 2);
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