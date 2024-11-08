package core.logic;

import static core.logic.DisplayManager.messageModifier;
import static core.logic.TextFunc.alignment;
import static java.lang.System.out;

public class BorderFunc {
    public static int numberOfSymbols = 1;
    public static final int borderWidth = 62;
    private static String[] symbolsOfBorder = new String[]{"+", "-", "|", "*", "_", "~", "·"};

    public static void smallBorder(int numberOfSymbol) {
        out.print(symbolsOfBorder[0]);
        for (int i = 0; i < borderWidth; i++) {
            out.print(symbolsOfBorder[numberOfSymbol]);
        }
        out.println(symbolsOfBorder[0]);
    }

    public static void bigBorder() {
        smallBorder(numberOfSymbols);
        out.println(symbolsOfBorder[2] + alignment(-61) + symbolsOfBorder[2]);
        smallBorder(numberOfSymbols);
    }

    public static void marginBigBorder() {
        messageModifier('n', 1);
        bigBorder();
        messageModifier('n', 1);
    }
}
