package core.configs;

import core.logic.JsonManager;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.List;

import static core.configs.TextConfigs.*;
import static java.lang.System.out;

public class AppearanceConfigs {

    private static final String CONFIG_PATH = "config.json";

    // Alignment
    @Getter @Setter
    private static int searchingLineAlignment = JsonManager.getInt("/alignment/searchingLineAlignment");

    @Getter @Setter
    private static int defaultTextAlignment = JsonManager.getInt("/alignment/defaultTextAlignment");

    @Getter @Setter
    private static int defaultLogoAlignment = JsonManager.getInt("/alignment/defaultLogoAlignment");

    public static String searchingArrow = "-> ";

    // Colors
    @Getter @Setter
    public static int sysMainColor = JsonManager.getInt("/colors/sysMainColor");

    @Getter @Setter
    public static int sysLayoutColor = JsonManager.getInt("/colors/sysLayoutColor");

    @Getter @Setter
    public static int sysAcceptanceColor = JsonManager.getInt("/colors/sysAcceptanceColor");

    @Getter @Setter
    public static int sysRejectionColor = JsonManager.getInt("/colors/sysRejectionColor");

    // Borders
    public static final int DEFAULT_BORDER_WIDTH = JsonManager.getInt("/borders/defaultBorderWidth");

    private static List<String> borderChars = JsonManager.getStringList("/borders/borderChars");

    public static final String RESET = "\033[0m";
    public static final String UNDERLINE = "\033[4m";

    // Save method
    public static void saveConfig() {
        JsonManager.updateJson("/alignment/searchingLineAlignment", searchingLineAlignment);
        JsonManager.updateJson("/alignment/defaultTextAlignment", defaultTextAlignment);
        JsonManager.updateJson("/alignment/defaultLogoAlignment", defaultLogoAlignment);
        JsonManager.updateJson("/colors/sysMainColor", sysMainColor);
        JsonManager.updateJson("/colors/sysLayoutColor", sysLayoutColor);
        JsonManager.updateJson("/colors/sysAcceptanceColor", sysAcceptanceColor);
        JsonManager.updateJson("/colors/sysRejectionColor", sysRejectionColor);
        JsonManager.updateJson("/borders/defaultBorderWidth", DEFAULT_BORDER_WIDTH);
    }


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

    // Border Methods
    @Getter
    private static int borderWidth = DEFAULT_BORDER_WIDTH;

    public static void setBorderWidth(int width) {
        if (width > 0) {
            borderWidth = width;
            saveConfig();
        }
    }

    public static boolean setBorderCharIndex(int index) {
        return index >= 0 && index < borderChars.size();
    }

    public static void addBorderChar(String newChar) {
        if (newChar != null && !newChar.isEmpty() && !borderChars.contains(newChar)) {
            borderChars.add(newChar);
            saveConfig();
        }
    }

    public static void border() {
        message("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━",
                sysLayoutColor, 62, 0, out::print);
    }

    public static void marginBorder(int upperSide, int lowerSide) {
        insertControlChars('n', upperSide);
        border();
        insertControlChars('n', lowerSide);
    }

    // Animations
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


        if (borderChars.isEmpty()) {
            borderChars = List.of("━", "-", "*", "#");
        }

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
