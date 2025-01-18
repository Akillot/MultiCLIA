package core.configs;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

import static core.configs.TextConfigs.alignment;
import static core.configs.TextConfigs.modifyMessage;
import static java.lang.System.out;

public class AppearanceConfigs {

    //Colors
    public static int systemMainColor = 247;
    public static int systemLayoutColor = 15;

    public static int systemAcceptanceColor = 46;
    public static int systemRejectionColor = 160;

    public static final String RESET = "\033[0m";
    public static final String UNDERLINE = "\033[4m";

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

    //Borders
    public static final int DEFAULT_BORDER_WIDTH = 62;
    private static int borderCharIndex = 0;

    private static final ArrayList<String> borderChars = new ArrayList<>();
    @Getter
    private static int borderWidth = DEFAULT_BORDER_WIDTH;

    static {
        borderChars.add("━");
        borderChars.add("-");
        borderChars.add("*");
        borderChars.add("#");
    }

    public static void setBorderWidth(int width) {
        if (width > 0) {
            borderWidth = width;
        }
    }

    public static boolean setBorderCharIndex(int index) {
        if (index >= 0 && index < borderChars.size()) {
            borderCharIndex = index;
            return true;
        }
        return false;
    }

    public static void addBorderChar(String newChar) {
        if (newChar != null && !newChar.isEmpty() && !borderChars.contains(newChar)) {
            borderChars.add(newChar);
        }
    }

    public static void border() {
        String borderChar = borderChars.get(borderCharIndex);
        out.print(getAnsi256Color(systemLayoutColor) + borderChar);
        for (int i = 0; i < 115; i++) {
            out.print(getAnsi256Color(systemLayoutColor) + borderChar);
        }
        out.println(getAnsi256Color(systemLayoutColor) + borderChar);
    }

    public static void marginBorder(int upperSide, int lowerSide) {
        modifyMessage('n',upperSide);
        border();
        modifyMessage('n',lowerSide);
    }

    //Animations
    public static void loadingAnimation(int frames, int duration) {
        String[] spinner = {"    |", "    /", "    —", "    \\"};
        for (int i = 0; i < duration; i++) {
            out.print(getRandom256Color() + "\r" + spinner[i % spinner.length] + RESET);
            try {
                Thread.sleep(frames);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        out.print(getAnsi256Color(systemAcceptanceColor) + "\r    ✓" + RESET);
    }

    public static void progressbarAnimation(String title) {
        int barLength = 97;
        int animationDuration = 3000;
        int steps = 100;
        int delay = animationDuration / steps;

        for (int step = 0; step <= steps; step++) {
            double progress = step / (double) steps;
            int completed = (int) (progress * barLength);
            StringBuilder bar = new StringBuilder("\r" + alignment(64)
                    + getAnsi256Color(systemLayoutColor) + title + "[");

            for (int i = 0; i < barLength; i++) {
                bar.append(i < completed ? getRandom256Color() + borderChars.get(0) + getAnsi256Color(systemLayoutColor) : " ");
            }
            bar.append("] ");
            bar.append(String.format("%.2f%%", progress * 100));

            out.print(bar);

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        out.print("\r" + alignment(64) + " ".repeat(barLength + title.length() + 10) + "\r");
        out.print("\n".repeat(2));
    }
}