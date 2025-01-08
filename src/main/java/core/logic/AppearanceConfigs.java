package core.logic;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static core.logic.TextConfigs.alignment;
import static core.logic.TextConfigs.modifyMessage;
import static java.lang.System.out;

public class AppearanceConfigs {

    //Colors
    public static int systemMainColor = 147;
    public static int systemLayoutColor = 15;

    public static int systemAcceptanceColor = 46;
    public static int systemRejectionColor = 196;

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
    public static final int borderWidth = 62;
    public static int borderChar = 0;

    private static String[] symbolsOfBorder = new String[]{"━"};

    public static void border() {
        out.print(getAnsi256Color(systemLayoutColor) + symbolsOfBorder[borderChar]);
        for (int i = 0; i < 115; i++) {
            out.print(symbolsOfBorder[borderChar]);
        }
        out.println(symbolsOfBorder[borderChar]);
    }

    public static void marginBorder(int upperSide, int lowerSide) {
        modifyMessage('n', upperSide);
        border();
        modifyMessage('n', lowerSide);
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
                bar.append(i < completed ? getRandom256Color() + "━" + getAnsi256Color(systemLayoutColor) : " ");
            }
            bar.append("] ");
            bar.append(String.format("%.2f%%", progress * 100));

            System.out.print(bar);

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.print("\r" + alignment(64) + " ".repeat(barLength + title.length() + 10) + "\r");
        out.print("\n".repeat(2));
    }
}