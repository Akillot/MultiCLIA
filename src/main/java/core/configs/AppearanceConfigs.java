package core.configs;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

import static core.configs.TextConfigs.*;
import static java.lang.System.out;

public class AppearanceConfigs {

    //Delay
    @Getter @Setter
    private static int defaultDelay = 0;

    //Alignment
    @Getter @Setter
    private static int searchingLineAlignment = 48;

    @Getter @Setter
    static int defaultTextAlignment = 58;

    @Getter @Setter
    private static int defaultLogoAlignment = 48;

    public static String searchingArrow = "-> ";

    //Colors of logo

    public static int color1 = 219;
    public static int color2 = 183;
    public static int color3 = 147;
    public static int color4 = 218;
    public static int color5 = 182;
    public static int color6 = 218;

    //System Colors
    public static int sysMainColor = 147;
    public static int sysLayoutColor = 15;
    public static int sysAcceptanceColor = 46;
    public static int sysRejectionColor = 160;

    //Borders
    public static final int DEFAULT_BORDER_WIDTH = 62;

    public static final String RESET = "\033[0m";
    public static final String UNDERLINE = "\033[4m";

    @Contract(pure = true)
    public static @NotNull String getColorText(String text, int color) {
        if (color < 0 || color > 255) {
            throw new IllegalArgumentException("Color code must be between 0 and 255");
        }
        return getColor(color) + text + RESET;
    }

    @Contract(pure = true)
    public static @NotNull String getColor(int colorCode) {
        return "\033[38;5;" + colorCode + "m";
    }

    @Contract(pure = true)
    public static @NotNull String getBackColor(int colorCode) {
        return "\033[48;5;" + colorCode + "m";
    }

    public static @NotNull String getRandomColor() {
        Random rand = new Random();
        int colorCode = rand.nextInt(256);
        return getColor(colorCode);
    }

    public static @NotNull String getRandomBackColor() {
        Random rand = new Random();
        int colorCode = rand.nextInt(256);
        return getBackColor(colorCode);
    }

    //Border
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
        return index >= 0 && index < borderChars.size();
    }

    public static void addBorderChar(String newChar) {
        if (newChar != null && !newChar.isEmpty() && !borderChars.contains(newChar)) {
            borderChars.add(newChar);
        }
    }

    public static void border() {
        message("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━",
                sysLayoutColor,62,0,out::print);
    }

    public static void marginBorder(int upperSide, int lowerSide) {
        insertControlChars('n',upperSide);
        border();
        insertControlChars('n',lowerSide);
    }

    //Animations
    public static void loadingAnimation(int frames, int duration) {
        String[] spinner = {"    |", "    /", "    —", "    \\"};
        for (int i = 0; i < duration; i++) {
            out.print(getRandomColor() + "\r" + spinner[i % spinner.length] + RESET);
            try {
                Thread.sleep(frames);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        out.print(getColor(sysAcceptanceColor) + "\r    ✓" + RESET);
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
                    + getColor(sysLayoutColor) + title + "[");

            for (int i = 0; i < barLength; i++) {
                bar.append(i < completed ? getRandomColor() + borderChars.get(0) + getColor(sysLayoutColor) : " ");
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