package core.layout;

import static core.layout.TextFunc.alignment;

public class BorderFunc {
    public static int numberOfSymbols = 1;
    public static final int borderWidth = 62;
    private static String[] symbolsOfBorder = new String[]{"+", "-", "|", "*", "_", "~", "·"};

    public static void displaySmallBorder(int numberOfSymbol) {
        System.out.print(symbolsOfBorder[0]);
        for (int i = 0; i < borderWidth; i++) {
            System.out.print(symbolsOfBorder[numberOfSymbol]);
        }
        System.out.println(symbolsOfBorder[0]);
    }

    public static void displayBigBorder() {
        displaySmallBorder(numberOfSymbols);
        System.out.println(symbolsOfBorder[2] + alignment(-61) + symbolsOfBorder[2]);
        displaySmallBorder(numberOfSymbols);
    }

    public static void displayMarginBigBorder() {
        System.out.print("\n");
        displayBigBorder();
        System.out.print("\n");
    }
}
