package ui.layout;

import java.util.Random;

import static ui.layout.TextWork.contentAlignment;

public class ColorWork {

    //ANSI colors
    public static final String WHITE = "\033[0;38m";
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";

    //ANSI text styles
    public static final String BOLD = "\033[1m";
    public static final String UNDERLINE = "\033[4m";

    //Randomly picks a color
    static ColorWork.Color getRandomColor() {
        Random rand = new Random();
        int randomColorIndex = rand.nextInt(ColorWork.Color.values().length);
        return ColorWork.Color.values()[randomColorIndex];
    }

    //Get a user choice color
    static String getColoredText(String text, ColorWork.Color color) {
        String colorCode = switch (color) {
            case RED -> RED;
            case YELLOW -> YELLOW;
            case GREEN -> GREEN;
            case BLUE -> BLUE;
            case PURPLE -> PURPLE;
            case CYAN -> CYAN;
            case WHITE -> WHITE;
        };
        return colorCode + BOLD + text + RESET;
    }

    //Show colorful content with alignment
    public static void displayColorCommand(String text, String colorName, byte alignment) {
        ColorWork.Color color;
        try {
            color = ColorWork.Color.valueOf(colorName.toUpperCase());
        } catch (IllegalArgumentException e) {
            if (colorName.equalsIgnoreCase("randomly")) {
                color = getRandomColor();
            } else {
                displayColorCommand("Invalid input", "red", (byte) 0);
                return;
            }
        }

        String coloredText = getColoredText(text, color);
        int alignLength = alignment == 0 ? text.length() : alignment;

        if (alignment < 0) {
            displayColorCommand("Invalid input", "red", (byte) 0);
        } else {
            System.out.println(contentAlignment(alignLength) + coloredText);
        }
    }

    public enum Color {
        RED, YELLOW, GREEN, BLUE, PURPLE, CYAN, WHITE
    }
}
