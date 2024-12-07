package core.logic;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ColorConfigs {

    public static int systemDefaultColor = 99; //Purple
    public static int systemDefaultRed = 196; //Red
    public static int systemDefaultWhite = 15; //White
    public static int systemDefaultGreen = 46; //Green

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

    @Contract(pure = true)
    static @NotNull String getColoredText(String text, int color) {
        if (color < 0 || color > 255) {
            throw new IllegalArgumentException("Color code must be between 0 and 255");
        }
        return getAnsi256Color(color) + BOLD + text + RESET;
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
}