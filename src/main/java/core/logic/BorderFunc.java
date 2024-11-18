package core.logic;

import static core.logic.DisplayManager.messageModifier;
import static java.lang.System.out;

public class BorderFunc {
    public static final int borderWidth = 62;
    private static String[] symbolsOfBorder = new String[]{"+", "-", "|", "*", "_", "~", "·", "━"};

    public static void border() {
        out.print(symbolsOfBorder[7]);
        for (int i = 0; i < borderWidth; i++) {
            out.print(symbolsOfBorder[7]);
        }
        out.println(symbolsOfBorder[7]);
    }

    public static void marginBorder() {
        messageModifier('n', 1);
        border();
        messageModifier('n', 1);
    }
}