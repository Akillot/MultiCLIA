package core.layout;

import java.util.Random;

import static core.layout.TextFunc.alignment;
import static java.lang.System.out;

public class ColorFunc {

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

    public static Color getRandomColor() {
        Random rand = new Random();
        int randomColorIndex = rand.nextInt(Color.values().length);
        return Color.values()[randomColorIndex];
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
            case GRAY -> GRAY;
            case BLACK -> BLACK;
            case RESET -> RESET;
        };
        return colorCode + BOLD + text + RESET;
    }

    public enum Color {
        RED, YELLOW, GREEN, BLUE, PURPLE, CYAN, WHITE, GRAY, BLACK, RESET
    }

    public static void getRgbText(String text, String textColorCode, String backColorCode) {
        String rgbCodeText = "\033[38;2;"+ textColorCode +"m";
        String rgbCodeBack = "\033[48;2;" + backColorCode + "m";
        String resetColor = "\033[0m";
        out.print(alignment(58) + (rgbCodeBack + rgbCodeText + text + resetColor));
    }
}