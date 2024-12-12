package core.logic;

import static core.logic.ColorConfigs.getAnsi256Color;
import static core.logic.ColorConfigs.systemDefaultWhite;
import static core.logic.TextConfigs.modifyMessage;
import static java.lang.System.out;

public class BorderConfigs {
    public static final int borderWidth = 62;
    public static int borderChar = 0;

    private static String[] symbolsOfBorder = new String[]{"━"};

    public static void border() {
        out.print(getAnsi256Color(systemDefaultWhite) + symbolsOfBorder[borderChar]);
        for (int i = 0; i < 115; i++) {
            out.print(symbolsOfBorder[borderChar]);
        }
        out.println(symbolsOfBorder[borderChar]);
    }

    public static void marginBorder() {
        modifyMessage('n', 1);
        border();
        modifyMessage('n', 1);
    }
}