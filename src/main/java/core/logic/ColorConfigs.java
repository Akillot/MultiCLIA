package core.logic;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ColorConfigs {

    public static final String RESET = "\033[0m";
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String GRAY = "\033[0;37m";
    public static final String WHITE = "\033[0;38m";

    public static final String BOLD = "\033[1m";
    public static final String ITALIC = "\u001b[3m";
    public static final String UNDERLINE = "\033[4m";
    public static final String REVERSE = "\u001B[7m";

    public static final String BRIGHT_BLACK = "\u001B[30;1m";
    public static final String BRIGHT_RED = "\u001B[31;1m";
    public static final String BRIGHT_GREEN = "\u001B[32;1m";
    public static final String BRIGHT_YELLOW = "\u001B[33;1m";
    public static final String BRIGHT_BLUE = "\u001B[34;1m";
    public static final String BRIGHT_PURPLE = "\u001B[35;1m";
    public static final String BRIGHT_CYAN = "\u001B[36;1m";
    public static final String BRIGHT_WHITE = "\u001B[37;1m";

    public static final String BACKGROUND_BLACK = "\u001B[40m";
    public static final String BACKGROUND_RED = "\u001B[41m";
    public static final String BACKGROUND_GREEN = "\u001B[42m";
    public static final String BACKGROUND_YELLOW = "\u001B[43m";
    public static final String BACKGROUND_BLUE = "\u001B[44m";
    public static final String BACKGROUND_PURPLE = "\u001B[45m";
    public static final String BACKGROUND_CYAN = "\u001B[46m";
    public static final String BACKGROUND_WHITE = "\u001B[47m";

    public static final String BRIGHT_BACKGROUND_BLACK = "\u001B[40;1m";
    public static final String BRIGHT_BACKGROUND_RED = "\u001B[41;1m";
    public static final String BRIGHT_BACKGROUND_GREEN = "\u001B[42;1m";
    public static final String BRIGHT_BACKGROUND_YELLOW = "\u001B[43;1m";
    public static final String BRIGHT_BACKGROUND_BLUE = "\u001B[44;1m";
    public static final String BRIGHT_BACKGROUND_PURPLE = "\u001B[45;1m";
    public static final String BRIGHT_BACKGROUND_CYAN = "\u001B[46;1m";
    public static final String BRIGHT_BACKGROUND_WHITE = "\u001B[47;1m";

    public static Color getRandomColor() {
        Random rand = new Random();
        int randomColorIndex = rand.nextInt(Color.values().length);
        return Color.values()[randomColorIndex];
    }

    @Contract(pure = true)
    static @NotNull String getColoredText(String text, @NotNull Color color) {
        String colorCode = switch (color) {
            case RED -> RED;
            case YELLOW -> YELLOW;
            case GREEN -> GREEN;
            case BLUE -> BLUE;
            case PURPLE -> PURPLE;
            case CYAN -> CYAN;
            case WHITE -> WHITE;
            case GRAY -> GRAY;
            case BLACK -> BLACK;
            case BRIGHT_RED -> BRIGHT_RED;
            case BRIGHT_YELLOW -> BRIGHT_YELLOW;
            case BRIGHT_GREEN -> BRIGHT_GREEN;
            case BRIGHT_BLUE -> BRIGHT_BLUE;
            case BRIGHT_PURPLE -> BRIGHT_PURPLE;
            case BRIGHT_CYAN -> BRIGHT_CYAN;
            case BRIGHT_WHITE -> BRIGHT_WHITE;
            case BRIGHT_BLACK -> BRIGHT_BLACK;
            case RESET -> RESET;
        };
        return colorCode + BOLD + text + RESET;
    }

    public enum Color {
        RED, YELLOW, GREEN, BLUE, PURPLE, CYAN, WHITE, GRAY, BLACK, RESET,
        BRIGHT_RED, BRIGHT_YELLOW, BRIGHT_GREEN, BRIGHT_BLUE, BRIGHT_PURPLE,
        BRIGHT_CYAN, BRIGHT_WHITE, BRIGHT_BLACK
    }

    @Contract(pure = true)
    public static @NotNull String getAnsi256Color(int colorCode) {
        return "\033[38;5;" + colorCode + "m";
    }

    @Contract(pure = true)
    public static @NotNull String getAnsi256BackgroundColor(int colorCode) {
        return "\033[48;5;" + colorCode + "m";
    }

    public static @NotNull String getRandom256Color() {
        Random rand = new Random();
        int colorCode = rand.nextInt(256);
        return getAnsi256Color(colorCode);
    }

    public static @NotNull String getRandom256BackgroundColor() {
        Random rand = new Random();
        int colorCode = rand.nextInt(256);
        return getAnsi256BackgroundColor(colorCode);
    }

public static final String[] colors = {
            "red", "blue", "green", "yellow", "purple",
            "cyan", "white", "gray", "black", "bright_black",
            "bright_red", "bright_green", "bright_yellow", "bright_blue",
            "bright_purple", "bright_cyan", "bright_white"};
}