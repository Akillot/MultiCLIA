package core.logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Consumer;

import static core.command_handling_system.CommandHandler.extensionCmds;
import static core.command_handling_system.CommandHandler.systemCmds;
import static core.logic.BorderFunc.bigBorder;
import static core.logic.BorderFunc.marginBigBorder;
import static core.logic.ColorFunc.*;
import static core.logic.CommandManager.*;
import static core.logic.TextFunc.alignment;
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

    private static final String[] COLORS = {
            "red", "blue", "green", "yellow", "purple",
            "cyan", "white", "gray", "black"};

    private static final String[] RULES = {
            "sys.cmds: show all commands", "sys.setts: show a settings of the application",
            "sys.rerun: restart an app\n" + alignment(58) + "without cleaning previous context",
            "sys.time: show current time", "sys.ip: show local and external IP addresses",
            "sys.info: show info about the app", "sys.help: show description to all commands",
            "sys.exit: terminate the application", "sys.exitq: terminate the application quietly"};

    public static void logoAscii(String[] logo, int alignment) {
        int indexOfLogo = rand.nextInt(8);
        switch (indexOfLogo) {
            case 0:
                logoAscii(logo, alignment, COLORS[5], COLORS[4], COLORS[6], COLORS[0], COLORS[1], COLORS[2]);
                break;
            case 1:
                logoAscii(logo, alignment, COLORS[6], COLORS[4], COLORS[6], COLORS[6], COLORS[4], COLORS[4]);
                break;
            case 2:
                logoAscii(logo, alignment, COLORS[6], COLORS[5], COLORS[4], COLORS[3], COLORS[1], COLORS[6]);
                break;
            case 3:
                logoAscii(logo, alignment, COLORS[6], COLORS[0], COLORS[3], COLORS[1], COLORS[6], COLORS[0]);
                break;
            case 4:
                logoAscii(logo, alignment, COLORS[1], COLORS[5], COLORS[4], COLORS[0], COLORS[6], COLORS[6]);
                break;
            case 5:
                logoAscii(logo, alignment, COLORS[6], COLORS[4], COLORS[0], COLORS[4], COLORS[6], COLORS[6]);
                break;
            case 6:
                logoAscii(logo, alignment, COLORS[3], COLORS[4], COLORS[5], COLORS[4], COLORS[6], COLORS[0]);
                break;
            case 7:
                logoAscii(logo, alignment, COLORS[4], COLORS[6], COLORS[0], COLORS[5], COLORS[2], COLORS[3]);
                break;
            default:
                logoAscii(logo, alignment, COLORS[4], COLORS[4], COLORS[4], COLORS[4], COLORS[4], COLORS[4]);
                break;
        }
    }

    public static void logoAscii(String[] logo, int alignment ,String color1, String color2,
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

    public static void alert(String modification ,String text, int alignment) {
        out.println(alignment(alignment) + WHITE + BOLD + "["  + modification + "] " + RESET + text);
    }

    public static void commandList(){
        messageModifier('n', 2);
        alert("i", "select a command type", 58);
        message("Enter '+' to open and '-' to skip", "white", 58,0, System.out::print);
        messageModifier('n',1);
        choice("System", commandList(systemCmds));
        choice("Extensions",commandList(extensionCmds));
        choice("All", DisplayManager::allCommandList);
        marginBigBorder();
    }

    private static Runnable commandList(String[] commands) {
        return () -> {
            for (String command : commands) {
                message("· " + command, "white", 58,0, out::print);
            }
        };
    }

    private static void allCommandList() {
        messageModifier('n', 1);
        message("System Commands               Extensions", "blue", 58,0, System.out::print);

        int maxRows = Math.max(systemCmds.length, extensionCmds.length);

        for (int i = 0; i < maxRows; i++) {
            String systemCmd = i < systemCmds.length ? "· " + systemCmds[i] : "";
            String extensionCmd = i < extensionCmds.length ? "· " + extensionCmds[i] : "";

            out.printf(alignment(58) + "%-20s          %-20s%n", systemCmd, extensionCmd);
        }
    }

    public static void message(String text, String colorName, int alignment, int delay, Consumer<String> printMethod) {
        ColorFunc.Color color;

        try {
            color = ColorFunc.Color.valueOf(colorName.toUpperCase());
        } catch (IllegalArgumentException e) {
            if (colorName.equalsIgnoreCase("randomly")) {
                color = getRandomColor();
            } else {
                errorAscii();
                return;
            }
        }

        String coloredText = getColoredText(text, color);
        String alignedText = alignment(alignment) + coloredText;

        StringBuilder output = new StringBuilder();
        for (char ch : alignedText.toCharArray()) {
            output.append(ch);
            if (delay > 0) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                    errorAscii();
                    return;
                }
            }
        }
        printMethod.accept(output.toString());
        messageModifier('n', 1);
    }


    public static void messageModifier(char modifier, int amount) {
        if(amount <= 0){
            errorAscii();
        }
        String output = switch(modifier){
            case 'n' -> "\n";
            case 't' -> "\t";
            case 'b' -> "\b";
            case 'r' -> "\r";
            case '\\' -> "\\";
            default -> "\\" + modifier;
        };

        for (int i = 0; i < amount; i++) {
            out.print(output);
        }
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

    public static Runnable textModification() {
        return () -> {
            messageModifier('n', 1);
            message("All colors and text modifiers", "white", 58,0, out::print);
            messageModifier('n', 1);
            for (String color : COLORS) {
                message(color, color, 58,0, out::print);
            }
            messageModifier('n', 1);
            message("Bold", "white", 58,0, out::print);
            message(ITALIC + "Italic", "white", 58,0, out::print);
            message(UNDERLINE + "Underline", "white", 58,0, out::print);
        };
    }

    public static void time(){
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy " + "HH:mm");
        String formattedTime = localTime.format(myFormatter);
        message("Current time: " + formattedTime, "white", 58,0, out::print);
    }

    public static void userIp(){
        messageModifier('n', 2);
        getUserLocalIp();
        getUserExternalIp();
        messageModifier('n', 1);
        marginBigBorder();
    }

    public static void usingMemory(){
        message("Memory used: " +
                ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
                        / (1000 * 1000) + "M"), "white", 58,0, out::print);
    }

    public static Runnable appDescription() {
        return () -> {
            messageModifier('n', 1);
            marginBigBorder();
            messageModifier('n', 1);
            message("MultiCLIA [" + ITALIC + "Multi Command Line Interface App" + RESET + "]\n\n" +
                            alignment(58) + "is an open-source application designed for \n" +
                            alignment(58) + "streamlined command-line interaction.\n\n" +
                            alignment(58) + "It provides a flexible, modular interface where\n" +
                            alignment(58) + "functionality is built on extensible components.\n\n" +
                            alignment(58) + "Users will soon be able to add new extensions\n" +
                            alignment(58) + "with any functionality, allowing limitless customization\n" +
                            alignment(58) + "and adaptation to specific workflows.",
                    "white", 58, 0, out::print);
            messageModifier('n', 2);
            bigBorder();
        };
    }

    public static void commandsDescription(){
        messageModifier('n', 2);
        for(String rule : RULES){
            message(rule,"white",58,0,out::print);
            messageModifier('n', 1);
        }
        marginBigBorder();
    }
}