package ui.layout;

import java.util.Random;
import java.util.Scanner;

import static commands_language_packages.CommandHandler.*;
import static ui.layout.BorderFunc.displayMarginBigBorder;
import static ui.layout.ColorFunc.*;
import static ui.layout.TextFunc.alignment;

public class DisplayManager {
    public static Scanner scanner = new Scanner(System.in);
    private static Random rand = new Random();

    private static String[] logoLines = {
            "ooo        ooooo             oooo      .    o8o  ",
            "`88.       .888'             `888    .o8    `\"'  ",
            " 888b     d'888  oooo  oooo   888  .o888oo oooo  ",
            " 8 Y88. .P  888  `888  `888   888    888   `888  ",
            " 8  `888'   888   888   888   888    888    888  ",
            " 8    Y     888   888   888   888    888 .  888  ",
            "o8o        o888o  `V88V\"V8P' o888o   \"888\" o888o ",
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

    private static String[] langs = new String[]{
            "· English", "· Čeština", "· Deutsch",
            "· Русский", "· Українська", "· Polski",
            "· Français", "· Español", "· Toki Pona"};

    private static String[][] commandPacks = {
            calculatorCommands, basicFunctionsCommands, timeCommands,
            browserCommands, infoCommands, notepadCommands, langsCommands,
            exitCommands};

    private static String[] commands = {
            "calculator", "browser", "notepad",
            "commands", "info", "langs", BOLD + RED + "exit" + RESET};

    private static final String[] COLORS = {
            "red", "blue", "green",
            "yellow", "purple", "cyan",
            "magenta", "white"};

    public static void tip(String text, int alignment) {
        System.out.println(alignment(alignment) + BOLD
                + "[" + WHITE + BOLD + "i" + RESET
                + BOLD + "] " + text + RESET);
    }

    public static void langs() {
        System.out.print("\n\n");
        for(String lang : langs){
            message(lang, "white", 58);
        }
        displayMarginBigBorder();
    }

    public static void langCommands() {
        System.out.print("\n\n");
        for (int packIndex = 0; packIndex < commandPacks.length; packIndex++) {
            String[] commandPack = commandPacks[packIndex];
            for (int i = 0; i < commandPack.length; i++) {
                if (i == 0) {
                    message(commandPack[i], "purple", 58);
                } else {
                    message(commandPack[i], "white", 58);
                }
            }

            if (packIndex < commandPacks.length - 1) {
                System.out.print("\n");
                message("--------------------", "white", 58);
                System.out.print("\n");
            }
        }
        displayMarginBigBorder();
    }

    public static void menuCommands() {
        System.out.print("\n\n");
        String[] commands = {
                "calculator", "browser", "notepad",
                "commands", "info", "langs", BOLD + RED + "exit" + RESET};
        for (String command : commands) {
            message("· " + command, "white", 58);
        }
        displayMarginBigBorder();
    }

    public static void message(String text, String colorName, int alignment) {
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
        System.out.println(alignment(alignment) + coloredText);
    }

    public static void exitMessage(String exitText) {
        for (char ch : exitText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                errorAscii();
            }
        }
    }

    public static void logo() {
        int indexOfLogo = rand.nextInt(11);

        switch (indexOfLogo) {
            case 0:
                logo(COLORS[1], COLORS[4], COLORS[7], COLORS[4], COLORS[4], COLORS[7]);
                break;
            case 1:
                logo(COLORS[7], COLORS[4], COLORS[7], COLORS[7], COLORS[4], COLORS[4]);
                break;
            case 2:
                logo(COLORS[4], COLORS[7], COLORS[4], COLORS[1], COLORS[7], COLORS[7]);
                break;
            case 3:
                logo(COLORS[1], COLORS[7], COLORS[7], COLORS[1], COLORS[1], COLORS[5]);
                break;
            case 4:
                logo(COLORS[7], COLORS[0], COLORS[3], COLORS[1], COLORS[7], COLORS[0]);
                break;
            case 5:
                logo(COLORS[1], COLORS[5], COLORS[5], COLORS[1], COLORS[1], COLORS[5]);
                break;
            case 6:
                logo(COLORS[7], COLORS[4], COLORS[0], COLORS[4], COLORS[7], COLORS[7]);
                break;
            case 7:
                logo(COLORS[5], COLORS[4], COLORS[7], COLORS[4], COLORS[4], COLORS[5]);
                break;
            case 8:
                logo(COLORS[1], COLORS[4], COLORS[7], COLORS[1], COLORS[1], COLORS[4]);
                break;
            case 9:
                logo(COLORS[4], COLORS[1], COLORS[4], COLORS[1], COLORS[7], COLORS[4]);
                break;
            case 10:
                logo(COLORS[4], COLORS[7], COLORS[7], COLORS[4], COLORS[7], COLORS[4]);
                break;
            default:
                logo(COLORS[4], COLORS[4], COLORS[4], COLORS[4], COLORS[4], COLORS[4]);
                break;
        }
    }

    public static void logo(String color1, String color2, String color3,
                            String color4, String color5, String color6) {
        String[] colors = {color1, color2, color3, color4, color5, color6};
        for (int i = 0; i < logoLines.length; i++) {
            message(logoLines[i], colors[i % colors.length], 48);
        }
    }

    public static void errorAscii() {
        for (String line : errorAscii) {
            message(line, "red", 40);
        }
    }
}
