package ui.layout;

import static ui.layout.TextFunc.contentAlignment;

public class BorderFunc {
    public static int numberOfSymbols = 1;
    public static final int borderWidth = 62;
    public static String[] symbolsOfBorder = new String[]{"+", "-", "|", "*", "_", "~", "·"};

    public static void drawSingleBorder(int numberOfSymbol) {
        System.out.print(symbolsOfBorder[0]);
        for (int i = 0; i < borderWidth; i++) {
            System.out.print(symbolsOfBorder[numberOfSymbol]);
        }
        System.out.println(symbolsOfBorder[0]);
    }

    public static void drawTripleBorder() {
        drawSingleBorder(numberOfSymbols);
        System.out.println(symbolsOfBorder[2] + contentAlignment(-61) + symbolsOfBorder[2]);
        drawSingleBorder(numberOfSymbols);
    }
}
