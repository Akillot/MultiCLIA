package core.ui.essential.configs.appearance;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

import static core.ui.essential.configs.appearance.TextConfigs.*;
import static java.lang.System.out;

public class AppearanceConfigs {

    //Delay
    @Getter @Setter
    private static int defaultDelay = 0;

    //Alignment
    @Getter @Setter
    private static int searchingLineAlignment = 48;

    @Getter @Setter
    private static int defaultTextAlignment = 58;

    @Getter @Setter
    private static int defaultLogoAlignment = 48;

    public static String searchingArrow = "> ";

    //Colors of logo
    @Getter @Setter
    public static int color1 = 219;
    @Getter @Setter
    public static int color2 = 183;
    @Getter @Setter
    public static int color3 = 147;
    @Getter @Setter
    public static int color4 = 218;
    @Getter @Setter
    public static int color5 = 182;
    @Getter @Setter
    public static int color6 = 218;

    //System Colors
    @Getter @Setter
    public static int mainColor = 147;
    @Getter @Setter
    public static int layoutColor = 15;
    @Getter @Setter
    public static int acceptanceColor = 46;
    @Getter @Setter
    public static int rejectionColor = 160;

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
                layoutColor,62,0,out::print);
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
        out.print(getColor(acceptanceColor) + "\r    ✓" + RESET);
    }

    //Color table components
    @Contract(pure = true)
    public static void displayColorTable() {
        insertControlChars('n',1);
        message("Color Table:", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        printColorRange(0, layoutColor);
        printColorBlock();
        printColorRange(232, 255);
    }

    @Contract(pure = true)
    private static void printColorRange(int start, int end) {
        out.println(RESET);
        for (int i = start; i <= end; i++) {
            out.print(getColor(layoutColor) + getBackColor(i)
                    + tableAlignment() + " " + i + " " + RESET);
            if ((i - start + 1) % 8 == 0) insertControlChars('n',2);
        }
    }

    @Contract(pure = true)
    private static void printColorBlock() {
        int columns = 6;
        int rows = (231 - 16 + 1) / columns;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int colorCode = 16 + row + col * rows;
                out.print(getColor(layoutColor) + getBackColor(colorCode)
                        + tableAlignment() + " " + colorCode + " " + RESET);
            }
            if(row == 11){
                insertControlChars('n',1);
            }
            insertControlChars('n', 1);
        }
    }

    @Contract(pure = true)
    private static @NotNull String tableAlignment() {
        return " ".repeat(Math.max(0, 4));
    }
}