package core.logic;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ColorConfigs {

    public static int systemFirstColor = 99;
    public static int systemSecondColor = 0;
    public static int systemLayoutColor = 15;

    public static int systemAcceptanceColor = 46;
    public static int systemRejectionColor = 196;

    public static final String RESET = "\033[0m";
    public static final String BOLD = "\033[1m";
    public static final String ITALIC = "\u001b[3m";
    public static final String UNDERLINE = "\033[4m";
    public static final String REVERSE = "\u001B[7m";

    @Contract(pure = true)
    static @NotNull String getColoredText(String text, int color) {
        if (color < 0 || color > 255) {
            throw new IllegalArgumentException("Color code must be between 0 and 255");
        }
        return getAnsi256Color(color) + text + RESET;
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