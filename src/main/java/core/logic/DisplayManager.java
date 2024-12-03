package core.logic;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import static java.lang.System.out;

public class DisplayManager {
    public static Scanner scanner = new Scanner(in);
    private static Random rand = new Random();

    public static String[] mainLogoAscii = {
            "ooo        ooooo             oooo      .    o8o  ",
            "`88.       .888'             `888    .o8    `\"'  ",
            " 888b     d'888  oooo  oooo   888  .o888oo oooo  ",
            " 8 Y88. .P  888  `888  `888   888    888   `888  ",
            " 8  `888'   888   888   888   888    888    888  ",
            " 8    Y     888   888   888   888    888 .  888  ",
            "o8o        o888o  `V88V\"V8P' o888o   \"888\" o888o ",
            "                                                    ",
            "    .oooooo.   ooooo        ooooo       .o.       ",
            "   d8P'  `Y8b  `888'        `888'      .888.      ",
            "  888           888          888      .8\"888.     ",
            "  888           888          888     .8' `888.    ",
            "  888           888          888    .88ooo8888.   ",
            "  `88b    ooo   888       o  888   .8'     `888.  ",
            "   `Y8bood8P'  o888ooooood8 o888o o88o     o8888o "
    };

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

    private static final String[] colors = {
            "red", "blue", "green", "yellow", "purple",
            "cyan", "white", "gray", "black"};

    private static final String[] rules = {
            "sys.cmds: show all commands", "sys.setts: show a settings of the application",
            "sys.rerun: restart an app\n" + alignment(58) + "without cleaning previous context",
            "sys.time: show current time", "sys.ip: show local and external IP addresses",
            "sys.info: show info about the app", "sys.help: show description to all commands",
            "sys.exit: terminate the application", "sys.exitq: terminate the application quickly"};

    public static void switchLogoAscii(String[] logo, int alignment) {
        int indexOfLogo = rand.nextInt(2);
        switch (indexOfLogo) {
            case 0:
                switchLogoAscii(logo, alignment, colors[5], colors[4], colors[6], colors[0], colors[1], colors[2]);
                break;
            case 1:
                switchLogoAscii(logo, alignment, colors[3], colors[4], colors[5], colors[4], colors[6], colors[0]);
                break;
            default:
                switchLogoAscii(logo, alignment, colors[4], colors[4], colors[4], colors[4], colors[4], colors[4]);
                break;
        }
    }

    public static void switchLogoAscii(String @NotNull [] logo, int alignment , String color1, String color2,
                                       String color3, String color4, String color5, String color6) {
        String[] colors = {color1, color2, color3, color4, color5, color6};
        for (int i = 0; i < logo.length; i++) {
            message(logo[i], colors[i % colors.length], alignment,0, out::print);
        }
    }

    public static void errorAscii() {
        for (String line : errorAscii) {
            message(line, "red", 40, 0, out::print);
        }
    }

    /*Show a message with [x], where x is a special character.
    Can be used as tip([i]) or a clarification([?]) or another alert message*/
    public static void alert(String modification ,String text, int alignment) {
        out.println(alignment(alignment) + WHITE + BOLD + "["  + modification + "] " + RESET + BOLD + text);
    }

    public static void displayCommandList() {
        try {
            messageModifier('n', 2);
            alert("i", "show all lists together", 58);
            out.print(alignment(58) + WHITE + BOLD + "Enter '" + RESET + BLUE + BOLD + "+" + RESET +  WHITE + BOLD);
            message("Enter '+' to open and '-' to skip", "white", 58, 0, out::print);
            messageModifier('n', 1);

            out.print(alignment(58) + WHITE + BOLD + "Choice: " + RESET);
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "+":
                    displayAllCommandList();
                    messageModifier('n', 1);
                    break;
                case "-":
                    messageModifier('n', 1);
                    choice("System", displayCommandList(systemCmds));
                    choice("Extensions", displayCommandList(extensionCmds));
                    marginBorder();
                    break;
                case "exit":
                    marginBorder();
                    return;
                default:
                    message("Invalid input", "red", 58, 0, out::print);
                    messageModifier('n', 1);
                    marginBorder();
                    break;
            }
        } catch (Exception e) {
            marginBorder();
            errorAscii();
            message("Unknown error occurred", "red", 58, 0, out::print);
        }
    }

    @Contract(pure = true)
    private static @NotNull Runnable displayCommandList(String[] commands) {
        return () -> {
            for (String command : commands) {
                message("· " + command, "white", 58,0, out::print);
            }
        };
    }

    private static void displayAllCommandList() {
        messageModifier('n', 1);
        message("System Commands" + alignment(0) + "Extensions", "blue", 58,0, out::print);

        int maxRows = Math.max(systemCmds.length, extensionCmds.length);

        for (int i = 0; i < maxRows; i++) {
            String systemCmd = i < systemCmds.length ? "· " + systemCmds[i] : "";
            String extensionCmd = i < extensionCmds.length ? "· " + extensionCmds[i] : "";

            out.printf(alignment(58) + BOLD + "%-20s" + alignment(10) + BOLD + "%-20s%n", systemCmd, extensionCmd);
        }
        messageModifier('n', 2);
        border();
    }

    public static void loadingAnimation(int frames, int duration) {
        String[] spinner = {"    |", "    /", "    —", "    \\"};
        for (int i = 0; i < duration; i++) {
            out.print(WHITE + BOLD + "\r" + spinner[i % spinner.length] + RESET);
            try {
                Thread.sleep(frames);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        out.print(WHITE + BOLD + "\r    ✓" + RESET);
    }

    @Contract(pure = true)
    public static @NotNull Runnable textModification() {
        return () -> {
            messageModifier('n', 1);
            message("All colors and text modifiers", "white", 58,0, out::print);
            messageModifier('n', 1);
            for (String color : colors) {
                message(color, color, 58,0, out::print);
            }
            messageModifier('n', 1);
            message("Bold", "white", 58,0, out::print);
            message(ITALIC + "Italic", "white", 58,0, out::print);
            message(UNDERLINE + "Underline", "white", 58,0, out::print);
        };
    }

    public static void displayTime(){
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy " + "HH:mm");
        String formattedTime = localTime.format(myFormatter);
        message("Current time: " + formattedTime, "white", 58,0, out::print);
    }

    public static void displayUserIp() {
        messageModifier('n', 2);
        getUserLocalIp();

        String externalIp = httpRequest("https://api.ipify.org?format=json", "GET", "Your external IP:");
        if (externalIp != null) {
            try {
                JSONObject jsonResponse = new JSONObject(externalIp);
                String ip = jsonResponse.getString("ip");

                out.println(alignment(58) + WHITE + BOLD + "Your external IP: " + RESET + BLUE + ip + RESET);
            } catch (Exception e) {
                message("Error parsing external IP: " + e.getMessage(), "red", 58, 0, out::print);
            }
        }

        messageModifier('n', 1);
        marginBorder();
    }

    public static void displayUsingMemory(){
        message("Memory used: " +
                ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
                        / (1000 * 1000) + "M"), "white", 58,0, out::print);
    }

    @Contract(pure = true)
    public static @NotNull Runnable displayAppDescription() {
        return () -> {
            messageModifier('n', 1);
            marginBorder();
            messageModifier('n', 1);
            message( BLUE + BOLD + "MultiCLIA " + RESET + "[" + ITALIC + BOLD + "Multi Command Line Interface App" + RESET + "]\n\n" +
                            BOLD + alignment(58) + "is an open-source application designed for \n" +
                            alignment(58) + "streamlined command-line interaction.\n\n" +
                            alignment(58) + "It provides a flexible, modular interface where\n" +
                            alignment(58) + "functionality is built on extensible components.\n\n" +
                            alignment(58) + "Users will soon be able to add new extensions\n" +
                            alignment(58) + "with any functionality, allowing limitless customization\n" +
                            alignment(58) + "and adaptation to specific workflows.",
                    "white", 58, 0, out::print);
            messageModifier('n', 2);
            border();
        };
    }

    public static void displayCommandsDescription(){
        messageModifier('n', 2);
        for(String rule : rules){
            message(rule,"white",58,0,out::print);
            messageModifier('n', 1);
        }
        marginBorder();
    }
}