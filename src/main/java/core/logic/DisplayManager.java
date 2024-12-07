package core.logic;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.Scanner;

import static core.command_handling_system.CommandHandler.extensionCmds;
import static core.command_handling_system.CommandHandler.systemCmds;
import static core.logic.ApiConfigs.httpRequest;
import static core.logic.BorderConfigs.*;
import static core.logic.ColorConfigs.*;
import static core.logic.CommandManager.*;
import static core.logic.TextConfigs.*;

import static java.lang.System.*;

public class DisplayManager {
    public static Scanner scanner = new Scanner(in);
    public static Random rand = new Random();

    private static String[] errorAscii = {
            "  .oooooo.                                 ",
            " d8P'  `Y8b                                ",
            "888      888  .ooooo.  oo.ooooo.   .oooo.o ",
            "888      888 d88' `88b  888' `88b d88(  \"8 ",
            "888      888 888   888  888   888 `\"Y88b.  ",
            "888      888 888   888  888   888 o.  )88b ",
            "`88b    d88' 888   888  888   888 o.  )88b ",
            " `Y8bood8P'  `Y8bod8P'  888bod8P' 8\"\"888P' ",
            "                        888                ",
            "                       o888o               \n"
    };

    private static final String[] rules = {
            "sys.cmds: show all commands", "sys.setts: show a settings of the application",
            "sys.rerun: restart an app\n" + alignment(58) + "without cleaning previous context",
            "sys.time: show current time", "sys.ip: show local and external IP addresses",
            "sys.info: show info about the app", "sys.help: show description to all commands",
            "sys.exit: terminate the application", "sys.exitq: terminate the application quickly"};

    public static void errorAscii() {
        for (String line : errorAscii) {
            message(line,systemDefaultRed,40,0,out::print);
        }
    }

    public static void displayCommandList() {
        try {
            modifyMessage('n', 2);
            alert("i", "show all lists together", 58);

            out.print(alignment(58) + getAnsi256Color(systemDefaultWhite) + BOLD + "Enter '" + RESET
                    + getAnsi256Color(systemDefaultColor)+ BOLD + "+" + RESET
                    + getAnsi256Color(systemDefaultWhite) + BOLD + "' to open and '"
                    + RESET + getAnsi256Color(systemDefaultColor) + BOLD + "-" + RESET + getAnsi256Color(systemDefaultWhite)
                    + BOLD + "' to skip" + RESET);
            modifyMessage('n', 1);

            out.print(alignment(58) + getAnsi256Color(systemDefaultWhite) + BOLD + "Choice: " + RESET);
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "+":
                    displayAllCommandList();
                    modifyMessage('n', 1);
                    break;
                case "-":
                    modifyMessage('n', 1);
                    choice("System", displayCommandList(systemCmds));
                    choice("Extensions", displayCommandList(extensionCmds));
                    marginBorder();
                    break;
                case "exit":
                    marginBorder();
                    return;
                default:
                    message("Invalid input",systemDefaultRed,58,0,out::print);
                    modifyMessage('n',1);
                    marginBorder();
                    break;
            }
        } catch (Exception e) {
            marginBorder();
            errorAscii();
            message("Unknown error occurred",systemDefaultRed,58,0,out::print);
        }
    }

    @Contract(pure = true)
    private static @NotNull Runnable displayCommandList(String[] commands) {
        return () -> {
            for (String command : commands) {
                message("· " + command,systemDefaultWhite,58,0,out::print);
            }
        };
    }

    private static void displayAllCommandList() {
        modifyMessage('n', 1);
        message("System Commands" + alignment(0) + "Extensions",systemDefaultColor,58,0,out::print);

        int maxRows = Math.max(systemCmds.length, extensionCmds.length);

        for (int i = 0; i < maxRows; i++) {
            String systemCmd = i < systemCmds.length ? "· " + systemCmds[i] : "";
            String extensionCmd = i < extensionCmds.length ? "· " + extensionCmds[i] : "";

            out.printf(alignment(58) + getAnsi256Color(systemDefaultWhite) + BOLD + "%-20s"
                    + alignment(10) + getAnsi256Color(systemDefaultWhite) + BOLD + "%-20s%n", systemCmd, extensionCmd);
        }
        modifyMessage('n', 2);
        border();
    }

    public static void loadingAnimation(int frames, int duration) {
        String[] spinner = {"    |", "    /", "    —", "    \\"};
        for (int i = 0; i < duration; i++) {
            out.print(getAnsi256Color(systemDefaultWhite) + BOLD + "\r" + spinner[i % spinner.length] + RESET);
            try {
                Thread.sleep(frames);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        out.print(getAnsi256Color(systemDefaultWhite) + BOLD + "\r    ✓" + RESET);
    }

    public static void displayUserIp() {
        modifyMessage('n', 2);
        getUserLocalIp();
        httpRequest("https://api.ipify.org?format=json", "GET", "Your external IP:", "ip");
        modifyMessage('n', 1);
        marginBorder();
    }

    public static void displayCommandsDescription(){
        modifyMessage('n', 2);
        for(String rule : rules){
            message(rule,systemDefaultWhite,58,0,out::print);
            modifyMessage('n', 1);
        }
        marginBorder();
    }
}