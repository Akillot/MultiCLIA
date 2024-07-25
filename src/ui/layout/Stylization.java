package ui.layout;

import java.util.Random;

public class Stylization {

    //ANSI colors
    public static final String WHITE = "\033[0;38m";
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";

    //Show horizontal border
    public static int borderWidth = 21;

    //ANSI text styles
    public static final String BOLD = "\033[1m";
    public static final String UNDERLINE = "\033[4m";

    //Randomly picks a color
    static Color getRandomColor() {
        Random rand = new Random();
        int randomColorIndex = rand.nextInt(Color.values().length);
        return Color.values()[randomColorIndex];
    }

    //Get a user choice color
    static String getColoredText(String text, Color color) {
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

    public static void drawHorizontalBorder(int numberOfSymbol) {
        System.out.print(symbolsOfBorder[0]);
        for (int i = 0; i < borderWidth; i++) {
            System.out.print(symbolsOfBorder[numberOfSymbol]);
        }
        System.out.println(symbolsOfBorder[0]);
    }
    public static String[] symbolsOfBorder = new String[]{"+", "-", "|", "*", "_", "~", "·"};

    //Show colorful content with alignment
    public static void displayColorCommand(String text, String colorName, byte alignment) {
        Color color;
        try {
            color = Color.valueOf(colorName.toUpperCase());
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

    //Show transition border
    public static void transitionBorder() {
        System.out.println("|" + contentAlignment(-19) + "|");
    }

    //Show triple border
    public static void drawFullTripleBorder() {
        drawHorizontalBorder(numberOfSymbols);
        transitionBorder();
        drawHorizontalBorder(numberOfSymbols);
    }

    //Method to wrap the content
    public static void wrapText(String text, int width) {
        for (int i = 0; i < text.length(); i += width) {
            if (i == 0) {
                int end = Math.min(i + width, text.length());
                drawFullTripleBorder();
                System.out.print(BOLD + contentAlignment(text.length() + 2) + "·" + text.substring(i, end) + "·" + RESET);
                if (end < text.length()) {
                    System.out.println();
                }
            } else {
                int end = Math.min(i + width, text.length());
                System.out.print(BOLD + contentAlignment(text.length() + 2) + "·" + text.substring(i, end) + "·" + RESET);
                if (end < text.length()) {
                    System.out.println();
                }
            }
        }
        System.out.println();
    }

    enum Color {
        RED, YELLOW, GREEN, BLUE, PURPLE, CYAN, WHITE
    }

    //Show slow motion text
    public static void displaySlowMotionText(int delay, int alignment, boolean isUnderlineActive, String mainText, String additionalText) {
        String formattedText = contentAlignment(alignment) +
                (isUnderlineActive ? UNDERLINE : "") +
                BOLD + mainText + RESET + additionalText;

        for (char ch : formattedText.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                displayColorCommand("Error, try again", "red", (byte) 0);
            }
        }
        System.out.print("");
    }

    //Aligns the content
    public static String contentAlignment(int widthOfElement) {
        int fullWidth = borderWidth + 2; //Where 2 is "+"
        int oneSide = (fullWidth - widthOfElement) / 2;
        return " ".repeat(Math.max(0, oneSide));
    }

    public static int numberOfSymbols = 1;
}