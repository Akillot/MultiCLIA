package layout;

import java.util.Random;

import static layout.UserInterface.scanner;

public class Stylization {

    //COLORS//LOGO
    //ANSI colors
    //public static final String WHITE = "\033[0;38m";
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";

    //WRAPPING//ALIGNMENT//SLOW-MOTION//
    //Method to wrap the content
    public static void wrapText(String text, int width) {
        for (int i = 0; i < text.length(); i += width) {
            if (i == 0) {
                int end = Math.min(i + width, text.length());
                drawFullTripleBorder();
                System.out.print(BOLD + contentAlignment(text.length() + 2) + "·" + text.substring(i, end) + "·" + RESET);
                if (end < text.length()) {
                    System.out.println(); //Add newline after first segment
                }
            } else {
                int end = Math.min(i + width, text.length());
                System.out.print(BOLD + contentAlignment(text.length() + 2) + "·" + text.substring(i, end) + "·" + RESET);
                if (end < text.length()) {
                    System.out.println(); //Add newline after each wrapped line
                }
            }
        }
        System.out.println();
    }

    //Text styles
    public static final String BOLD = "\033[1m";
    public static final String UNDERLINE = "\033[4m";


    //BORDERS//
    //Show horizontal border
    public static String[] symbolsOfBorder = new String[]{"+", "-", "|", "*", "_", "~", "·"};
    public static int borderWidth = 21;
    public static int numberOfSymbols = 1;

    public static void drawHorizontalBorder(int numberOfSymbol) {
        System.out.print(symbolsOfBorder[0]);
        for (int i = 0; i < borderWidth; i++) {
            System.out.print(symbolsOfBorder[numberOfSymbol]);
        }
        System.out.println(symbolsOfBorder[0]);
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

    //Get a user choice color
    static String getColoredText(String text, Color color) {
        String colorCode = switch (color) {
            case RED -> RED;
            case YELLOW -> YELLOW;
            case GREEN -> GREEN;
            case BLUE -> BLUE;
            case PURPLE -> PURPLE;
            case CYAN -> CYAN;
        };
        return colorCode + BOLD + text + RESET;
    }

    //Randomly picks a color
    static Color getRandomColor() {
        Random rand = new Random();
        int randomColorIndex = rand.nextInt(Color.values().length);
        return Color.values()[randomColorIndex];
    }

    //Switching the logo
    public static void logoSwitcher() {

        String nameOfLogo = scanner.nextLine().toLowerCase();

        //IN PROCESS
        if (nameOfLogo.equalsIgnoreCase("default")) {
            displayDefaultLogo();
        } else if (nameOfLogo.equalsIgnoreCase("google")) {
            displayGoogleReferenceLogo();
        }
    }

    //Show a logo
    public static void displayDefaultLogo() {
        System.out.print(contentAlignment(11) +
                BOLD + "+---------+\n" +
                contentAlignment(11) + "|" +
                RED + BOLD + "M" + RESET + GREEN + BOLD + "u" + RESET +
                YELLOW + BOLD + "l" + RESET + BLUE + BOLD + "t" + RESET +
                PURPLE + BOLD + "i" + RESET + BOLD + "CLIA|\n" +
                contentAlignment(11) + "+---------+\n" + RESET);
    }

    //Show a Google reference logo
    public static void displayGoogleReferenceLogo() {
        System.out.print(contentAlignment(11) + "+---------+\n" +
                contentAlignment(11) + "|" +
                BLUE + "M" + RESET + RED + "u" + RESET + YELLOW + "l" + RESET +
                BLUE + "ti" + RESET + GREEN + "CL" + RESET + RED + "IA" + RESET + "|\n" +
                contentAlignment(11) + "+---------+\n");
    }

    //Show slow motion text
    public static void displaySlowMotionText(int delay, int alignment, boolean isUnderlineActive, String mainText, String additionalText) {
        //Construct the text with or without underline based on the isUnderlineActive parameter
        String formattedText = contentAlignment(alignment) +
                (isUnderlineActive ? UNDERLINE : "") +
                BOLD + mainText + RESET + additionalText;

        //Gradually display the text with a delay
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

    enum Color {
        RED, YELLOW, GREEN, BLUE, PURPLE, CYAN
    }
}