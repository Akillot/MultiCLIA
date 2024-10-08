package ui.layout;

import java.util.Random;

import static ui.layout.TextWork.contentAlignment;
import static ui.layout.ThemesWork.displayErrorAscii;

public class ColorWork {

    public static final String WHITE = "\033[0;38m";
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String MAGENTA = "\u001B[35m";

    public static final String BOLD = "\033[1m";
    public static final String UNDERLINE = "\033[4m";

    static ColorWork.Color getRandomColor() {
        Random rand = new Random();
        int randomColorIndex = rand.nextInt(ColorWork.Color.values().length);
        return ColorWork.Color.values()[randomColorIndex];
    }

    static String getColoredText(String text, Color color) {
        String colorCode = switch (color) {
            case RED -> RED;
            case YELLOW -> YELLOW;
            case GREEN -> GREEN;
            case BLUE -> BLUE;
            case PURPLE -> PURPLE;
            case CYAN -> CYAN;
            case WHITE -> WHITE;
            case MAGENTA -> MAGENTA;
            case RESET -> RESET;
        };
        return colorCode + BOLD + text + RESET;
    }

    public static void displayContent(String text, String colorName, int alignment) {
        ColorWork.Color color;
        try {
            color = ColorWork.Color.valueOf(colorName.toUpperCase());
        } catch (IllegalArgumentException e) {
            if (colorName.equalsIgnoreCase("randomly")) {
                color = getRandomColor();
            } else {
                displayErrorAscii();
                displayContent("Invalid input", "red", 0);
                return;
            }
        }

        String coloredText = getColoredText(text, color);
        int alignLength = alignment == 0 ? text.length() : alignment;

        if (alignment < 0) {
            displayErrorAscii();
            displayContent("Invalid input", "red", 0);
        } else {
            System.out.println(contentAlignment(alignLength) + coloredText);
        }
    }

    public enum Color {
        RED, YELLOW, GREEN, BLUE, PURPLE, CYAN, WHITE, MAGENTA, RESET
    }

    public enum Color_Background {
        Red_Background, Yellow_Background, Green_Background,
        Blue_Background, Purple_Background, Cyan_Background,
        Magenta_Background, Black_Background
    }
}
