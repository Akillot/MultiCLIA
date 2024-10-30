package core.layout;

import static core.layout.TextFunc.alignment;
import static java.lang.System.out;

public class BorderFunc {
    public static int numberOfSymbols = 1;
    public static final int borderWidth = 62;
    private static String[] symbolsOfBorder = new String[]{"+", "-", "|", "*", "_", "~", "·"};

    public static void displaySmallBorder(int numberOfSymbol) {
        out.print(symbolsOfBorder[0]);
        for (int i = 0; i < borderWidth; i++) {
            out.print(symbolsOfBorder[numberOfSymbol]);
        }
        out.println(symbolsOfBorder[0]);
    }

    public static void displayBigBorder() {
        displaySmallBorder(numberOfSymbols);
        out.println(symbolsOfBorder[2] + alignment(-61) + symbolsOfBorder[2]);
        displaySmallBorder(numberOfSymbols);
    }

    public static void displayMarginBigBorder() {
        out.print("\n");
        displayBigBorder();
        out.print("\n");
    }
}
