package ui.layout;

import java.util.Random;

public class ColorFunc {

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

    static ColorFunc.Color getRandomColor() {
        Random rand = new Random();
        int randomColorIndex = rand.nextInt(ColorFunc.Color.values().length);
        return ColorFunc.Color.values()[randomColorIndex];
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

    public enum Color {
        RED, YELLOW, GREEN, BLUE, PURPLE, CYAN, WHITE, MAGENTA, RESET
    }

   /* public enum Color_Background {
        Red_Background, Yellow_Background, Green_Background,
        Blue_Background, Purple_Background, Cyan_Background,
        Magenta_Background, Black_Background
    }
    */
}
